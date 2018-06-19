/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'table', 'form', 'restclient', 'env', 'tpl', 'element','laydate'], function (exports) {
    var layer = layui.layer,
        client = layui.restclient,
        env = layui.env,
        tpl = layui.tpl,
        element = layui.element,
        platforms = {1: '饿了么', 2: '百度', 3: '美团'};

    var Finance = function () {
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

    Finance.prototype = {
        constructor: Finance,

        /**
         * 主入口
         */
        business_all: function () {
            var self = this,
                urlFinanceList = env.server + "/v1/shops",

                urlFinanceDelete = env.server + "/v1/shops/{shop_id}";
            $('#mainDiv').tpl('/finance/business_trend', {
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
                    searchParam = $.extend({column: "origin_fee"}, searchParam);
                    console.log(searchParam);

                    client.iGet("/api/v1/finance/total_stat?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '营业总额统计',
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
                                    name: '商家营业总额',
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

        sail_all: function () {
            var self = this,
                urlFinanceList = env.server + "/v1/shops",

                urlFinanceDelete = env.server + "/v1/shops/{shop_id}";
            $('#mainDiv').tpl('/finance/business_trend', {
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
                    searchParam = $.extend({column: "bonus_fee"}, searchParam);
                    console.log(searchParam);

                    client.iGet("/api/v1/finance/total_stat?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '销售补贴总额统计',
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
                                    name: '商家营业总额',
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

        income_all: function () {
            var self = this,
                urlFinanceList = env.server + "/v1/shops",

                urlFinanceDelete = env.server + "/v1/shops/{shop_id}";
            $('#mainDiv').tpl('/finance/business_trend', {
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
                    searchParam = $.extend({column: "real_fee"}, searchParam);
                    console.log(searchParam);

                    client.iGet("/api/v1/finance/total_stat?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '净收入总额统计',
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
                                    name: '商家净收入总额',
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

        business_detail: function () {
            var self = this,
                urlFinanceList = env.server + "/v1/shops",

                urlFinanceDelete = env.server + "/v1/shops/{shop_id}";
            $('#mainDiv').tpl('/finance/business_detail', {
                'platforms': platforms,
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
                    table.reload('detail_list', {where: $.extend({asc: false, column: "origin_fee"}, data.field)});
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });

                var table = layui.table;

                //列表
                table.render({
                    elem: '#detail_list'
                    // , height: 315
                    , url: "/api/v1/finance/day_stat" //数据接口
                    , where: {asc: false, column: "origin_fee"}
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'platform', title: '平台', sort: true, fixed: 'left'}
                        , {field: 'shop_name', title: '店铺'}
                        , {field: 'order_date', title: '日期'}
                        , {field: 'fee', title: '营业额'}

                    ]]
                    , done: function (res, page, count) {
                        $("[data-field='platform']").children().each(function () {
                            var platform = parseInt($(this).text());
                            $(this).text(platforms[platform]);
                        });
                    }
                });
            });
        },


        sail_detail: function () {
            var self = this,
                urlFinanceList = env.server + "/v1/shops",

                urlFinanceDelete = env.server + "/v1/shops/{shop_id}";
            $('#mainDiv').tpl('/finance/sail_detail', {
                'platforms': platforms,
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
                    table.reload('detail_list', {where: $.extend({asc: false, column: "bonus_fee"}, data.field)});
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });

                var table = layui.table;

                //列表
                table.render({
                    elem: '#detail_list'
                    // , height: 315
                    , url: "/api/v1/finance/day_stat" //数据接口
                    , where: {asc: false, column: "bonus_fee"}
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'platform', title: '平台', sort: true, fixed: 'left'}
                        , {field: 'shop_name', title: '店铺'}
                        , {field: 'order_date', title: '日期'}
                        , {field: 'fee', title: '销售补贴额'}

                    ]]
                    , done: function (res, page, count) {
                        $("[data-field='platform']").children().each(function () {
                            var platform = parseInt($(this).text());
                            $(this).text(platforms[platform]);
                        });
                    }
                });
            });
        },


        income_detail: function () {
            var self = this;
            $('#mainDiv').tpl('/finance/business_detail', {
                'platforms': platforms,
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
                    table.reload('income_list', {where: $.extend({asc: false, column: "real_fee"}, data.field)});
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });

                var table = layui.table;

                //列表
                table.render({
                    elem: '#detail_list'
                    // , height: 315
                    , url: "/api/v1/finance/day_stat" //数据接口
                    , where: {asc: false, column: "real_fee"}
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'platform', title: '平台', sort: true, fixed: 'left'}
                        , {field: 'shop_name', title: '店铺'}
                        , {field: 'order_date', title: '日期'}
                        , {field: 'fee', title: '净收入额'}

                    ]]
                    , done: function (res, page, count) {
                        $("[data-field='platform']").children().each(function () {
                            var platform = parseInt($(this).text());
                            $(this).text(platforms[platform]);
                        });
                    }
                });
            });
        },


        business_trend: function () {
            var self = this;
            $('#mainDiv').tpl('/finance/business_trend', {
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
                    if (searchParam == undefined) {
                        searchParam = {};
                    }
                    searchParam = $.extend({column: "origin_fee"}, searchParam);
                    console.log(searchParam);
                    client.iGet("/api/v1/finance/every_day_stat?" + parseParam(searchParam), {}, function (ret) {
                        console.log(ret);
                        var option = {
                            title: {
                                text: '营业额曲线走势',
                                subtext: '营业额曲线走势'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: ['营业额']
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: ret.names
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value} 元'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '营业额',
                                    type: 'line',
                                    data: ret.values,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }

                            ]
                        };

                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                };

                reloadChart({});

                // window.addEventListener('resize', function () { // 解决echarts宽度无法自适应问题
                //     myChart.resize();
                // });


            });
        },

        sail_trend: function () {
            var self = this;
            $('#mainDiv').tpl('/finance/sail_trend', {
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
                    if (searchParam == undefined) {
                        searchParam = {};
                    }
                    searchParam = $.extend({column: "bonus_fee"}, searchParam);
                    console.log(searchParam);
                    client.iGet("/api/v1/finance/every_day_stat?" + parseParam(searchParam), {}, function (ret) {
                        console.log(ret);
                        var option = {
                            title: {
                                text: '销售补贴曲线走势',
                                subtext: '销售补贴曲线走势'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: ['额度']
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: ret.names
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value} 元'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '销售补贴额',
                                    type: 'line',
                                    data: ret.values,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }

                            ]
                        };

                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                };

                reloadChart({});

                // window.addEventListener('resize', function () { // 解决echarts宽度无法自适应问题
                //     myChart.resize();
                // });


            });
        },

        income_trend: function () {
            var self = this;
            $('#mainDiv').tpl('/finance/income_trend', {
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
                    if (searchParam == undefined) {
                        searchParam = {};
                    }
                    searchParam = $.extend({column: "real_fee"}, searchParam);
                    console.log(searchParam);
                    client.iGet("/api/v1/finance/every_day_stat?" + parseParam(searchParam), {}, function (ret) {
                        console.log(ret);
                        var option = {
                            title: {
                                text: '净收入曲线走势',
                                subtext: '净收入曲线走势'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: ['额度']
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: ret.names
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value} 元'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '净收入',
                                    type: 'line',
                                    data: ret.values,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }

                            ]
                        };

                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                };

                reloadChart({});

                // window.addEventListener('resize', function () { // 解决echarts宽度无法自适应问题
                //     myChart.resize();
                // });


            });

        }
    };
    exports('finance', new Finance());






});