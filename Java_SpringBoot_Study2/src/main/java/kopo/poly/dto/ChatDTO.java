package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ChatDTO {
    private String name;    //이름
    private String msg;    //채팅 메세지
    private String date;    // 발송 날짜
    
    
}
