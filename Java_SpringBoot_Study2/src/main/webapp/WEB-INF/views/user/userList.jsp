<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%
    List<UserInfoDTO> rList = (List<UserInfoDTO>) request.getAttribute("rList");
%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>회원 리스트</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        function doDetail(userId) {
            location.href = "/user/userInfo?userId=" + userId;
        }

    </script>
</head>
<body>
<h2>회원정보 확인</h2>
<hr/>
<br/>

<% if (rList == null || rList.isEmpty()) { %>
게시글을 찾을 수 없습니다.
<% } else { %>

<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순번</div>
            <div class="divTableHead">회원아이디 </div>
            <div class="divTableHead">이름</div>
            <div class="divTableHead">이메일</div>
            <div class="divTableHead">주소</div>
        </div>
    </div>
    <div class="divTableBody">
        <% for (UserInfoDTO dto : rList) { %>
        <div class="divTableRow">
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getUserSeq())%></div>
            <div class="divTableCell"
                 onclick="doDetail('<%=CmmUtil.nvl(dto.getUserId())%>')"><%=CmmUtil.nvl(dto.getUserId())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getUserName())%></div>
            <div class="divTableCell"><%=EncryptUtil.decAES128CBC(CmmUtil.nvl(dto.getEmail()))%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getAddr1())%></div>
        </div>
        <% } %>
    </div>
</div>

<% } %>

</body>
</html>