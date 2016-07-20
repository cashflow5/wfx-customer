<style type="text/css">
        #codeico{
            position:absolute;//生成绝对定位的元素，相对于浏览器窗口进行定位。元素的位置通过 "left", "top", "right" 以及 "bottom" 
            z-index:9999999;
            width:30px; 
            height:30px;
            background:url('http://w1.ygimg.cn/SHOP_LOGO/default_logo.png') no-repeat;
            background-size: cover;
        }
    </style>
	<script src="${context}/static/js/jquery.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${context}/static/js/jquery.qrcode.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
    <script>
       function utf16to8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }
      function generateQRCode(rendermethod, picwidth, picheight, url) {
        $("#qrcode").qrcode({ 
            render: rendermethod, // 渲染方式有table方式（IE兼容）和canvas方式
            width: picwidth, //宽度 
            height:picheight, //高度 
            text: utf16to8(url), //内容 
            typeNumber:-1,//计算模式
            correctLevel:2,//二维码纠错级别
            background:"#ffffff",//背景颜色
            foreground:"#000000"  //二维码颜色 

        });
        var margin = ($("#qrcode").height()-$("#codeico").height())/2;
        $("#codeico").css("margin",margin);
         $("#qrcode").css("position","relative");
    }   
    </script>