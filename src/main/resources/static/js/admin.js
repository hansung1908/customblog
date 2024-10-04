let index = {
    init: function(){
        $("#btn-admin-delete").on("click", ()=>{
            this.boardDeleteById();
        });

        $("#btn-admin-board-search").on("click", ()=>{
            this.boardSearch();
        });

        $(document).on("click", "button[data-user-id]", function() {
            let userId = $(this).data("user-id");

            // 문자열 보간을 사용하여 사용자 ID를 메시지에 삽입
            let message = `Are you sure you want to delete user with ID "${userId}"?`;

            // confirm 대화상자를 띄우고, 사용자가 확인을 누르면 delete 작업 수행
            if (confirm(message)) {
                index.userDeleteById(userId);
            }
        });

        $("#btn-admin-user-search").on("click", ()=>{
            this.userSearch();
        });

        $("#btn-notice-save").on("click", ()=>{
            this.noticeSave();
        });

        $("#btn-admin-notice-search").on("click", ()=>{
            this.noticeSearch();
        });

        $(document).on("click", "button[data-notice-title]", function() {
            let noticeTitle = $(this).data("notice-title");

            let message = `Are you sure you want to delete notice with Title "${noticeTitle}"?`;

            if (confirm(message)) {
                index.noticeDeleteByTitle(noticeTitle);
            }
        });
    },

    boardDeleteById: function(){
        let boardId = $("#boardId").text();

        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "DELETE",
            url: "/api/admin/board/"+boardId,
            dataType: "json",
            headers: {
                'X-CSRF-TOKEN': csrfToken // CSRF 토큰 추가
            }
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
                location.href = "/admin/boards";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    replyDelete: function(boardId, replyId) {
        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "DELETE",
            url: `/api/admin/board/${boardId}/reply/${replyId}`,
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
                location.href = "/admin/board/"+boardId;
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    boardSearch: function() {
        let boardKeyword = $("#boardKeyword").text();

        $.ajax({
            type: "GET",
            url: `/admin/boards?keyword=${boardKeyword}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status !== 200) {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    userDeleteById: function(userId){
        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "DELETE",
            url: "/api/admin/user/"+userId,
            dataType: "json",
            headers: {
                'X-CSRF-TOKEN': csrfToken // CSRF 토큰 추가
            }
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
                location.href = "/admin/users";
            } else {
                 alert(JSON.stringify(resp.data));
            }
        });
    },

    userSearch: function() {
        let userKeyword = $("#userKeyword").text();

        $.ajax({
            type: "GET",
            url: `/admin/users?keyword=${userKeyword}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status !== 200) {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    noticeSave: function(){
        let data = {
            noticeType: $("#noticeType").val().toUpperCase(),
            title: $("#title").val(),
            content: $("#content").val()
        }

        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "POST",
            url: "/api/admin/notice",
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
                location.href = "/admin/dashboard";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    noticeSearch: function() {
        let noticeKeyword = $("#noticeKeyword").text();

        $.ajax({
            type: "GET",
            url: `/admin/dashboard?keyword=${noticeKeyword}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status !== 200) {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    noticeDeleteByTitle: function(noticeTitle){
        // CSRF 토큰값 가져오기
        const csrfToken = $('input[name="_csrf"]').val();

        $.ajax({
            type: "DELETE",
            url: "/api/admin/notice",
            data: JSON.stringify(noticeTitle),
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
                location.href = "/admin/dashboard";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    }
 }

index.init();