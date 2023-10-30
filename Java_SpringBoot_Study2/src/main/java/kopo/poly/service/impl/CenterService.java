package kopo.poly.service.impl;

import kopo.poly.dto.NoticeDTO;
import kopo.poly.persistance.mapper.ICenterMapper;
import kopo.poly.persistance.mapper.INoticeMapper;
import kopo.poly.service.ICenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CenterService implements ICenterService {

    private final ICenterMapper centerMapper;


    @Override
    public List<NoticeDTO> getCenterList() throws Exception {   //조회

        log.info(this.getClass().getName() + ".getCenterList Start");


        List<NoticeDTO> resultList = centerMapper.getCenterList();
        if (resultList == null) {
            log.info("resultList is null!");
        } else if (resultList.isEmpty()) {
            log.info("resultList is empty!");
        } else {
            log.info("resultList size: " + resultList.size());
        }

        log.info(this.getClass().getName() + ".getCenterList End");

        return centerMapper.getCenterList();
    }

    @Transactional
    @Override
    public void insertCenterInfo(NoticeDTO pDTO) throws Exception { //관리자만 조작이 가능.

        log.info(this.getClass().getName() + ".insertCenterInfo start!");

        centerMapper.insertCenterInfo(pDTO);
    }

    @Transactional
    @Override
    public void updateCenterInfo(NoticeDTO pDTO) throws Exception {     //관리자만 조작이 가능
        log.info(this.getClass().getName() + ".updateCenterInfo start!");

        centerMapper.updateCenterInfo(pDTO);
    }

    @Transactional
    @Override
    public void deleteCenterInfo(NoticeDTO pDTO) throws Exception { //관리자만 조작이 가능
        log.info(this.getClass().getName() + ".deleteCenterInfo start!");

        centerMapper.deleteCenterInfo(pDTO);

    }
}
