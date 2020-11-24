// var userSession = JSON.parse(sessionStorage.getItem('user'));
// var tokenSession = sessionStorage.getItem('token');
var settleMap = JSON.parse(sessionStorage.getItem('settleMap'));
// // console.log(userSession);
// // if (userSession == null || tokenSession == null) {
// //     window.location.href = "login.html";
// // } else {
// //     var userId = userSession.userId;
// //     document.getElementById("user-headimg").src = userSession.headImage;
// // }
// // console.log(settleMap)

// var userId = 2;
// var username = "wuziyi";

// 提示框初始化
toastr.options.positionClass = 'toast-center-center';

// 收货地址
getAddressInfo(userId, username);

var addressInfo;

function getAddressInfo(ownId, ownName) {
    $.ajax({
        url: "/api/address/findAddressByOwn/" + ownId + "/" + ownName,
        method: 'GET',
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result)
            addressInfo = result.data;
            if (result.data.length > 0) {
                $("#no-address").attr("style", "display: none;");
                addressBody(result.data);
                realpayAddress(0);
            }
        }
    })
}

function addressBody(list) {
    var defaultAddressBody = ``;
    var commonAddressBody = ``;
    var defaultAddressId;
    $.each(list, function(index, data) {
        if (data.defaultAddress == "true") {
            defaultAddressId = data.addressId;
        }
    });
    $.each(list, function(index, data) {
        if (data.defaultAddress == "true") {
            defaultAddressBody = defaultAddressBody +
                `<div class="addr-item-wrapper TwoRow addr-default addr-selected" data-address-index="${index}">
                    <div class="inner-infos">
                        <div class="addr-hd"><span>${data.province.split("省").join("")}</span><span>${data.city.split("市").join("")}</span><span>（${data.consigneeName}收）</span></div>
                        <div class="addr-bd">
                            <span>${data.area.split("区").join("")}</span><span>${data.detailAddress}</span><span>${data.consigneePhone}</span>
                        </div>
                        <a title="修改地址" class="modify-operation" onclick="modifyAddress(${index}, ${data.addressId});">修改</a>
                    </div>
                    <div class="default-tip">默认地址</div>
                    <div class="curMarker"></div>
                </div>`;
        } else {
            commonAddressBody = commonAddressBody +
                `<div class="addr-item-wrapper TwoRow addr-not-default" data-address-index="${index}">
                    <div class="inner-infos">
                        <div class="addr-hd"><span>${data.province.split("省").join("")}</span><span>${data.city.split("市").join("")}</span><span>（${data.consigneeName}收）</span></div>
                        <div class="addr-bd">
                            <span>${data.area.split("区").join("")}</span><span>${data.detailAddress}</span><span>${data.consigneePhone}</span>
                        </div>
                        <a title="修改地址" class="modify-operation" onclick="modifyAddress(${index}, ${data.addressId});">修改</a>
                    </div>
                    <div class="set-default" onclick="setDefault(${data.addressId}, ${defaultAddressId});" title="设置当前地址为默认">设为默认</div>
                    <div class="curMarker"></div>
                </div>`;
        }
    });
    // 点击效果
    $("#address-body").append(defaultAddressBody + commonAddressBody);
    $('.addr-item-wrapper.TwoRow .inner-infos').on('mouseover', function() {
        if (!$(this).parent().hasClass("addr-selected")) {
            $(this).css("background", "url('img/sureOrder/address-border.png') no-repeat");
        }
    });

    $('.addr-item-wrapper.TwoRow .inner-infos').on('mouseout', function() {
        if (!$(this).parent().hasClass("addr-selected")) {
            $(this).css("background", "url('img/sureOrder/address-border-default.png') no-repeat");
        }
    });

    $(".addr-item-wrapper").on('click', function() {
        var brother = $(this).siblings();
        brother.removeClass("addr-selected");
        brother.children(".inner-infos").css("background", "url('img/sureOrder/address-border-default.png') no-repeat");
        $(this).addClass("addr-selected");

        realpayAddress($(this).attr("data-address-index"));
    })
}

// 添加收货地址
document.getElementById("addAddress").addEventListener('click', function() {
    $('#addAddressModal').modal();
    $('#address-body').load(location.href + " #address-body");
});

document.getElementById("address_btn").addEventListener('click', function() {
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

                document.getElementById("address-body").innerHTML = "";
                $('#address-body').load(location.href + " #address-body", function() {
                    getAddressInfo(userId, username);
                });
            }
        })
    } else {
        $.ajax({
            url: "/api/address/updateAddress/" + userId,
            method: 'POST',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                'addressId': Number($("#address_btn").attr("data-modify-addressId")),
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

                document.getElementById("address-body").innerHTML = "";
                $('#address-body').load(location.href + " #address-body", function() {
                    getAddressInfo(userId, username);
                });
            }
        })
    }
});

document.getElementById("address_btn_close").addEventListener('click', function() {
    $('#addAddressModal').modal("hide");
    document.getElementById("address-body").innerHTML = "";
    $('#address-body').load(location.href + " #address-body", function() {
        getAddressInfo(userId, username);
    });
});

function setDefault(addressId, defaultId) {
    var trueStatus = "true",
        falseStatus = "false";
    $.ajax({
        url: "/api/address/updateDefaultAddress/" + addressId + "/" + trueStatus,
        method: "POST",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.data == "更新默认收货地址成功") {
                $.ajax({
                    url: "/api/address/updateDefaultAddress/" + defaultId + "/" + falseStatus,
                    method: "POST",
                    timeout: 15000,
                    beforeSend: function() {
                        $("#loading").html("<img src='img/loading.gif' />");
                    },
                    success: function(result) {
                        $("#loading").empty();
                        if (result.data == "更新默认收货地址成功") {
                            toastr.success('设置成功！！！');
                            $('#promptModal').modal();
                            $('#promptModal').modal('hide');
                        } else {
                            toastr.error('设置失败，请稍后重试！！！');
                            $('#promptModal').modal();
                            $('#promptModal').modal('hide');
                        }

                        document.getElementById("address-body").innerHTML = "";
                        $('#address-body').load(location.href + " #address-body", function() {
                            getAddressInfo(userId, username);
                        });
                    }
                })
            } else {
                toastr.error('设置失败，请稍后重试！！！');
                $('#promptModal').modal();
                $('#promptModal').modal('hide');
            }

            document.getElementById("address-body").innerHTML = "";
            $('#address-body').load(location.href + " #address-body", function() {
                getAddressInfo(userId, username);
            });
        }
    })
}

function modifyAddress(index, addressId) {
    $("#addressProvince").citypicker("setValue", addressInfo[index].province + "/" + addressInfo[index].city + "/" + addressInfo[index].area);
    document.getElementById("detailAddress").value = addressInfo[index].detailAddress;
    document.getElementById("postCode").value = addressInfo[index].postCode;
    document.getElementById("addressName").value = addressInfo[index].consigneeName;
    document.getElementById("addressPhone").value = addressInfo[index].consigneePhone;
    if (addressInfo[index].defaultAddress == "true") {
        document.getElementById('defaultAddress').checked = true;
    }
    if (addressInfo[index].defaultAddress == "false") {
        document.getElementById('defaultAddress').checked = false;
    }
    $('#address_btn').attr("data-modify-addressId", addressId);
    $('#addAddressModal').modal();
}

// 下单商品信息
orderInfoBody(settleMap);

function orderInfoBody(map) {
    var str = ``,
        total = 0;
    $.each(settleMap, function(key, value) {
        str = str + `<div class="order-border"></div>`;
        $.each(value, function(index, data) {
            var image = data.image.split("、");
            var sellStr = ``;
            total = total + data.price * data.cartNumber;
            if (index == 0) {
                sellStr =
                    `<div class="order-orderInfo">
                        <img src="img/sureOrder/store.png" class="shopIcon">
                        <span class="shop-seller">卖家:&nbsp;${key}</span>
                    </div>`;
            }
            str = str +
                `<div id="order-item-row">` + sellStr +
                `<div class="order-itemInfo">
                    <div class="info-detail info-cell">
                        <a href="#">
                            <img class="info-img" src="${image[0]}">
                        </a>
                        <div class="info-cell info-msg">
                            <a href="CommodityDetails.html?productId=${data.productId}" class="order-link info-title">${data.productName}</a>
                            <span class="delivery-time">极速发货</span>
                        </div>
                    </div>
                    <div class="info-price info-cell">${toDecimal2(data.price)}</div>
                </div>
                <div class="order-quantity">
                    <div class="quantity-inner">
                        <p>${data.cartNumber}</p>
                    </div>
                </div>
                <div class="item-row__price">
                    <div class="label item-row__price-item">
                        <span>${toDecimal2(data.price * data.cartNumber)}</span>
                    </div>
                </div>
            </div>

            <div class="order-note">
                <div class="order-note-block">
                    <div class="order-note-row">
                        <div class="order-note-row-item">
                            <div class="order-note-row-item-border">
                                <div class="textarea">
                                    <label class="textarea__title">
                                        <div>给卖家留言：</div>
                                    </label>
                                    <div class="textarea__wrapper">
                                        <span class="next-input next-input-textarea textarea__input">
                                            <textarea placeholder="选填,请先和商家协商一致" maxlength="200"
                                                rows="4" id="order-remark-${data.productId}"></textarea>
                                            <span class="next-input-control">
                                                <span class="next-input-len">0/200</span>
                                        </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="order-note-row-item">
                            <div class="order-note-row-item-border">
                                <div class="delivery-method">
                                    <div class="delivery-select">
                                        <span class="delivery-title">运送方式：</span>
                                        <div class="delivery-box">
                                            <span class="single-method">
                                                <label class="title-text">${data.tradeName}</label>
                                            </span>
                                            <div class="appoint-container"></div>
                                        </div>
                                    </div><span class="select-price">0.00</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="order-note-block">
                    <div class="order-item-total">
                        <div class="label">
                            <span class="label__header">商品合计</span>
                            <span id="order-total">${toDecimal2(data.price * data.cartNumber)}</span>
                        </div>
                    </div>
                </div>
            </div>`;
        });
    });
    $("#orderInfo").append(str);
    document.getElementById("realpay-price").innerHTML = toDecimal2(total);
}

// 实付盒子的收货信息
function realpayAddress(index) {
    var address = addressInfo[index];
    document.getElementById("realpay-address").innerHTML = address.province + " " + address.city + " " + address.area + " " + address.detailAddress;
    document.getElementById("realpay-consignee").innerHTML = address.consigneeName + " " + address.consigneePhone;
    $("#order-submit").attr("data-address-id", address.addressId);
}

// 提交订单
console.log("settleMap", settleMap);
document.getElementById("order-submit").addEventListener("click", function() {
    var addressId = $("#order-submit").attr("data-address-id");
    if (addressId == undefined) {
        toastr.error('请选择一个收货地址！！！');
        $('#promptModal').modal();
        $('#promptModal').modal('hide');
        return false;
    }
    var childOrderList = new Array();
    var shopCartIdList = new Array();
    $.each(settleMap, function(key, value) {
        $.each(value, function(index, data) {
            var childOrder = {
                "productId": data.productId,
                "number": data.cartNumber,
                "price": toDecimal2(data.price),
                "ownName": data.ownName,
                "remark": $("#order-remark-" + data.productId).val(),
                "product": {
                    "productName": data.productName,
                    "number": data.number
                }
            }
            childOrderList.push(childOrder);
            shopCartIdList.push(data.id);
        });
    });

    if (childOrderList.length > 0) {
        $.ajax({
            url: "/api/order/insertOrder/" + userId + "/" + addressId + "/" + shopCartIdList,
            method: "POST",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(childOrderList),
            dataType: "json",
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