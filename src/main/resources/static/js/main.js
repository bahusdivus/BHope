$(function () {
   $('.like').on("click", function(e) {
       e.preventDefault();
       var $link = $(this);
       $.ajax({
           type: 'POST',
           contentType: "application/json",
           url: $link.parent().attr("action"),
           data: JSON.stringify(getFormData($link.parent())),
           dataType: 'json',
           cache: false,
           beforeSend: function(request) {
               request.setRequestHeader('Csrf-Token', $('input[name="_csrf"]').attr('value'));
               request.setRequestHeader('X-CSRF-Token', $('input[name="_csrf"]').attr('value'));
           },
           success: (function (data) {
               $link.children('span').html(data);
           })
       })
   });

   $('.edit').on("click", function (e) {
       e.preventDefault();
       $.ajax({
           type: 'GET',
           url: $(this).attr("href"),
           cache: false,
           beforeSend: function(request) {
               request.setRequestHeader('Csrf-Token', $('input[name="_csrf"]').attr('value'));
               request.setRequestHeader('X-CSRF-Token', $('input[name="_csrf"]').attr('value'));
           },
           success: (function (data) {
               $('#edit-modal').find(".modal-content").empty().append(data);
           })
       })
   });
});

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};
    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    return indexed_array;
}