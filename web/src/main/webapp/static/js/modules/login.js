/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'form', 'restclient', 'env'], function (exports) {
    var layer = layui.layer,
        form = layui.form,
        client = layui.restclient,
        env = layui.env;

    var Login = function () {
        this.baseUrl = "tpl/"
    };

    Login.prototype = {
        constructor : Login,

        /**
         * 登陆处理逻辑
         */
        login : function() {
            var cookieTest = $.cookie("access_token");
            if ("" != cookieTest && undefined != cookieTest && null != cookieTest) {
                location.href = "/main.html";
            }
            var url = env.server + '/v1/tokens';
            form.on('submit(formDemo)', function(data) {
                client.post(url, data.field, {}, function(rsp) {
                    client.helper.makeCookie(rsp);
                    location.href = "/main.html";
                });
                return false;
            });
        }
    };
    var login = new Login();
    login.login();

    exports('login', new Login());
});