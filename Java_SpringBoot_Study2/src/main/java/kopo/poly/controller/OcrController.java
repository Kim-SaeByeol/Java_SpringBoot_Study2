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
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private String fullFileInfo = "";

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

            String fullFileInfo = saveFilePath + saveFileName;

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

    @GetMapping("/download")
    public void download(@RequestParam("filePath") String filePath, HttpServletResponse response) {
        log.info(this.getClass().getName() + ".다운로드 컨트롤 시작~");
        OcrDTO pDTO = new OcrDTO();

        log.info("원래 파일 이름 : " + pDTO.getSaveFileName());
        log.info("저장할 파임 이름 : " + pDTO.getOrgFileName());
        try {
            String decodedFilePath = URLDecoder.decode(filePath, String.valueOf(StandardCharsets.UTF_8));
            Path file = Paths.get(decodedFilePath.replace("\\", "/"));
            log.info("다운로드 받을 파일 경로 : " + file);

            if (Files.exists(file) && Files.isRegularFile(file)) {
                log.info("파일이 존재합니다~");
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                response.setHeader("Content-Disposition", "attachment; filename=" + pDTO.getOrgFileName());

                try (InputStream inputStream = Files.newInputStream(file)) {
                    IOUtils.copy(inputStream, response.getOutputStream());
                    log.info("파일 다운로드 하라고 서버에 전달 했어요");
                    response.flushBuffer();
                }
            } else {
                log.error("해당 경로에 같은 파일이 없음! : " + decodedFilePath);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(this.getClass().getName() + ".다운로드 컨트롤 에러~", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            log.info(this.getClass().getName() + ".다운로드 컨트롤 끝~");
        }
    }


}