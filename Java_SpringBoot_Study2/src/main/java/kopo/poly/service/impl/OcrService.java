package kopo.poly.service.impl;

import kopo.poly.dto.OcrDTO;
import kopo.poly.service.IOcrService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class OcrService implements IOcrService {

    @Value("${ocr.model.data")
    private String ocrModel;
    @Override
    public OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getReadforImageText 시작!");

        File imageFile = new File(CmmUtil.nvl(pDTO.getFilePath()) + "//" + CmmUtil.nvl(pDTO.getFileName()));

        ITesseract instance = new Tesseract();

        instance.setDatapath(ocrModel);

        instance.setLanguage("kor");
        //instance.setLanguage("eng");

        String result = instance.doOCR(imageFile);

        pDTO.setTextFromImage(result);

        log.info("result : " + result);

        log.info(this.getClass().getName() + ".getReadforImageText 끝!");

        return pDTO;
    }
}