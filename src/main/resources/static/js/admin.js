let index = {
    init: function(){
        $("#btn-admin-delete").on("click", ()=>{
            this.deleteById();
        });
    },

    deleteById: function(){
        let id = $("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/admin/board/"+id,
            dataType: "json"
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

    adminReplyDelete: function(boardId, replyId) {
        $.ajax({
            type: "DELETE",
            url: `/admin/board/${boardId}/reply/${replyId}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
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