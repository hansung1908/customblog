let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
        });

        $("#btn-checkName").on("click", ()=>{
            this.checkName();
        });

        $(document).on("click", "button[data-user-name]", function() {
            let username = $(this).data("user-name");

            let message = `Are you sure you want to delete user with username "${username}"?`;

            if (confirm(message)) {
                index.userDeleteByUsername(username);
            }
        });
    },

    save: function(){
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }

        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        // ajax를 이용한 비동기 호출 활용
        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // 요청 타입
            dataType: "json", // 응답 타입
            headers: {
                'X-CSRF-TOKEN': csrfToken // CSRF 토큰 추가
            }
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
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

        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "PUT",
            url: "/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: {
                'X-CSRF-TOKEN': csrfToken // CSRF 토큰 추가
            }
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
                location.href = "/";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    checkName: function(){
        let username = $("#username").val();

        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "POST",
            url: "/auth/joinProc/checkName",
            data: JSON.stringify(username),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: {
                'X-CSRF-TOKEN': csrfToken // CSRF 토큰 추가
            }
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    userDeleteByUsername: function(username){
        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "DELETE",
            url: "/api/user",
            data: JSON.stringify(username),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: {
                'X-CSRF-TOKEN': csrfToken // CSRF 토큰 추가
            }
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
                location.href = "/";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    }
}

index.init();