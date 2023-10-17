package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GptTestDTO {

    //GPT에서 나에게 주는 메세지
   String gptMessage;

   //이용자가 GPT에게 주는 메세지
   String userMessage;
}
