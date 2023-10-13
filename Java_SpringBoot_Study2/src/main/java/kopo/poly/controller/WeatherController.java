package kopo.poly.controller;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
@Controller
public class WeatherController {

    private final IWeatherService weatherService; //Food 서비스 객체 주입하기

    /**
     * 날씨 수집을 위한 URL 호출
     */

    @GetMapping(value = "toDayWeather")
    public String collectWeather(ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".getWeather 시작!");

        // 서비스 호출 결과 받기
        WeatherDTO rList = weatherService.toDayWeather();

        // 크롤링 결과 모델에 넣기
        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".getWeather 종료!");

        return "/weather/toDayWeather";
    }

}
