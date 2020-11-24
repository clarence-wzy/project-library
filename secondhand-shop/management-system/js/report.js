if (getCookie("admin") == null) {
    window.location.href = "login.html";
}
var admin = JSON.parse(getCookie("admin"));
var adminId = admin.adminId;

var list;
let promise = new Promise(function(resolve, reject) {
    $.ajax({
        url: '/api/report/findReport/0/' + adminId + '/0/create',
        method: "GET",
        data: {
            'page': 1,
            'limit': 0
        },
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
            elem: '#reportTable',
            id: "reportTable",
            // url: "/api/report/findReport/0/1/0/null",
            loading: true,
            page: {
                layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],
                limit: 10,
                groups: 5
            },
            cols: [
                [{
                    field: 'productName',
                    title: '商品名称',
                    unresize: true,
                    templet: "#productName_tr"
                }, {
                    field: 'ownName',
                    title: '商品发布者',
                    unresize: true
                }, {
                    field: 'reportContent',
                    title: '举报理由',
                    unresize: true
                }, {
                    field: 'reportCreateTime',
                    title: '举报时间',
                    unresize: true
                }, {
                    field: 'reportAuditTime',
                    title: '审核时间',
                    unresize: true
                }, {
                    field: 'operation',
                    title: '操作',
                    width: 300,
                    unresize: true,
                    align: 'center',
                    toolbar: '#barDemo'
                }]
            ],
            data: list,
            done: function(res, curr, count) {
                // console.log(res);
            }
        });

        table.on('tool(reportTable)', function(obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                var contentStr;
                if (data.reportStatus == "审核通过") {
                    contentStr = data.auditContent + "<br>已扣除用户（" + data.ownName + "）信用度：" + data.deductCredit;
                }
                if (data.reportStatus == "审核不通过") {
                    contentStr = data.auditContent;
                }
                layer.alert('审核结果：<br>' + contentStr);
            } else if (obj.event === 'edit') {
                layer.open({
                    title: '在线审核',
                    content: `<div class="form-group">
                        <label class="control-label">审核结果：</label>
                        <textarea class="form-control" row="3" id="auditContent-${data.id}"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="control-label">扣除信用：</label>
                        <input type="text" class="form-control" id="deduct-${data.id}" />
                    </div>`,
                    btn: ["确定", "取消"],
                    btnAlign: 'c',
                    yes: function() {
                        var auditContent = $("#auditContent-" + data.id).val();
                        var deduct = $("#deduct-" + data.id).val();
                        $.ajax({
                            url: "/api/report/updateReport",
                            method: "POST",
                            data: {
                                'id': data.id,
                                'auditContent': auditContent,
                                'reportStatus': "审核通过",
                                'deductCredit': deduct,
                                'userId': data.ownId
                            },
                            success: function(result) {
                                // console.log(result);
                                if (result.data == "更新成功") {
                                    let promise1 = new Promise(function(resolve, reject) {
                                        $.ajax({
                                            url: '/api/report/findReport/0/1/0/create',
                                            method: "GET",
                                            data: {
                                                'page': 1,
                                                'limit': 0
                                            },
                                            success: function(data) {
                                                // console.log("a", data);
                                                list = data.data;
                                                resolve();
                                            }
                                        });
                                    });
                                    promise1.then(function(fulfilled) {
                                        table.reload("reportTable", {
                                            page: {
                                                curr: 1,
                                                limit: 10
                                            },
                                            data: list
                                        });
                                        layer.msg("审核通过成功");
                                    });
                                } else {
                                    layer.msg("审核通过失败！！！");
                                }
                            }
                        });
                    },
                    btn2: function(index) {
                        layer.close(index);
                    }
                });
            } else if (obj.event === 'del') {
                layer.open({
                    title: '在线审核',
                    content: `<div class="form-group">
                        <label class="control-label">审核结果：</label>
                        <textarea class="form-control" row="3" id="not-auditContent-${data.id}"></textarea>
                    </div>`,
                    btn: ["确定", "取消"],
                    btnAlign: 'c',
                    yes: function() {
                        var notAuditContent = $("#not-auditContent-" + data.id).val();
                        $.ajax({
                            url: "/api/report/updateReport",
                            method: "POST",
                            data: {
                                'id': data.id,
                                'auditContent': notAuditContent,
                                'reportStatus': "审核不通过",
                                'deductCredit': 0,
                                'userId': 0
                            },
                            success: function(result) {
                                // console.log(result);
                                if (result.data == "更新成功") {
                                    let promise2 = new Promise(function(resolve, reject) {
                                        $.ajax({
                                            url: '/api/report/findReport/0/1/0/create',
                                            method: "GET",
                                            data: {
                                                'page': 1,
                                                'limit': 0
                                            },
                                            success: function(data) {
                                                // console.log("a", data);
                                                list = data.data;
                                                resolve();
                                            }
                                        });
                                    });
                                    promise2.then(function(fulfilled) {
                                        table.reload("reportTable", {
                                            page: {
                                                curr: 1,
                                                limit: 10
                                            },
                                            data: list
                                        });
                                        layer.msg("审核不通过");
                                    });
                                } else {
                                    layer.msg("操作失败！！！");
                                }
                            }
                        });
                    },
                    btn2: function(index) {
                        layer.close(index);
                    }
                });
            }
        });
    });
});