var userSession = JSON.parse(sessionStorage.getItem('user'));
var tokenSession = sessionStorage.getItem('token');
// if (userSession == null || tokenSession == null) {
//     window.location.href = "login.html";
// } else {
//     var userId = userSession.userId;
//     document.getElementById("user-headimg").src = userSession.headImage;
// }

// var userId = userSession.userId;
// var username = userSession.username;
var productId = getUrlArgs().productId;

var userId = 2;
var username = "wuziyi";

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

//发布商品 
toastr.options.positionClass = 'toast-center-center';

function divHasChanged(value, str, divId) {
    if (value.indexOf(str) == 0) {
        divId.css('display', 'inline-block');
    } else {
        divId.css('display', 'none');
    }
}

function divNoHasChanged(value, str, divId) {
    if (value.indexOf(str) == 0) {
        divId.css('display', 'none');
    } else {
        divId.css('display', 'block');
    }
}

function typeChanged(value) {
    if (!pTypeMap.get(value)) {
        return false;
    } else {
        document.getElementById('productType2').innerHTML = ``;
    }

    for (var item in pTypeMap.get(value)) {
        var optionItem = document.createElement('option');
        optionItem.innerHTML = pTypeMap.get(value)[item];
        $('#productType2').append(optionItem);
        $('#productType2').selectpicker('refresh');
    }

    divHasChanged(value, '美食馆', $('#expirationTimeDiv'));
    divNoHasChanged(pType1[0], '美食馆', $('#qualityTypeDiv'));
}

var pType1 = new Array(),
    pType2 = new Array();
var pTypeMap = new Map();
$.ajax({
    url: "/api/product/findAllType",
    type: "GET",
    timeout: 15000,
    beforeSend: function() {
        $("#loading").html("<img src='img/loading.gif' />");
    },
    success: function(result) {
        $("#loading").empty();
        $.each(result.data, function(index, data) {
            var types = data.typeName.split("-");
            if (pTypeMap.has(types[0])) {
                pTypeMap.get(types[0]).push(types[1]);
            } else {
                pTypeMap.set(types[0], new Array(types[1]));
            }
        });
        for (var item of pTypeMap) {
            pType1.push(item[0]);
            pType2.push(item[1]);
        }
        for (var i in pType1) {
            var str = `<option> ${pType1[i]} </option>`;
            $('#productType1').append(str);
        }
        $('#productType1').selectpicker('refresh');

        typeChanged(pType1[0]);

        divHasChanged(pType1[0], '美食馆', $('#expirationTimeDiv'));
        divNoHasChanged(pType1[0], '美食馆', $('#qualityTypeDiv'));

    }
})

$.ajax({
    url: "/api/product/findAllQuality",
    type: "GET",
    timeout: 15000,
    beforeSend: function() {
        $("#loading").html("<img src='img/loading.gif' />");
    },
    success: function(result) {
        $("#loading").empty();
        $.each(result.data, function(index, data) {
            var str = `<option> ${data.qualityName} </option>`;
            $('#qualityType').append(str);
        });
        $('#qualityType').selectpicker('refresh');
    }
})
$.ajax({
    url: "/api/product/findAllTrade",
    type: "GET",
    timeout: 15000,
    beforeSend: function() {
        $("#loading").html("<img src='img/loading.gif' />");
    },
    success: function(result) {
        $("#loading").empty();
        $.each(result.data, function(index, data) {
            var str = `<option> ${data.tradeName} </option>`;
            $('#tradeType').append(str);
        });
        divHasChanged(result.data[0].tradeName, '校内交易', $('#tradeDescriptionDiv'));
        $('#tradeType').selectpicker('refresh');
    }
})

// 商品信息渲染
var productInfo;
var showImagesList = new Array(),
    detailImagesList = new Array();
getProductInfo(productId);

function getProductInfo(id) {
    $.ajax({
        url: "/api/product/findProductByProductId/" + id + "/" + userId,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log(result);

            document.getElementById("productName").value = result.data.productName;
            document.getElementById("productPrice").value = result.data.price;
            document.getElementById("productNumber").value = result.data.number;
            document.getElementById("productDescription").value = result.data.productDecription;
            document.getElementById("expirationTimeInput").value = result.data.expirationTime;

            var productType = result.data.typeName.split("-");
            document.getElementById("productType1").value = productType[0];
            $.each(pType1, function(index, data) {
                if (data == productType[0]) {
                    typeChanged(pType1[index]);
                }
            })
            document.getElementById("productType2").value = productType[1];
            $('#productType1').selectpicker('refresh');
            $('#productType2').selectpicker('refresh');

            document.getElementById("qualityType").value = result.data.qualityName;
            $('#qualityType').selectpicker('refresh');

            document.getElementById("tradeType").value = result.data.tradeName;
            $('#tradeType').selectpicker('refresh');
            divHasChanged(result.data.tradeName, '校内交易', $('#tradeDescriptionDiv'));

            showImagesList = result.data.image.split("、");
            var imagesList = new Array(),
                configList = new Array();
            $.each(showImagesList, function(index) {
                imagesList.push(`<img src='${showImagesList[index]}' class='file-preview-image' style="max-width: 400px;">`);
                configList.push({
                    caption: "show_" + index,
                    url: "../../api/product/deleteImage?url=" + index,
                    key: "show_" + index
                })
            });
            // imageBodys(imagesList, configList, "showImageInput");
            imageBody(showImagesList, configList, "file-3");

            detailImagesList = result.data.detailImage.split("、");
            imagesList = [];
            configList = [];
            $.each(detailImagesList, function(index) {
                imagesList.push(`<img src='${detailImagesList[index]}' class='file-preview-image' style="max-width: 400px;">`);
                configList.push({
                    caption: "detail_" + index,
                    url: "POST ../../api/product/deleteImage?url=" + index,
                    key: "detail_" + index
                })
            });
            // imageBodys(imagesList, configList, "detailImageInput");
            imageBody(detailImagesList, configList, "detailImageInput");

            productInfo = result.data;
        }
    })
}

var delShowImages = new Array(),
    delDetailImages = new Array();

$("#showImageInput").fileinput({
    showCaption: false,
    showClose: false
});

function imageBody(list, configList, id) {
    $("#" + id).fileinput({
        language: 'zh',
        theme: 'fas',
        showUpload: false,
        // showCaption: false,
        browseClass: "btn btn-primary",
        fileType: "any",
        autoReplace: true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        overwriteInitial: false,
        initialPreviewAsData: true,
        initialPreview: list,
        initialPreviewConfig: configList
    }).on('filepredelete', function(event, key) {
        console.log('Key = ' + key);
        if (!key.indexOf('show')) {
            delShowImages.push(key.split("_")[1]);
        }
        if (!key.indexOf('detail')) {
            delDetailImages.push(key.split("_")[1]);
        }
    });
}

function imageBodys(imgList, configList, id) {
    $("#" + id).fileinput("refresh", {
        showClose: false,
        // dropZoneEnabled: false,
        layoutTemplates: {
            // actionDelete: '', //去除上传预览的缩略图中的删除图标
            // actionUpload: '', //去除上传预览缩略图中的上传图片；
            // actionZoom: '' //去除上传预览缩略图中的查看详情预览的缩略图标。
        },
        overwriteInitial: false,
        initialPreview: imgList,
        initialPreviewAsData: true,
        initialPreviewFileType: 'image',
        initialPreviewConfig: configList
    }).on('filepredelete', function(event, key) {
        console.log('Key = ' + key);
        if (!key.indexOf('show')) {
            delShowImages.push(key.split("_")[1]);
        }
        if (!key.indexOf('detail')) {
            delDetailImages.push(key.split("_")[1]);
        }
    });
}

var showImages = new Array();
var detailImages = new Array();
//发布商品 --图片上传监听
$("#showImage").on("filebatchselected", function(event, files) {
    showImages = files;
});

$("#detailImage").on("filebatchselected", function(event, files) {
    detailImages = files;
});

//发布商品 --发布
$('#expirationTime').datetimepicker({
    format: 'yyyy-mm-dd hh:mm:ss',
    autoclose: true,
    todayBtn: true,
    clearBtn: true
});
$(".datetimepicker .datetimepicker-dropdown-bottom-right .dropdown-menu").attr("style", "top: 0;")

document.getElementById("btn_success").addEventListener('click', function() {

    // console.log(showImages);
    // return false;

    var showImageStr = '';
    if (delShowImages.length > 0) {
        $.each(delShowImages, function(index, data) {
            showImagesList[data] = "delete";
        });
    }
    $.each(showImagesList, function(index, data) {
        if (data != "delete") {
            showImageStr = showImageStr + data + "、";
        }
    });
    showImageStr = showImageStr.substring(0, showImageStr.length - 1);
    // console.log("showImageStr", showImageStr)

    var detailImageStr = '';
    if (delDetailImages.length > 0) {
        $.each(delDetailImages, function(index, data) {
            detailImagesList[data] = "delete";
        });
    }
    // console.log("showImageStr", detailImagesList)
    $.each(detailImagesList, function(index, data) {
        if (data != "delete") {
            // console.log(data)
            detailImageStr = detailImageStr + data + "、";
        }
    });
    detailImageStr = detailImageStr.substring(0, detailImageStr.length - 1);
    // console.log("detailImageStr", detailImageStr)

    var productName = $('#productName').val();
    var productPrice = $('#productPrice').val();
    var productType1 = $('#productType1 option:selected').val();
    var productType2 = $('#productType2 option:selected').val();
    var productType = productType1 + '-' + productType2;
    var productNumber = $('#productNumber').val();
    var productDescription = $('#productDescription').val();
    var qualityType = $('#qualityType option:selected').val();
    var expirationTime = $('#expirationTime').data().date;
    if (expirationTime == undefined) {
        expirationTime = '';
    }
    var tradeType = $('#tradeType option:selected').val();
    var tradeDescription = $('#tradeDescription').val();

    if (productName == "" || productPrice == "" || productType1 == undefined || productType2 == undefined ||
        productNumber == "" || productDescription == "" ||
        qualityType == undefined || tradeType == undefined) {
        toastr.error('请填写完整！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
        return;
    }

    var productJson = "{'productId': " + productId + ", 'expirationTime': '" + expirationTime + "', 'number': " + productNumber + ", 'price': " + productPrice +
        ", 'productDecription': '" + productDescription + "', 'productName': '" + productName + "', 'typeName': '" + productType +
        "', 'qualityName': '" + qualityType + "', 'tradeDecription': '" + tradeDescription + "', 'tradeName': '" + tradeType +
        "', 'image': '" + showImageStr + "', 'detailImage': '" + detailImageStr + "'}";
    var formData = new FormData();
    formData.append("productJson", productJson);
    for (i = 0; i <= showImages.length; i++) {
        formData.append("image", showImages[i]);
    }
    for (i = 0; i <= detailImages.length; i++) {
        formData.append("detail_image", detailImages[i]);
    }
    // console.log("showImages", showImages);
    // console.log("detailImages", detailImages);
    console.log("productJson", productJson);

    // console.log($("#file-3"))
    // return false;

    $.ajax({
        url: "/api/product/updateProduct",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        dataType: "json",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log(result);
            var str1 = '',
                str2 = '';
            if (result.data.展示图片不合规 != undefined) {
                for (let val of Object.entries(result.data.展示图片不合规)) {
                    if (str1 == '') {
                        str1 = str1 + val[0]
                    } else {
                        str1 = str1 + '、' + val[0]
                    }
                }
            }
            if (result.data.详情图片不合规 != undefined) {
                for (let val of Object.entries(result.data.详情图片不合规)) {
                    if (str2 == '') {
                        str2 = str2 + val[0]
                    } else {
                        str2 = str2 + '、' + val[0]
                    }
                }
            }

            if (result.data == "更新商品成功") {
                toastr.success('更新商品成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                setTimeout(function() {
                    turnTo(4, 'my-product');
                }, 1000);
            } else {
                if (str1 == '' && str2 != '') {
                    toastr.error('详情图片 ' + str2 + ' 存在违规！！！');
                } else if (str1 != '' && str2 == '') {
                    toastr.error('展示图片 ' + str1 + ' 存在违规！！！');
                } else {
                    toastr.error('展示图片 ' + str1 + ' 存在违规！！！', '详情图片 ' + str2 + ' 存在违规！！！');
                }
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }

        },
        fail: function(error) {
            toastr.error('更新商品失败！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }
    });

});


// 返回
function turnTo(index, data) {
    window.location.href = 'PersonalInformation.html?index=' + index + '&nav=' + data;
}