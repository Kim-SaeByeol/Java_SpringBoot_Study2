package kopo.poly.controller;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.OcrDTO;
import kopo.poly.service.IOcrService;
import kopo.poly.service.impl.OcrService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/ocr")
@RequiredArgsConstructor
@Controller
public class OcrController {
    private final IOcrService ocrService;

    final private String FILE_UPLOAD_SAVE_PATH = "C:/upload";

    @GetMapping(value = "uploadImage")
    public String uploadImage() {
        log.info(this.getClass().getName() + ".uploadImage!");

        return "/ocr/uploadImage";
    }
    @PostMapping(value = "readImage")
    public String readImage(ModelMap model, @RequestParam(value = "fileUpload") MultipartFile mf)
            throws Exception {

        log.info(this.getClass().getName() + ".readImage 시작!");

        String res = "";

        String originalFileName = mf.getOriginalFilename();

        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1,
                originalFileName.length()).toLowerCase();

        if (ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {

            String saveFileName = DateUtil.getDateTime("HHmmss") + "." + ext;

            String saveFilePath = FileUtil.mkdirForDate(FILE_UPLOAD_SAVE_PATH);

            String fullFileInfo = saveFilePath + "/" + saveFileName;

            log.info("ext : " + ext);
            log.info("saveFileName : " + saveFileName);
            log.info("saveFilePath : " + saveFilePath);
            log.info("fullFileInfo : " + fullFileInfo);

            mf.transferTo(new File(fullFileInfo));

            OcrDTO pDTO = new OcrDTO();

            pDTO.setFileName(saveFileName);
            pDTO.setFilePath(saveFilePath);

            pDTO.setSaveFileName(saveFileName);
            pDTO.setSaveFilePath(saveFilePath);
            pDTO.setOrgFileName(originalFileName);
            pDTO.setExt(ext);
            pDTO.setRegId("김새별");

            OcrDTO rDTO = Optional.ofNullable(ocrService.getReadforImageText(pDTO)).orElseGet(OcrDTO::new);

            res = CmmUtil.nvl(rDTO.getOcrText());
            rDTO = null;
            pDTO = null;
        } else {
            res = "이미지 파일이 아니라서 인식이 불가능합니다.";

        }
        model.addAttribute("res", res);


        log.info(this.getClass().getName() + ".readImage 끝!");

        return "/ocr/readImage";
    }

    @GetMapping(value = "OcrList")
    public String OcrList(ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".OcrList 시작!");

        List<OcrDTO> rList = Optional.ofNullable(ocrService.getOcrList())
                .orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".OcrList End!");

        return "/ocr/OcrList";
    }

}