let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });

        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
        });

        $("#btn-reply-save").on("click", ()=>{
            this.replySave();
        });
    },

    save: function(){
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        // ajax를 이용한 비동기 호출 활용
        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data), // HTTP body 데이터
            contentType: "application/json; charset=utf-8", // 요청 타입
            dataType: "json" // 응답 타입
        }).always(function(resp) {  // 요청이 성공하든 실패하든 항상 호출
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            // 응답 코드에 따라 다르게 호출
            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
                location.href = "/";
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    },

    deleteById: function(){
        let id = $("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType: "json"
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
        let id = $("#id").val();

        let data = {
        title: $("#title").val(),
        content: $("#content").val()
        }

        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
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

    replySave: function(){
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#reply-content").val()
        }

        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
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

    replyDelete: function(boardId, replyId) {
        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
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