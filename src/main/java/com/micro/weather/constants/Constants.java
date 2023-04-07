package com.micro.weather.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static String API_URL;

    public static String ACCESS_KEY_PARAM="?access_key=";
    public static String API_KEY="";

    public static String QUERY_PARAM="&query=";

    // This can read application.yaml before spring context upload
    @Value("${weather-stack.API_URL}")
    public void setApiUrl(String API_URL) {
        Constants.API_URL=API_URL;
    }

    @Value("${weather-stack.API_KEY}")
    public void setApiKey(String API_KEY) {
        Constants.API_KEY = API_KEY;
    }

    public void setAccessKeyParam(String accessKeyParam) {
        ACCESS_KEY_PARAM = accessKeyParam;
    }

    public void setQueryParam(String queryParam) {
        QUERY_PARAM = queryParam;
    }
}
