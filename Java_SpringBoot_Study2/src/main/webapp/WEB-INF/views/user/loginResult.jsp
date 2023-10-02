<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%
    UserInfoDTO rDTO = (UserInfoDTO) session.getAttribute("SESSION_USER_INFO");
    String ssUserName = CmmUtil.nvl((String) session.getAttribute("SS_USER_NAME"));
    String ssUserId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>로그인 성공</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {
            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnSend").on("click", function () {
                location.href = "/user/login";
            })

           $("#btnSend2").on("click", function () {
               var userIdParam = "<%= rDTO.getUserId() %>"; // userIdParam 변수에 사용자 아이디 저장
               if(userIdParam !== '') {
                   location.href = "/user/userInfo?userId=" + userIdParam;
               } else {
                   alert("로그인 해주시기 바랍니다.");
               }
           })
        })
    </script>
</head>
<body>
<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">로그인된 사용자이름
            </div>
            <div class="divTableCell"><%=rDTO.getUserName()%>님이 로그인하였습니다.</div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">로그인된 사용자아이디
            </div>
            <div class="divTableCell"><%=rDTO.getUserId()%>입니다.</div>
        </div>
    </div>
</div>
<br/><br/>
<button id="btnSend" type="button">메인 화면 이동</button>
<button id="btnSend2" type="button">회원정보 보기</button>
</body>
</html>