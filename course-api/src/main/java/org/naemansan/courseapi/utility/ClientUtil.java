package org.naemansan.courseapi.utility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.common.TagDto;
import org.naemansan.courseapi.dto.common.UserNameDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class ClientUtil {
    @Value("${naver-api.map.url}")
    private String NAVER_MAP_URL;

    @Value("${naver-api.map.client-id}")
    private String CLIENT_ID;

    @Value("${naver-api.map.client-secret}")
    private String CLIENT_SECRET;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final HttpHeaders headers = new HttpHeaders();

    public String getLocationName(LocationDto location) {
        headers.clear();
        headers.add("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        headers.add("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

        StringBuilder sb = new StringBuilder();
        sb.append(NAVER_MAP_URL);
        sb.append("?coords=").append(location.toString());
        sb.append("&output=json");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                request,
                String.class
        );

        JsonObject jsonObject = gson
                .fromJson(response.getBody(), JsonObject.class)
                .getAsJsonArray("results").get(0).getAsJsonObject()
                .getAsJsonObject("region");

        String siName = jsonObject.getAsJsonObject("area1").get("name").getAsString();
        String guName = jsonObject.getAsJsonObject("area2").get("name").getAsString();
        String dongName = jsonObject.getAsJsonObject("area3").get("name").getAsString();

        return siName + " " + guName + " " + dongName;
    }

    public List<String> getTagNames(List<Long> tagIds) {
        headers.clear();
        headers.add("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);

        String url = "http://tag-api:8080/tags";

        if (tagIds != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(url).append("?ids=");

            for (int i = 0; i < tagIds.size(); i++) {
                sb.append(tagIds.get(i));

                if (i != tagIds.size() - 1) {
                    sb.append(",");
                }
            }

            url = sb.toString();
        }

        List<String> names = new ArrayList<>();

        try {
            JsonArray jsonArray = gson.fromJson(
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            request,
                            String.class).getBody(),
                    JsonObject.class).getAsJsonArray("data");

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                names.add(jsonObject.get("name").getAsString());
            }

            return names;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserNameDto> getUserNames(List<String> userIds) {
        headers.clear();
        headers.add("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);

        String url = "http://user-api:8080/internal-users/name";

        if (userIds != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(url).append("?userIds=");

            for (int i = 0; i < userIds.size(); i++) {
                sb.append(userIds.get(i));

                if (i != userIds.size() - 1) {
                    sb.append(",");
                }
            }

            url = sb.toString();
        }

        List<UserNameDto> names = new ArrayList<>();

        try {
            JsonArray jsonArray = gson.fromJson(
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            request,
                            String.class).getBody(),
                    JsonObject.class).getAsJsonArray("data");

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                names.add(UserNameDto.of(
                        jsonObject.get("uuid").getAsString(),
                        jsonObject.get("nickname").getAsString(),
                        jsonObject.get("profileImageUrl").getAsString()
                ));
            }

            return names;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserNameDto getUserName(String uuid) {
        headers.clear();
        headers.add("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);

        String url = "http://user-api:8080/internal-users/" + uuid + "/name";

        try {
            JsonObject jsonObject = gson.fromJson(
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            request,
                            String.class).getBody(),
                    JsonObject.class).getAsJsonObject("data");

            System.out.println(jsonObject.toString());

            return UserNameDto.of(
                    jsonObject.get("uuid").getAsString(),
                    jsonObject.get("nickname").getAsString(),
                    jsonObject.get("profileImageUrl").getAsString()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
