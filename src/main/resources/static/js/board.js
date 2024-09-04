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
        // FormData 객체 생성
        let formData = new FormData();

        // 게시판 데이터 추가
        formData.append('board', JSON.stringify({
            title: $("#title").val(),
            content: $("#content").val()
        }));

        // 파일 데이터 추가
        let fileInput = $('#file')[0];
        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]);
        }

        // ajax를 이용한 비동기 호출 활용
        $.ajax({
            type: "POST",
            url: "/api/board",
            data: formData, // HTTP body 데이터
            contentType: false, // 브라우저가 자동으로 'multipart/form-data'를 설정, 요청 타입
            processData: false, // jQuery가 데이터를 문자열로 변환하지 않도록 설정
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
        let boardId = $("#boardId").val();

        $.ajax({
            type: "DELETE",
            url: "/api/board/"+boardId,
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
        let boardId = $("#boardId").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "PUT",
            url: "/api/board/"+boardId,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).always(function(resp) {
            console.log("HTTP Status Code: " + resp.status);
            console.log("Response Text: ", resp.data);

            if (resp.status === 200) {
                alert(JSON.stringify(resp.data));
                location.href = "/board/"+boardId;
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
                location.href = "/board/"+data.boardId;
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
                location.href = "/board/"+boardId;
            } else {
                alert(JSON.stringify(resp.data));
            }
        });
    }
}

index.init();