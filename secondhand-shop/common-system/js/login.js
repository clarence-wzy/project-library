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

$('#login').click(function() {
    var username = $('#username').val();
    var userpassword = $('#userpassword').val();

    if (username.length == 0) {
        show_validate_msg("#login-username", "error", "请输入用户名");
        return false;
    }
    if (userpassword.length == 0) {
        show_validate_msg("#login-password", "error", "请输入密码");
        return false;
    }

    $.ajax({
        url: "/api/login",
        type: "POST",
        data: {
            name: username,
            password: userpassword
        },
        success: function(result) {
            if (result.msg == "OK") {
                jQuery.alertWindow("登录成功", true);
                // console.log(result);
                //设置 session
                sessionStorage.setItem("token", result.data.token);
                sessionStorage.setItem("user", JSON.stringify(result.data.user[0]));
                //设置 cookie （有限期一天）
                // $.cookie('token', result.data.token, {
                //     expires: 1
                // });
                // $.cookie('user', JSON.stringify(result.data.user[0]), {
                //     expires: 1
                // });

                setCookie("user", JSON.stringify(result.data.user[0]), "s86400");
                setCookie("token", JSON.stringify(result.data.token), "s86400");

                setTimeout(function() {
                    window.location.href = "index.html";
                }, 1500);
            } else {
                jQuery.alertWindow("登录失败，" + result.msg, false);
                // setTimeout(function() {
                //     window.location.href = "login.html";
                // }, 500);
            }
        }
    });
});

//  注册框提交注册事件
$('#register').click(function() {
    var registerUsername = $("#register-username").val();
    var registerPassword = $("#register-password").val();
    var registerPhone = $("#register-phone").val();
    var registerEmail = $("#register-email").val();
    var registerCode = $("#register-code").val();

    $.ajax({
        url: "/api/user/register/" + registerCode,
        type: "POST",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({
            'username': registerUsername,
            'password': registerPassword,
            'phone': registerPhone,
            'email': registerEmail
        }),
        dataType: "json",
        success: function(result) {
            if (result.data == "注册成功") {
                jQuery.alertWindow("注册成功", true);
                setTimeout(function() {
                    window.location.href = "login.html";
                }, 2000);
            } else if (result.data == "验证码错误") {
                jQuery.alertWindow("验证码错误！！！", false);
            }
        },
        error: function() {
            jQuery.alertWindow("注册失败", false);
        },
    });
});

//  校验 username 是否存在
document.getElementById('register-username').addEventListener("blur", () => {
    var registerUsername = $("#register-username").val();
    $.ajax({
        url: "/api/user/findUserByUsername/" + registerUsername,
        type: "GET",
        success: function(result) {
            if (result.data != null) {
                show_validate_msg($("#register-username"), "error", "用户名已存在");
            }
        }
    });
});

//  校验 username
document.getElementById('register-username').addEventListener("input", () => {
    validate_form_template("#register-username", /^[a-zA-Z0-9_-]{6,16}$/, "用户名格式不正确");
});
//  校验 password
document.getElementById('register-password').addEventListener("input", () => {
    //	清除确认密码元素的校验状态
    $("#register-password-again").parent().removeClass("has-success has-error");
    $("#register-password-again").parent().next("span").text("");
    validate_form_template("#register-password", /^[a-zA-Z0-9_-]{6,16}$/, "密码格式不正确");
});
//  校验 password-again
document.getElementById('register-password-again').addEventListener("input", () => {
    var registerPassword = $("#register-password").val();
    var registerPasswordAgain = $("#register-password-again").val();
    if (registerPassword.length == 0) {
        show_validate_msg($("#register-password-again"), "error", "请先输入密码");
    } else if (registerPassword != registerPasswordAgain) {
        show_validate_msg($("#register-password-again"), "error", "密码不一致");
    } else {
        show_validate_msg($("#register-password-again"), "success", "");
    }
});
//  校验 phone
document.getElementById('register-phone').addEventListener("input", () => {
    validate_form_template("#register-phone", /^1[34578]\d{9}$/, "手机格式不正确");
});
//  校验 email
document.getElementById('register-email').addEventListener("input", () => {
    validate_form_template("#register-email", /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/, "邮箱格式不正确");
});

//	显示校验结果的提示信息
function show_validate_msg(ele, status, msg) {
    //	清除当前元素的校验状态
    $(ele).parent().removeClass("has-success has-error");
    $(ele).parent().next("span").text("");

    if (status == "success") {
        $(ele).parent().addClass("has-success");
        $(ele).next("span").text("");
    } else if (status == "error") {
        $(ele).parent().addClass("has-error");
        $(ele).parent().next("span").text(msg);
    }
}

//  校验模板
function validate_form_template(id, regula, msg) {
    var idVal = $(id).val();
    if (idVal.length == 0) {
        show_validate_msg(id, "error", msg);
        return false;
    }
    if (!regula.test(idVal)) {
        show_validate_msg(id, "error", msg);
        return false;
    }
    show_validate_msg(id, "success", "");
    return true;
}

//  自定义提示框
jQuery.extend({
    alertWindow: function(e, status) {
        var successStatus = '<div class="alertWindow1" style="width: 100%;height: 100%; background:rgba(0,0,0,0.5);position: fixed; left:0px; top: 0px; z-index: 9999;"><div style="width: 360px; height: 170px;background: #FFF;margin: 300px auto;border: 2px solid #CFCFCF;"><div style="width: inherit;height: 20px;"><div class="alertWindowCloseButton1" style="float: right; width: 10px; height: 30px;margin-right:5px;font-family:\'microsoft yahei\';cursor: pointer;"></div></div><div id="successImg" class="alertWindowTitle" style="margin-top:10px;text-align:center;font-family:\'Verdana, Geneva, Arial, Helvetica, sans-serif\';font-size: 18px;font-weight: normal;"></div><div class="alertWindowContent" style="width:360px;height: 40px;text-align:center;font-size: 18px;color: #7F7F7F;margin-top:10px;">' + e + '</div></div></div>';;
        var failStatus = '<div class="alertWindow1" style="width: 100%;height: 100%; background:rgba(0,0,0,0.5);position: fixed; left:0px; top: 0px; z-index: 9999;"><div style="width: 360px; height: 170px;background: #FFF;margin: 300px auto;border: 2px solid #CFCFCF;"><div style="width: inherit;height: 20px;"><div class="alertWindowCloseButton1" style="float: right; width: 10px; height: 30px;margin-right:5px;font-family:\'microsoft yahei\';cursor: pointer;"></div></div><div id="failImg" class="alertWindowTitle" style="margin-top:10px;text-align:center;font-family:\'Verdana, Geneva, Arial, Helvetica, sans-serif\';font-size: 18px;font-weight: normal;"></div><div class="alertWindowContent" style="width:360px;height: 40px;text-align:center;font-size: 18px;color: #7F7F7F;margin-top:10px;">' + e + '</div></div></div>';;
        var i = (status == true ? successStatus : failStatus);
        $("body").append(i);
        var s = $(".alertWindow1");
        //10秒后自动关闭窗口
        setTimeout(function() {
            s.hide()
        }, 1000);
    }
})

// 发送验证码
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

$("#send-code").on('click', function() {
    var codeRadios = document.getElementsByName("codeRadios");
    var codeRadio = "";
    for (var i = 0; i < codeRadios.length; i++) {
        if (codeRadios[i].checked) {
            codeRadio = codeRadios[i].value;
        }
    }
    if (codeRadio == "mail") {
        var mailCode = document.getElementsByName("register-email")[0].value;
        var regula = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!regula.test(mailCode)) {
            jQuery.alertWindow("请输入正确的邮箱", false);
            return false;
        }
        getMailCode(mailCode);
    } else if (codeRadio == "phone") {
        var phoneCode = document.getElementsByName("register-phone")[0].value;
        var regula = /^1[34578]\d{9}$/;
        if (!regula.test(phoneCode)) {
            jQuery.alertWindow("请输入正确的手机号", false);
            return false;
        }
        getPhoneCode(phoneCode);
    }
    time(this);
})

function getMailCode(data) {
    $.ajax({
        url: "/api/user/mail/" + data,
        method: "GET",
        success: function(result) {
            console.log("code:", result);
        }
    })
}

function getPhoneCode(data) {
    $.ajax({
        url: "/api/user/phone/" + data,
        method: "GET",
        success: function(result) {
            console.log("code:", result);
        }
    })
}

//  验证码单选框
$(":radio").click(function() {
    var codeType = $(this).val();
    if (codeType == "phone") {
        $('.phone-group').css('display', 'block');
        $('.mail-group').css('display', 'none');
    } else {
        $('.phone-group').css('display', 'none');
        $('.mail-group').css('display', 'block');
    }
});