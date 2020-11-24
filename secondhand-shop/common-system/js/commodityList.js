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

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

var startIndex = 0;
var pageSize = 20;
var pageNum = 1;
var productName = "null";
var menu = "null";
if (getUrlArgs() != undefined) {
    if (getUrlArgs().page != undefined) {
        pageNum = getUrlArgs().page;
        startIndex = 20 * (pageNum - 1);
        pageSize = 20 * pageNum;
    }
    if (getUrlArgs().menu != undefined) {
        var menuTitle = getQueryString("menu").split("-")[0];
        var menuId = getQueryString("menu").split("-")[1];
        if (menuId != undefined && menuTitle != undefined) {
            menu = menuTitle + '-' + menuId;
        }
        document.getElementById('typeName').innerHTML = "►" + menuTitle;
    }
    if (getUrlArgs().productName != undefined) {
        productName = getQueryString("productName");
    }
}

$(function() {
    console.log("menuId: " + menuId, "menuTitle：" + menuTitle, "menu：" + menu, "productName：" + productName);
    if (menu != "null" && menu != undefined) {
        $.ajax({
            url: "/api/product/findTypeByTypeName/" + menuTitle,
            type: "GET",
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                for (i = 0; i < result.data.length; i++) {
                    var tName = result.data[i].typeName.split("-")[1];
                    if (tName != menuId) {
                        $('#typeId').append(`<li><a href="CommodityList.html?menu=${menuTitle + '-' + result.data[i].typeName.split("-")[1]}">` + result.data[i].typeName.split("-")[1] + '</a></li>');
                    } else {
                        $('#typeId').append(`<li><a href="CommodityList.html?menu=${menuTitle + '-' + menuId}" style="color: #eb6687;;">` + result.data[i].typeName.split("-")[1] + '</a></li>');
                    }
                }
            }
        });
    } else {
        $("#type-list").attr("style", "display: none");
        if (getUrlArgs().menu != undefined) {
            menu = getQueryString("menu");
        }
    }

    var totalProductNum;
    let promise = new Promise(function(resolve, reject) {
        $.ajax({
            url: "/api/product/findProductWithCondition",
            type: "GET",
            data: {
                'typeName': menu,
                'productName': productName
            },
            timeout: 15000,
            beforeSend: function() {
                $("#loading").html("<img src='img/loading.gif' />");
            },
            success: function(result) {
                $("#loading").empty();
                console.log("totalProductNum", result);
                totalProductNum = Math.ceil(result.data.length / 20);
                resolve();
            }
        });
    });
    promise.then(function(fulfilled) {
        if (totalProductNum != 0) {
            $.ajax({
                url: "/api/product/findProductWithCondition",
                type: "GET",
                data: {
                    'typeName': menu,
                    'productName': productName,
                    'startIndex': startIndex,
                    'pageSize': pageSize
                },
                timeout: 15000,
                beforeSend: function() {
                    $("#loading").html("<img src='img/loading.gif' />");
                },
                success: function(result) {
                    $("#loading").empty();
                    console.log("all", result);
                    if (totalProductNum != 1) {
                        //分页
                        $('#pageLimit').bootstrapPaginator({
                            currentPage: pageNum, //当前的页面
                            totalPages: totalProductNum, //页数
                            size: "normal", //页眉的大小
                            bootstrapMajorVersion: 3, //bootstrap的版本要求
                            alignment: "right",
                            numberOfPages: 5, //页面显示几页
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
                            onPageClicked: function(event, oldPage, newPage) {
                                //设置页码缓存
                                // sessionStorage.setItem('pageNum', oldPage.data.page);
                                // var menu = menuTitle + '-' + menuId;
                                window.location.href = "CommodityList.html?menu=" + menu + "&page=" + oldPage.data.page;
                            }
                        });
                    }

                    $.each(result.data, function(index, data) {
                        var image = new Array();
                        image = data.image.split("、");
                        var imageStr;
                        $.each(image, function(index, data) {
                            if (data.indexOf(".AVI") != -1 || data.indexOf(".mov") != -1 || data.indexOf(".rmvb") != -1 ||
                                data.indexOf(".rm") != -1 || data.indexOf(".FLV") != -1 || data.indexOf(".mp4") != -1) {} else {
                                imageStr = data;
                                return false;
                            }
                        });

                        var str =
                            `<div class="col-md-3 goodsListContentBox" onclick="window.location.href = 'CommodityDetails.html?productId='+'${data.productId}'">
                            <ul id="goodsListContent">
                                <li class="listSty">

                                    <div class="listpicBox">
                                        <img src="${imageStr}" />
                                    </div>
                                    <a title="${data.productName}"> ${data.productName} </a>
                                    <strong>¥ ${data.price}</strong>
                                </li>
                            </ul>
                        </div>`;
                        $('#goodsListContentRow').append(str);
                    });
                }
            });
        } else {
            var str =
                `<div class="col-md-12" style="height: 200px; color: #B0C4DE; padding-top: 80px;">
                    <span">暂无商品</span>
                </div>`;
            $('#goodsListContentRow').append(str);
        }
    });

    // var defaultSort = document.getElementById('defaultSort');
    // var saleSort = document.getElementById('saleSort');
    // var priceSort = document.getElementById('priceSort');
    // var commentSort = document.getElementById('commentSort');
    // var saleSortI = document.getElementById('saleSortI');
    // var priceSortI = document.getElementById('priceSortI');
    // var commentSortI = document.getElementById('commentSortI');
    // var priceSortStatus = true;

    // defaultSort.onclick = function() {
    //     defaultSort.setAttribute("style", "background: #f1f1f1 ; color: #008842;");
    //     saleSort.setAttribute("style", "background: #fff ;");
    //     priceSort.setAttribute("style", "background: #fff ;");
    //     commentSort.setAttribute("style", "background: #fff ;");
    // };
    // saleSort.onclick = function() {
    //     defaultSort.setAttribute("style", "background: #fff ;");
    //     saleSort.setAttribute("style", "background: #f1f1f1 ; color: #008842;");
    //     priceSort.setAttribute("style", "background: #fff ;");
    //     commentSort.setAttribute("style", "background: #fff ;");
    //     saleSortI.setAttribute("style", "background-position: -10px 0;");
    //     priceSortI.setAttribute("style", "background-position: -20px 0;");
    //     commentSortI.setAttribute("style", "background-position: 0 0;");
    // };
    // priceSort.onclick = function() {
    //     defaultSort.setAttribute("style", "background: #fff ;");
    //     saleSort.setAttribute("style", "background: #fff ;");
    //     priceSort.setAttribute("style", "background: #f1f1f1 ; color: #008842;");
    //     commentSort.setAttribute("style", "background: #fff ;");
    //     saleSortI.setAttribute("style", "background-position: 0 0;");
    //     if (priceSortStatus == true) {
    //         priceSortI.setAttribute("style", "background-position: -30px 0;");
    //     } else {
    //         priceSortI.setAttribute("style", "background-position: -40px 0;");
    //     }
    //     priceSortStatus = !priceSortStatus;
    //     commentSortI.setAttribute("style", "background-position: 0 0;");
    // };
    // commentSort.onclick = function() {
    //     defaultSort.setAttribute("style", "background: #fff ;");
    //     saleSort.setAttribute("style", "background: #fff ;");
    //     priceSort.setAttribute("style", "background: #fff ;");
    //     commentSort.setAttribute("style", "background: #f1f1f1 ; color: #008842;");
    //     saleSortI.setAttribute("style", "background-position: 0 0;");
    //     priceSortI.setAttribute("style", "background-position: -20px 0;");
    //     commentSortI.setAttribute("style", "background-position: -10px 0;");
    // };

    $('#goodsListContentRow').on('mouseover', '.listSty', function() {
        $('.listpicBox').eq($('.listSty').index($(this))).children('img').css('transform', 'scale(1.1)');
        $('.addShopCarBtn').eq($('.listSty').index($(this))).css('display', 'block');
    });
    $('#goodsListContentRow').on('mouseout', '.listSty', function() {
        $('.listpicBox').eq($('.listSty').index($(this))).children('img').css('transform', 'scale(1)');
        $('.addShopCarBtn').eq($('.listSty').index($(this))).css('display', 'none');
    });
    $('#goodsListContentRow').on('mouseover', '.addShopCarBtn', function() {
        $('.addShopCarBtn').eq($('.addShopCarBtn').index($(this))).css('background', '#007F4D');
    });

})

window.onload = function() {
    // document.getElementById("search-btn").addEventListener('click', function() {
    //     var searchStr = $("#search").val();
    //     window.location.href = 'CommodityList.html?productName=' + searchStr;
    // });
};

function productSearch(id) {
    var searchStr = $("#" + id).val();
    window.location.href = 'CommodityList.html?productName=' + searchStr;
}

function inputChange() {}