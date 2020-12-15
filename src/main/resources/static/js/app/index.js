// post
const main = {
    init : function () {
        const _this = this;

        //등록
        $('#btn-save').on('click', function () {
            _this.save();
        });

        //수정
        $('#btn-update').on('click', function() {
            _this.update();
        })

        //삭제
        $('#btn-delete').on('click',function () {
            _this.delete();
        })

    }, //init
    save : function () {
        const data = {
            title   : $('#title').val(),
            userId  : $('#useId').val(),
            content : $('#content').val()
        };

        $.ajax({
            type        : 'POST',
            url         : '/api/post',
            dataType    : 'json',
            contentType : 'application/json; charset=UTF-8',
            data        : JSON.stringify(data)
        }).done(function () {
            alert('등록되었습니다.');
            window.location.href = '/post/list';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    update : function () {
        const data = {
            title   : $('#title').val(),
            content : $('#content').val()
        };

        const id = $('#id').val();

        $.ajax({
            type        : 'PUT',
            url         : '/api/post/'+id,
            dataType    : 'json',
            contentType : 'application/json; charset=UTF-8',
            data        : JSON.stringify(data)
        }).done(function(){
            alert('수정되었습니다.');
            window.location.href = '/post/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }, //update
    delete : function () {
        const id = $('#id').val();

        $.ajax({
            type        : 'DELETE',
            url         : '/api/post/'+id,
            dataType    : 'json',
            contentType : 'application/json; charset=UTF-8'
        }).done(function () {
            alert('삭제되었습니다.');
            window.location.href = '/post/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    } //delete
} //main

main.init();