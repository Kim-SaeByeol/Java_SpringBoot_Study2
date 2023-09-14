package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoService {

    //아이디 중복 체크
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    //이메일 주소 중복 체크 및 인증 값
    UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception;

    //회원 가입하기 (회원 등록)
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;
}
