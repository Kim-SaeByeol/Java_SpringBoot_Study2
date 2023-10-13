package kopo.poly.service.impl;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService implements IWeatherService {
    @Override
    public WeatherDTO toDayWeather() throws Exception {

        log.info(this.getClass().getName() + ".toDayWeather Start!");

        // WeatherDTO 객체 생성
        WeatherDTO pDTO = new WeatherDTO();

        // 크롤링 할 사이트 주소. => 강서캠퍼스 식단표
        String url = "https://weather.naver.com/";

        // JSOP 라이브러리를 통해 사이트에 접속되면, 그 사이트의 전체 HTML 소스를 저장할 변수.
        Document doc = null;

        // 사이트 접속(http 프로토콜만 가능 https는 보안상 안됨)
        doc = Jsoup.connect(url).get();

        // F12를 눌러서 나온 필요한 구역을 스크랩.
        Elements elements = doc.select("div.today_weather");

        // 현재 장소
        pDTO.setLocation_name(CmmUtil.nvl(elements.select("div.location_info_area strong.location_name").text().trim()));


        // 온도
        pDTO.setTemperature(CmmUtil.nvl(elements.select("div.weather_area div.weather_now div.summary_img strong.current").text().substring(5, 10)));

        // 날씨
        pDTO.setWeather(CmmUtil.nvl(elements.select("div.weather_area div.weather_now p.summary span.weather").text().trim()));

        // 들어온 값 확인
        log.info(this.getClass().getName() + pDTO.getLocation_name());
        log.info(this.getClass().getName() + pDTO.getTemperature());
        log.info(this.getClass().getName() + pDTO.getWeather());

        log.info(this.getClass().getName() + "toDayWeather End!");

        return pDTO;
    }
}