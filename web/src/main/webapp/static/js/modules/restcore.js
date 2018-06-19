/**
 * restful 请求核心方法
 * @author zj
 */

layui.define(['layer'], function (exports) {
    "use strict";

    var RestCore = function () {
        this._uitest = 0;
        this._sync = true;   //false同步,true异步
    };
    RestCore.prototype = {
        constructor: RestCore,

        _do_request: function (type, requestUrl, headers, cb, errFun, sync, jsonData) {
            var self = this;
            if (typeof sync !== 'boolean') {
                sync = (self._uitest !== 1) ? self._sync : false;
            }

            headers = $.extend(headers, self._def_http_headers);

            var request = {
                url: requestUrl,
                headers: headers,
                type: type,
                async: sync,
                dataType: 'json',
                error: function (jqxhr, status, thrown) {
                    self._def_http_fail(jqxhr, status, thrown, errFun, cb);
                },
                success: function (ret, status, jqxhr) {
                    self._def_http_success(ret, status, jqxhr, cb);
                }
            };
            if (jsonData) {
                request.data = jsonData;
            }
            console.log('do request[' + type + ']:');
            console.log(request);
            $.ajax(request);
        },

        _def_http_success: function (ret, status, jqxhr, cb) {
            if (cb) {
                cb(ret);
            }
        },

        _def_http_fail: function (jqxhr, status, thrown, errFun, cb) {
            if (jqxhr.status && jqxhr.status >= 200 && jqxhr.status < 300) {
                this._def_http_success(jqxhr.responseText, jqxhr.status, jqxhr, cb);
                return;
            }
            errFun = errFun || this._def_err_function;
            if (status === 'timeout') {
                errFun({s: '-1000', m: '连接超时'});
            } else if (status === 'parsererror') {
                errFun({s: '-1001', m: '不是正确的json格式'});
            } else if (status === 'error') {
                if (jqxhr.readyState > 0) {
                    var errorResponse = $.parseJSON(jqxhr.responseText)
                    if (errorResponse) {
                        errFun({
                            s: errorResponse.code ? errorResponse.code : '-1002',
                            m: errorResponse.message ? errorResponse.message : '暂时无法连接到服务器，请稍候重试'
                        }, errorResponse);
                    } else {
                        errFun({s: '-1002', m: '暂时无法连接到服务器，请稍候重试'});
                    }
                }
            }
        },

        _def_err_function: function (ret) {
            if (parseInt(ret.s) === 1002) {
                $.dialog('出错提示', ret.m,
                    [
                        {
                            text: '确定', primary: true, okfun: function () {
                            window.location.href = sys.cfg.index + '/login';
                        }
                        }
                    ], true);
            } else {
                layer.msg(ret.m);
                //$.alertMsg('出错提示', ret.m);
            }
        },

        _def_http_headers: {
            Accept: 'application/json; charset=utf-8',
            'Content-Type': 'application/json; charset=utf-8'
        }


    };

    exports('restcore', new RestCore()); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});