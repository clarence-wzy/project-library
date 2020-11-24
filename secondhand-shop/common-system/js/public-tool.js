// 设置cookies
function setCookie(name, value, time) {
    var strsec = getsec(time);
    var exp = new Date();
    exp.setTime(exp.getTime() + strsec * 1);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

function getsec(str) {
    var str1 = str.substring(1, str.length) * 1;
    var str2 = str.substring(0, 1);
    if (str2 == "s") {
        return str1 * 1000;
    } else if (str2 == "h") {
        return str1 * 60 * 60 * 1000;
    } else if (str2 == "d") {
        return str1 * 24 * 60 * 60 * 1000;
    }
}

//读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

var user = JSON.parse(getCookie("user"));
if (user == null) {
    // window.location.href = "login.html";
} else {
    var userId = user.userId;
    var username = user.username;
}
// console.log(user)

// var userSession = JSON.parse(sessionStorage.getItem('user'));
// var tokenSession = sessionStorage.getItem('token');

// if (userSession == null || tokenSession == null) {
//     // window.location.href = "login.html";
// } else {
//     var userId = userSession.userId;
// }

setTimeout(function() {
    var str = ``;
    try {
        if (user.studentId != null && user.studentPassword != null) {
            str = str +
                `<li onclick="turnTo(7, 'release-product');">发布商品</li>
                <li onclick="turnTo(8, 'my-product');">我的商品</li>
                <li onclick="turnTo(9, 'deliver-product');">我要发货</li>`;
        }
    } catch (err) {}
    str = str + `<li onclick="loginOut();">退出登录</li>`;
    $("#myMenu").append(str);

    if (user != null) {
        document.getElementById('username').innerHTML = username;
        $('.noLogin').css('display', 'none');
        $('.hasLogin').css('display', 'block');
        $("#userMenu").css('display', 'block');
    }
}, 1000)

window.addEventListener("load", function() {
    if (document.getElementById('backTop') !== null) {
        //回到顶部
        document.onscroll = function() {
            scrollStyle();
        }
    }
    if (userId) {
        //购物车信息渲染
        if ($("#shopcartNum").length > 0) {
            $.ajax({
                url: "/api/shopCart/findAllShopCart/" + userId,
                type: "GET",
                timeout: 15000,
                beforeSend: function() {
                    $("#loading").html("<img src='img/loading.gif' />");
                },
                success: function(result) {
                    $("#loading").empty();
                    // console.log(result)
                    document.getElementById('shopcartNum').innerHTML = result.data.length;

                    $('#noLoginP').css('display', 'none');
                    var str, totalPrice = 0.00;
                    if (result.data.length == 0) {
                        str = '<p>购物车中还没有商品，赶紧选购吧！</p>';
                        $('#shopcart').append(str);
                    } else {
                        str = `<div class="row"><div class="col-md-4">商品信息</div>
                    <div class="col-md-4">数量</div>
                    <div class="col-md-4">单价</div>
                    </div>`;
                        $.each(result.data, function(index, data) {
                            if (index < 5) {
                                str = str +
                                    `<div class="row">
                                        <div class="col-md-4 carPname">${data.productName}</div>
                                        <div class="col-md-4">${data.cartNumber}</div>
                                        <div class="col-md-4">¥${data.price}</div>
                                    </div>`;
                            }
                            totalPrice = totalPrice + data.price * data.cartNumber;
                        });
                        str = str +
                            `<div class="row">
                                <div class="col-md-12">
                                    <a style="cursor: pointer;" onclick="turnTo(5, 'my-shopcart');">更多操作</a>
                                </div>
                            </div>`;
                        $('#shopcart').append(str);
                        document.getElementById('shopcartPrice').innerHTML = parseFloat(totalPrice).toFixed(2);
                    }
                }
            });
        }
    }
}, false);

function backTop() {
    document.documentElement.scrollTop = 0;
}

function scrollStyle() {
    var oBackTop = document.getElementById('backTop');
    scro = document.documentElement.scrollTop;
    if (scro > 200) {
        oBackTop.style.display = 'block';
    } else {
        oBackTop.style.display = 'none';
    }
}

function turnTo(index, data) {
    window.open('PersonalInformation.html?index=' + index + '&nav=' + data);
}

toastr.options.positionClass = 'toast-center-center';

function loginOut() {
    delCookie("user");
    window.location.href = "index.html";
    $.ajax({
        url: "/api/logout",
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.data == "您已退出登录") {
                toastr.success('已退出登录！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
                setTimeout(function() {
                    window.location.href = "index.html";
                }, 500);
            } else {
                toastr.error('操作失败！请稍后尝试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }
        }
    });
}