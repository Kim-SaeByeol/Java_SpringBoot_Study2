package kopo.poly.service;

import kopo.poly.dto.NoticeDTO;

import java.util.List;

public interface ICenterService {


    // 조회
    List<NoticeDTO> getCenterList() throws Exception;

    // 등록
    void insertCenterInfo(NoticeDTO pDTO) throws Exception;

    // 수정
    void updateCenterInfo(NoticeDTO pDTO) throws Exception;

    // 삭제
    void deleteCenterInfo(NoticeDTO pDTO) throws Exception;


}
