package org.dongguk.userapi.adapter.out.persistence;

import org.dongguk.userapi.application.port.out.FindTagNamesPort;
import org.dongguk.userapi.dto.response.TagDto;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.utility.InternalHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
public class FindTagPersistenceAdapter implements FindTagNamesPort {
    private final InternalHttpClient httpClient;
    private final JsonParser jsonParser;

    public FindTagPersistenceAdapter() {
        this.httpClient = new InternalHttpClient(new RestTemplate());
        this.jsonParser = new JsonParser();
    }

    @Override
    public List<TagDto> findAll(List<Long> ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);

        String url = "http://tag-api:8080/tags";

        if (ids != null && !ids.isEmpty()) {
            url += "?ids=" + ids.stream()
                    .map(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");
        }

        List<TagDto> tagDtos = new ArrayList<>();

        try {
            JsonArray tagNames = jsonParser.parse(
                    (String) httpClient.sendGetRequest(
                            url,
                            request,
                            String.class))
                    .getAsJsonObject().get("data").getAsJsonArray();

            for (int i = 0; i < tagNames.size(); i++) {
                JsonObject tag = tagNames.get(i).getAsJsonObject();

                tagDtos.add(TagDto.builder()
                        .id(tag.get("id").getAsLong())
                        .name(tag.get("name").getAsString())
                        .build());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tagDtos;
    }
}
