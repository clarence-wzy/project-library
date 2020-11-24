if (getCookie("admin") == null) {
    window.location.href = "login.html";
}
var admin = JSON.parse(getCookie("admin"));
var adminId = admin.adminId;

var list;
let promise = new Promise(function(resolve, reject) {
    $.ajax({
        url: '/api/user/findAllByAdminId/' + adminId,
        method: "GET",
        success: function(data) {
            // console.log(data);
            list = data.data;
            resolve();
        }
    });
});
promise.then(function(fulfilled) {
    layui.use(['table'], function() {
        var table = layui.table;
        table.render({
            elem: '#userTable',
            id: "userTable",
            // url: "/api/user/findAllByAdminId/1",
            loading: true,
            page: {
                layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],
                limit: 5,
                limits: [5, 10, 15, 20, 30, 40],
                groups: 5
            },
            cols: [
                [{
                    field: 'username',
                    title: '用户'
                }, {
                    field: 'role',
                    title: '角色',
                    width: 70,
                    templet: "#role_tr"
                }, {
                    field: 'realName',
                    title: '实名',
                    width: 100
                }, {
                    field: 'credit',
                    title: '信用度',
                    width: 100
                }, {
                    field: 'email',
                    title: '邮箱'
                }, {
                    field: 'phone',
                    title: '手机号'
                }, {
                    field: 'product',
                    title: '已发布的商品',
                    templet: "#product"
                }, {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    toolbar: '#barOpera'
                }]
            ],
            data: list,
            done: function(res, curr, count) {
                // console.log(res);
                productTable([]);
            }
        });

        table.on('tool(userTable)', function(obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                productTable(data.productList);
            } else if (obj.event === 'delete') {
                layer.open({
                    title: '在线操作',
                    content: `<div class="form-group">
                        <label class="control-label">理由：</label>
                        <input type="text" class="form-control" id="forbid-${data.userId}" />
                    </div>`,
                    btn: ["确定", "取消"],
                    btnAlign: 'c',
                    yes: function() {
                        if (data.userStatus == "FORBID") {
                            var status = "ENABLE";
                            var logContent = "管理员" + adminId + "解除禁用用户" + data.username;
                            var msg = "已解除禁用";
                        } else {
                            var status = "FORBID";
                            var logContent = "管理员" + adminId + "禁用用户" + data.username;
                            var msg = "已禁用";
                        }
                        var reason = $("#forbid-" + data.userId).val();
                        $.ajax({
                            url: "/api/user/updateUserStatus/" + adminId + "/" + data.userId + "/" + status,
                            method: "POST",
                            data: {
                                "log_content": logContent,
                                "reason": reason
                            },
                            success: function(result) {
                                // console.log(result);
                                if (result.data == "更新成功") {
                                    layer.msg(msg);
                                    let promise1 = new Promise(function(resolve, reject) {
                                        $.ajax({
                                            url: '/api/user/findAllByAdminId/' + adminId,
                                            method: "GET",
                                            success: function(data) {
                                                // console.log(data);
                                                list = data.data;
                                                resolve();
                                            }
                                        });
                                    });
                                    promise1.then(function(fulfilled) {
                                        layui.use(['table'], function() {
                                            var table = layui.table;
                                            table.reload("userTable", {
                                                loading: true,
                                                data: list
                                            });
                                        });
                                    });
                                } else {
                                    layer.msg("操作失败，请重试");
                                }
                            }
                        });
                    },
                    btn2: function(index) {
                        layer.close(index);
                    }
                });
            } else if (obj.event === 'detail') {
                layer.open({
                    title: '用户信息',
                    content: data.username + "，实名：" + data.realName + "，性别：" + data.sex + "，信用度：" + data.credit + "，手机号：" +
                        data.phone + "，邮箱：" + data.email + "，qq：" + data.qq + "，微信：" + data.wechat + "，已发布商品数：" + data.productList.length
                });
            }
        });
    });
});

function productTable(list1) {
    layui.use(['table'], function() {
        var table = layui.table;
        table.render({
            elem: '#productTable',
            id: "productTable",
            loading: true,
            page: {
                layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],
                limit: 10,
                limits: [5, 10, 15, 20, 30, 40],
                groups: 5
            },
            cols: [
                [{
                    field: 'productName',
                    title: '商品名称',
                    unresize: true,
                    templet: "#productName_tr"
                }, {
                    field: 'price',
                    title: '价格',
                    unresize: true,
                    width: 100
                }, {
                    field: 'number',
                    title: '数量',
                    unresize: true,
                    width: 70
                }, {
                    field: 'typeName',
                    title: '商品类型',
                    unresize: true,
                    width: 120
                }, {
                    field: 'qualityName',
                    title: '成色',
                    unresize: true,
                    width: 120
                }, {
                    field: 'trade',
                    title: '交易方式',
                    unresize: true,
                    templet: "#trade_tr"
                }, {
                    field: 'productCreateTime',
                    title: '发布时间',
                    unresize: true
                }, {
                    field: 'expirationTime',
                    title: '过期时间',
                    unresize: true
                }]
            ],
            data: list1,
            done: function(res, curr, count) {
                // console.log(res);
            }
        });
    });
}