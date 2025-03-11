package com.example.weather_api.dto;

public record WeatherResponse (
    double temperature,
    double humidity,
    double windSpeed
){

}
