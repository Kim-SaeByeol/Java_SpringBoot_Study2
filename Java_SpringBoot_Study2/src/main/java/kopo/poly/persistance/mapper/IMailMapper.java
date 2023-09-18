package kopo.poly.persistance.mapper;

import kopo.poly.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMailMapper {
    List<MailDTO> getMailList() throws Exception;

    //게시판 글 등록
    void insertMailInfo(MailDTO pDTO) throws Exception;
}
