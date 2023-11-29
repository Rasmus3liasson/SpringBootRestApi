package com.example.springbootrestapi.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Component
public class ResponseMapper {
    private final ObjectMapper objectMapper;

    public ResponseMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public <T> ResultMatcher convertJsonToObject(Object object, Class<T> targetClass) {
        return result -> {
            String json = result.getResponse().getContentAsString();
            T convertedObject = objectMapper.readValue(json, targetClass);
            assertThat(convertedObject).usingRecursiveComparison().isEqualTo(object);
        };
    }

    public ResultMatcher containsError(String expectedTitle, String expectedMessage) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            Map<String, Object> errResult = objectMapper.readValue(json, Map.class);
            String title = (String) errResult.get("title");
            String description = (String) errResult.get("detail");

            assertThat(title).isEqualTo(expectedTitle);
            assertThat(description).isEqualTo(expectedMessage);
        };
    }

}
