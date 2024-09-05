let index = {
    init: function(){
        $("#btn-admin-delete").on("click", ()=>{
            this.boardDeleteById();
        });

        $("#btn-admin-search").on("click", ()=>{
            this.search();
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
    },

    boardDeleteById: function(){
        let boardId = $("#boardId").text();

        $.ajax({
            type: "DELETE",
            url: "/api/admin/board/"+boardId,
            dataType: "json"
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
        $.ajax({
            type: "DELETE",
            url: `/api/admin/board/${boardId}/reply/${replyId}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
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

    search: function() {
        let keyword = $("#keyword").text();

        $.ajax({
            type: "GET",
            url: `/admin/boards?keyword=${keyword}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
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

    userDeleteById: function(userId){
        $.ajax({
            type: "DELETE",
            url: "/api/admin/user/"+userId,
            dataType: "json"
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
    }
 }

index.init();