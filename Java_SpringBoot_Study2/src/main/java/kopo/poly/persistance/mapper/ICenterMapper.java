package kopo.poly.persistance.mapper;

import kopo.poly.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICenterMapper {
    //게시판 리스트
    List<NoticeDTO> getCenterList() throws Exception;

    //게시판 글 등록
    void insertCenterInfo(NoticeDTO pDTO) throws Exception;

    //게시판 글 수정
    void updateCenterInfo(NoticeDTO pDTO) throws Exception;

    //게시판 글 삭제
    void deleteCenterInfo(NoticeDTO pDTO) throws Exception;
}
