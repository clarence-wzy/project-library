// $('#nav-list li').eq(8).tab('show');
// document.getElementsByClassName('person-body')[0].classList.remove('in');
// document.getElementsByClassName('person-body')[0].classList.remove('active');
// document.getElementsByClassName('deliver-product-body')[0].classList.add('in');
// document.getElementsByClassName('deliver-product-body')[0].classList.add('active');

// var userSession = JSON.parse(sessionStorage.getItem('user'));
// var tokenSession = sessionStorage.getItem('token');
// console.log(userSession);
// if (userSession == null || tokenSession == null) {
//     window.location.href = "login.html";
// } else {
//     var userId = userSession.userId;
//     document.getElementById("user-headimg").src = userSession.headImage;
// }

//读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
var user = JSON.parse(getCookie("user"));
if (user == null) {
    window.location.href = "login.html";
} else {
    var userId = user.userId;
    var username = user.username;
}
// var userId = 2;
// var username = "wuziyi";

if (user.studentId != null && user.studentPassword != null) {
    var str =
        `<li><a href="#release-product" data-toggle="tab">发布商品</a></li>
        <li><a href="#my-product" data-toggle="tab">我的商品</a></li>
        <li><a href="#deliver-product" data-toggle="tab">我要发货</a></li>`;
    $("#nav-list").append(str);
}

// 首页跳转处理
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

function showNav(index, nav) {
    $('#nav-list li').eq(index - 1).tab('show');
    document.getElementsByClassName('person-body')[0].classList.remove('in');
    document.getElementsByClassName('person-body')[0].classList.remove('active');
    document.getElementsByClassName(nav + '-body')[0].classList.add('in');
    document.getElementsByClassName(nav + '-body')[0].classList.add('active');
}

// console.log(getUrlArgs())
if (getUrlArgs() != undefined) {
    var data = getUrlArgs();
    if (data.index == undefined) {
        showNav(1, 'person');
    } else {
        showNav(data.index, data.nav);
    }

    if (data.shopCartIdList != undefined) {
        var list = data.shopCartIdList.split("[").join("").split("]").join("").split("%20").join("").split(",");
        $.each(list, function(i) {
            list[i] = Number(list[i]);
        });
        // 支付成功跳转回来后更新数据库订单状态 已支付
        if (data.masterOrderId != undefined && data.paystatus == "true" && data.shopCartIdList != undefined) {
            $.ajax({
                url: "/api/order/updateStatus/" + data.masterOrderId + "/paid/" + list,
                method: "POST",
                timeout: 15000,
                beforeSend: function() {
                    $("#loading").html("<img src='img/loading.gif' />");
                },
                success: function(result) {
                    $("#loading").empty();
                    console.log(result);
                }
            })
        }
    }

    if (data.id != undefined) {
        $.ajax({
            url: "/api/order/updateStatusById/" + data.id + "/paid",
            method: "POST",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                console.log(result);
            }
        })
    }

}

//个人信息
var userInfo;
$.ajax({
    url: "/api/user/findUserByUserId/" + userId,
    type: "GET",
    timeout: 15000,
    beforeSend: function() {
        $("#loading").html("<img src='img/loading.gif' />");
    },
    success: function(result) {
        $("#loading").empty();
        // console.log(result);
        userInfo = result.data;
        document.getElementById('username').innerHTML = userInfo.username;
        document.getElementById('realName').innerHTML = userInfo.realName;
        document.getElementById('userSex').innerHTML = userInfo.sex;
        document.getElementById('userCredit').innerHTML = userInfo.credit;
        document.getElementById('userEmail').innerHTML = userInfo.email;
        document.getElementById('userQQ').innerHTML = userInfo.qq;
        document.getElementById('userWechat').innerHTML = userInfo.wechat;

        if (userInfo.phone == '') {
            $('.hasBindPhone').css('display', 'none');
            $('.noBindPhone').css('display', 'inline-block');
        } else {
            $('.hasBindPhone').css('display', 'inline-block');
            $('.noBindPhone').css('display', 'none');
            document.getElementById('userPhone').innerHTML = userInfo.phone;
        }

        if (userInfo.email == '') {
            $('.hasBindEmail').css('display', 'none');
            $('.noBindEmail').css('display', 'inline-block');
        } else {
            $('.hasBindEmail').css('display', 'inline-block');
            $('.noBindEmail').css('display', 'none');
            document.getElementById('userEmail').innerHTML = userInfo.email;
        }

        if (userInfo.studentId == null) {
            $('.hasCertified').css('display', 'none');
            $('.noCertified').css('display', 'inline-block');
        } else {
            $('.hasCertified').css('display', 'inline-block');
            $('.noCertified').css('display', 'none');
        }
    }
});

function showPhoneModal() {
    $('#phoneModal').modal();
}

function showStudentModal() {
    $('#studentModal').modal();
}

function showEmailModal() {
    $('#emailModal').modal();
}

//个人信息 --编辑
document.getElementById("person-edit").addEventListener('click', function() {
    $('.person-control-label').css('display', 'none');
    $('.person-control-input').css('display', 'inline-block');
    document.getElementById('usernameInput').value = userInfo.username;
    document.getElementById('realNameInput').value = userInfo.realName;
    document.getElementById('userSexInput').value = userInfo.sex;
    // document.getElementById('userEmailInput').value = userInfo.email;
    // document.getElementById('userPhoneInput').value = userInfo.phone;
    document.getElementById('userQQInput').value = userInfo.qq;
    document.getElementById('userWechatInput').value = userInfo.wechat;

    if (userInfo.phone == '') {
        $('.hasBindPhone').css('display', 'none');
        $('.noBindPhone').css('display', 'inline-block');
    } else {
        $('.hasBindPhone').css('display', 'inline-block');
        $('.noBindPhone').css('display', 'none');
        document.getElementById('userPhone').innerHTML = userInfo.phone;
    }

    if (userInfo.email == '') {
        $('.hasBindEmail').css('display', 'none');
        $('.noBindEmail').css('display', 'inline-block');
    } else {
        $('.hasBindEmail').css('display', 'inline-block');
        $('.noBindEmail').css('display', 'none');
        document.getElementById('userEmail').innerHTML = userInfo.email;
    }

    if (userInfo.studentId == null) {
        $('.hasCertified').css('display', 'none');
        $('.noCertified').css('display', 'inline-block');
    } else {
        $('.hasCertified').css('display', 'inline-block');
        $('.noCertified').css('display', 'none');
    }

    $('.person-btn-body').css('display', 'none');
    $('.person-btn-edit-body').css('display', 'block');
});
//个人信息 --确定
document.getElementById("person-ensure").addEventListener('click', function() {
    var usernameInput = $('#usernameInput').val() != "" ? $('#usernameInput').val() : null;
    var userSexInput = $('#userSexInput').val() != "" ? $('#userSexInput').val() : null;
    var userQQInput = $('#userQQInput').val() != "" ? $('#userQQInput').val() : null;
    var userWechatInput = $('#userWechatInput').val() != "" ? $('#userWechatInput').val() : null;

    $.ajax({
        url: "/api/user/updateUserInfo",
        method: "POST",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({
            'userId': userId,
            'username': usernameInput,
            'sex': userSexInput,
            'qq': userQQInput,
            'wechat': userWechatInput
        }),
        dataType: "json",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.msg == "OK") {
                toastr.success('更新信息成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                setTimeout(function() {
                    // document.getElementById("person-cancel").click();
                    window.location.href = "PersonalInformation.html";
                }, 2000);
            } else {
                toastr.error('更新信息失败！请稍后尝试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }
        }
    })
});
//个人信息 --取消
document.getElementById("person-cancel").addEventListener('click', function() {
    $('.person-control-label').css('display', 'inline-block');
    $('.person-control-input').css('display', 'none');
    $('.person-btn-body').css('display', 'block');
    $('.person-btn-edit-body').css('display', 'none');

    if (userInfo.phone == '') {
        $('.hasBindPhone').css('display', 'none');
        $('.noBindPhone').css('display', 'inline-block');
    } else {
        $('.hasBindPhone').css('display', 'inline-block');
        $('.noBindPhone').css('display', 'none');
        document.getElementById('userPhone').innerHTML = userInfo.phone;
    }

    if (userInfo.email == '') {
        $('.hasBindEmail').css('display', 'none');
        $('.noBindEmail').css('display', 'inline-block');
    } else {
        $('.hasBindEmail').css('display', 'inline-block');
        $('.noBindEmail').css('display', 'none');
        document.getElementById('userEmail').innerHTML = userInfo.email;
    }
});

//个人信息 --绑定邮箱
function getMailCode(data) {
    $.ajax({
        url: "/api/user/mail/" + data,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log("邮箱code:", result);
        }
    })
}
$("#email-sendcode").on('click', function() {
    var email = $('#email').val();
    var regula = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;

    if (email == "") {
        toastr.error('邮箱都不能为空！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else if (!regula.test(email)) {
        toastr.error('请输入正确的邮箱！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else {
        getMailCode(email);
        time(this);
    }
});
document.getElementById("email_btn").addEventListener('click', function() {
    var email = $('#email').val();
    var emailCode = $('#emailCode').val();
    $.ajax({
        url: "/api/user/bindEmail/" + userId + "/" + email + "/" + emailCode,
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.data == "绑定邮箱成功") {
                toastr.success('绑定邮箱成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                $('#emailModal').modal('hide');
                $(".modal.fade").hide();
                $('.hasBindEmail').css('display', 'inline-block');
                $('.noBindEmail').css('display', 'none');
                document.getElementById('userEmail').innerHTML = email;
            } else {
                toastr.error(result.msg + '！请重试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                document.getElementById("email").value = "";
                document.getElementById("emailCode").value = "";
                wait = 0;
                time("#email-sendcode");
            }
        }
    })
});

//个人信息 --绑定手机
function getPhoneCode(data) {
    $.ajax({
        url: "/api/user/phone/" + data,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log("手机code:", result);
        }
    })
}
$("#phone-sendcode").on('click', function() {
    var phone = $('#phone').val();
    var regula = /^1[34578]\d{9}$/;

    if (phone == "") {
        toastr.error('手机号都不能为空！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else if (!regula.test(phone)) {
        toastr.error('请输入正确的手机号！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else {
        getPhoneCode(phone);
        time(this);
    }
});
document.getElementById("phone_btn").addEventListener('click', function() {
    var phone = $('#phone').val();
    var phoneCode = $('#phoneCode').val();
    $.ajax({
        url: "/api/user/bindPhone/" + userId + "/" + phone + "/" + phoneCode,
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log(result);
            if (result.data == "绑定手机成功") {
                toastr.success('绑定手机成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                $('#phoneModal').modal('hide');
                $(".modal.fade").hide();
                $('.hasBindPhone').css('display', 'inline-block');
                $('.noBindPhone').css('display', 'none');
                document.getElementById('userPhone').innerHTML = phone;
            } else {
                toastr.error(result.msg + '！请重试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                document.getElementById("phone").value = "";
                document.getElementById("phoneCode").value = "";
                wait = 0;
                time("#phone-sendcode");
            }
        }
    })
});

//个人信息 --发送验证码倒计时
var wait = 60;

function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        o.value = "获取验证码";
        wait = 60;
    } else {
        o.setAttribute("disabled", true);
        o.value = "重新发送(" + wait + ")";
        wait--;
        setTimeout(function() {
            time(o)
        }, 1000)
    }
}

//个人信息 --学生认证模态框
document.getElementById("student_btn").addEventListener('click', function() {
    var studentId = $('#studentId').val();
    var studentPassword = $('#studentPassword').val();
    if (studentId == '' || studentPassword == '') {
        toastr.error('学号跟密码都不能为空！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else {
        $.ajax({
            url: "/api/user/scse2",
            type: "POST",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                userId: Number(userId),
                id: Number(studentId),
                password: String(studentPassword),
            }),
            dataType: "json",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                if (result.status == 200) {
                    toastr.success('认证成功！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                    let promise = new Promise((resolve, reject) => {
                        $.ajax({
                            url: "/api/user/findUserByUserId/" + userId,
                            method: 'GET',
                            timeout: 15000,
                            beforeSend: function() {
                                $("#loading").html("<img src='img/loading.gif' />");
                            },
                            success: function(result) {
                                $("#loading").empty();
                                setCookie("user", JSON.stringify(result.data), "s86400");
                                resolve();
                            }
                        });
                    });
                    promise.then(function(fulfilled) {
                        location.reload();
                    });
                } else {
                    toastr.error(result.data + '！请重新输入！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                }
            }
        })
    }
});

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

$("#showImage").fileinput({
    // 5138 if (showUpl) 去掉单张图片上的上传按钮
    language: "zh",
    uploadUrl: '#',
    theme: 'fas',
    showUpload: false,
    showCaption: true,
    browseClass: "btn btn-primary",
    fileType: "any",
    autoReplace: true,
    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
    allowedFileExtensions: ['jpg', 'png', 'jpeg', 'gif', 'bmp', 'AVI', 'mov', 'rmvb', 'rm', 'FLV', 'mp4'],
    previewSettings: {
        image: {
            width: "130px",
            height: "130px"
        },
    }
});

$("#detailImage").fileinput({
    // 5138 if (showUpl) 去掉单张图片上的上传按钮
    language: "zh",
    uploadUrl: '#',
    theme: 'fas',
    showUpload: false,
    showCaption: true,
    browseClass: "btn btn-primary",
    fileType: "any",
    autoReplace: true,
    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
    allowedFileExtensions: ['jpg', 'png', 'jpeg', 'gif', 'bmp', 'AVI', 'mov', 'rmvb', 'rm', 'FLV', 'mp4'],
    previewSettings: {
        image: {
            width: "130px",
            height: "130px"
        },
    }
});

var showImages = new Array();
var detailImages = new Array();
//发布商品 --图片上传监听
$("#showImage").on("filebatchselected", function(event, files) {
    showImages = files;
    // $.each(files, function(index, data) {
    //     showImages.push(files[index]);
    // });
    // console.log(showImages)
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
document.getElementById("release-product-btn").addEventListener('click', function() {
    // console.log(showImages);
    // return false;
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
        productNumber == "" || showImages.length == 0 || detailImages.length == 0 || productDescription == "" ||
        qualityType == undefined || tradeType == undefined) {
        toastr.error('请填写完整！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
        return;
    }

    var productJson = "{'expirationTime': '" + expirationTime + "', 'number': " + productNumber + ", 'price': " + productPrice +
        ", 'productDecription': '" + productDescription + "', 'productName': '" + productName + "', 'typeName': '" + productType +
        "', 'qualityName': '" + qualityType + "', 'tradeDecription': '" + tradeDescription + "', 'tradeName': '" + tradeType +
        "', 'ownId': " + userId + ", 'ownName': '" + username + "'}"
    var formData = new FormData();
    formData.append("productJson", productJson);
    // for (i = 0; i <= showImages.length; i++) {
    //     formData.append("image", showImages[i]);
    // }
    $.each(showImages, function(i, image) {
        formData.append("image", image.file);
    });
    // for (i = 0; i <= detailImages.length; i++) {
    //     formData.append("detail_image", detailImages[i]);
    // }
    $.each(detailImages, function(i, image) {
        formData.append("detail_image", image.file);
    });

    // console.log(showImages, detailImages);
    // return false;

    $.ajax({
        url: "/api/product/insertProduct",
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

            if (result.data == "插入商品成功") {
                toastr.success('发布商品成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                setTimeout(function() {
                    location.reload()
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
            toastr.error('发布商品失败！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }
    });
});

// 我的商品
function productListBody(index, list) {
    var image = list.image.split("、");
    var str =
        `<div class="my-product-list" id="list-${index}">
        <div class="row">
            <div class="col-md-2">
                <img src="${image[0]}" alt="">
            </div>
            <div class="col-md-10">
                <div class="form-group row">
                    <label class="control-label col-md-2 my-product-list-title-${index}">商品名称：</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control my-product-list-input-${index}" value="${list.productName}" />
                        <label class="control-label my-product-list-label-${index}">
                            ${list.productName}
                        </label>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="control-label col-md-2 my-product-list-title-${index}">类型：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control my-product-list-input-${index}" value="${list.typeName}" />
                        <label class="control-label my-product-list-label-${index}">
                            ${list.typeName}
                        </label>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="control-label col-md-2 my-product-list-title-${index}">价格：</label>
                    <div class="col-md-2">
                        <input type="text" class="form-control my-product-list-input-${index}" value="￥${list.price}" />
                        <label class="control-label my-product-list-label-${index}">
                            ￥ ${list.price}
                        </label>
                    </div>
                </div>
                
            </div>
        </div>
        <div class="row">
            <div class="my-product-btn-body-${index}" id="my-product-btn-body-${index}" align="center" style="display: block;">
                <button id="my-product-edit" class="btn btn-success" onclick="myProductEdit(${index})" style="width: 15%;">修改</button>
            </div>
            <div class="my-product-edit-btn-body-${index}" align="center" style="display: none;">
                <button id="my-product-ensure" class="btn btn-success" onclick="myProductEnsure(${index})" style="width: 15%;">确定</button>
                <button id="my-product-cancel" class="btn btn-default" onclick="myProductCancel(${index})" style="margin-left: 10px; width: 15%;">取消</button>
            </div>
            <div class="my-product-sold-btn-body-${index}" align="center" style="display: none;">
                <button id="my-product-edit" class="btn btn-default disabled">已售出</button>
            </div>
        </div>
    </div>`;
    $('#my-product-list-body').append(str);
    // <div class="form-group row">
    //                 <label class="control-label col-md-2 my-product-list-title-${index}">数量：</label>
    //                 <div class="col-md-3">
    //                     <input type="number" class="form-control my-product-list-input-${index}" value="${list.number}" min="1" style="text-align: center;">
    //                     <label class="control-label my-product-list-label-${index}">
    //                         ${list.number}
    //                     </label>
    //                 </div>
    //             </div>
    //             <div class="form-group row">
    //                 <label class="control-label col-md-2 my-product-list-title-${index}">商品描述：</label>
    //                 <div class="col-md-10">
    //                     <textarea class="form-control my-product-list-input-${index}" rows="4" style="resize: none;">${list.productDecription}</textarea>
    //                     <label class="control-label my-product-list-label-${index}">
    //                         ${list.productDecription}
    //                     </label>
    //                 </div>
    //             </div>
    //             <div class="form-group row">
    //                 <label class="control-label col-md-2 my-product-list-title-${index}">交易方式：</label>
    //                 <div class="col-md-3">
    //                     <input type="text" class="form-control my-product-list-input-${index}" value="${list.tradeName}" />
    //                     <label class="control-label my-product-list-label-${index}">
    //                         ${list.tradeName}
    //                     </label>
    //                 </div>
    //             </div>
    //             <div class="form-group row">
    //                 <label class="control-label col-md-2 my-product-list-title-${index}">成色：</label>
    //                 <div class="col-md-2">
    //                     <div class="my-product-list-input-${index}" style="display: none;">
    //                         <select class="selectpicker show-tick" id="my-product-qualityName-${index}"></select>
    //                     </div>
    //                     <label class="control-label my-product-list-label-${index}">
    //                         ${list.qualityName}
    //                     </label>
    //                 </div>
    //             </div>
    //             <div class="form-group row">
    //                 <label class="control-label col-md-2 my-product-list-title-${index}">过期时间：</label>
    //                 <div class="col-md-4">
    //                     <div class="my-product-list-input-${index}">
    //                         <div class='input-group date' id='myProduct-expirationTime'>
    //                             <input type='text' class="form-control" value="${list.expirationTime}" />
    //                             <span class="input-group-addon">
    //                                 <span class="glyphicon glyphicon-calendar"></span>
    //                             </span>
    //                         </div>
    //                     </div>
    //                     <label class="control-label my-product-list-label-${index}">
    //                         ${list.expirationTime}
    //                     </label>
    //                 </div>
    //             </div>
}

//我的商品 --编辑
function myProductEdit(index) {
    window.open('UpdateProduct.html?productId=' + myProducts[index].productId);

    // $('.my-product-btn-body-' + index).css('display', 'none');
    // $('.my-product-edit-btn-body-' + index).css('display', 'block');
    // $('.my-product-list-input-' + index).css('display', 'block');
    // $('.my-product-list-label-' + index).css('display', 'none');
    // $('.my-product-list-title-' + index).css('height', '34px');
    // $('.my-product-list-title-' + index).css('margin-top', '7px');

    // $.ajax({
    //     url: "/api/product/findAllQuality",
    //     type: "GET",
    //     timeout: 15000,
    //     beforeSend: function() {
    //         $("#loading").html("<img src='img/loading.gif' />");
    //     },
    //     success: function(result) {
    //         $("#loading").empty();
    //         console.log("bj", result)
    //         $.each(result.data, function(index, data) {
    //             var str = `<option> ${data.qualityName} </option>`;
    //             $('#my-product-qualityName-' + index).append(str);
    //         });
    //         $('#my-product-qualityName-' + index).selectpicker('refresh');
    //     }
    // })
}
//我的商品 --确定
// document.getElementById("my-product-ensure").addEventListener('click', function() {

// });
//我的商品 --取消
function myProductCancel(index) {
    $('.my-product-btn-body-' + index).css('display', 'block');
    $('.my-product-edit-btn-body-' + index).css('display', 'none');
    $('.my-product-list-input-' + index).css('display', 'none');
    $('.my-product-list-label-' + index).css('display', 'block');
    $('.my-product-list-title-' + index).css('height', 'auto');
    $('.my-product-list-title-' + index).css('margin-top', 'auto');
}

var myProducts = new Array();
$.ajax({
    url: "/api/product/findProductByUserId/" + userId,
    type: "GET",
    timeout: 15000,
    beforeSend: function() {
        $("#loading").html("<img src='img/loading.gif' />");
    },
    success: function(result) {
        $("#loading").empty();
        // console.log(result);
        myProducts = result.data;
        $.each(result.data, function(index, data) {
            productListBody(index, data);
        });

    }
})

$('#myProduct-expirationTime').datetimepicker({
    format: 'yyyy-mm-dd hh:mm:ss',
    autoclose: true,
    todayBtn: true,
    clearBtn: true
});

//我的收藏夹
var current_page = 1;

function collectListBody(index, list, page) {
    var image = list.image.split("、");
    var str =
        `<div class="row my-collect-${index}">
            <div class="col-sm-2 col-sm-offset-1 my-collect-table-image" onclick="window.open('CommodityDetails.html?productId='+'${list.productId}')">
                <img src="${image[0]}" />
            </div>
            <div class="col-sm-8">
                <div class="my-collect-table-title" title="${list.productName}" onclick="window.open('CommodityDetails.html?productId='+'${list.productId}')">${list.productName}</div>
                <div class="row" style="margin-top: 25px;">
                    <div class="my-collect-table-price col-sm-4" onclick="window.open('CommodityDetails.html?productId='+'${list.productId}')">￥ ${list.price}</div>
                    <div class="my-collect-table-operation col-sm-2 col-sm-offset-6">
                        <button class="btn btn-primary" onclick="myCollect(${index}, ${list.productId}, ${page})">不喜欢了</button>
                    </div>
                </div>
            </div>
        </div>`;
    $('#my-collect-table').append(str);
}

function collectInfo(page, start, end) {
    // console.log(start, end);
    var totalProductNum = 0;
    $.ajax({
        url: "/api/collect/getCollectCount/" + userId,
        type: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log("总数：", result)
            totalProductNum = Math.ceil(result.data / 5);
        }
    });

    $.ajax({
        url: "/api/collect/findAllCollect/" + userId,
        method: "GET",
        data: {
            'startIndex': start,
            'pageSize': end
        },
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
            // console.log(totalProductNum)
            if (totalProductNum == 0) {
                $('.no-collect').css('display', 'block');
                return;
            }
            $.each(result.data, function(index, data) {
                collectListBody(index, data, page);
            });
            if (totalProductNum != 1) {
                pageinatorBody(totalProductNum, page);
            }
        }
    })
}

function pageinatorBody(num, index) {
    var str =
        `<div class="collect-paginator">
            <ul id="collect-pageLimit"></ul>
        </div>`;
    $('#my-collect-table').append(str);

    $('#collect-pageLimit').bootstrapPaginator({
        currentPage: index, //当前的页面
        totalPages: num, //页数
        size: "normal", //页眉的大小
        bootstrapMajorVersion: 3, //bootstrap的版本要求
        alignment: "right",
        numberOfPages: 4, //页面显示几页
        itemTexts: function(type, page, current) {
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        onPageClicked: function(event, newPage) {
            var page = newPage.data.page;
            $("#my-collect-table").innerHTML = "";
            if (page == 1) {
                $('#my-collect').load(location.href + " #my-collect-table", function() {
                    collectInfo(1, 0, 5);
                });
            } else {
                $('#my-collect').load(location.href + " #my-collect-table", function() {
                    collectInfo(page, (page - 1) * 5, 5);
                });
            }
        }
    });
}

collectInfo(1, 0, 5);

// 我的收藏夹  --不喜欢了
function myCollect(index, productId, page) {
    // console.log(index, productId, page);
    $.ajax({
        url: "/api/collect/updateCollectStatus",
        type: "POST",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({
            'productId': productId,
            'userId': userId,
            'collectStatus': "DISABLE"
        }),
        dataType: "json",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            $('.my-collect-' + index).css('display', 'none');
            $('#my-collect').load(location.href + " #my-collect-table", function() {
                collectInfo(page, (page - 1) * 5, 5);
            });
        }
    })
}

// 我的购物车
var shopcart;
$.ajax({
    url: "/api/shopCart/findAllShopCart/" + userId,
    method: "GET",
    timeout: 15000,
    beforeSend: function() {
        $("#loading").html("<img src='img/loading.gif' />");
    },
    success: function(result) {
        $("#loading").empty();
        shopcart = result.data;
        // console.log(result);
        if (result.data.length > 0) {
            $('.no-shopcart').css('display', 'none');
            $('.my-shopcart-total').css('display', 'block');
            $('.my-shopcart-total-button').css('display', 'block');
            $.each(result.data, function(index, data) {
                shopCartInfo(index, data);
            });
        } else {
            $('.no-shopcart').css('display', 'block');
            $('.my-shopcart-total').css('display', 'none');
            $('.my-shopcart-total-button').css('display', 'none');
        }
    }
});

function shopCartInfo(index, list) {
    // console.log(list)
    var image = list.image.split("、");
    var str =
        `<div class="row my-shopcart" id="my-shopcart-${index}">
        <div class="col-sm-3 my-shopcart-table-image">
            <input type="checkbox" name="my-shopcart-product" onclick="checkboxChange(${index});" />
            <img src="${image[0]}" />
        </div>
        <div class="col-sm-8">
            <div class="my-shopcart-table-title" title="${list.productName}">
                <a onclick="window.open('CommodityDetails.html?productId='+'${list.productId}')">${list.productName}</a>
            </div>
            <div class="row" style="margin-top: 25px;">
                <div class="my-shopcart-table-price col-sm-4">￥ <b id="shopcart-price-${index}">${list.price}</b></div>
                <div class="my-shopcart-table-number col-sm-3 col-sm-offset-5">
                    <div class="input-group">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" onclick="numberSub(${index});" id="shopcart-sub-${index}">-</button>
                        </span>
                        <input type="text" class="form-control" value="${list.cartNumber}" id="shopcart-number-${index}" 
                            min="1" max="${list.number}" onfocus=this.blur() data-cart-id="${list.id}" data-cart-index="${index}" />
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" onclick="numberAdd(${index});" id="shopcart-add-${index}">+</button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>`;
    $('#my-shopcart-table').append(str);
}

// 我的购物车 --全选按钮
function allCheck() {
    var allCheck = document.getElementById("allCheck");
    var allCheckStatus = allCheck.checked;
    var myShopcartProduct = document.getElementsByName("my-shopcart-product");
    if (allCheckStatus) {
        var productPrice, productNumber;
        var total = 0.00;
        for (var i = 0; i < myShopcartProduct.length; i++) {
            myShopcartProduct[i].checked = true;
            //处理总价
            productPrice = document.getElementById("shopcart-price-" + i).innerHTML;
            productNumber = document.getElementById("shopcart-number-" + i).value;
            total = total + productPrice * productNumber;
        }
        document.getElementById("shopcart-total").innerHTML = toDecimal2(total);
    } else {
        for (var i = 0; i < myShopcartProduct.length; i++) {
            myShopcartProduct[i].checked = false;
        }
        document.getElementById("shopcart-total").innerHTML = toDecimal2(0.00);
    }
}
// 我的购物车 --复选框状态改变
function checkboxChange(index) {
    // console.log(index)
    var totalDiv = document.getElementById("shopcart-total");
    var total = totalDiv.innerHTML;

    var productPrice = document.getElementById("shopcart-price-" + index).innerHTML;
    var productNumber = document.getElementById("shopcart-number-" + index).value;

    var myShopcartProduct = document.getElementsByName("my-shopcart-product");
    if (myShopcartProduct[index].checked == true) {
        totalDiv.innerHTML = toDecimal2(Number(total) + productPrice * productNumber);
    } else {
        totalDiv.innerHTML = toDecimal2(Number(total) - productPrice * productNumber);
    }

    for (var i = 0; i < myShopcartProduct.length; i++) {
        if (myShopcartProduct[i].checked == false) {
            document.getElementById("allCheck").checked = false;
            return;
        }
    }
    document.getElementById("allCheck").checked = true;
}
// 我的购物车 --商品加减按钮
function numberAdd(index) {
    var productNumber = document.getElementById("shopcart-number-" + index);
    if (productNumber.value == productNumber.max) {
        $("#shopcart-add-" + index).attr("disabled", true);
        return;
    }
    productNumber.value = Number(productNumber.value) + 1;
    $("#shopcart-sub-" + index).attr("disabled", false);

    var cartId = document.getElementById("shopcart-number-" + index).getAttribute("data-cart-id");
    updateCartNumber(cartId, productNumber.value);

    if (productNumber.value >= productNumber.max) {
        $("#shopcart-add-" + index).attr("disabled", true);
    }
}

function numberSub(index) {
    var productNumber = document.getElementById("shopcart-number-" + index);
    if (productNumber.value == productNumber.min) {
        $("#shopcart-sub-" + index).attr("disabled", true);
        return;
    }
    productNumber.value = Number(productNumber.value) - 1;
    $("#shopcart-add-" + index).attr("disabled", false);

    var cartId = document.getElementById("shopcart-number-" + index).getAttribute("data-cart-id");
    updateCartNumber(cartId, productNumber.value);

    if (productNumber.value <= productNumber.min) {
        $("#shopcart-sub-" + index).attr("disabled", true);
    }
}

function updateCartNumber(id, number) {
    $.ajax({
        url: "/api/shopCart/updateShopCartNumber/" + id + "/" + number,
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
        }
    })
}

// 我的购物车 --删除按钮
document.getElementById("shopcart-del").addEventListener('click', function() {
    var myShopcartProduct = document.getElementsByName("my-shopcart-product");
    var delList = new Array();
    for (var i = 0; i < myShopcartProduct.length; i++) {
        if (myShopcartProduct[i].checked) {
            var cartId = document.getElementById("shopcart-number-" + i).getAttribute("data-cart-id");
            delList.push(cartId);
        }
    }
    //根据购物车id进行删除
    if (delList.length > 0) {
        $.ajax({
            url: "/api/shopCart/updateShopCartBatch/" + delList,
            method: "PUT",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                // console.log(result);
                if (result.data == "更新购物车状态成功") {
                    toastr.success('删除成功！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');

                    document.getElementById("my-shopcart-table").innerHTML = "";
                    $.ajax({
                        url: "/api/shopCart/findAllShopCart/" + userId,
                        method: "GET",
                        timeout: 15000,
                        beforeSend: function() {
                            $("#loading").html("<img src='img/loading.gif' />");
                        },
                        success: function(result) {
                            $("#loading").empty();
                            shopcart = result.data;
                            // console.log(result);
                            if (result.data.length > 0) {
                                document.getElementById("shopcart-total").innerHTML = "0.00";
                                $('.my-shopcart-total').css('display', 'block');
                                $('.my-shopcart-total-button').css('display', 'block');
                                $.each(result.data, function(index, data) {
                                    shopCartInfo(index, data);
                                });
                            } else {
                                var str = `<div class="no-shopcart" style="display: none;">您还没加入喜欢的商品！ (*￣︶￣)</div>`;
                                $('#my-shopcart-table').append(str);
                                $('.my-shopcart-total').css('display', 'none');
                                $('.my-shopcart-total-button').css('display', 'none');
                            }
                        }
                    });
                } else {
                    toastr.error('删除失败，请稍后重试！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                }
            }
        })
    }
});

// 我的购物车 --结算按钮
document.getElementById("shopcart-settle").addEventListener('click', function() {
    var myShopcartProduct = document.getElementsByName("my-shopcart-product");
    var list = new Array();
    for (var i = 0; i < myShopcartProduct.length; i++) {
        if (myShopcartProduct[i].checked) {
            var index = document.getElementById("shopcart-number-" + i).getAttribute("data-cart-index");
            list.push(shopcart[index]);
        }
    }

    var settleMap = {};
    for (var i = 0; i < list.length; i++) {
        if (settleMap[list[i].ownName] == undefined) {
            settleMap[list[i].ownName] = [];
        }
        settleMap[list[i].ownName].push(list[i]);
    }

    //设置 session
    sessionStorage.setItem("settleMap", JSON.stringify(settleMap));
    window.location.href = "SureOrder.html";
});

// 保留两位小数函数
function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

// 我的订单
getOrder("全部", "all");

function getOrder(data, type) {
    $.ajax({
        url: "/api/order/findOrderByUserId/" + userId,
        method: "GET",
        data: {
            "childOrderStatus": data
        },
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log(result);
            if (result.data.length > 0) {
                $.each(result.data, function(index, data) {
                    orderInfo(type, index, data);
                });
            } else {
                $("#no-order-div").attr("style", "display: block;");
            }
        }
    });
}

function orderInfo(type, position, list) {
    if (list.childOrderList.length > 0) {
        var str = ``;
        $.each(list.childOrderList, function(index, data) {
            str = str + orderTableInfo(index, data, list.childOrderList.length, position);
        });

        str = `<div class="my-order-table">${str}</div>`;
        $('#' + type + '-order').append(str);
    } else {
        var typeStr;
        if (type == "pay") {
            typeStr = "待付款的";
        }
        if (type == "deliver") {
            typeStr = "待收货的";
        }
        if (type == "refund") {
            typeStr = "退款/售后的";
        }
        var str = `<div class="no-order">暂无${typeStr}订单(*￣︶￣)</div>`;
        $('#' + type + '-order').append(str);
    }
}

function orderTableInfo(index, list, length, position) {
    // console.log(list)
    var image = list.product.image.split("、");
    var str =
        `<div class="row my-order-table-info" id="my-order-table-info-${position}-${index}">
            <div class="col-sm-2 my-order-table-image">
                <img src="${image[0]}" />
            </div>
            <div class="col-sm-10">
                <div class="my-order-table-title">
                    <a onclick="window.open('CommodityDetails.html?productId='+'${list.product.productId}')" title="${list.product.productName}">${list.product.productName}</a>
                </div>
                <div class="row my-order-table-content" style="margin-top: 20px;">
                    <div class="my-order-table-number col-sm-2">数量：x<span id="order-number">${list.number}</span>
                    </div>
                    <div class="col-sm-3">单价：￥ <span id="order-price">${toDecimal2(list.product.price)}</span></div>
                    <div class="col-sm-3 col-sm-offset-3">总价：￥ <span id="order-total">${toDecimal2(list.product.price * list.number)}</span>
                    </div>
                </div>
                <div class="row my-order-table-content">
                    <div class="col-sm-2">卖家：<span id="order-own">${list.product.ownName}</span></div>
                    <div class="col-sm-4">订单编号：<span id="order-">${list.childOrderId}</span></div>
                </div>
                <div class="row my-order-table-content">
                    <div class="col-sm-4">创建时间：<span>${list.createTime}</span></div>
                    <div class="col-sm-4">付款时间：<span>${list.payTime == null? "未付款":list.payTime}</span></div>
                    <div class="col-sm-4">发货时间：<span>${list.deliverTime == null ? "未发货":list.deliverTime}</span></div>
                </div>
            </div>
        </div>
        <div class="row my-order-table-btn" id="my-order-table-btn-${position}-${index}">` +
        orderStatusBody(list.childOrderStatus, list.id) + `</div>`;
    if (length > 1 && index != length - 1) {
        str = `${str}<hr class="simple" style="top: 30px; color: #6f5499;" />`;
    }
    return str;
}

function orderStatusBody(status, id) {
    var str = ``;
    if (status == "待付款") {
        str = `<div class="col-sm-3 col-sm-offset-8">
                <button class="btn btn-primary" onclick="toPay(${id})">前往支付</button>
            </div>`;
    }
    if (status == "已支付") {
        str = `<div class="col-sm-3 col-sm-offset-8">
                <button class="btn btn-default" id="refund-childOrder-${id}">我要退款</button>
                <button class="btn btn-primary" disabled>待发货</button>
            </div>`;
    }
    if (status == "已发货") {
        str = `<div class="col-sm-5 col-sm-offset-6">
                <button class="btn btn-default" onclick="toRefund(${id})">我要退款</button>
                <button class="btn btn-default" id="checkLogistics-childOrder-${id}">查看物流</button>
                <button class="btn btn-primary" onclick="sureProduct(${id})">确定收货</button>
            </div>`;
    }
    if (status == "已签收") {
        str = `<div class="col-sm-3 col-sm-offset-8">
                <button class="btn btn-primary" onclick="toEvaluation(${id})">我要评价</button>
            </div>`;
    }
    if (status == "已评价") {
        str = `<div class="col-sm-3 col-sm-offset-8">
                <button class="btn btn-success" disabled>订单完成</button>
            </div>`;
    }
    return str;
}

function orderType(type) {
    document.getElementById(type + "-order").innerHTML = ``;

    var orderType = ['all', 'pay', 'wait-deliver', 'deliver', 'refund'];
    $.each(orderType, function(index, data) {
        if (data == type) {
            $("#" + data + "-order").attr("style", "display: inline-block;");
            $("#" + data + "-order-a").attr("style", "background-color: rgb(250, 240, 229);");
        } else {
            $("#" + data + "-order").attr("style", "display: none;");
            $("#" + data + "-order-a").attr("style", "background-color: rgb(216, 227, 228);");
        }
    })
    var orderType = document.getElementById(type + "-order-a").innerText;
    if (orderType == "全部订单") {
        orderType = "全部";
    }

    if (orderType == "待发货") {
        orderType = "已支付";
    }

    getOrder(orderType, type);
}

function toPay(id) {
    $.ajax({
        url: "/api/order/toPay/" + id,
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.data.indexOf("<form>")) {
                sessionStorage.setItem("form-body", result.data);
                window.location.href = "Payment.html";
            } else {
                toastr.error('下单失败，请稍后重试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }
        }
    })
}

function toEvaluation(id) {
    window.location.href = 'Evaluation.html?id=' + id;
}

function sureProduct(id) {
    $("#makeSureModal").attr("data-order-id", id);
    $("#makeSureModal").modal();
}

document.getElementById("make_sure_btn").addEventListener("click", function() {
    var id = $("#makeSureModal").attr("data-order-id");
    $.ajax({
        url: "/api/order/updateStatusSenior/" + id + "/receive/已签收",
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.data == "更新订单状态成功") {
                $('#makeSureModal').modal('hide');
                document.getElementById("all-order").innerHTML = "";
                getOrder("全部", "all");
            } else {
                toastr.error('操作失败，请稍后重试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }
        }
    });
});

// 我的评价

getEvaluation(null, userId, username);

function getEvaluation(childOrderId, userId, username) {
    $.ajax({
        url: "/api/commentary/findCommentary/" + childOrderId + "/" + userId + "/" + username,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
            if (result.data.length > 0) {
                var str = ``;
                $.each(result.data, function(index, data) {
                    var imageStr = ``;
                    if (data.commentaryImage != "" && data.commentaryImage != null) {
                        imageStr = `<div class="eval-img">`;
                        $.each(data.commentaryImage.split("、"), function(key, value) {
                            imageStr = imageStr + `<img src="${value}" />`;
                        });
                        imageStr = imageStr + `</div>`;
                    }
                    str = str +
                        `<div class="evaluation-item">
                        <div class="user-info">
                            <div class="user-info-img">
                                <img class="img-circle" src="${data.user.headImage}" />
                            </div>
                            <span>${data.user.username}</span>
                        </div>
                        <div class="item-time">
                            <span>${data.commentaryCreateTime}</span>
                        </div>
                        <div class="item-body">
                            <span class="eval-content">${data.commentaryContent}</span>
                            ` + imageStr + `
                        </div>
                        <div class="item-productInfo">
                            <img src="${data.childOrder.product.image.split("、")[0]}" />
                            <div class="product-name-price">
                                <a title="${data.childOrder.product.productName}">${data.childOrder.product.productName}</a>
                                <span>￥ ${data.childOrder.product.price}</span>
                            </div>
                        </div>
                    </div>`
                });
                $("#evaluation-info").append(str);
            } else {
                $("#no-evaluation-div").css("display", "block");
            }

        }
    })
}

// 我的收货地址

addressBody(userId, username);

var addressInfo;

function addressBody(id, name) {
    $.ajax({
        url: "/api/address/findAddressByOwn/" + id + "/" + name,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
            addressInfo = result.data;

            var str = ``;
            $.each(result.data, function(index, data) {
                if (data.defaultAddress == "true") {
                    var statusStr = `<td class="defult-address">默认地址</td>`;
                } else {
                    var statusStr = `<td class="change-default-address" onclick="changeDefault(${data.addressId})">设为默认</td>`;
                }
                str = str +
                    `<tr>
                        <td>${data.consigneeName}</td>
                        <td>${data.consigneePhone}</td>
                        <td>${data.province + ' ' + data.city + ' ' + data.area + ' ' + data.detailAddress}</td>
                        <td>
                            <img src="img/icon/address_modify.png" width="15px" height="15px" onclick="modifyAddress(${index})" />
                            <img src="img/icon/address_del.png" width="18px" height="18px" onclick="delAddress(${data.addressId})" />
                        </td>
                        ` + statusStr + `
                    </tr>`;
            });
            $("#address-list").append(str);
        }
    })
}

function changeDefault(id) {
    $.ajax({
        url: "/api/address/updateDefaultAddressByOwnId/" + id + "/" + userId,
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log(result);
            if (result.data == "更新默认收货地址成功") {
                toastr.success('设置成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                $('#addAddressModal').modal('hide');
            } else {
                toastr.error('系统异常，请重新操作！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }

            document.getElementById("address-list").innerHTML = "";
            $('#address-list').load(location.href + " #address-list", function() {
                addressBody(userId, username);
            });
        }
    })
}

function modifyAddress(index) {
    $("#addressProvinceModal").citypicker("setValue", addressInfo[index].province + "/" + addressInfo[index].city + "/" + addressInfo[index].area);
    document.getElementById("detailAddressModal").value = addressInfo[index].detailAddress;
    document.getElementById("postCodeModal").value = addressInfo[index].postCode;
    document.getElementById("addressNameModal").value = addressInfo[index].consigneeName;
    document.getElementById("addressPhoneModal").value = addressInfo[index].consigneePhone;
    if (addressInfo[index].defaultAddress == "true") {
        document.getElementById('defaultAddressModal').checked = true;
    }
    if (addressInfo[index].defaultAddress == "false") {
        document.getElementById('defaultAddressModal').checked = false;
    }

    $("#addAddressModal").modal();
    $("#address_btn_modal").attr("data-modify-addressId", addressInfo[index].addressId);
}

document.getElementById("address_btn_modal").addEventListener("click", function() {
    var addressProvinces = $('#addressProvinceModal').val().split('/');
    var detailAddress = $('#detailAddressModal').val();
    var postCode = $('#postCodeModal').val();
    var addressName = $('#addressNameModal').val();
    var addressPhone = $('#addressPhoneModal').val();
    var defaultAddress = document.getElementById('defaultAddressModal').checked;

    if (addressProvinces == "" || detailAddress == "") {
        toastr.error('地址信息和详细地址不能为空！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else if (addressName == "" || addressPhone == "") {
        toastr.error('收货人姓名和手机号码不能为空！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else if (postCode != "") {
        if (!/^[1-9][0-9]{5}$/.test(postCode)) {
            toastr.error('邮编地址格式不正确！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }
    }

    if ($("#address_btn").attr("data-modify-addressId") == "null") {
        $.ajax({
            url: "/api/address/insertAddress",
            method: 'POST',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                'consigneeName': addressName,
                'consigneePhone': addressPhone,
                'province': addressProvinces[0],
                'city': addressProvinces[1],
                'area': addressProvinces[2],
                'detailAddress': detailAddress,
                'postCode': Number(postCode),
                'defaultAddress': String(defaultAddress),
                'ownId': Number(userId),
                'ownName': username
            }),
            dataType: "json",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                if (result.data == "插入收货地址成功") {
                    toastr.success('添加收货地址成功！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                    $('#addAddressModal').modal('hide');
                } else {
                    toastr.error('系统异常，请重新添加！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                }

                document.getElementById("address-list").innerHTML = "";
                $('#address-list').load(location.href + " #address-list", function() {
                    addressBody(userId, username);
                });
            }
        })
    } else {
        $.ajax({
            url: "/api/address/updateAddress/" + userId,
            method: 'POST',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                'addressId': Number($("#address_btn_modal").attr("data-modify-addressId")),
                'consigneeName': addressName,
                'consigneePhone': addressPhone,
                'province': addressProvinces[0],
                'city': addressProvinces[1],
                'area': addressProvinces[2],
                'detailAddress': detailAddress,
                'postCode': Number(postCode),
                'defaultAddress': String(defaultAddress),
                'ownId': Number(userId),
                'ownName': username
            }),
            dataType: "json",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                if (result.data == "更新收货地址成功") {
                    toastr.success('更新收货地址成功！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                    $('#addAddressModal').modal('hide');
                } else {
                    toastr.error('系统异常，请重新添加！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                }

                document.getElementById("address-list").innerHTML = "";
                $('#address-list').load(location.href + " #address-list", function() {
                    addressBody(userId, username);
                });
            }
        })
    }
});

function delAddress(id) {
    $.ajax({
        url: "/api/address/updateAddressStatus/" + id + "/DISABLE",
        method: 'POST',
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.data == "更新收货地址状态成功") {
                toastr.success('删除成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                $('#addAddressModal').modal('hide');
            } else {
                toastr.error('系统异常，请重新操作！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }

            document.getElementById("address-list").innerHTML = "";
            $('#address-list').load(location.href + " #address-list", function() {
                addressBody(userId, username);
            });
        }
    })
}

document.getElementById("add-address-info").addEventListener("click", function(e) {
    var addressProvinces = $('#addressProvince').val().split('/');
    var detailAddress = $('#detailAddress').val();
    var postCode = $('#postCode').val();
    var addressName = $('#addressName').val();
    var addressPhone = $('#addressPhone').val();
    var defaultAddress = document.getElementById('defaultAddress').checked;

    if (addressProvinces == "" || detailAddress == "") {
        toastr.error('地址信息和详细地址不能为空！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else if (addressName == "" || addressPhone == "") {
        toastr.error('收货人姓名和手机号码不能为空！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
    } else if (postCode != "") {
        if (!/^[1-9][0-9]{5}$/.test(postCode)) {
            toastr.error('邮编地址格式不正确！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }
    }
    $.ajax({
        url: "/api/address/insertAddress",
        method: 'POST',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({
            'consigneeName': addressName,
            'consigneePhone': addressPhone,
            'province': addressProvinces[0],
            'city': addressProvinces[1],
            'area': addressProvinces[2],
            'detailAddress': detailAddress,
            'postCode': Number(postCode),
            'defaultAddress': String(defaultAddress),
            'ownId': Number(userId),
            'ownName': username
        }),
        dataType: "json",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.data == "插入收货地址成功") {
                toastr.success('添加收货地址成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                $('#addAddressModal').modal('hide');
            } else {
                toastr.error('系统异常，请重新添加！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }

            document.getElementById("address-list").innerHTML = "";
            $('#address-list').load(location.href + " #address-list", function() {
                addressBody(userId, username);
            });
        }
    })
});

// 我要发货

$('#startTime').datetimepicker({
    format: 'yyyy-mm-dd hh:mm:ss',
    autoclose: true,
    todayBtn: true,
    clearBtn: true
});

$('#endTime').datetimepicker({
    format: 'yyyy-mm-dd hh:mm:ss',
    autoclose: true,
    todayBtn: true,
    clearBtn: true
});

deliverProductBody(username);

var logisticsInfo;

function deliverProductBody(name) {
    $.ajax({
        url: "/api/order/findOrderByOwn/" + name,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log("order:", result);
            logisticsInfo = result.data;
            deliverProductBodyInfo(result.data);
        }
    })
}

function deliverProductBodyInfo(list) {
    var str = ``;
    $.each(list, function(index, data) {
        var logisticsTd, logisticsModify;
        if (data.logistics == null) {
            logisticsTd = `<td><a onclick="bindLogistics(${data.childOrderId})">绑定物流号</a></td>`;
            logisticsModify = ``;
        } else {
            logisticsTd = `<td>${data.logistics.logisticsType + ' ' + data.logistics.logisticsId}</td>`;
            logisticsModify = `<a onclick="changeLogistics(${index}, ${data.childOrderId})">修改物流</a>`;
        }

        var statusTd;
        if (data.childOrderStatus == "已支付") {
            statusTd = `<td style="color: rgb(250, 75, 75);">待发货</td>`;
        } else if (data.childOrderStatus == "已发货") {
            statusTd = `<td>已发货</td>`;
        } else if (data.childOrderStatus == "已签收") {
            statusTd = `<td>待评价</td>`;
        } else if (data.childOrderStatus == "已评价") {
            statusTd = `<td><a onclick="seeCommentary(${data.childOrderId})">查看评价</a></td>`;
        } else {
            statusTd = `<td></td>`;
        }

        str = str +
            `<tr>
                <td>${data.childOrderId}</td>
                <td><span class="deliver-productName" onclick="window.open('CommodityDetails.html?productId='+'${data.product.productId}')" title="${data.product.productName}">${data.product.productName}</span></td>
                <td>${data.masterOrder.address.consigneeName + ' ' + data.masterOrder.address.consigneePhone}</td>
                <td>${data.payTime}</td>
                ` + statusTd + `<td>${data.remark}</td>` + logisticsTd + `
                <td style="display: flex; flex-direction: column; min-height: 57px; justify-content: center;">
                    ` + logisticsModify + `
                    <a onclick="cancelOrder(${data.childOrderId})">取消订单</a>
                </td>
            </tr>`;
    });
    $("#deliver-product-list").append(str);
}

function bindLogistics(id) {
    $("#bindLogisticsModal").modal();
    $("#bindLogisticsModal").attr("child-order-id", id);
    document.getElementById("logistics-type").innerHTML = "";
    document.getElementById("logistics-id").innerHTML = "";
}

document.getElementById("bindLogistics_btn_modal").addEventListener('click', function() {
    var logistics_id = $("#bindLogistics_btn_modal").attr("data-modify-logistics-id");
    if (logistics_id == "null") {
        var childOrderId = $("#bindLogisticsModal").attr("child-order-id");
        var logisticsType = $("#logistics-type").val();
        var logisticsId = $("#logistics-id").val();

        // console.log(logisticsId, logisticsType, childOrderId, usreId, username);

        if (logisticsType == '') {
            toastr.error('物流类型不能为空！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }

        if (logisticsType.indexOf("校") == -1 && logisticsId == "") {
            toastr.error('物流单号不能为空！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }

        $.ajax({
            url: "/api/logistics/insertLogistics",
            method: "POST",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                'logisticsId': logisticsId,
                'logisticsType': logisticsType,
                'childOrderId': childOrderId,
                'supplierId': userId,
                'supplierName': username
            }),
            dataType: "json",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                if (result.data == "插入物流信息成功") {
                    toastr.success('绑定成功！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                    $("#bindLogisticsModal").modal('hide');

                    document.getElementById("deliver-product-list").innerHTML = "";
                    $('#deliver-product-list').load(location.href + " #deliver-product-list", function() {
                        deliverProductBody(username)
                    });
                } else {
                    toastr.error('绑定失败，请重试！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                }
            }
        })
    } else {
        var childOrderId = $("#bindLogisticsModal").attr("child-order-id");
        var logisticsType = $("#logistics-type").val();
        var logisticsId = $("#logistics-id").val();

        if (logisticsType == '') {
            toastr.error('物流类型不能为空！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }

        if (logisticsType.indexOf("校") == -1 && logisticsId == "") {
            toastr.error('物流单号不能为空！！！');
            $('#promptModal').modal();
            $('#promptModal').modal('hide');
        }

        $.ajax({
            url: "/api/logistics/updateLogistics",
            method: "POST",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                'logisticsId': logisticsId,
                'logisticsType': logisticsType,
                'childOrderId': childOrderId,
                'id': logistics_id
            }),
            dataType: "json",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                if (result.msg == "OK") {
                    toastr.success('更新成功！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                    $("#bindLogisticsModal").modal('hide');

                    document.getElementById("deliver-product-list").innerHTML = "";
                    $('#deliver-product-list').load(location.href + " #deliver-product-list", function() {
                        deliverProductBody(username)
                    });
                } else {
                    toastr.error('更新失败，请重试！！！');
                    $('#promptModal').modal();
                    $('#promptModal').modal('hide');
                }
            }
        })
    }
});

$('#bindLogisticsModal').on('hide.bs.modal', function() {
    $("#bindLogistics_btn_modal").attr("data-modify-logistics-id", null);
});

function seeCommentary(id) {

}

function changeLogistics(index, id) {
    $("#bindLogisticsModal").attr("child-order-id", id);
    document.getElementById("logistics-type").value = logisticsInfo[index].logistics.logisticsType;

    var logisticsId = logisticsInfo[index].logisticsId;
    if (logisticsId == null) {
        logisticsId = '';
    }
    document.getElementById("logistics-id").value = logisticsId;
    $("#bindLogistics_btn_modal").attr("data-modify-logistics-id", logisticsInfo[index].logistics.id);
    $("#bindLogisticsModal").modal();
    console.log(logisticsInfo[index])
}

function cancelOrder(id) {
    $.ajax({
        url: "/api/order/updateChildOrderStatusByChildOrderId/" + id + "/DISABLE",
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();

            if (result.data == "更新子订单状态成功") {
                toastr.success('取消成功！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                $("#bindLogisticsModal").modal('hide');

                document.getElementById("deliver-product-list").innerHTML = "";
                $('#deliver-product-list').load(location.href + " #deliver-product-list", function() {
                    deliverProductBody(username)
                });
            } else {
                toastr.error('取消失败，请重试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }
        }
    })
}

document.getElementById("deliver-select").addEventListener('click', function() {
    var productName = $('#deliverProductName').val() == '' ? null : $('#deliverProductName').val();
    var orderStartTime = $("#startTime").data().date;
    if (orderStartTime == undefined) {
        orderStartTime = '';
    }
    var orderEndTime = $("#endTime").data().date;
    if (orderEndTime == undefined) {
        orderEndTime = '';
    }
    var childOrderId = $('#deliverChildOrderId').val() == '' ? null : $('#deliverChildOrderId').val();
    var consigneeName = $('#deliverBuyer').val() == '' ? null : $('#deliverBuyer').val();
    var logisticsType = $('#deliverLogisticsType').val() == '' ? null : $('#deliverLogisticsType').val();
    var logisticsId = $('#deliverLogisticsId').val() == '' ? null : $('#deliverLogisticsId').val();
    var childOrderStatus = $('#deliverStatus option:selected').val();

    if (childOrderStatus == "全部") {
        childOrderStatus = null;
    }
    if (childOrderStatus == "未发货") {
        childOrderStatus = "已支付";
    }
    if (childOrderStatus == "未评价") {
        childOrderStatus = "已签收";
    }

    console.log(productName, orderStartTime, orderEndTime, childOrderId, consigneeName, logisticsType, logisticsId, childOrderStatus);

    $.ajax({
        url: "/api/order/findOrderByOwnWithSelect",
        method: "POST",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({
            'ownName': username,
            'productName': productName,
            'orderStartTime': orderStartTime,
            'orderEndTime': orderEndTime,
            'childOrderId': childOrderId,
            'consigneeName': consigneeName,
            'logisticsType': logisticsType,
            'logisticsId': logisticsId,
            'childOrderStatus': childOrderStatus
        }),
        dataType: "json",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log(result);

            document.getElementById("deliver-product-list").innerHTML = "";
            if (result.data != null && result.data.length > 0) {
                deliverProductBodyInfo(result.data);
            } else {
                console.log("aaa");
            }
        }
    })
});

// $('#productType1 option:selected').val();

// $("#showImage").fileinput({
//     // 5138 if (showUpl) 去掉单张图片上的上传按钮
//     language: "zh",
//     uploadUrl: '#',
//     theme: 'fas',
//     showUpload: false,
//     showCaption: true,
//     browseClass: "btn btn-primary",
//     fileType: "any",
//     autoReplace: true,
//     previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
//     allowedFileExtensions: ['jpg', 'png', 'jpeg', 'gif', 'bmp', 'AVI', 'mov', 'rmvb', 'rm', 'FLV', 'mp4']
// }).on('filepredelete', function(event, id) {
//     showImages = [];
//     console.log('Uploaded thumbnail successfully removed');
// });

// $('.fileinput-remove:not([disabled])'), 'click',
//     function() {
//         console.log('Uploaded thumbnail successfully removed');
//     };