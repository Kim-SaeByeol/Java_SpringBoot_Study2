package kopo.poly.controller;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
@Controller
public class MailController {

    private final IMailService mailService; // 메일 발송을 위한 서비스 객체를 사용하기

    /**
     * 메일 발송하기폼
     */

    @GetMapping(value = "mailForm")
    public String mailForm() throws Exception {
        log.info(this.getClass().getName() + " mailForm Start!");

        return "/mail/mailForm";
    }

    /**
     * 메일 발송하기
     */
    @ResponseBody
    @PostMapping(value = "sendMail")
    public MsgDTO sendMAil(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".sendMail Start!");

        String msg = ""; // 발송 결과 메시지

        // 웹 URL로부터 전달받는 값들
        String to_mail = CmmUtil.nvl(request.getParameter("to_mail"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String contents = CmmUtil.nvl(request.getParameter("contents"));

        log.info("to_mail : " + to_mail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        // 메일 발송할 정보를 넣기 위한 DTO 객체 생성하기
        MailDTO pDTO = new MailDTO();

        // 웹에서 받은 값을 DTO에 넣기
        pDTO.setToMail(to_mail); // 받는 사람을 DTO 저장
        pDTO.setTitle(title); // 제목을 DTO 저장
        pDTO.setContents(contents); // 내용을 DTO 저장

        // 메일발송하기
        int res = mailService.doSendMail(pDTO);

        if (res == 1) { // 메일발송 성공
            mailService.insertMailInfo(pDTO);
            msg = "메일 발송하였습니다.";
        } else {
            msg = "메일 발송 실패하였습니다.";
        }

        log.info(msg);

        // 결과 메시지 전달하기
        MsgDTO dto = new MsgDTO();
        dto.setMsg(msg);

        log.info(this.getClass().getName() + ".sendMail End!");

        return dto;
    }

    @GetMapping(value = "mailList")
    public String mailList(ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".mailList 시작!");

        List<MailDTO> rList = Optional.ofNullable(mailService.getMailList())
                .orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".mailList End!");

        return "/mail/mailList";
    }

    @ResponseBody
    @PostMapping(value = "mailInsert")
    public String noticeInsert(HttpServletRequest request, HttpSession session) {

        log.info(this.getClass().getName() + ".mailInsert Start!");

        String msg = ""; // 메시지 내용

        MsgDTO dto = null; // 결과 메시지 구조

        try {
            // 로그인된 사용자 아이디를 가져오기
            // 로그인을 아직 구현하지 않았기에 공지사항 리스트에서 로그인 한 것처럼 Session 값을 저장함
            String mail_seq = CmmUtil.nvl(request.getParameter("mail_seq")); // 제목
            String to_mail = CmmUtil.nvl(request.getParameter("to_mail")); // 제목
            String title = CmmUtil.nvl(request.getParameter("title")); // 공지글 여부
            String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용
            String send_time = CmmUtil.nvl(request.getParameter("send_time")); // 내용

            /*
             * ####################################################################################
             * 반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함 반드시 작성할 것
             * ####################################################################################
             */
            log.info("mail_seq: " + mail_seq);
            log.info("to_mail : " + to_mail);
            log.info("title : " + title);
            log.info("contents : " + contents);
            log.info("send_time : " + send_time);

            // 데이터 저장하기 위해 DTO에 저장하기
            MailDTO pDTO = new MailDTO();
            pDTO.setMailSeq(mail_seq);
            pDTO.setToMail(to_mail);
            pDTO.setTitle(title);
            pDTO.setContents(contents);
            pDTO.setSendTime(send_time);

            /*
             * 게시글 등록하기위한 비즈니스 로직을 호출
             */
            mailService.insertMailInfo(pDTO);

            // 저장이 완료되면 사용자에게 보여줄 메시지
            msg = "등록되었습니다.";

        } catch (Exception e) {

            // 저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            // 결과 메시지 전달하기
            dto = new MsgDTO();
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".mailInsert End!");
        }

        return "/redirect";
    }
}