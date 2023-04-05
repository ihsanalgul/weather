package com.micro.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// If response cannot be welcome, unknown properties can be ignorable, if they are not necessary
@JsonIgnoreProperties(ignoreUnknown = true)
public record Current(
        Integer temperature) {
}
