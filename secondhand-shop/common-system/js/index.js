function getMenuName(e, n) {
    // sessionStorage.setItem('menuTitle', n);
    // sessionStorage.setItem('menuId', e.innerText);
    var menu = n + "-" + e.innerText;
    window.location.href = 'CommodityList.html?menu=' + menu;
}

window.onload = function() {
    //图片轮播
    var oBannerPicList = document.getElementsByClassName("bannerPicList");
    var oBeforeBtn = document.getElementById("beforeBtn");
    var oNextBtn = document.getElementById("nextBtn");
    var oBannerPicBtn = document.getElementsByClassName("bannerPicBtn");
    slider();

    function slider() {
        inter();
        var iNow = 0;
        var timer;
        for (var i = 0; i < oBannerPicBtn.length; i++) {
            bind(i);
        }

        function bind(index) {
            oBannerPicBtn[index].onclick = function() {
                clearInterval(timer);
                iNow = index;
                changePic();
                inter();
            }
        }

        function changePic() {
            for (var j = 0; j < oBannerPicList.length; j++) {
                //把所有照片的宽度改为100%				
                oBannerPicBtn[j].style.backgroundColor = '#b5b2a8';
                oBannerPicList[j].style.transform = 'scaleX(' + 1.1 + ') scaleY(' + 1.1 + ')';
                oBannerPicList[j].style.opacity = 0;
                //当j等于索引值是
                if (j == iNow) {
                    oBannerPicBtn[iNow].style.backgroundColor = '#5aaa16';
                    oBannerPicList[j].style.transform = 'scaleX(' + 1 + ') scaleY(' + 1 + ')';
                    oBannerPicList[j].style.opacity = 1;
                }
            }
        }
        //定时器
        function inter() {
            timer = setInterval(function() {
                iNow++;
                if (iNow > oBannerPicList.length - 1) {
                    iNow = 0;
                }
                changePic();
            }, 5000)
        }
        //向前
        oBeforeBtn.onclick = function() {
                iNow--;
                clearInterval(timer);
                if (iNow < 0) {
                    iNow = oBannerPicList.length - 1;
                }
                changePic();
                inter();
            }
            //向后
        oNextBtn.onclick = function() {
            iNow++;
            clearInterval(timer);
            if (iNow > oBannerPicList.length - 1) {
                iNow = 0;
            }
            changePic();
            inter();
        }
    }

    //回到顶部 + 吸顶 + 锚点
    var oheaderSearch2 = document.getElementsByClassName("headerSearch2")[0];
    var oBackTop = document.getElementById('backTop');
    oBackTop.onclick = function() {
        document.documentElement.scrollTop = 0;
    }
    var scro;
    var osideBarMenu = document.getElementById("sideBarMenu");
    document.onscroll = function() {
        scro = document.documentElement.scrollTop;
        if (scro > 200) {
            oBackTop.style.display = 'block';
        } else {
            oBackTop.style.display = 'none';
        }
        if (scro > 90) {
            oheaderSearch2.style.display = 'block';
        } else {
            oheaderSearch2.style.display = 'none';
        }
        if (scro < 800) {
            osideBarMenu.style.display = 'none';
        } else {
            osideBarMenu.style.display = 'block';
        }
    }

    //鼠标移入右移动画
    $('.moveRight').mouseover(function() {
        $(this).children('img').css('transform', 'translateX(3px)')
    })
    $('.moveRight').mouseout(function() {
        $(this).children('img').css('transform', 'none')
    })

    //左侧导栏航
    $(document).scroll(function() {
        var scroH = $(document).scrollTop();
        if (scroH >= 800 && scroH < 1300) {
            $('.sideBarMenuBtnDiv').eq(0).children('.sideBarMenuBtnText').css('color', '#F08080');
        } else {
            $('.sideBarMenuBtnDiv').eq(0).children('.sideBarMenuBtnText').css('color', '#9C9C9C');
        }
        if (scroH >= 1300 && scroH < 1800) {
            $('.sideBarMenuBtnDiv').eq(1).children('.sideBarMenuBtnText').css('color', '#F08080');
        } else {
            $('.sideBarMenuBtnDiv').eq(1).children('.sideBarMenuBtnText').css('color', '#9C9C9C');
        }
        if (scroH >= 1800 && scroH < 2300) {
            $('.sideBarMenuBtnDiv').eq(2).children('.sideBarMenuBtnText').css('color', '#F08080');
        } else {
            $('.sideBarMenuBtnDiv').eq(2).children('.sideBarMenuBtnText').css('color', '#9C9C9C');
        }
        if (scroH >= 2300 && scroH < 2800) {
            $('.sideBarMenuBtnDiv').eq(3).children('.sideBarMenuBtnText').css('color', '#F08080');
        } else {
            $('.sideBarMenuBtnDiv').eq(3).children('.sideBarMenuBtnText').css('color', '#9C9C9C');
        }
        if (scroH >= 2800 && scroH < 3300) {
            $('.sideBarMenuBtnDiv').eq(4).children('.sideBarMenuBtnText').css('color', '#F08080');
        } else {
            $('.sideBarMenuBtnDiv').eq(4).children('.sideBarMenuBtnText').css('color', '#9C9C9C');
        }
        if (scroH >= 3300 && scroH < 3800) {
            $('.sideBarMenuBtnDiv').eq(5).children('.sideBarMenuBtnText').css('color', '#F08080');
        } else {
            $('.sideBarMenuBtnDiv').eq(5).children('.sideBarMenuBtnText').css('color', '#9C9C9C')
        }
        if (scroH >= 3800 && scroH < 4300) {
            $('.sideBarMenuBtnDiv').eq(6).children('.sideBarMenuBtnText').css('color', '#F08080')
        } else {
            $('.sideBarMenuBtnDiv').eq(6).children('.sideBarMenuBtnText').css('color', '#9C9C9C')
        }
    });

    // document.getElementById("search-btn").addEventListener('click', function() {
    //     var searchStr = $("#search").val();
    //     window.location.href = 'CommodityList.html?productName=' + searchStr;
    // });

    // document.getElementById("search2-btn").addEventListener('click', function() {
    //     var searchStr = $("#search2").val();
    //     window.location.href = 'CommodityList.html?productName=' + searchStr;
    // });
}

function inputChange(id) {
    if (id == "search") {
        document.getElementById("search2").value = $("#" + id).val();
    }
    if (id == "search2") {
        document.getElementById("search").value = $("#" + id).val();
    }
}

function productSearch(id) {
    var searchStr = $("#" + id).val();
    window.location.href = 'CommodityList.html?productName=' + searchStr;
}

function imgTo(typeName) {
    window.location.href = 'CommodityList.html?menu=' + typeName;
}

// function backTop() {
//     document.documentElement.scrollTop = 0;
// }

// window.addEventListener("load", function() {
//     if (userId) {
//         //购物车信息渲染
//         $.ajax({
//             url: "/api/shopCart/findAllShopCart/" + userId,
//             type: "GET",
//             timeout: 15000,
//             beforeSend: function() {
//                 $("#loading").html("<img src='img/loading.gif' />");
//             },
//             success: function(result) {
//                 $("#loading").empty();
//                 // console.log(result)
//                 document.getElementById('shopcartNum').innerHTML = result.data.length;
//                 $('#noLoginP').css('display', 'none');
//                 var str, totalPrice = 0.00;
//                 if (result.data.length == 0) {
//                     str = '<p>购物车中还没有商品，赶紧选购吧！</p>';
//                     $('#shopcart').append(str);
//                 } else {
//                     str = `<div class="row"><div class="col-md-4">商品信息</div>
//                     <div class="col-md-4">数量</div>
//                     <div class="col-md-4">单价</div>
//                     </div>`;
//                     $.each(result.data, function(index, data) {
//                         if (index < 5) {
//                             str = str + `<div class="row">
//                         <div class="col-md-4 carPname">${data.productName}</div>
//                         <div class="col-md-4">${data.cartNumber}</div>
//                         <div class="col-md-4">¥${data.price}</div>
//                         </div>`;
//                         }
//                         totalPrice = totalPrice + data.price * data.cartNumber;
//                     });
//                     str = str + `<div class="row"><div class="col-md-12"><a onclick="turnTo(6, 'my-shopcart');">更多操作</a></div></div>`;
//                     $('#shopcart').append(str);
//                     document.getElementById('shopcartPrice').innerHTML = parseFloat(totalPrice).toFixed(2);
//                 }
//             }
//         });
//     }
// }, false);

// function turnTo(index, data) {
//     window.location.href = 'PersonalInformation.html?index=' + index + '&nav=' + data;
// }