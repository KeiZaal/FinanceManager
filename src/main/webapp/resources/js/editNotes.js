$(document).ready(function () {
    $('.notes').on('click', '.editBtn', function () {
        var note = $(this).closest('.tableRow').find(".note");
        note.attr('contenteditable', 'true');
        note.focus();
    });

    $('.notes').on('keyup', function (event) {
        var esc = event.which == 27;
        var element = event.target;
        if (esc) {
            document.execCommand('undo');
            element.blur();
        }
    });

    $('.notes').on('submit', '.saveNote', function (event) {
        event.preventDefault();
        var newNote = $(this).closest('.tableRow').find('.note').text();
        $(this).find('[name=note]').attr('value', newNote);
        var form = $(this);
        var data = form.serializeArray();
        var objectifyData = objectifyForm(data);
        var jsonData = JSON.stringify(objectifyData);
        $.ajax({
            type: 'POST',
            beforeSend: function (request) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(header, token);
            },
            contentType: 'application/json; charset=UTF-8',
            url: form.attr('action'),
            data: jsonData
        }).done(function (data) {

        }).fail(function (error) {
            form.find(".with-errors").each(function () {
                $(this).addClass("fail");
                $(this).text("Введите корректные данные");
            });
        });
    });
});
function objectifyForm(formArray) {  //serialize data function
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}
