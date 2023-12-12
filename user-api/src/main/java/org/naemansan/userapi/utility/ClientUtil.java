package org.naemansan.userapi.utility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.NoArgsConstructor;
import org.naemansan.userapi.dto.response.TagDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class ClientUtil {
    @Value("${internal-service.course-api}")
    private String COURSE_API_URL;
    @Value("${internal-service.tag-api}")
    private String TAG_API_URL;
    @Value("${internal-service.auth-api}")
    private String AUTH_API_URL;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final HttpHeaders headers = new HttpHeaders();

    public List<TagDto> getTagDtos(List<Long> tagIds) {
        headers.clear();
        headers.add("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);

        String url = String.format("%s/tags", TAG_API_URL);

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

        List<TagDto> tagDtos = new ArrayList<>();

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
                tagDtos.add(TagDto.builder()
                        .id(jsonObject.get("id").getAsLong())
                        .name(jsonObject.get("name").getAsString())
                        .build());
            }

            return tagDtos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
