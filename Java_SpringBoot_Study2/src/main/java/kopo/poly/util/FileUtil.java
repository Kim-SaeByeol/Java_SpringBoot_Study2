package kopo.poly.util;

import java.io.File;

public class FileUtil {

    public static String mkdirForDate(String uploadDir) {


        // 파일을 저장하기 위한 폴더는 /년/월/일로 폴더를 생성함
        // 보통 기업들은 30일이 지나면 메모리를 비우기 위해
        // 기간이 지난 파일들을 삭제하게 되는데
        // 이때 /년/월/일로 폴더를 생성하면 관리가 용이함.
        // + 삼성의 갤럭시폰 갤러리. 시간 순서대로 업로드 됨.
        String path = uploadDir + DateUtil.getDateTime("/yyyy/MM/dd");

        File Folder = new File(path);

        if (!Folder.exists()) {
            Folder.mkdirs();

        }
        return path;
    }
}