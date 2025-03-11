package com.example.weather_api.service;

import com.example.weather_api.dto.WeatherResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

  private static final String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/japan";

  @Value("${weather.api.key}")
  private String apiKey;

  public WeatherResponse getWeather() {
    RestTemplate restTemplate = new RestTemplate();

    String url = UriComponentsBuilder.fromUriString(API_URL)
        .queryParam("key", apiKey)
        .queryParam("unitGroup", "metric")
        .queryParam("include", "current")
        .queryParam("contentType", "json")
        .toUriString();

    ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {
    };

    ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
        null, responseType);

    Map<String, Object> currentConditions = getStringObjectMap(responseEntity);

    double temperature = (double) currentConditions.get("temp");
    double humidity = (double) currentConditions.get("humidity");
    double windSpeed = (double) currentConditions.get("windspeed");

    return new WeatherResponse(temperature, humidity, windSpeed);
  }

  private static Map<String, Object> getStringObjectMap(
      ResponseEntity<Map<String, Object>> responseEntity) {
    Map<String, Object> response = responseEntity.getBody();

    if (response == null || !response.containsKey("currentConditions")) {
      throw new RuntimeException("Invalid API response");
    }

    Object conditionObj = response.get("currentConditions");

    if (!(conditionObj instanceof Map)) {
      throw new RuntimeException("Invalid data format: currentConditions is not a map");
    }

    // 上でチェックはしているので警告は抑止する
    @SuppressWarnings("unchecked") Map<String, Object> currentConditions = (Map<String, Object>) conditionObj;
    return currentConditions;
  }
}
