package com.micro.weather.dto;

import com.micro.weather.model.WeatherEntity;

public record WeatherDTO(
        String cityName,
        String country,
        Integer temperature
) {
    public static WeatherDTO convert(WeatherEntity from) {
        return new WeatherDTO(from.getCityName(), from.getCountry(), from.getTemperature());
    }
}
