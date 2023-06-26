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
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // 요청 타입
            dataType: "json" // 응답 타입
        }).done(function(resp){
            alert("글쓰기가 완료되었습니다.");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

   deleteById: function(){
       let id = $("#id").text();

       $.ajax({
           type: "DELETE",
           url: "/api/board/"+id,
           dataType: "json"
       }).done(function(resp){
           alert("삭제가 완료되었습니다.");
           location.href = "/";
       }).fail(function(error){
           alert(JSON.stringify(error));
       });
   },

   update: function(){
       let id = $("#id").val();

       let data = {
           title: $("#title").val(),
           content: $("#content").val()
       }

       // ajax를 이용한 비동기 호출 활용
       $.ajax({
           type: "PUT",
           url: "/api/board/"+id,
           data: JSON.stringify(data), // http body 데이터
           contentType: "application/json; charset=utf-8", // 요청 타입
           dataType: "json" // 응답 타입
       }).done(function(resp){
           alert("글수정이 완료되었습니다.");
           location.href = "/";
       }).fail(function(error){
           alert(JSON.stringify(error));
       });
   }
}

index.init();