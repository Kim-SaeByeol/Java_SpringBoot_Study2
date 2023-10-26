package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrDTO {
    private String filePath;    //저장된 이미지 파일의 파일 저장 경로
    private String fileName;    // 저장된 이미지 파일 이름


    
    

    // DB 에 저장될 값.
    private String seq; //순번
    private String saveFileName;    //서버에 저장된 파일 이름
    private String saveFilePath;    //서버에 저장된 파일의 주소
    private String orgFileName; //원래 파일 이름

    private String ext; //파일 확장자
    private String ocrText; // 이미지 인식 문자열 (저장된 이미지로 부터 얻은 문자열)
    private String regId;   // 최초 등록자
    private String regDt;   // 생성 등록일
    private String chgId;   // 최초 수정자
    private String chgDt;   //최근 수정일
    
    
}
