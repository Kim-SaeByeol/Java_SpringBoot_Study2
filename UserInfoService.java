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
import sun.java2d.cmm.kcms.CMM;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {

    private final IUserInfoMapper userInfoMapper;

    private final IMailService mailService;

    @Override   //아이디 중복체크
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass() + ".getUserIdExists Start!");

        UserInfoDTO rDTO = userInfoMapper.getUserIdExists(pDTO);

        log.info(this.getClass().getName() + ".getUserIdExists End!");

        return rDTO;
    }

    @Override   //이메일 중복체크
    public UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".emailAuth Start!");

        //DB 이메일이 존재하는지 SQL 쿼리 실행
        // SQL 쿼리에 COUNT()를 사용하기 때문에 반드시 조회 결과가 존재
        UserInfoDTO rDTO = userInfoMapper.getEmailExists(pDTO);

        String existsYn = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYn : " + existsYn);

        if(existsYn.equals("N")){

            //6자리 랜덤 숫자 생성하기
            int authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);

            log.info("authNumber : " + authNumber + " : 입니다.");

            //인증번호 발송 로직
            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 중복확인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mailService.doSendMail(dto);    // 이메일 발송
            dto = null;

            rDTO.setAuthnumber(authNumber); //인증번호를 결과 값에 넣어줌.
        }

        log.info(this.getClass().getName() + ".emailAuth End!");

        return rDTO;
    }

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo Start!");

        //회원가입 성공 : 1, 아이디 중복으로만 가입 취소 : 2, 기타 에러 발생 : 0
        int res = 0;

        //회원 가입
        int success = userInfoMapper.insertUserInfo(pDTO);

        // DB에 데이터가 등록되었다면(어떤 값이든 들어왔다면)
        if (success > 0){

            res = 1;

            /**
             * ######################################
             *           메일 발송 로직 시작
             *######################################
             */
            MailDTO mDTO = new MailDTO();

            //회원 정보 화면에서 입력 받은 이메일 변수 (아직 암호화 되어 넘어오기 때문에 복호화를 수행)
            mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mDTO.setTitle("회원가입을 축하드립니다."); // 제목

            //메일 내용에 가입자 이름 넣어서 내용 발송
            mDTO.setContents(CmmUtil.nvl(pDTO.getUser_Name()) + "님의 회원가입을 진심으로 축하드립니다.");

            //회원 가입이 성공했기 때문에 메일을 발송함
            mailService.doSendMail(mDTO);
            /**
             * ######################################
             *           메일 발송 로직 종료
             *######################################
             */
        }

        log.info(this.getClass().getName() + ".insertUserInfo End!");

        return res;

    }
}
