package kopo.poly.persistance.mapper;

import kopo.poly.dto.OcrDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOcrMapper {
    List<OcrDTO> getOcrList() throws Exception;

    //게시판 글 등록
    void insertOcrInfo(OcrDTO pDTO) throws Exception;

    void selectOcrInfo(OcrDTO pDTO) throws Exception;
}
