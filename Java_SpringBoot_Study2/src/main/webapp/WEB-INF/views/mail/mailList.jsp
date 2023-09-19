<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.MailDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>

<%
    List<MailDTO> rList = (List<MailDTO>) request.getAttribute("rList");
%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>메일 리스트</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<h2>이메일 확인</h2>
<hr/>
<br/>

<% if (rList == null || rList.isEmpty()) { %>
게시글을 찾을 수 없습니다.
<% } else { %>

<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순번</div>
            <div class="divTableHead">받는 사람</div>
            <div class="divTableHead">제목</div>
            <div class="divTableHead">내용</div>
            <div class="divTableHead">발송시간</div>
        </div>
    </div>
    <div class="divTableBody">
        <% for (MailDTO dto : rList) { %>
        <div class="divTableRow">
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getMailSeq())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getToMail())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getTitle())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getContents())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getSendTime())%></div>
        </div>
        <% } %>
    </div>
</div>

<% } %>

</body>
</html>