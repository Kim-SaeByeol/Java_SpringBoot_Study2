<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.FoodDTO" %>
<%
    // NoticeController 함수에서 model 객체에 저장된 값 불러오기
    List<FoodDTO> rList = (List<FoodDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>서울 강서 캠퍼스 식단</title>
    <link rel="stylesheet" href="/css/table.css"/>
</head>
<body>
<h2>이번주 한국 폴리텍 대학 서울강서캠퍼스 점심식사 메뉴는?</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">요일</div>
            <div class="divTableHead">메뉴</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (FoodDTO dto : rList) {
        %>
        <div class="divTableRow">
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getDay())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getFood_nm())%>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
