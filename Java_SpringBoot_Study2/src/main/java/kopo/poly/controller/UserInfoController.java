package kopo.poly.controller;


import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Controller
public class UserInfoController {

    private final IUserInfoService userInfoService;

    @GetMapping(value = "userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm");

        return "/user/userRegForm";
    }

    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists 시작!");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserIdExists(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getUserIdExists 끝!");

        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "getEmailExists")
    public UserInfoDTO getEmailExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getEmailExists 시작!");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getEmailExists(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getEmailExists 끝!");

        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo 시작!");

        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        UserInfoDTO pDTO = null;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String userSeq = CmmUtil.nvl(request.getParameter("userSeq"));
            String userName = CmmUtil.nvl(request.getParameter("userName"));
            String password = CmmUtil.nvl(request.getParameter("password"));
            String email = CmmUtil.nvl(request.getParameter("email"));
            String addr1 = CmmUtil.nvl(request.getParameter("addr1"));
            String addr2 = CmmUtil.nvl(request.getParameter("addr2"));

            log.info("userId : " + userId);
            log.info("userSeq : " + userSeq);
            log.info("userName : " + userName);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);

            pDTO = new UserInfoDTO();

            pDTO.setUserId(userId);
            pDTO.setUserSeq(userSeq);
            pDTO.setUserName(userName);

            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            pDTO.setEmail(EncryptUtil.encAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);

            res = userInfoService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }
        } catch (Exception e) {

            msg = "실패하였습니다. : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".insertUserInfo 끝!");
        }

        return dto;
    }


    @GetMapping(value = "userList")
    public String userList(ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".userList 시작!");

        List<UserInfoDTO> rList = Optional.ofNullable(userInfoService.getUserList())
                .orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".userList End!");

        return "/user/userList";
    }

    @GetMapping(value = "userInfo")
    public String userInfo(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".userInfo Start!");
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        log.info("userId : " + userId);
        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserInfo(pDTO, true))
                .orElseGet(UserInfoDTO::new);
        model.addAttribute("rDTO", rDTO);
        log.info(this.getClass().getName() + ".userInfo End!");
        return "/user/userInfo";
    }

    /*
     * 로그인을 위한 입력 화면으로 이동
     */
    @GetMapping(value = "login")
    public String login() {
        log.info(this.getClass().getName() + ".user/login Start");
        log.info(this.getClass().getName() + ".user/login End");

        return "/user/login";
    }

    /*
     * 로그인 처리 및 결과 알려주는 화면으로 이동
     */
    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) {
        log.info(this.getClass().getName() + ".loginProc Start!");

        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        UserInfoDTO pDTO = null;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String password = CmmUtil.nvl(request.getParameter("password"));

            log.info("userId : " + userId);
            log.info("password : " + password);

            pDTO = new UserInfoDTO();
            pDTO.setUserId(userId);
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            UserInfoDTO rDTO = userInfoService.getLogin(pDTO);

            if (CmmUtil.nvl(rDTO.getUserId()).length() > 0) {
                res = 1;
                msg = "로그인이 성공했습니다.";

                // 세션에 이미 사용자 정보가 있는지 확인
                UserInfoDTO sessionUserInfo = (UserInfoDTO) session.getAttribute("SESSION_USER_INFO");
                if (sessionUserInfo == null) {
                    // 세션에 사용자 정보가 없다면 추가
                    session.setAttribute("SESSION_USER_INFO", rDTO);
                }
            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다.";
            }
        } catch (Exception e) {
            msg = "시스템 문제로 로그인이 실패했습니다.";
            res = 2;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".loginProc End!");
        }

        return dto;
    }

    /**
     * 아이디 찾기 화면
     */

    @GetMapping(value = "searchUserId")
    public String searchUserId() {
        log.info(this.getClass().getName() + ".user/searchUserId Start !");
        log.info(this.getClass().getName() + ".user/searchUserId End !");
        return "/user/searchUserId";
    }

    /**
     * 아이디 찾기 로직 수행
     */
    @PostMapping(value = "searchUserIdProc")
    public String searchUserIdProc(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".user/searchUserIdProc Start!");

        /**
         * #################################################
         *      웹(회원 정보 입력화면) 에서 받는 정보를 String 변수에 저장
         *      무조건 웹으로 받는 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
         * #################################################
         */


        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        /**
         * #################################################
         *      반드시, 값을 받았으면 꼭 로그를 찍을 것.
         * #################################################
         */

        log.info("userName : " + userName);
        log.info("email : " + email);

        /**
         * #################################################
         *      웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장
         *      무조건 웹으로 받는 정보는 DTO에 저장해야 한다고 이해하길 권함
         * #################################################
         */

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserName(userName);
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.searchUserIdOrPasswordProc(pDTO)).orElseGet(UserInfoDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".user/searchUserIdProc End!");

        return "/user/searchUserIdResult";
    }

    @GetMapping("/loginResult")
    public String loginResult(ModelMap model, HttpSession session) {
        log.info(this.getClass().getName() + ".loginResult Start!");

        // 세션에 저장된 속성 이름 확인
        String attributeName = "SESSION_USER_INFO";
        UserInfoDTO userInfoDTO = (UserInfoDTO) session.getAttribute(attributeName);

        //세션 못 받아오는 로그를 보기 위함
        if (userInfoDTO == null) {
            log.info("세션에서 " + attributeName + " 속성을 가져오지 못했습니다.");
            return "redirect:/user/login";
        }

        // 사용자 정보를 ModelMap에 전달
        model.addAttribute("rDTO", userInfoDTO);

        log.info(this.getClass().getName() + ".loginResult End!");

        return "/user/loginResult";
    }

    /**
     * 비밀번호 찾기 화면
     */
    @GetMapping(value = "searchPassword")
    public String searchPassword(HttpSession session) {
        log.info(this.getClass().getName() + ".user/searchPassword Start!");

        // 강제 URL 입력 등으로 오는 경우가 있어 세션은 항상 삭제
        // 비밀번호 재설정하는 화면은 보안을 위해 생성한 New_Password 세션을 삭제함.
        session.setAttribute("NEW_PASSWORD", "");
        session.removeAttribute("NEW_PASSWORD");

        log.info(this.getClass().getName() + ".user/searchPassword End!");

        return "/user/searchPassword";
    }


    @PostMapping(value = "searchPasswordProc")
    public String searchPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + "./user/searchPasswordProc Start!");

        /**
         * ################################################################################################
         *      웹(회원정보 입력화면) 에서 받는 정보를 String 변수에 저장
         *      무조건 웹으로 받는 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함.
         * ################################################################################################
         */

        String userId = CmmUtil.nvl(request.getParameter("userId"));    //아이디
        String userName = CmmUtil.nvl(request.getParameter("userName"));    //이름
        String email = CmmUtil.nvl(request.getParameter("email"));    //이메일

        /**
         * ################################################################################################
         *      반드시 값을 받았다면 값을 받았다는 확인을 위해
         *                  로그를 찍을 것.
         * ################################################################################################
         */

        log.info("userId : " + userId);
        log.info("userNam : " + userName);
        log.info("email : " + email);

        /**
         * ################################################################################################
         *      반드시 웹에서 받는 정보는 DTO 에 저장하기.
         * ################################################################################################
         */

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);
        pDTO.setUserName(userName);
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        // 비밀번호 찾기 가능한지 확인하기
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.searchUserIdOrPasswordProc(pDTO)).orElseGet(UserInfoDTO::new);

        model.addAttribute("rDTO", rDTO);

        // 비밀번호 재생성하는 화면은 보안을 위해 반드시 NEW_PASSWORD 세션이 존재해야 접속 가능하도록 구현
        // userId 값을 넣은 이유는 비밀번호 재설정하는 newPasswordProc 함수에서 사용하기 위함
        session.setAttribute("NEW_PASSWORD", userId);

        log.info(this.getClass().getName() + "./user/searchPasswordProc End!");

        return "/user/newPassword";
    }

    @PostMapping(value = "newPasswordProc")
    public String newPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + "./user/newPasswordProc Start!");

        String msg = "";

        //정상적인 접근인지 체크
        String newPassword = CmmUtil.nvl((String) session.getAttribute("NEW_PASSWORD"));

        if (newPassword.length() > 0) {  //정상
            String password = CmmUtil.nvl(request.getParameter("password"));    // 신규 비밀번호

            log.info("password : " + password);

            UserInfoDTO pDTO = new UserInfoDTO();
            pDTO.setUserId(newPassword);
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            userInfoService.newPasswordProc(pDTO);

            // 비밀번호 재생성하는 화면은 보안을 위해 생성한 NEW_PASSWORD 세션 삭제
            session.setAttribute("NEW_PASSWORD", "");
            session.removeAttribute("NEW_PASSWORD");

            msg = "비밀번호가 재설정되었습니다.";
        } else {    // 비정상 접근
            msg = "잘못된 접근 입니다.";
        }

        model.addAttribute("msg", msg);

        log.info(this.getClass().getName() + "./user/newPasswordProc End!");

        return "/user/newPasswordResult";
    }

    @ResponseBody
    @PostMapping(value = "getcheckAuthNumber")
    public UserInfoDTO getcheckAuthNumber(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getcheckAuthNumber 시작!");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getcheckAuthNumber(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getcheckAuthNumber 끝!");

        return rDTO;
    }
}