console.log("===================Apply Module===================")
let applyService = (() => {
    //apply.js 등록 처리 -  연관있는 html register
    function add(apply, callback, error) {
        console.log("apply....");
        $.ajax({
            type: 'POST',
            url: '/apply/register',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(apply),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        }).done(function (response) {
            console.log(JSON.stringify(response))
        }).fail(function (error) {
            console.log(JSON.stringify(error));
            console.log(error);
        });
    }

    function getList(param, callback, error) {
        let page = param.page || 1;

        $.getJSON("/apply/list/" + page + ".json",
            function (data) {
                if (callback) {
                    callback(data);
                }
            }).fail(function (xhr, status, err){
                if(error){
                    error();
                }
        })
    }

    function read(param, callback, error) {
        $.getJSON("/apply/read/" + param.applyId + ".json",
            function (data) {
                if (callback) {
                    callback(data);
                }
            }).fail(function (xhr, status, err){
            if(error){
                error();
            }
        })
    }

    function remove(param){
        $.ajax({
            type: 'delete',
            url: '/apply/delete/'+param.applyId,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        }).done(function (response) {
            console.log(JSON.stringify(response))
        }).fail(function (error) {
            console.log(JSON.stringify(error));
            console.log(error);
        });
    }
    return {
        add: add,
        getList: getList,
        read: read,
        remove: remove
    };
})();