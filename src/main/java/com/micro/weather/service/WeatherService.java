package com.micro.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.weather.dto.WeatherDTO;
import com.micro.weather.dto.WeatherResponse;
import com.micro.weather.model.WeatherEntity;
import com.micro.weather.repository.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {

    private static final String API_URL = "http://api.weatherstack.com/current?access_key=da9f18823bc296bd66aded122241c128&query=";

    private final WeatherRepository weatherRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    // returns db data
    public WeatherDTO getWeatherByCityName(String city) {

        Optional<WeatherEntity> weatherEntityOptional = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(city);

        // if there is no available data on db fetch from the weatherstack

        return weatherEntityOptional.map(
                weatherEntity -> {
                    if (weatherEntity.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))) {
                        return WeatherDTO.convert(getWeatherFromWeatherStack(city));
                    } return WeatherDTO.convert(weatherEntity);
                }
        ).orElseGet(() -> WeatherDTO.convert(getWeatherFromWeatherStack(city)));
    }


    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_URL + city, String.class);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeatherEntity(city, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // crate new weather entity and save to db
    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        WeatherEntity weatherEntity = new WeatherEntity(
                city,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localTime(), dateTimeFormatter));

        return weatherRepository.save(weatherEntity);
    }
}
