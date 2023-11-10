package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PapagoDTO {
    private String langCode;    //원문 언어( 한국어 : ko / 영어 : en)

    private String text;    // 분석을 위한 문장
}
