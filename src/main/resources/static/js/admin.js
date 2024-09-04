let index = {
    init: function(){
        $("#btn-admin-delete").on("click", ()=>{
            this.deleteById();
        });

        $("#btn-admin-search").on("click", ()=>{
            this.search();
        });
    },

    deleteById: function(){
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
    }
 }

index.init();