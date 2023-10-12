package kopo.poly.service.impl;


import kopo.poly.dto.FoodDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class FoodService implements IFoodService {
    @Override
    public List<FoodDTO> toDayFood() throws Exception {

        log.info(this.getClass().getName() + "toDayFood Start!");

        int res = 0;    //크롤링 여부를 판단할 res. 0보다 크면 크롤링 성공. => 값이 들어갔으니 0보다 큼.

        //크롤링 할 사이트 주소. => 강서캠퍼스 식단표
        String url = "https://www.kopo.ac.kr/kangseo/content.do?menu=262";

        //JSOP 라이브러리를 통해 사이트에 접속되면, 그 사이트의 전체 HTML 소스를 저장할 변수.
        Document doc = null;

        //사이트 접속(http 프로토콜만 가능 https는 보안상 안됨)
        doc = Jsoup.connect(url).get();

        Elements element = doc.select("table.tbl_table.menu tbody");

        //Iterator을 사용하여 식단정보 가져오기
        Iterator<Element> foodIt = element.select("tr").iterator();

        FoodDTO pDTO = null;

        List<FoodDTO> pList = new ArrayList<>();


        int idx = 0;    //아래 while의 반복 횟수를 위한 변수.

        //수집된 데이터 DB에 저장하기
        while (foodIt.hasNext()) {

            //반복횟수 카운팅하기, 5번째가 금요일. 6번째는 실행될 필요가 없음.
            //반복문은 5번이면 충분함.
            if (idx++ > 4) {
                break;
        }

        pDTO = new FoodDTO();   //수집된 식단정보를 DTO에 저장하기 위한 new 생성자.

        /**
         * 수집되는 데이터 예)
         * 월요일 밥, 국, 반찬, 반찬, 반찬, 반찬
         * 화요일 밥, 국, 반찬, 반찬, 반찬, 반찬
         * 수요일 밥, 국, 반찬, 반찬, 반찬, 반찬
         * 목요일 밥, 국, 반찬, 반찬, 반찬, 반찬
         * 금요일 밥, 국, 반찬, 반찬, 반찬, 반찬
         */

        //요일별 식단 정보 들어옴
        String food = CmmUtil.nvl(foodIt.next().text().trim());

        //요일별 식단 정보
        log.info("food : " + food);

        //앞의 3글자가 요일이기 때문에 요일 저장
        pDTO.setDay(food.substring(0, 3));

        //식단 정보
        pDTO.setFood_nm(food.substring(4));

        pList.add(pDTO);
    }
        log.info(this.getClass().getName() + "toDayFood End!");

        return pList;
    }
}
