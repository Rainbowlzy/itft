// 江苏交通学习网，挂机脚本
var itvl = setInterval(function () {
    if ($(".dfss_down_no").text() !== "下一页") {
        showNext()
    }
}, 1000)


