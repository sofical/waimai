/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'form', 'restclient', 'env'], function (exports) {
    var layer = layui.layer,
        form = layui.form,
        client = layui.restclient,
        env = layui.env;

    var Tpl = function () {
        this.baseUrl = "tpl/"
    };

    Tpl.prototype = {
        constructor : Tpl,

        cfg : {
            'tplCache': {},           // 模板缓存
            'tpl': '/static/tpl/',  // 模版文件位置
            'nowjs': '/_js/',  // nowjs位置
            'index': '/',  // 首页位置
            'check': {},             // 后端的验证列表
            'ver': '',//版本
            'defPath' : '/static/img/default/' // 默认图片目录
        },

        /**
         * 根据模板名称和数据内容，得到组装好的模板数据，一般不要直接使用 各项目会有一个额外封装的方法
         * @param tpl   模板名称
         * @param data  数据内容
         * @param okFun 完成模板组装后的回调
         */
        tpl: function (tpl, data, okFun, sync) {
            var self = this,
                _cfg = self.cfg,
                _c = _cfg.tplCache,
                _t = _cfg.tpl,
                rpl_html = function (s) {
                    return s.replace(/\r/g, '\\r').replace(/\n/g, '\\n').replace(/'/g, "\\'").replace(/{#/g, "';s+=").replace(/#}/g, ";s+='");
                }, rpl_js = function (s) { // 替换函数
                    return s.replace(/\r/g, '').replace(/\n/g, '').replace(/'/g, "\'");
                }, rpl = function (s) {
                    var sArr = s.split('<!--#'),
                        ssArr = null,
                        jsStr = '';
                    if (sArr.length > 1) {
                        jsStr += rpl_html(sArr[0]);
                        for (var i = 1; i < sArr.length; i++) {
                            ssArr = sArr[i].split('#-->');
                            jsStr += '\';' + rpl_js(ssArr[0]) + 's+=\'' + rpl_html(ssArr[1]);
                        }
                    } else {
                        jsStr += rpl_html(s);
                    }
                    jsStr = " var s='" + jsStr + "';return s;";
                    return jsStr;
                }, rpl_fast = function (s) {
                    /*代码规范
                     {#if (data['para'] > 10) #}
                     {#else if(data['para'] < -10)#}
                     {#else#}
                     {#end#}

                     {#each data['users'] user#}
                     姓名：{#user['name']#}
                     {#end#}
                     */
                    s = s.replace(/\{#end#\}/ig, '<!--# } #-->');
                    s = s.replace(/\{#else#\}/ig, '<!--# } else { #-->');
                    s = s.replace(/\{#else(.*?)#\}/ig, '<!--# } else $1 { #-->');
                    s = s.replace(/\{#if(.*?)#\}/ig, '<!--# if $1{ #-->');
                    s = s.replace(/\{#each\s+(.*?)\s+(.*?)#\}/ig, '<!--# for(var i=0;i<$1.length; i++){ var $2 = $1[i];#-->');

                    return rpl(s);
                };



                $.ajax({
                    url: _t + tpl + '.html?v=' + new Date().getTime(),
                    type: "GET",
                    async: sync,
                    success: function (html) {
                        _c[tpl] = new Function(['data', 'require'], rpl_fast(html));
                        okFun(_c[tpl](data));
                    }
                });
        },

        get : function (tpl) {
            var self = this,
                _cfg = self.cfg,
                _t = _cfg.tpl,
                getHtml = "";
            $.ajax({
                url: _t + tpl + '.html?v=' + new Date().getTime(),
                type: "GET",
                success: function (html) {
                    getHtml = html
                }
            });
            return getHtml;
        }
    };

    var tplNow = new Tpl();

    $.fn.tpl = function (tpl, para, fun, sync) {
        sync = sync ? true : false;
        return this.each(function () {
            var _me = $(this);
            if ($.isFunction(para)) {
                fun = para;
                para = {};
            }
            var _cb = function (data) {
                tplNow.tpl(tpl, data, function (html) {
                    _me.html(html);
                    if ($.isFunction(fun)) {
                        fun(data);
                    }
                }, sync);
            };
            if (para._c) {
                var cmd = para._c;
                delete para._c;
                client.iGet(cmd, para, function (ret) {
                    _cb($.extend(para, ret));
                });
            } else {
                _cb(para);
            }
        });
    };

    exports('tpl', new Tpl());
});