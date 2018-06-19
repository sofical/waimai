/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'table', 'form', 'restclient', 'env', 'tpl', 'element','laydate'], function (exports) {
    var layer = layui.layer,
        client = layui.restclient,
        env = layui.env,
        tpl = layui.tpl,
        element = layui.element,
        dimensions = {"style": '菜系', "taste": '口味', "tag": '分类'},
        platforms = {1: '饿了么', 2: '百度', 3: '美团'};

    var Customer = function () {
        this.baseUrl = "tpl/"
    };
    var parseParam=function(param){
        var paramStr="";
        for (var index in param){
            if(paramStr.length > 0){
                paramStr += "&"+index+"="+param[index];
            }else {
                paramStr += index+"="+param[index];
            }
        }
        return paramStr;
    };
    Customer.prototype = {
        constructor: Customer,

        /**
         * 主入口
         */
        customer_stat: function () {
            var self = this;
            $('#mainDiv').tpl('/customer/old_new-stat', {
                platforms: platforms,
                '_c': '/api/v1/shops?page=1&limit=1000'
            }, function (data) {

                element.render();
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#start-date-picker'
                });
                laydate.render({
                    elem: '#end-date-picker'
                });
                // 搜索表单
                var searchForm = layui.form;
                searchForm.render();
                searchForm.on('submit(*)', function (data) {
                    reloadChart(data.field);
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
                var myChart = echarts.init(document.getElementById('mainChart'));

                var reloadChart = function (searchParam) {
                    // client.get("/v1/stat/",{},function () {
                    //
                    // });
                    if (searchParam == undefined) {
                        searchParam = {};
                    }

                    client.iGet("/api/v1/customers//actions/old_new_stat?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '新老顾客数量统计',
                                subtext: '',
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'left',
                                data: names
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {
                                        show: true,
                                        type: ['pie', 'funnel'],
                                        option: {
                                            funnel: {
                                                x: '25%',
                                                width: '50%',
                                                funnelAlign: 'left',
                                                max: 1548
                                            }
                                        }
                                    },
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            series: [
                                {
                                    name: '新老顾客数量统计',
                                    type: 'pie',
                                    radius: '55%',
                                    center: ['50%', '60%'],
                                    data: ret
                                }
                            ]
                        };
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                };

                reloadChart();


            });
        },
        customer_info: function () {
            var self = this,
                urlCustomerList = env.server + "/v1/customers";
            $('#mainDiv').tpl('/customer/list', {}, function (data) {

                element.render();
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#start-date-picker'
                });
                laydate.render({
                    elem: '#end-date-picker'
                });
                // 搜索表单
                var searchForm = layui.form;
                searchForm.render();
                searchForm.on('submit(*)', function (data) {
                    table.reload('list-table', {where: data.field});
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });

                var table = layui.table,
                    barHtml = '<script type="text/html" id="operate-row">' +
                        '<a class="layui-btn layui-btn-xs" lay-event="shop_dishes_manage">菜品管理</a>' +
                        '<a class="layui-btn layui-btn-xs" lay-event="shop_mdf">修改</a>' +
                        '<a class="layui-btn layui-btn-xs" lay-event="shop_del">删除</a>' +
                        '</script>';
                $('#mainDiv').append(barHtml);

                //列表
                table.render({
                    elem: '#list-table'
                    // , height: 315
                    , url: urlCustomerList //数据接口
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'phone', title: '手机号码', sort: true, fixed: 'left'}
                        , {field: 'names', title: '顾客名称'}
                        , {field: 'address_list', title: '地址'}
                         // {field: 'address_list',width: 200, title: '单用户分析'}
                        // {fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#operate-row'}
                    ]]
                    , done: function (res, page, count) {
                        $('td[data-field="names"]').children().each(function (e,v) {
                            var ele = $(this);
                            var list =JSON.parse(ele.text());
                            var str = '';
                            $.each(list,function (k,v) {
                                str += v+"<br>";
                            });
                            ele.html(str);
                        });
                        $('td[data-field="address_list"]').children().each(function (e,v) {
                            var ele = $(this);
                            var list =JSON.parse(ele.text());
                            var str = '';
                            $.each(list,function (k,v) {
                                str += v+"<br>";
                            });
                            ele.html(str);
                        });
                    }
                });
                table.on('tool(customer)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象
                    if (layEvent === 'shop_del') { //删除
                        layer.confirm('真的删除行么', function (index) {
                            layer.close(index);
                            client.iDelete(urlCustomerDelete.replace('{shop_id}', data.shop_id), {}, function () {
                                layer.msg("删除成功");
                                table.reload('shop_list');
                            });
                        });
                    } else if (layEvent === 'shop_mdf') { //查看
                        self.add(true,data);
                    }else if(layEvent === 'shop_dishes_manage'){
                        self.dish(data)
                    }
                });

                $('#shop_add').on('click', function () {
                    self.add(false,{});
                });
            });
        },
        customer_preference: function () {
            var self = this;
            $('#mainDiv').tpl('/customer/preference', {
                platforms: platforms,
                dimensions: dimensions,
                '_c': '/api/v1/shops?page=1&limit=1000'
            }, function (data) {

                element.render();
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#start-date-picker'
                });
                laydate.render({
                    elem: '#end-date-picker'
                });
                // 搜索表单
                var searchForm = layui.form;
                searchForm.render();
                searchForm.on('submit(*)', function (data) {
                    reloadChart(data.field);
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
                var myChart = echarts.init(document.getElementById('mainChart'));

                var reloadChart = function (searchParam) {
                    // client.get("/v1/stat/",{},function () {
                    //
                    // });
                    if (searchParam == undefined) {
                        searchParam = {};
                    }

                    client.iGet("/api/v1/order_dishes/stat?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '客户偏好分析',
                                subtext: '',
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'left',
                                data: names
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {
                                        show: true,
                                        type: ['pie', 'funnel'],
                                        option: {
                                            funnel: {
                                                x: '25%',
                                                width: '50%',
                                                funnelAlign: 'left',
                                                max: 1548
                                            }
                                        }
                                    },
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            series: [
                                {
                                    name: '客户偏好分析',
                                    type: 'pie',
                                    radius: '55%',
                                    center: ['50%', '60%'],
                                    data: ret
                                }
                            ]
                        };
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                };

                reloadChart();


            });
        },
        customer_analysis: function () {
            var self = this,
                urlCustomerList = env.server + "/v1/customers";
            $('#mainDiv').tpl('/customer/analysis', {}, function (data) {

                element.render();
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#start-date-picker'
                });
                laydate.render({
                    elem: '#end-date-picker'
                });
                // 搜索表单
                var searchForm = layui.form;
                searchForm.render();
                searchForm.on('submit(*)', function (data) {
                    table.reload('list-table', {where: data.field});
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });

                var table = layui.table,
                    barHtml = '<script type="text/html" id="operate-row">' +
                        '<a class="layui-btn layui-btn-xs" lay-event="analysis">单用户分析</a>' +
                        '</script>';
                $('#mainDiv').append(barHtml);

                //列表
                table.render({
                    elem: '#list-table'
                    // , height: 615
                    , url: urlCustomerList //数据接口
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'phone', title: '手机号码', sort: true, fixed: 'left'}
                        , {field: 'names', title: '顾客名称'}
                        , { title: '分析',toolbar: '#operate-row'}
                        // {field: 'address_list',width: 200, title: '单用户分析'}
                        // {fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#operate-row'}
                    ]]
                    , done: function (res, page, count) {
                        $('td[data-field="names"]').children().each(function (e,v) {
                            var ele = $(this);
                            var list =JSON.parse(ele.text());
                            var str = '';
                            $.each(list,function (k,v) {
                                str += v+"<br>";
                            });
                            ele.html(str);
                        });
                    }
                });
                table.on('tool(customer)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象
                    if (layEvent === 'analysis') { //删除
                        layer.tab({
                            area: ['600px', '300px'],
                            tab: [{
                                title: '常用平台',
                                content: '<table id="ana-platform" lay-filter="ana_platform"></table>'
                            }, {
                                title: '常点菜品',
                                content: '<table id="ana-dish" lay-filter="ana_dish"></table>'
                            }, {
                                title: '订餐时段',
                                content: '<table id="ana-hour" lay-filter="ana_hour"></table>'
                            }]});
                            client.iGet(urlCustomerList+"/"+data.customer_id+"/actions/analysis", {}, function (ret) {
                                table.render({
                                    elem: '#ana-platform'
                                    , height: 230
                                    ,width:580
                                    ,data:ret["platform"]
                                    , page: false //开启分页
                                    , cols: [[ //表头
                                        {field: 'name', title: '平台', sort: true, fixed: 'left'}
                                        , {field: 'value', title: '频次'}
                                    ]]
                                });
                                table.render({
                                    elem: '#ana-dish'
                                    , height: 230
                                    ,width:580
                                    ,data:ret["dish"]
                                    , page: false //开启分页
                                    , cols: [[ //表头
                                        {field: 'name', title: '菜品', sort: true, fixed: 'left'}
                                        , {field: 'value', title: '频次'}
                                    ]]
                                });
                                table.render({
                                    elem: '#ana-hour'
                                    , height: 230
                                    ,width:580
                                    ,data:ret["hour"]
                                    , page: false //开启分页
                                    , cols: [[ //表头
                                        {field: 'name', title: '时间', sort: true, fixed: 'left'}
                                        , {field: 'value', title: '频次'}
                                    ]]
                                });
                            });


                    } else if (layEvent === 'shop_mdf') { //查看
                        self.add(true,data);
                    }else if(layEvent === 'shop_dishes_manage'){
                        self.dish(data)
                    }
                });

                $('#shop_add').on('click', function () {
                    self.add(false,{});
                });
            });
        }
    };
    exports('customer', new Customer());






});