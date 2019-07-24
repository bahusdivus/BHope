$(function () {
   $('.like').on("click", function(e) {
       e.preventDefault();
       $.ajax({
           url: '/incrementLikeCount',
           contentType: "application/json",
           type: 'POST',
           data: JSON.stringify({"postId" : $(this).data("id")}),
           dataType: 'json',
           success: (function (data) {
               $(this).children("span").text(data);
           })
       })
   });
});