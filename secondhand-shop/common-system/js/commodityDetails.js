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

if (user == null && getUrlArgs().admin == undefined) {
    window.location.href = "login.html";
}

if (getUrlArgs().admin != undefined) {
    var userId = getUrlArgs().admin;
    // $("#commodityDetails-header").attr("style", "display: none;");
    // $("#commodityDetails-footer").attr("style", "display: none;");
}

// var userId = 2;
if (userId) {
    var pId = Number($.query.get("productId"));
    var pNum = 0;
    var image = new Array();
    var productName = '';
    //商品信息渲染
    $.ajax({
        url: "/api/product/findProductByProductId/" + pId + "/" + userId,
        type: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            console.log(result);
            if (result.msg == "OK") {
                var credit = result.data.user.credit;
                if (credit == 500) {
                    document.getElementById("own-credit").innerHTML = "正常";
                } else if (credit < 500 && credit >= 300) {
                    document.getElementById("own-credit").innerHTML = "一般";
                    $("#own-credit").css("color", "#3399FF");
                } else if (credit > 500 && credit <= 700) {
                    document.getElementById("own-credit").innerHTML = "较好";
                    $("#own-credit").css("color", "#CC6600");
                } else if (credit > 700) {
                    document.getElementById("own-credit").innerHTML = "优秀";
                    $("#own-credit").css("color", "#FF3300");
                } else if (credit < 300) {
                    document.getElementById("own-credit").innerHTML = "差";
                    $("#own-credit").css("color", "#CC00CC");
                }

                var productTypes = new Array();
                productTypes = result.data.typeName.split("-");
                for (i = 0; i < productTypes.length; i++) {
                    var typeSymbol = document.createElement("span");
                    typeSymbol.innerHTML = "&gt;";
                    document.getElementById('productType').appendChild(typeSymbol);

                    var typeA = document.createElement("a");
                    var typeAttr = document.createAttribute("href");
                    typeAttr.nodeValue = "#";
                    typeA.setAttributeNode(typeAttr);
                    typeA.innerHTML = productTypes[i];

                    document.getElementById('productType').appendChild(typeA);
                }
                // 为您推荐
                getRecommendProduct(productTypes[1], 5);
                // 猜你喜欢
                getYouLike(productTypes[0], 5);

                document.getElementById('productName').innerHTML = result.data.productName;
                productName = result.data.productName;
                document.getElementById('productPrice').innerHTML = result.data.price;
                document.getElementById('productUserName').innerHTML = result.data.ownName;
                document.getElementById('productQuality').innerHTML = result.data.qualityName;
                document.getElementById('productTrade').innerHTML = result.data.tradeName;

                if (result.data.tradeName.indexOf("校内交易") != -1) {
                    document.getElementById('productTradeDecription').innerHTML = result.data.tradeDecription;
                } else {
                    document.getElementById('productTradeDecription').innerHTML = "校外交易";
                }

                document.getElementById('productNum').innerHTML = " （ 仅剩" + result.data.number + "件 ） ";

                var productDecrs = result.data.productDecription.split("，");
                for (i = 0; i < productDecrs.length; i++) {
                    var strs = productDecrs[i].split("：");
                    if (strs[1] == undefined) {
                        var otherStrs = productDecrs[i].split(":");
                        if (otherStrs[1] != undefined) {
                            $('#productDescription').append('<tr> <th> ' + otherStrs[0] + ' </th> <td> ' + otherStrs[1] + ' </td> </tr>');
                        }
                    } else {
                        $('#productDescription').append('<tr> <th> ' + strs[0] + ' </th> <td> ' + strs[1] + ' </td> </tr>');
                    }
                }

                image = result.data.image.split("、");
                var bigPicStr = ``;
                var smallPicStr = ``;
                var imageStr;
                $.each(image, function(index, data) {
                    if (data.indexOf(".AVI") != -1 || data.indexOf(".mov") != -1 || data.indexOf(".rmvb") != -1 ||
                        data.indexOf(".rm") != -1 || data.indexOf(".FLV") != -1 || data.indexOf(".mp4") != -1) {
                        imageStr = "img/icon/player.jpg";
                    } else {
                        imageStr = data;
                        return;
                    }
                });
                $.each(image, function(i, img) {
                    if (img.indexOf(".AVI") != -1 || img.indexOf(".mov") != -1 || img.indexOf(".rmvb") != -1 ||
                        img.indexOf(".rm") != -1 || img.indexOf(".FLV") != -1 || img.indexOf(".mp4") != -1) {
                        bigPicStr = bigPicStr +
                            `<li class="bigVideo bigPic">
                            <video type="video/mp4" controls="controls"
                                style="width: 100%; height: 100%; background-color: #f7f8f8;">
                                <source type="video/mp4" src="${img}">
                            </video>
                            </li>`;
                        smallPicStr = smallPicStr +
                            `<li class="picBtn l" style="border: 1px solid rgb(188, 188, 188);">
                            <img src="img/icon/player.jpg">
                            </li>`;
                    } else {
                        bigPicStr = bigPicStr +
                            `<li class="bigPic">
                            <img src="${img}" class="bigPicImg"  width="200" height="200">
                            </li>`;
                        smallPicStr = smallPicStr +
                            `<li class="picBtn l" style="border: 1px solid rgb(188, 188, 188);">
                            <img src="${img}">
                            </li>`;
                    }
                });
                document.getElementById('huge').setAttribute("src", image[0]);
                $("#productImage").append(bigPicStr);
                $("#btnBox").append(smallPicStr);

                if ($("#productImage")[0].children[0].innerHTML.indexOf("video/mp4") !== -1) {
                    $('#picBox').attr("data-status", "1");
                } else {
                    $('#picBox').attr("data-status", "0");
                }

                var detailImg = new Array();
                detailImg = result.data.detailImage.split("、");
                for (i = 0; i < detailImg.length; i++) {
                    var img = document.createElement("img");
                    var imgAttr = document.createAttribute("src");
                    imgAttr.nodeValue = detailImg[i];
                    img.setAttributeNode(imgAttr);

                    document.getElementById('productContent').appendChild(img);
                }

                var instructions =
                    `<div class="tb-price-spec">
                        <h3 class="spec-title">价格说明</h3>
                        <p class="title">划线价格</p>
                        <p class="info">指商品的专柜价、吊牌价、正品零售价、厂商指导价或该商品的曾经展示过的销售价等，<strong>并非原价</strong>，仅供参考。</p>
                        <p class="title">未划线价格</p>
                        <p class="info">
                            指商品的<strong>实时标价</strong>，不因表述的差异改变性质。具体成交价格根据商品参加活动，或会员使用优惠券、积分等发生变化，最终以订单结算页价格为准。
                        </p>
                        <p class="info">
                            商家详情页（含主图）以图片或文字形式标注的一口价、促销价、优惠价等价格可能是在使用优惠券、满减或特定优惠活动和时段等情形下的价格，具体请以结算页面的标价、优惠条件或活动规则为准。
                        </p>
                        <p class="info">此说明仅当出现价格比较时有效，具体请参见《淘宝价格发布规范》。若商家单独对划线价格进行说明的，以商家的表述为准。</p>
                    </div>`;
                $("#productContent").append(instructions);

                pNum = result.data.number;

                if (result.data.collect.length > 0) {
                    if (result.data.collect[0].collectStatus == "ENABLE") {
                        $('.glyphicon-heart').addClass('like');
                        $('.glyphicon-heart').attr('value', "true");
                    }
                }
            }
        }
    });

    //收藏
    $('.glyphicon-heart').on('click', function() {
        $(this).toggleClass('like');
        $(this).attr('value', !eval($('.glyphicon-heart').attr('value')));
        var cStatus;
        if ($(this).attr('value') == 'false') {
            cStatus = "DISABLE";
        } else {
            cStatus = "ENABLE";
        }
        $.ajax({
            url: "/api/collect/updateCollectStatus",
            type: "POST",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                'productId': pId,
                'userId': userId,
                'collectStatus': cStatus
            }),
            dataType: "json",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                if (result.msg == "OK") {
                    if (cStatus == "ENABLE") {
                        jQuery.alertWindow("已收藏", true);
                    } else {
                        jQuery.alertWindow("取消收藏", true);
                    }
                } else {
                    jQuery.alertWindow("系统异常", false);
                }
            }
        })

    });

    //商品详情用户评论切换
    var oIntroduce = document.getElementById('Introduce');
    var oAppraise = document.getElementById('Appraise');
    var oAllInContent = document.getElementsByClassName('allInContent')[0];
    var oAllAppraise = document.getElementsByClassName('allAppraise')[0];
    oIntroduce.onclick = function() {
        oIntroduce.setAttribute("class", "goodsIntroduceChoose");
        oAppraise.setAttribute("class", "goodsAppraise");
        oAllAppraise.style.display = 'none';
        oAllInContent.style.display = 'block';
    }
    oAppraise.onclick = function() {
        oIntroduce.setAttribute("class", "goodsIntroduce");
        oAppraise.setAttribute("class", "goodsAppraiseChoose");
        oAllAppraise.style.display = 'block';
        oAllInContent.style.display = 'none';
    }

    //购物车加减
    var oDecreaseBtn = document.getElementById('decreaseBtn');
    var oIncreaseBtn = document.getElementById('increaseBtn');
    var oPnumber = document.getElementById('pnumber');
    oIncreaseBtn.onclick = function() {
        oIncreaseBtn.removeAttribute('disabled');
        oDecreaseBtn.removeAttribute('disabled');
        if (oPnumber.value >= pNum) {
            oIncreaseBtn.setAttribute('disabled', 'disabled');
        } else {
            oPnumber.value++;
        }
    }
    oDecreaseBtn.onclick = function() {
        oIncreaseBtn.removeAttribute('disabled');
        oDecreaseBtn.removeAttribute('disabled');
        if (oPnumber.value <= 1) {
            oDecreaseBtn.setAttribute('disabled', 'disabled');
        } else {
            oPnumber.value--;
        }
    }

    // 放大镜
    $('#picBox').mousemove(function(event) {
        var status = $('#picBox').attr("data-status");
        if (status == 1) {
            return false
        };

        var nowleft = Math.floor(event.pageX - $('#picBox').offset().left) - 100;
        var nowtop = event.pageY - $('#picBox').offset().top - 100;
        $('#square').css('display', 'block');
        $('#hugePic').css('display', 'block');
        if (nowleft < 0) {
            nowleft = 0;
            $('#square').css('display', 'none');
            $('#hugePic').css('display', 'none');
        }
        if (nowleft > 250) {
            nowleft = 250;
            $('#square').css('display', 'none');
            $('#hugePic').css('display', 'none');
        }
        if (nowtop < 0) {
            nowtop = 0;
            $('#square').css('display', 'none');
            $('#hugePic').css('display', 'none');
        }
        if (nowtop > 250) {
            nowtop = 250;
            $('#square').css('display', 'none');
            $('#hugePic').css('display', 'none');
        }

        $('#square').css({
            'top': nowtop,
            'left': nowleft
        });
        $('#huge').css({
            'top': -nowtop * 2,
            'left': -nowleft * 2
        });
    });
}

function slider() {
    var oPicBtn = document.getElementsByClassName('picBtn');
    var oBigPic = document.getElementsByClassName('bigPic');
    var oHuge = document.getElementById('huge');
    var num;

    for (var i = 0; i < oPicBtn.length; i++) {
        bind(i);
    }

    function bind(index) {
        oPicBtn[index].onmouseover = function() {
            if ($("#huge").attr("src").indexOf(".mp4") != -1) {
                $('#picBox').attr("data-status", "1");
            } else {
                $('#picBox').attr("data-status", "0");
            }
            changePic(index);
        }
    }

    function changePic(index) {
        for (var j = 0; j < oBigPic.length; j++) {
            //把所有照片的透明度改为零
            oBigPic[j].style.opacity = 0;
            oPicBtn[j].style.border = 1 + 'px' + ' ' + 'solid' + ' ' + '#bcbcbc';
            //当j等于索引值是
            if (j == index) {
                oBigPic[j].style.opacity = 1;
                oPicBtn[j].style.border = 1 + 'px' + ' ' + 'solid' + ' ' + '#008842';
                num = index;

                oHuge.setAttribute('src', image[num]);
            }
        }
    }
}

// 为您推荐
function getRecommendProduct(name, count) {
    $.ajax({
        url: "/api/product/findRandProduct/" + count + "/" + name,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
            var str = ``;
            $.each(result.data, function(key, value) {
                var images = value.image.split("、");
                var imageStr;
                $.each(images, function(index, data) {
                    if (data.indexOf(".AVI") != -1 || data.indexOf(".mov") != -1 || data.indexOf(".rmvb") != -1 ||
                        data.indexOf(".rm") != -1 || data.indexOf(".FLV") != -1 || data.indexOf(".mp4") != -1) {} else {
                        imageStr = data;
                        return false;
                    }
                });
                str = str +
                    `<li class="l hotSalegoods">
                        <div class="p-img">
                            <img src="${imageStr}" width="135" height="135">
                        </div>
                        <div class="p-name">
                            <a href="CommodityDetails.html?productId=${value.productId}" title="${value.productName}">${value.productName}</a>
                        </div>
                        <div class="p-price">
                            <strong>¥${value.price}</strong>
                        </div>
                    </li>`;
            });
            $("#recommend-list").append(str);
        }
    })
}

// 猜你喜欢
function getYouLike(name, count) {
    $.ajax({
        url: "/api/product/findRandProduct/" + count + "/" + name,
        method: "GET",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            // console.log(result);
            var str = ``;
            $.each(result.data, function(key, value) {
                var images = value.image.split("、");
                var imageStr;
                $.each(images, function(index, data) {
                    if (data.indexOf(".AVI") != -1 || data.indexOf(".mov") != -1 || data.indexOf(".rmvb") != -1 ||
                        data.indexOf(".rm") != -1 || data.indexOf(".FLV") != -1 || data.indexOf(".mp4") != -1) {} else {
                        imageStr = data;
                        return false;
                    }
                });
                str = str +
                    `<div class="guessLikeGoodsBox" onclick="window.location.href='CommodityDetails.html?productId='+'${value.productId}'">
                        <div class="guessLikeGoodsPic l">
                            <img src="${imageStr}">
                        </div>
                        <div class="guessLikeGoodsTxt l">
                            <a href="CommodityDetails.html?productId=${value.productId}" title="${value.productName}">
                                <strong onclick="window.location.href='CommodityDetails.html?productId='+'${value.productId}'">${value.productName}</strong>
                                <span>¥${value.price}</span>
                            </a>
                        </div>
                    </div>`;
            });
            $("#you-like-list").append(str);
        }
    })
}

this.setTimeout(function() {
    slider();
}, 1000);

document.getElementById('toReport').addEventListener('click', function(e) {
    document.getElementById('modalProductName').innerHTML = productName;;
    $("#reportModal").modal();
});

document.getElementById('report-btn').addEventListener('click', function() {
    var reportContent = $("#report-content").val();
    if (reportContent != '') {
        $.ajax({
            url: "/api/report/insertReport",
            method: "POST",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                'reportContent': reportContent,
                'userId': userId,
                'productId': pId
            }),
            dataType: "json",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                console.log(result);
                if (result.data == "提交成功") {
                    $("#reportModal").modal("hide");
                    jQuery.alertWindow("提交成功", true);
                } else {
                    jQuery.alertWindow("提交失败，请重试", false);
                }
            }
        })
    } else {
        jQuery.alertWindow("提交失败，举报内容必须填写", false);
    }
});

// 加入购物车
document.getElementById('addShopCart').addEventListener('click', function() {
    $.ajax({
        url: "/api/shopCart/insertShopCart",
        method: "POST",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({
            'productId': pId,
            'cartNumber': document.getElementById('pnumber').value,
            'userId': userId
        }),
        dataType: "json",
        timeout: 15000,
        beforeSend: function() {
            $("#loading").html("<img src='img/loading.gif' />");
        },
        success: function(result) {
            $("#loading").empty();
            if (result.msg == "OK") {
                jQuery.alertWindow("已加入", true);

                document.getElementById("shopcart").innerHTML = "";
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
            } else {
                jQuery.alertWindow("加入失败，请重新尝试", false);
            }
        }
    })
});

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
});