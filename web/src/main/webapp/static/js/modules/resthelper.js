/**
 * ND UC相关辅助方法
 * @author zj
 */
layui.define(['ucauthutil'],function(exports){
    "use strict";

    var ucAuthUtil = layui.ucauthutil;

    var RestHelper = function () {
    };
    RestHelper.prototype = {
        constructor: RestHelper,

        /**
         * 创建cookie
         * @param successJson
         */
        makeCookie : function (rsp) {
            $.cookie("access_token", rsp.access_token);
            $.cookie("user_id", rsp.user_id);
            $.cookie("mac_key", rsp.mac_key);
        },

        /**
         * 清除cookie
         */
        clearCookie : function() {
            $.cookie("access_token", '');
            $.cookie("mac_key", '');
        },

        getStime : function () {
            if($.cookie('stime') == undefined || $.cookie('stime') == '' ){
                var serverTime=ucAuthUtil.getDate($.cookie('server_time'));
                var server_timestamp=serverTime.getTime();
                var timestamp=new Date().getTime();
                $.cookie("stime", server_timestamp-timestamp);
            }
            return parseInt($.cookie("stime"));
        },

        /**
         * 创建请求地址Mac
         * @param uri
         * @param method
         * @returns {string}
         */
        buildMac : function (uri, method) {
            var self = this;
            var accessToken = $.cookie("access_token");
            if (undefined == accessToken||accessToken == "") {
                console.log("未登陆");
            }

            return "MAC id=\"" + accessToken + "\"";
        },

        buildGaea : function () {
            var self = this;
            var GaeaId = $.cookie("Gaea_id");
            if (undefined == GaeaId||GaeaId == "") {
                console.log("未登陆");
            }
            return "GAEA id=" + GaeaId;
        },
    };

    exports('resthelper', new RestHelper()); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});