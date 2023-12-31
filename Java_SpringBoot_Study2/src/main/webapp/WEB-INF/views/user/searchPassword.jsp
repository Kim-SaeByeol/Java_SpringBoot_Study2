<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // 이메일 인증번호 변수 설정
        let emailAuthNumber = "";

        //Html 로딩이 완료되고 실행됨
        $(document).ready(function () {

            //로그인 화면 이동
            $("#btnLogin").on("click", function () { // 버튼을 클락하면 발생되는 이벤트 생성.
                location.href = "/user/login"
            })

            //비밀번호 찾기
            $("#btnSearchPassword").on("click", function () {
                let f = document.getElementById("f");   //form 태그
                if (f.userId.value === "") {
                    alert("아이디을 입력하세요.");
                    f.userId.focus();
                    return;
                }
                if (f.userName.value === "") {
                    alert("이름을 입력하세요.");
                    f.userName.focus();
                    return;
                }
                if (f.email.value === "") {
                    alert("이메일을 입력하세요.");
                    f.email.focus();
                    return;
                }

                if (f.authNumber.value === "") {
                    alert("이메일 인증번호를 입력하세요.");
                    f.authNumber.focus();
                    return;
                }

                if (checkemailAuthNumber !== "Y"){
                    alert("인증번호 확인 버튼을 다시 눌러주세요.");
                    return;
                }

                f.method = "post"   //비밀번호 찾기 정보 전송방식
                f.action = "/user/searchPasswordProc" //비밀번호 찾기 URL
                f.submit(); //비밀번호 찾기 정보 전송하기.
            })

            // 이메일 중복 확인하는 함수 실행
            $("#btnEmail").on("click", function () {
                emailExists(f)
            })

            // 이메일 중복을 확인하는 함수
            function emailExists(f) {

                if (f.email.value === "") {
                    alert("이메일을 입력하세요.");
                    f.email.focus();
                    return;
                }

                $.ajax({
                        url: "/user/getcheckAuthNumber",
                        type: "post",
                        dataType: "JSON",
                        data: $("#f").serialize(),
                        success: function (json) {
                            if (json.existsYn === "Y") {
                                alert("이메일로 인증번호가 발송되었습니다. \n받은 메일의 인증번호를 입력하기 바랍니다.");
                                emailAuthNumber = json.authNumber;
                            } else {
                                alert("해당 이메일은 등록되지 않은 이메일 입니다.");
                                return;
                            }
                        }
                    }
                )
            }

            let checkemailAuthNumber = "N";
            // 이메일 인증번호를 확인하는 함수 실행
            $("#btnauthNumber").on("click", function () {
                nauthNumberExists(f)
            })

            // 이메일 인증번호를 확인하는 함수
            function nauthNumberExists(f) {

                //인증번호가 같다면 Y 를 반환.
                if (f.authNumber.value !== emailAuthNumber.toString()) {
                    alert("이메일 인증번호가 일치하지 않습니다.");
                    f.authNumber.focus();
                    return;
                } else {
                    alert("인증번호 확인 되었습니다.");
                    checkemailAuthNumber = "Y";
                }

            }



        })
    </script>
</head>
<body>

<h2>비밀번호 찾기</h2>
<hr/>
<br/>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">아이디</div>
                <div class="divTableCell">
                    <input type="text" name="userId" id="userId" style="width:80%" placeholder="아이디"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">이름</div>
                <div class="divTableCell">
                    <input type="text" name="userName" id="userName" style="width:80%" placeholder="이름"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">이메일</div>
                <div class="divTableCell">
                    <input type="email" name="email" id="email" style="width:40%" placeholder="이메일주소"/>
                    <button id="btnEmail" type="button">인증번호 발송</button>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">인증번호</div>
                <div class="divTableCell">
                    <input type="text" name="authNumber" id="authNumber" style="width:40%" placeholder="이메일로 발송된 인증번호"/>
                    <button id="btnauthNumber" type="button">인증번호 확인</button>
                </div>
            </div>
        </div>
    </div>
    </div>
    <div>
        <button id="btnSearchPassword" type="button">비밀번호 찾기</button>
        <button id="btnLogin" type="button">로그인</button>
    </div>
</form>
</body>
</html>