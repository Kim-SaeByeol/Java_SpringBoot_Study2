<%@ page import="kopo.poly.dto.MailDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%
        // NoticeController 함수에서 model 객체에 저장된 값 불러오기
        List<MailDTO> rList = (List<MailDTO>) request.getAttribute("rList");
    %>
    <meta charset=UTF-8">
    <title>메일 작성하기</title>
    <link rel="stylesheet" href="/css/table.css">
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML 로딩이 완료되고, 실행됨
        $(document).ready(function () {

            $("#btnSend").on("click", function () {

                // Ajax 호출해서 글 등록하기
                $.ajax({
                        url: "/mail/sendMail",
                        type: "post",
                        dataType: "JSON",
                        data: $("#f").serialize(),
                        success: function (json) {
                            alert(json.msg);
                        }
                    }
                )

            })
        })

    </script>
</head>
<body>
<h2>메일 작성하기</h2>
<hr/>
<br/>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">받는사람</div>
                <div class="divTableCell"><input type="text" name="to_mail" maxlength="100" style="width: 95%"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">메일제목</div>
                <div class="divTableCell"><input type="text" name="title" maxlength="100" style="width: 95%"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">메일내용</div>
                <div class="divTableCell"><textarea name="contents" style="width: 95%; height: 400px"></textarea>
                </div>
            </div>

        </div>
    </div>
    <div>
        <button id="btnSend" type="button">메일 발송</button>
        <button id="reset">다시 작성</button>
    </div>
</form>
</body>
</html>