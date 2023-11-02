package kopo.poly.service;

import kopo.poly.dto.CenterDTO;

import java.util.List;

public interface ICenterService {


    // 조회
    List<CenterDTO> getCenterList() throws Exception;

    // 등록
    void insertCenterInfo(CenterDTO pDTO) throws Exception;

    // 수정
    void updateCenterInfo(CenterDTO pDTO) throws Exception;

    // 삭제
    void deleteCenterInfo(CenterDTO pDTO) throws Exception;


}
