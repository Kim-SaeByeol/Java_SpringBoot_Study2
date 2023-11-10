package kopo.poly.service;

import kopo.poly.dto.PapagoDTO;

public interface IPapagoService {
    //파파고 언어 감지 API
    String detectLangsApuURL = "https://openapi.naver.com/v1/papago/detectLangs";

    //네이버 파파고 API를 호출하여 입력된 언어가 어느 나라 언어인지 찾기
    PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception;
}
