<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.WeatherDTO" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>서울 강서 캠퍼스 날씨 정보</title>
    <link rel="stylesheet" href="/css/table.css"/>
</head>
<body>
<h2>이번주 한국 폴리텍 대학 서울강서캠퍼스 날씨 정보</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">장소 이름</div>
            <div class="divTableHead">온도</div>
            <div class="divTableHead">날씨</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            WeatherDTO dto = (WeatherDTO) request.getAttribute("rList");
        %>
        <div class="divTableRow">
            <div class="divTableCell"><%= CmmUtil.nvl(dto.getLocation_name()) %></div>
            <div class="divTableCell"><%= CmmUtil.nvl(dto.getTemperature()) %></div>
            <div class="divTableCell"><%= CmmUtil.nvl(dto.getWeather()) %></div>
        </div>
    </div>
</div>
</body>
</html>
