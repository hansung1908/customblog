let index = {
    init: function() {
        $("#btn-search").on("click", ()=>{
            this.search();
        });
    },

    search: function(keyword) {
        let keyword = $("#keyword").text();

        $.ajax({
            type: "GET",
            url: `/index?keyword=${keyword}`,
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