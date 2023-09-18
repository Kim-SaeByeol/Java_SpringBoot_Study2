package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IUserInfoMapper;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.java2d.cmm.kcms.CMM;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service

public class UserInfoService implements IUserInfoService {

    private final IUserInfoMapper userInfoMapper; // Mapper 가져오기

    private final IMailService mailService; // MailService 가져오기

    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists 시작!");

        UserInfoDTO rDTO = userInfoMapper.getUserIdExists(pDTO);

        log.info(this.getClass().getName() + ".getUserIdExists 끝!");

        return rDTO;
    }

    @Override
    public UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getEmailExists 시작!");

        UserInfoDTO rDTO = userInfoMapper.getEmailExists(pDTO);

        String existsYn = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYn : " + existsYn);

        if (existsYn.equals("N")) {

            // 6자리 랜덤 숫자 생성하기
            int authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);

            log.info("authNumber : " + authNumber);

            // 인증번호 발송 로직
            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 중복 확인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mailService.doSendMail(dto); // 이메일 발송

            dto = null;

            rDTO.setAuthNumber(authNumber); // 인증번호를 결과값에 넣어주기
        }

        log.info(this.getClass().getName() + ".getEmailExists 끝!"); // 인증번호를 결과값에 넣어주기

        return rDTO;
    }

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo 시작!");

        // 회원가입 성공 : 1, 아이디 중복으로인한 가입 취소 : 2, 기타 에러 발생 : 0
        int res = 0;

        // 회원가입
        int success = userInfoMapper.insertUserInfo(pDTO);

        // DB에 데이터가 등록되었다면(회원가입 성공했다면...)
        if (success > 0) {
            res = 1;

            /*
             * ########################################################
             *                 메일 발송 로직 추가 시작!
             * ########################################################
             */

            MailDTO mDTO = new MailDTO();

            // 회원정보화면에서 입력받은 이메일 변수(아직 암호화되어 넘어오기 때문에 복호화 수행함)
            mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mDTO.setTitle("회원가입을 축하드립니다");

            // 메일 내용에 가입자 이름넣어서 내용 발송
            mDTO.setContents(CmmUtil.nvl(pDTO.getUserName()) + "님의 회원가입을 진심으로 축하드립니다.");

            // 회원 가입이 성공했기 때문에 메일을 발송함
            mailService.doSendMail(mDTO);

            /*
             * ########################################################
             *                 메일 발송 로직 추가 끝!
             * ########################################################
             */

        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".insertUserInfo 끝!");

        return res;
    }

    @Override
    public List<UserInfoDTO> getUserList() throws Exception {

        log.info(this.getClass().getName() + ".getUserList 시작!");

        List<UserInfoDTO> resultList = userInfoMapper.getUserList();
        if (resultList == null) {
            log.info("resultList is null!");
        } else if (resultList.isEmpty()) {
            log.info("resultList is empty!");
        } else {
            log.info("resultList size: " + resultList.size());
        }

        return userInfoMapper.getUserList();
    }

    @Transactional
    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO, boolean type) throws Exception {
        log.info(this.getClass().getName() + ".getUserInfo start!");

        return userInfoMapper.getUserInfo(pDTO);
    }
}