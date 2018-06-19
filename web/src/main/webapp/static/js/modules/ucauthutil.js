/**
 * ND UC MAC生成辅助方法
 * @author zj
 */
layui.define(function(exports){
    "use strict";

    var UcAuthUtil = function () {
        this.chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
    };
    UcAuthUtil.prototype = {
        constructor: UcAuthUtil,

        /**
         * restful server_time to format
         * @param strDate
         * @returns {Object}
         */
        getDate : function(strDate) {
            //var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/, function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
            strDate = strDate.substr(0, strDate.indexOf('.'));
            strDate = strDate.replace('T', ' ');
            var obj = new Date(strDate);
            if (!obj || obj == undefined || isNaN(obj.getTime())) {
                var dateTimeArr = strDate.split( ' ');
                var dateArr = dateTimeArr[0].split("-");
                var timeArr = dateTimeArr[1].split(":");
                obj = new Date(dateArr[0], dateArr[1]-1, dateArr[2], timeArr[0], timeArr[1], timeArr[2]);
            }
            return obj;
        },

        /**
         * 获取随机字符串
         * @param n
         * @returns {string}
         */
        generateMixed : function(n) {
            var res = "";
            for(var i = 0; i < n ; i ++) {
                var id = Math.ceil(Math.random()*35);
                res += this.chars[id];
            }
            return res;
        },

        /**
         * 生成内站MAC
         * @param uri
         * @param method
         * @param access_token
         * @param mac_key
         * @param nonce
         * @returns {string}
         */
        getAuthHeader : function (uri, method,access_token,mac_key,nonce) {
            var strAuth = "MAC id=\"" + access_token + "\",nonce=\"";strAuth+= nonce + "\",mac=\"";
            var host = this._def_host();
            if(uri.indexOf('http://') > -1 || uri.indexOf('https://') > -1) {
                host = uri.replace(uri.substr(uri.indexOf("/", 8)),'').replace('http://','').replace('https://','');
                uri = uri.substr(uri.indexOf("/", 8));
            }
            var request_content = nonce + "\n" + method + "\n" + uri + "\n"  + host + "\n";
            var hash = CryptoJS.HmacSHA256(request_content, mac_key);
            var mac = hash.toString(CryptoJS.enc.Base64);
            strAuth += mac+"\"";
            return strAuth;
        },

        /**
         * 生成外站MAC
         * @param url
         * @param method
         * @param access_token
         * @param mac_key
         * @param nonce
         * @returns {string}
         */
        getThirdAuthHeader : function(url, method, access_token, mac_key, nonce) {
            var strAuth = "MAC id=\"" + access_token + "\",nonce=\"";strAuth+= nonce + "\",mac=\"";
            var b = url.indexOf("//") + 2;
            var e = url.indexOf('/', b);
            var host = url.substring(b, e);
            var path = url.substring(e);
            var request_content = nonce + "\n" + method + "\n" + path + "\n"  + host + "\n";
            var hash = CryptoJS.HmacSHA256(request_content, mac_key);
            var mac = hash.toString(CryptoJS.enc.Base64);
            strAuth += mac+"\"";
            return strAuth;
        },

        _def_host : function() {
            return location.host.toString();
        }
    };

    exports('ucauthutil', new UcAuthUtil()); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});