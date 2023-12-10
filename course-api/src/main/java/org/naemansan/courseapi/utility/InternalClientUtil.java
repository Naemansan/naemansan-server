package org.naemansan.courseapi.utility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.NoArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.dto.common.UserNameDto;
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
public class InternalClientUtil {
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final HttpHeaders headers = new HttpHeaders();

    public List<String> getTagNames(List<Long> tagIds) {
        // Header 설정
        headers.clear();
        headers.add("Content-Type", "application/json");

        // Body 설정
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

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.BAD_GATEWAY);
        }

        // 후처리 후 반환
        List<String> names = new ArrayList<>();
        JsonArray jsonArray = gson.fromJson(response.getBody(), JsonObject.class)
                .getAsJsonArray("data");

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            names.add(jsonObject.get("name").getAsString());
        }

        return names;
    }

    public UserNameDto getUserName(String uuid) {
        // Header 설정
        headers.clear();
        headers.add("Content-Type", "application/json");

        // Body 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);

        String url = "http://user-api:8080/internal-users/" + uuid + "/name";

        // 요청
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.BAD_GATEWAY);
        }

        // 후처리 후 반환
        JsonObject jsonObject = gson.fromJson(response.getBody(), JsonObject.class)
                .getAsJsonObject("data");

        return UserNameDto.of(
                jsonObject.get("uuid").getAsString(),
                jsonObject.get("nickname").getAsString(),
                jsonObject.get("profileImageUrl").getAsString()
        );
    }

    public List<UserNameDto> getUserNames(List<String> userIds) {
        // Header 설정
        headers.clear();
        headers.add("Content-Type", "application/json");

        // Body 설정
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

        // 요청
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.BAD_GATEWAY);
        }

        // 후처리 후 반환
        List<UserNameDto> names = new ArrayList<>();

        JsonArray jsonArray = gson.fromJson(response.getBody(), JsonObject.class)
                .getAsJsonArray("data");

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            names.add(UserNameDto.of(
                    jsonObject.get("uuid").getAsString(),
                    jsonObject.get("nickname").getAsString(),
                    jsonObject.get("profileImageUrl").getAsString()
            ));
        }

        return names;
    }
}
