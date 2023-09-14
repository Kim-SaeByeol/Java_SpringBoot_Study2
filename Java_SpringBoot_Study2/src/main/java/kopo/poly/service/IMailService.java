package kopo.poly.service;

import kopo.poly.dto.MailDTO;

import java.util.List;

public interface IMailService {
    //메일 발송
    int doSendMail(MailDTO pDTO) throws Exception;  // 서비스에서 최종 에러를 처리할 것이기에 예외처리 넣지 않음

    List<MailDTO> getMailList() throws Exception;
}
