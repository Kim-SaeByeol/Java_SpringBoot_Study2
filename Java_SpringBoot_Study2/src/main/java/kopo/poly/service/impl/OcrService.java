package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.OcrDTO;
import kopo.poly.persistance.mapper.IMailMapper;
import kopo.poly.persistance.mapper.IOcrMapper;
import kopo.poly.service.IOcrService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService implements IOcrService {


    private final IOcrMapper OcrMapper;

    @Value("${${ocr.model.data}}")
    private String ocrModel;

    @Override
    public OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception {

        log.info(this.getClass().getName()+".getReadforImageText Start!");

        String collectTime = DateUtil.getDateTime("yyyy/MM/dd");  // 수집 날짜 = 오늘 날짜
        pDTO.setRegDt(collectTime); // 실행시간 저장
        
        File imageFile = new File(CmmUtil.nvl(pDTO.getFilePath()) + "//" + CmmUtil.nvl(pDTO.getFileName()));
        log.info("image file : " + imageFile);

        //OCR 기술 사용을 위한 Tesseract 플랫폼 객체 생성
        ITesseract instance = new Tesseract();

        //OCR 분석에 필요한 기준 데이터(이미 각 나라의 언어별로 학습시킨 데이터 위치 폴더)
        // 저장 경로를 물리경로로 사용함(전체 경로)
        instance.setDatapath(ocrModel);

        // 한국어 학습 데이터 선택(기본 값은 영어)
        instance.setLanguage("kor");    // 한국어 설정

        //         instance.setLanguage("eng"); //영어 설정

        //이미지 파일로부터 텍스트 읽기
        String result = instance.doOCR(imageFile);  //글씨 인식하기
        log.info("4");

        // 읽은 글자를 DTO에 저장하기
        pDTO.setOcrText(result);

        log.info("result : " + result);

        log.info(this.getClass().getName()+".getReadforImageText End!");

        log.info(this.getClass().getName() + ".잘 읽어왔는지 확인해보자.");

        log.info(pDTO.getSeq());
        log.info(pDTO.getSaveFileName());
        log.info(pDTO.getSaveFilePath());
        log.info(pDTO.getOrgFileName());
        log.info(pDTO.getExt());
        log.info(pDTO.getOcrText());
        log.info(pDTO.getRegId());
        log.info(pDTO.getRegDt());

        log.info(this.getClass().getName() + ".잘 읽어왔냐?");

        insertOcrInfo(pDTO);
        return pDTO;
    }

    @Transactional
    @Override
    public void insertOcrInfo(OcrDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertOcrInfo start!");
        OcrMapper.insertOcrInfo(pDTO);
        log.info(this.getClass().getName() + ".insertOcrInfo end!");
    }

    @Override
    public List<OcrDTO> getOcrList() throws Exception {

        log.info(this.getClass().getName() + ".getOcrList 시작!");

        List<OcrDTO> resultList = OcrMapper.getOcrList();
        if (resultList == null) {
            log.info("resultList is null!");
        } else if (resultList.isEmpty()) {
            log.info("resultList is empty!");
        } else {
            log.info("resultList size: " + resultList.size());
        }

        return OcrMapper.getOcrList();
    }


}
