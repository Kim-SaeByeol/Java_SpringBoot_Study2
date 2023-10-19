<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GPT Test</title>
    <!-- jQuery를 사용하기 위한 CDN 추가 -->
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
</head>
<body>

<h1>GPT Test</h1>

<div>
    <label for="userMessage">사용자 입력: </label>
    <input type="text" id="userMessage" name="userMessage">
    <button onclick="sendUserMessage()">전송</button>
</div>

<hr>

<div>
    <p id="gptResponse"></p>
</div>

<script>
    function sendUserMessage() {
        var userMessage = $("#userMessage").val();

        // AJAX를 이용하여 서버에 사용자 메시지를 전송
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/v1/gpt-test/chat",
            data: JSON.stringify({ "userMessage": userMessage }),
            dataType: "json",
            success: function(response) {
                // 서버에서 받아온 응답을 화면에 출력
                $("#gptResponse").text("GPT 응답: " + response.gptMessage);
            },
            error: function() {
                alert("오류가 발생했습니다.");
            }
        });
    }
</script>

</body>
</html>
