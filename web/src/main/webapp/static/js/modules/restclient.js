/**
 * restful 请求快捷工具方法
 * @author zj
 */
layui.define(['env', 'restcore', 'resthelper'], function (exports) {
    "use strict";

    var core = layui.restcore,
        helper = layui.resthelper;

    var RestClient = function () {
        this.cfg = {
            'index': '',  // 首页位置
            'ver': 'v1', //版本,
            params: {}, //url替换参数设置,
            third: null
        };
        this.agentUrl = layui.env.diyform_gateway + "/apiagent";
        this.helper = helper;
    };
    RestClient.prototype = {
        constructor: RestClient,

        setIndex: function (index) {
            this.cfg.index = index;
        },

        setParam: function (name, value) {
            this.cfg.params[name] = value;
        },

        getParam: function (name) {
            return this.cfg.params[name];
        },

        setHeader: function (name, value) {
            core._def_http_headers[name] = value;
        },

        request: function (type, requestUrl, headers, cb, errFun, sync, jsonData) {
            core._do_request(type, requestUrl, headers, cb, errFun, sync, jsonData);
        },

        _requestUrl: function (uri) {
            $.each(this.cfg.params, function (name, value) {
                var query = '{' + name + '}';
                var regx = new RegExp(query, "g");
                uri = uri.replace(regx, value);
            });
            return this.cfg.index + uri;
        },

        get: function (uri, headers, cb, errFun, sync) {
            this.request('get', this._requestUrl(uri), headers, cb, errFun, sync);
        },

        post: function (uri, data, headers, cb, errFun, sync) {
            var jsonData = typeof data == 'string' ? data : JSON.stringify(data);   //Firefox，chrome，opera，safari，ie8及以上
            this.request('post', this._requestUrl(uri), headers, cb, errFun, sync, jsonData);
        },

        put: function (uri, data, headers, cb, errFun, sync) {
            var jsonData = typeof data == 'string' ? data : JSON.stringify(data);
            this.request('put', this._requestUrl(uri), headers, cb, errFun, sync, jsonData);
        },

        patch: function (uri, data, headers, cb, errFun, sync) {
            var jsonData = typeof data == 'string' ? data : JSON.stringify(data);
            this.request('patch', this._requestUrl(uri), headers, cb, errFun, sync, jsonData);
        },

        del: function (uri, headers, cb, errFun, sync) {
            this.request('delete', this._requestUrl(uri), headers, cb, errFun, sync);
        },

        iGet: function (uri, headers, cb, errFun, sync) {
            if (uri.indexOf("?") > 0) {
                uri += "&_=" + Date.parse(new Date());
            }
            headers['Authorization'] = helper.buildMac(this._requestUrl(uri), 'GET');
            this.get(uri, headers, cb, errFun, sync);
            /*var requestBody = {}, agentHeaders = [];
            requestBody.method = "GET";
            $.each(headers, function (key, val) {
                var temp = {"name": key, "value": val};
                agentHeaders.push(temp);
            });
            requestBody.header = agentHeaders;
            requestBody.body = "";
            requestBody.uri = this._requestUrl(uri);
            //console.log(requestBody);
            // 再次为header生成头部（每个Auth只能使用一次）
            headers['Authorization'] = helper.buildMac(this.agentUrl, 'POST');
            this.post(this.agentUrl, requestBody, headers, cb, errFun, sync);*/
        },
        iDown: function (uri) {
            if (uri.indexOf("?") > 0) {
                uri += "&_=" + Date.parse(new Date());
            }else {
                uri += "?_=" + Date.parse(new Date());
            }
            window.open(uri+"&auth="+helper.buildMac(this._requestUrl(uri), 'GET'));
            /*var requestBody = {}, agentHeaders = [];
            requestBody.method = "GET";
            $.each(headers, function (key, val) {
                var temp = {"name": key, "value": val};
                agentHeaders.push(temp);
            });
            requestBody.header = agentHeaders;
            requestBody.body = "";
            requestBody.uri = this._requestUrl(uri);
            //console.log(requestBody);
            // 再次为header生成头部（每个Auth只能使用一次）
            headers['Authorization'] = helper.buildMac(this.agentUrl, 'POST');
            this.post(this.agentUrl, requestBody, headers, cb, errFun, sync);*/
        },

        iPost: function (uri, data, headers, cb, errFun, sync) {
            headers['Authorization'] = helper.buildMac(this._requestUrl(uri), 'POST');
            this.post(uri, data, headers, cb, errFun, sync);
            /*headers['X-Gaea-Authorization'] = helper.buildGaea();
            var requestBody = {}, agentHeaders = [];
            requestBody.method = "POST";
            $.each(headers, function (key, val) {
                var temp = {"name": key, "value": val};
                agentHeaders.push(temp);
            });
            requestBody.header = agentHeaders;
            requestBody.body = data;
            requestBody.uri = this._requestUrl(uri);
            //console.log(requestBody);
            // 再次为header生成头部（每个Auth只能使用一次）
            headers['Authorization'] = helper.buildMac(this.agentUrl, 'POST');
            this.post(this.agentUrl, requestBody, headers, cb, errFun, sync);*/
        },

        iPut: function (uri, data, headers, cb, errFun, sync) {
            headers['Authorization'] = helper.buildMac(this._requestUrl(uri), 'PUT');
            /*var requestBody = {}, agentHeaders = [];
            requestBody.method = "PUT";
            $.each(headers, function (key, val) {
                var temp = {"name": key, "value": val};
                agentHeaders.push(temp);
            });
            requestBody.header = agentHeaders;
            requestBody.body = data;
            requestBody.uri = this._requestUrl(uri);*/
            //console.log(requestBody);
            this.put(uri, data, headers, cb, errFun, sync);
            // 再次为header生成头部（每个Auth只能使用一次）
            /*headers['Authorization'] = helper.buildMac(this.agentUrl, 'POST');
            this.post(this.agentUrl, requestBody, headers, cb, errFun, sync);*/
        },

        iPatch: function (uri, data, headers, cb, errFun, sync) {
            headers['Authorization'] = helper.buildMac(this._requestUrl(uri), 'PATCH');
            this.patch(uri, data, headers, cb, errFun, sync);
        },

        iDelete: function (uri, headers, cb, errFun, sync) {
            headers['Authorization'] = helper.buildMac(this._requestUrl(uri), 'DELETE');
            /*headers['X-Gaea-Authorization'] = helper.buildGaea();
            var requestBody = {}, agentHeaders = [];
            requestBody.method = "DELETE";
            $.each(headers, function (key, val) {
                var temp = {"name": key, "value": val};
                agentHeaders.push(temp);
            });
            requestBody.header = agentHeaders;
            requestBody.body = "";
            requestBody.uri = this._requestUrl(uri);*/
            this.del(uri, headers, cb, errFun, sync);
            // 再次为header生成头部（每个Auth只能使用一次）
            /*headers['Authorization'] = helper.buildMac(this.agentUrl, 'POST');
            this.post(this.agentUrl, requestBody, headers, cb, errFun, sync);*/
        }
    };

    exports('restclient', new RestClient()); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});