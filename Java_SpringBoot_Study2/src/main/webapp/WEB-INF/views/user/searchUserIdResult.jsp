<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%
    UserInfoDTO rDTO = (UserInfoDTO) request.getAttribute("rDTO");
    String msg = "";
    if(CmmUtil.nvl(rDTO.getUserId()).length() > 0) {    // 아이디 찾기 성공
        msg = CmmUtil.nvl(rDTO.getUserName()) + "회원님의 " + CmmUtil.nvl(rDTO.getUserId()) + "입니다.";
    } else {
        msg = "아이디가 존재하지 않습니다.";
    }
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%=msg%></title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        //HTML 로딩이 완료되고, 실행됨
        $(document).ready(function (){
            //로그인 화면으로 이동
            $("#btnLogin").on("click", function (){     //버튼 클릭할 때 실행되는 이벤트.
                location.href = "/user/Login";
            })
        })
    </script>
</head>
<body>
<h2>아이디 찾기 결과</h2>
<hr/>
<br/>
<form id = "f">

    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">
                    <%=msg%>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnLogin" type="button">로그인</button>
    </div>

</form>
</body>
</html>