// var userSession = JSON.parse(sessionStorage.getItem('user'));
// var tokenSession = sessionStorage.getItem('token');
// // console.log(userSession);
// // if (userSession == null || tokenSession == null) {
// //     window.location.href = "login.html";
// // } else {
// //     var userId = userSession.userId;
// //     document.getElementById("user-headimg").src = userSession.headImage;
// // }

// var userId = 2;
// var username = "wuziyi";

if (user == null) {
    window.location.href = "login.html";
}

$("#inputfile").fileinput({
    language: "zh",
    autoOrientImage: false,
    dropZoneTitle: "Bask in the product picture！",
    showUploadStats: false,
    showUpload: false,
    allowedFileExtensions: ['jpg', 'png', 'jpeg', 'gif', 'bmp', 'AVI', 'mov', 'rmvb', 'rm', 'FLV', 'mp4']
        // [jpg | png | jpeg | gif | bmp | AVI | mov | rmvb | rm | FLV | mp4]
})

toastr.options.positionClass = 'toast-center-center';
// 跳转处理
function getUrlArgs() {
    var url = location.href;
    var i = url.indexOf('?');
    if (i == -1) return;
    var querystr = url.substr(i + 1);
    var arr1 = querystr.split('&');
    var arr2 = new Object();
    for (i in arr1) {
        var t = arr1[i].split('=');
        arr2[t[0]] = t[1];
    }
    return arr2;
}

var childOrderId;
if (getUrlArgs() != undefined) {
    var data = getUrlArgs();
    if (data.id != undefined) {
        getProductInfo(data.id);
    } else {
        window.location.href = 'PersonalInformation.html';
    }
} else {
    window.location.href = 'PersonalInformation.html';
}

function getProductInfo(id) {
    $.ajax({
        url: "/api/order/findProductById/" + id,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
            childOrderId = result.data.childOrderId;
            document.getElementById("productImage").src = result.data.product.image.split("、")[0];
            document.getElementById("productName").innerHTML = result.data.product.productName;
            $("#productName").attr("href", "CommodityDetails.html?productId=" + result.data.product.productId);
        }
    })
}

var images = new Array();
//发布商品 --图片上传监听
$("#inputfile").on("filebatchselected", function(event, files) {
    images = files;
});

document.getElementById("evaluation-submit").addEventListener('click', function() {
    var rate = $('input[name=rate]:checked').val();
    if (rate == undefined) {
        toastr.error('好评、中评、差评，必须选一个！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    }

    var anonymity = $('input[name=anonymity]:checked').val();
    var content = $("#evaluation-content").val();

    var commentary_json = "{'childOrderId': '" + childOrderId + "', 'userId': " + userId + ", 'username': '" +
        username + "', 'rate': " + rate + ", 'commentaryContent': '" + content + "', 'anonymity': '" + anonymity + "'}"
    var formData = new FormData();
    formData.append("commentary_json", commentary_json);
    for (i = 0; i <= images.length; i++) {
        formData.append("commentary_images", images[i]);
    }

    $.ajax({
        url: "/api/commentary/insertCommentary",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        dataType: "json",
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
            if (result.data == "插入评价信息成功") {
                toastr.success('评价完成！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                setTimeout(function() {
                    window.location.href = 'PersonalInformation.html';
                }, 1000);
            }
        }
    });
});

document.getElementById("evaluation-cancel").addEventListener('click', function() {
    window.location.href = 'PersonalInformation.html';
});