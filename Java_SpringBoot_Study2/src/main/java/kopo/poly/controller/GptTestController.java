package kopo.poly.controller;

import kopo.poly.dto.GptTestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gpt-test")
public class GptTestController {

    @PostMapping("/chat")
    public GptTestDTO chat(@RequestBody GptTestDTO requestDTO) {
        // 실제로는 여기에서 GPT와의 통신이 이루어져야 합니다.
        // 현재는 간단한 예시로 사용자 입력을 그대로 응답으로 반환하는 것으로 대체합니다.

        GptTestDTO responseDTO = new GptTestDTO();
        responseDTO.setGptMessage("GPT 응답: " + requestDTO.getGptMessage());

        return responseDTO;
    }
}
