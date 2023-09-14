package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserInfoDTO {

    private String userId;
    private String user_Name;
    private String password;
    private String email;
    private String addr1;
    private String addr2;
    private String reg_Id;
    private String reg_Dt;
    private String chg_Id;
    private String chg_Dt;

    /*
    * 회원 가입시, 중복가입을 방지 하기 위해 사용할 변수.
    * DB를 조회할 때 회원이 존재하면 Y 값을,
    * 회원이 존재하지 않는다면 N 값을 반환 받는다.
        Mapper 참고
     */
    private String existsYn;

    //이메일 중복체크를 위한 인증번호
    private int authnumber;
}
