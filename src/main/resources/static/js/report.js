let index = {
    init: function() {
        $("#btn-report").on("click", ()=>{
            this.report();
        });
    },

    report: function() {
        let boardId = $("#boardId").val();
        let reason = $("#report-reason").text();

        $.ajax({
            type: "POST",
            url: "api/report",
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