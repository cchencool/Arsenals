$(document).ready(function(){

  $("button#btn1").click(function(){
        doPost1();
  });

  $("button#btn2").click(function(){
          doPost2();
    });
});

//var baseUrl = "http://127.0.0.1:8080/"

var baseUrl = "/"

function doPost1() {

    data = {}
    data["name"] = "zhang"
    data["time"] = "now"

    $.ajax({
        type : "post",
        url : baseUrl + "init",
        contentType : 'application/json;charset=UTF-8',
        dataType : "json",
        data: JSON.stringify(data),
        success: function(msg){

            ret = msg["result"]
            // alert( "Data received: " + ret )

            $("span#me").text(ret);
        },
        error: function(msg) {
            console.info(msg)
        }
    });
}

function doPost2() {

    data = {}
    data["name"] = "zhang"
    data["time"] = "now"

    $.ajax({
        type : "get",
        url : baseUrl + "init2?name=xxi&&time=now",
        contentType : 'application/json;charset=UTF-8',
//        data: JSON.stringify(data),
        success: function(msg){

            if ("string" == typeof(msg)) {
                ret = msg
            } else {
                ret = msg["result"]
            }
            // alert( "Data received: " + ret )

            $("span#me").text(ret);
        },
        error: function(msg) {
            console.info(msg)
        }
    });
}