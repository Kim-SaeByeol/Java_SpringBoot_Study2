<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.MovieDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.MovieDTO" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%
    // NoticeController 함수에서 model 객체에 저장된 값 불러오기
    List<MovieDTO> rList = (List<MovieDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>영화 순위 결과</title>
    <link rel="stylesheet" href="/css/table.css"/>


</head>
<body>
<h2>영화 순위 결과</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순위</div>
            <div class="divTableHead">제목</div>
            <div class="divTableHead">평점</div>
            <div class="divTableHead">개봉일</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (MovieDTO dto : rList) {
        %>
        <div class="divTableRow">


            <div class="divTableCell"><%=CmmUtil.nvl(dto.getMovieRank())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getMovieNm())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getScore())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getOpenDay())%>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
