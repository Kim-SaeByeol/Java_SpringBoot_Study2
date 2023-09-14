package kopo.poly.persistance.mapper;

import kopo.poly.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMailMapper {
    //게시판 리스트
    List<MailDTO> getMailList() throws Exception;

    //게시판 등록
    void insertMailInfo(MailDTO pDTO) throws Exception;
}
