let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });

        $("#btn-login").on("click", ()=>{
            this.login();
        });
    },

    save: function(){
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }

        // ajax를 이용한 비동기 호출 활용
        $.ajax({
            type: "POST",
            url: "/api/user",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // 요청 타입
            dataType: "json" // 응답 타입
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    login: function(){
        let data = {
            username: $("#username").val(),
            password: $("#password").val()
        }

        $.ajax({
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("로그인이 완료되었습니다.");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();