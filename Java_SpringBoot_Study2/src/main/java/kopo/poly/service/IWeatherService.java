package kopo.poly.service;

import kopo.poly.dto.WeatherDTO;

public interface IWeatherService {
    WeatherDTO toDayWeather() throws Exception;

}
