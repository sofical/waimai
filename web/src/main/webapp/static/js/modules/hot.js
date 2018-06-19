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

    var Hot = function () {
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

    Hot.prototype = {
        constructor: Hot,

        /**
         * 主入口
         */
        hot_dish: function () {
            var self = this;
            $('#mainDiv').tpl('/hot/hot_dishes', {
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

                    client.iGet("/api/v1/hot/dishes?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '热门菜品分析',
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
                                    name: '热门菜品分析',
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
        hot_shop: function () {
            var self = this;
            $('#mainDiv').tpl('/hot/hot_dishes', {
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

                    client.iGet("/api/v1/hot/shops?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '热门店铺分析',
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
                                    name: '热门店铺分析',
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
        hot_time: function () {
            var self = this;
            $('#mainDiv').tpl('/hot/hot_time', {
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
                    client.iGet("/api/v1/hot/time?" + parseParam(searchParam), {}, function (ret) {
                        var names =[],values =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                            values.push(row.value);
                        });
                        var option = {
                            title: {
                                text: '热门时段分析',
                                subtext: '每小时'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: ['热度']
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
                                    data: names
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value} '
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '热度',
                                    type: 'line',
                                    data: values,
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
    exports('hot', new Hot());






});