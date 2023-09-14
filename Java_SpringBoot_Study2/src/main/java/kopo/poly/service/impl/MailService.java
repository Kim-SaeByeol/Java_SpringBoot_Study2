package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.persistance.mapper.IMailMapper;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {
    private final JavaMailSender mailSender;

    private final IMailMapper mailMapper;
    @Value("${spring.mail.username}")
    private String fromMail;


    @Override
    public int doSendMail(MailDTO pDTO) {
        // 로그 찍기 ( 추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이(
        log.info(this.getClass() + ".doSendMail start!");

        //메일 발송 성공여부 ( 발송성공 : 1 / 발송실패 : 0 )
        int res = 1;

//        전달 받은 DTO로부터 데이터 가져오기 ( DTO 객체가 메모리에 올라가지 않아 Null이 발생할 수 있기 때문에
//        에러 방지 차원에서 if문 사요
        if (pDTO == null){
            pDTO = new MailDTO();
        }

        String toMail = CmmUtil.nvl(pDTO.getToMail());
        String title = CmmUtil.nvl(pDTO.getTitle());
        String contentse = CmmUtil.nvl(pDTO.getContents());

        //메일 발송 메시지 구조(파일 첨부 가능)
        MimeMessage message = mailSender.createMimeMessage();

        //메일 발송 메시지 구조를 쉽게 생성하게 도와주는 객체
        MimeMessageHelper massageHelper = new MimeMessageHelper(message, "UTF-8");

        try {
            massageHelper.setTo(toMail);
            massageHelper.setFrom(fromMail);
            massageHelper.setSubject(title);
            massageHelper.setText(contentse);

            mailSender.send(message);
            mailMapper.insertMailInfo(pDTO);

        } catch (Exception e) {
            res = 0;
            log.info("[ERROR]" + this.getClass().getName() + ".doSendMail: " + e);
        }


        //로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 확인)
        log.info(this.getClass().getName() + ".doSendMail end!");

        return res;
    }

    @Override
    public List<MailDTO> getMailList() throws Exception {

        log.info(".service 메일 리스트");

        return mailMapper.getMailList();
    }
}
