let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
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
            url: "/auth/joinProc",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // 요청 타입
            dataType: "json" // 응답 타입
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert("회원 가입이 완료되었습니다.");
                location.href = "/";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    update: function(){
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }

        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert("회원 수정이 완료되었습니다.");
                location.href = "/";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    }
}

index.init();