package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.INoticeService;
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


/*
 * Controller 선언해야만 Spring 프레임워크에서 Controller인지 인식 가능
 * 자바 서블릿 역할 수행
 *
 * slf4j는 스프링 프레임워크에서 로그 처리하는 인터페이스 기술이며,
 * 로그처리 기술인 log4j와 logback과 인터페이스 역할 수행함
 * 스프링 프레임워크는 기본으로 logback을 채택해서 로그 처리함
 * */
@Slf4j
@RequestMapping(value = "/notice")
@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final INoticeService noticeService;



    @GetMapping(value = "noticeList")
    public String noticeList(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".noticeList Start!");
        session.setAttribute("SESSION_USER_ID", "USER01");

        List<NoticeDTO> rList = Optional.ofNullable(noticeService.getNoticeList())
                .orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".noticeList End!");
        return "/notice/noticeList";
    }


    @GetMapping(value = "noticeReg")
    public String NoticeReg() {
        log.info(this.getClass().getName() + ".noticeReg Start!");
        log.info(this.getClass().getName() + ".noticeReg End!");
        return "/notice/noticeReg";
    }


    @ResponseBody
    @PostMapping(value = "noticeInsert")
    public MsgDTO noticeInsert(HttpServletRequest request, HttpSession session) {
        log.info(this.getClass().getName() + ".noticeInsert Start!");
        String msg = "";
        MsgDTO dto = null;
        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));
            String title = CmmUtil.nvl(request.getParameter("title"));
            String noticeYn = CmmUtil.nvl(request.getParameter("noticeYn"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));
            log.info("session user_id : " + userId);
            log.info("title : " + title);
            log.info("noticeYn : " + noticeYn);
            log.info("contents : " + contents);
            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setUserId(userId);
            pDTO.setTitle(title);
            pDTO.setNoticeYn(noticeYn);
            pDTO.setContents(contents);
            noticeService.insertNoticeInfo(pDTO);
            msg = "등록되었습니다.";
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".noticeInsert End!");
        }
        return dto;
    }



    @GetMapping(value = "noticeInfo")
    public String noticeInfo(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".noticeInfo Start!");
        String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
        log.info("nSeq : " + nSeq);
        NoticeDTO pDTO = new NoticeDTO();
        pDTO.setNoticeSeq(nSeq);
        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO, true))
                .orElseGet(NoticeDTO::new);
        model.addAttribute("rDTO", rDTO);
        log.info(this.getClass().getName() + ".noticeInfo End!");
        return "/notice/noticeInfo";
    }

    @GetMapping(value = "noticeEditInfo")
    public String noticeEditInfo(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".noticeEditInfo Start!");
        String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
        log.info("nSeq : " + nSeq);
        NoticeDTO pDTO = new NoticeDTO();
        pDTO.setNoticeSeq(nSeq);
        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO, false))
                .orElseGet(NoticeDTO::new);
        model.addAttribute("rDTO", rDTO);
        log.info(this.getClass().getName() + ".noticeEditInfo End!");
        return "/notice/noticeEditInfo";
    }

    @ResponseBody
    @PostMapping(value = "noticeUpdate")
    public MsgDTO noticeUpdate(HttpSession session, HttpServletRequest request) {
        log.info(this.getClass().getName() + ".noticeUpdate Start!");
        String msg = "";
        MsgDTO dto = null;
        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
            String title = CmmUtil.nvl(request.getParameter("title"));
            String noticeYn = CmmUtil.nvl(request.getParameter("noticeYn"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));
            log.info("userId : " + userId);
            log.info("nSeq : " + nSeq);
            log.info("title : " + title);
            log.info("noticeYn : " + noticeYn);
            log.info("contents : " + contents);
            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setUserId(userId);
            pDTO.setNoticeSeq(nSeq);
            pDTO.setTitle(title);
            pDTO.setNoticeYn(noticeYn);
            pDTO.setContents(contents);
            noticeService.updateNoticeInfo(pDTO);
            msg = "수정되었습니다.";
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".noticeUpdate End!");
        }
        return dto;
    }

    @ResponseBody
    @PostMapping(value = "noticeDelete")
    public MsgDTO noticeDelete(HttpServletRequest request) {
        log.info(this.getClass().getName() + ".noticeDelete Start!");
        String msg = "";
        MsgDTO dto = null;
        try {
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
            log.info("nSeq : " + nSeq);
            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setNoticeSeq(nSeq);
            noticeService.deleteNoticeInfo(pDTO);
            msg = "삭제되었습니다.";
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".noticeDelete End!");
        }
        return dto;
    }

}
