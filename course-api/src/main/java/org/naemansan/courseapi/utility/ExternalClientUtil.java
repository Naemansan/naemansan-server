package org.naemansan.courseapi.utility;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.type.EWeather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExternalClientUtil {
    private final AmazonS3 s3Client;

    @Value("${naver-api.map.url}")
    private String NAVER_MAP_URL;

    @Value("${open-weather.api.url}")
    private String OPEN_WEATHER_API_URL;

    @Value("${open-weather.api.key}")
    private String OPEN_WEATHER_API_KEY;

    @Value("${naver-api.map.client-id}")
    private String CLIENT_ID;

    @Value("${naver-api.map.client-secret}")
    private String CLIENT_SECRET;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_PATH;

    @Value("${cloud.aws.s3.url}")
    private String CLOUD_URL;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final HttpHeaders headers = new HttpHeaders();

    public String getLocationName(LocationDto location) {
        // Header 설정
        headers.clear();
        headers.add("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        headers.add("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

        StringBuilder sb = new StringBuilder();
        sb.append(NAVER_MAP_URL);
        sb.append("?coords=").append(location.toString());
        sb.append("&output=json");

        // Body 설정
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // 요청
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET,
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new CommonException(ErrorCode.BAD_GATEWAY);
        }

        // 후처리 후 반환
        JsonObject jsonObject = gson
                .fromJson(response.getBody(), JsonObject.class)
                .getAsJsonArray("results").get(0).getAsJsonObject()
                .getAsJsonObject("region");

        String siName = jsonObject.getAsJsonObject("area1").get("name").getAsString();
        String guName = jsonObject.getAsJsonObject("area2").get("name").getAsString();
        String dongName = jsonObject.getAsJsonObject("area3").get("name").getAsString();

        return siName + " " + guName + " " + dongName;
    }

    public EWeather getWeather(LocationDto location) {
        // Header 설정
        headers.clear();

        StringBuilder sb = new StringBuilder();
        sb.append(OPEN_WEATHER_API_URL);
        sb.append("?lat=").append(location.latitude());
        sb.append("&lon=").append(location.longitude());
        sb.append("&appid=").append(OPEN_WEATHER_API_KEY);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        // Body 설정
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // 요청
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET,
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new CommonException(ErrorCode.BAD_GATEWAY);
        }

        // 후처리 후 반환
        int code = gson
                .fromJson(response.getBody(), JsonObject.class)
                .getAsJsonArray("weather").get(0).getAsJsonObject()
                .get("id").getAsInt();

        LocalTime now = LocalTime.now();

        return switch (code / 100) {
            case 2, 3, 5 -> EWeather.RAIN;
            case 6 -> EWeather.SNOW;
            case 7 -> EWeather.CLOUDY;
            case 8 -> {
                if (code == 800) {
                    if (now.isAfter(LocalTime.of(6, 0)) && now.isBefore(LocalTime.of(18, 0))) {
                        yield EWeather.SUNNY_DAY;
                    } else {
                        yield EWeather.SUNNY_NIGHT;
                    }
                } else if (code == 801 || code == 802) {
                    if (now.isAfter(LocalTime.of(6, 0)) && now.isBefore(LocalTime.of(18, 0))) {
                        yield EWeather.CLOUDY_DAY;
                    } else {
                        yield EWeather.CLOUDY_NIGHT;
                    }
                } else {
                    yield EWeather.CLOUDY;
                }
            }
            default -> throw new CommonException(ErrorCode.BAD_GATEWAY);
        };
    }

    public Map<String, String> getPreSignedUrl(String dirName, String fileType) {
        String filePath = String.format("%s/%s.%s", dirName, UUID.randomUUID(), fileType);

        GeneratePresignedUrlRequest urlRequest = getGeneratePreSignedUrlRequest(BUCKET_PATH, filePath, fileType);
        URL url = s3Client.generatePresignedUrl(urlRequest);
        return Map.of(
                "preSignedUrl", url.toString(),
                "fileUrl", String.format("%s/%s", CLOUD_URL, filePath)
        );
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String filePath, String fileType) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, filePath)
                        .withMethod(com.amazonaws.HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());

        // ACL을 public-read로 지정
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
