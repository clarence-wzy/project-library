if (getCookie("admin") == null) {
    window.location.href = "login.html";
}
var admin = JSON.parse(getCookie("admin"));
console.log(admin);
var adminId = admin.adminId;

window.addEventListener("load", function() {
    document.getElementById("admin_remark").innerHTML = admin.remark;
    document.getElementById("tree-id").onmouseover = function(e) {
        e = e || window.event;
        var target = e.srcElement || e.target;
        var index = Number(target.dataset.index);
        // console.log(target, index); //当前的dom元素
        var children;
        if (target.tagName.toLowerCase() === "a") {
            children = this;
            this.querySelector("span").style.top = index * 58 + "px";
            this.querySelector("span").style.opacity = "1";
            var len = children.length;
            for (var i = 0; i < len; i++) {
                if (target == children[i]) { //当前值
                    var ss = document.getElementById("lt"); //获取下标元素
                    ss.style.left = children[i].offsetLeft + "px";
                    ss.style.top = children[i].offsetTop + children[i].offsetHeight + "px";
                    return;
                }
            }
        }
    };

    document.getElementById("tree-id").onmouseout = function(e) {
        e = e || window.event;
        var target = e.srcElement || e.target;
        var index = Number(target.dataset.index);
        var children;
        if (target.tagName.toLowerCase() === "a") {
            children = this;
            this.querySelector("span").style.opacity = "0";
            var len = children.length;
            for (var i = 0; i < len; i++) {
                if (target == children[i]) { //当前值
                    var ss = document.getElementById("lt"); //获取下标元素
                    ss.style.left = children[i].offsetLeft + "px";
                    ss.style.top = children[i].offsetTop + children[i].offsetHeight + "px";
                    return;
                }
            }
        }
    };

    $("#tree-id li").click(function() {
        $(this).siblings('li').removeClass('layui-nav-itemed');
        $(this).addClass('layui-nav-itemed');
        document.getElementById("iframe-body").src = $(this).attr("data-url") + ".html";
    });
});

function logout() {
    delCookie("admin");
    window.location.href = "login.html";
}