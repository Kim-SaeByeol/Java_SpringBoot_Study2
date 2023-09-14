package kopo.poly.persistance.mapper;

import kopo.poly.dto.UserInfoDTO;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserInfoMapper {

    //회원 가입하기(회원 등록)
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    //회원 가입 전 아이디 중복체크(DB 조회)
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    //회원 가입 전 이메일 중복체크(DB 조회)
    UserInfoDTO getEmailExists(UserInfoDTO DTO) throws Exception;
}
