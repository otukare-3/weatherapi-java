package com.example.weather_api.dto;

import java.io.Serializable;

public record WeatherResponse (
    double temperature,
    double humidity,
    double windSpeed
) implements Serializable {

}
