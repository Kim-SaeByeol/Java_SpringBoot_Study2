package kopo.poly.service.impl;


import kopo.poly.dto.MovieDTO;
import kopo.poly.persistance.mapper.IMovieMapper;
import kopo.poly.service.IMovieService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieService implements IMovieService{

    //RequiredArgsConstructor 이노테이션으로 생성자 자동생성
    // movieMapper 변수에 이미 메모리에 올라간 Mapper 객체를 넣어줌
    // 예전에는 outowired 이노테이션을 통해 설정 했지만, 이제는 생성자를 통해 객체를 주입함.

    private final IMovieMapper movieSMapper;




    @Override
    @Transactional
    public int colletMovieRank() throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".collectMovieRank Start");

        String collectTime = DateUtil.getDateTime("yyyyMMdd");  // 수집 날짜 = 오늘 날짜

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        pDTO = null;    // 기존 등록된 영화 순위 삭제 후, pDTO 값 제거하기
        int res = 0;    //크롤링 결과 (0보다 크면 크롤링 성공)

        // CGV 영화 순위 정보 가져올 사이트 주소
        String url = "http://www.cgv.co.kr/movies/";

        //JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML 소스 저장할 변수
        Document doc = null;

        //사이트 접속(http 프로토콜만 가능, https 프로토콜은 보안상 안됨)
        doc = Jsoup.connect(url).get();

        //CGV 홈페이지의 전체 소스 중 일부 태그를 선택하기 위해 사용
        //<div class = "wrap-movie-chart"> 이 태그 내에서 있는 HTML 소스만 element에 저장
        Elements element = doc.select("div.wrap-movie-chart");


        //Iterator을 사용하여 영화 순위 정보를 가져오기
        // 영화 순위는 기본적으로 1개 이상의 영화가 존재하기 때문에 태그의 반복이 존재할 수 밖에 없음
        Iterator<Element> movie_rank = element.select("strong.rank").iterator();    // 랭크 순위
        Iterator<Element> movie_name = element.select("strong.title").iterator();    // 영화 제목
        Iterator<Element> movie_reserve = element.select("strong.percent span").iterator();    // 영화 예매율
        Iterator<Element> score = element.select("span.percent").iterator();    // 평점
        Iterator<Element> open_day = element.select("span.txt-info strong").iterator();    // 개봉일

        while (movie_rank.hasNext()) {

            pDTO = new MovieDTO();  //수집된 영화정보를 DTO에 저장하기 위해 메모리에 올리기

            //수집기간을 기본키(pk)로 사용
            pDTO.setCollectTime(collectTime);

            //영화 순위(trim 함수 추가 이유 : trim 함수는 글자의 앞뒤 공백 삭제 역할을 수행하며,
            //데이터 수집시에 홈페이지 개발자들이 앞뒤 공백을 집어넣을 수 있어서 추가)

            String rank = CmmUtil.nvl(movie_rank.next().text().trim()); //No.1 들어옴
            pDTO.setMovieRank(rank.substring(3, rank.length()));

            //영화제목
            pDTO.setMovieNm(CmmUtil.nvl(movie_name.next().text().trim()));

            //영화 예매율
            pDTO.setMovieReserve(CmmUtil.nvl(movie_reserve.next().text().trim()));

            //영화 점수
            pDTO.setScore(CmmUtil.nvl(score.next().text().trim()));

            //영화 개봉일
            pDTO.setOpenDay(CmmUtil.nvl(open_day.next().text().trim().substring(0, 10)));

            //등록자
            pDTO.setRegId("김새별");

            //영화 한개씩 추가
            res += movieSMapper.insertMovieInfo(pDTO);
        }

        //로그 찍기
        log.info(this.getClass().getName() + ".collectMovieRank End");

        return res;
    }

    @Override
    public List<MovieDTO> getMovieInfo() throws Exception{
        //로그 찍기
        log.info(this.getClass().getName() + ".getMovieInfo Start!");

        String collectTime = DateUtil.getDateTime("yyyyMMdd");  // 수집 날짜 = 오늘 날짜

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        //DB에서 조회하기
        List<MovieDTO> rList = movieSMapper.getmovieInfo(pDTO);

        //로그찍기
        log.info(this.getClass().getName() + ".getMovieInfo End!");

        return rList;
    }
}

