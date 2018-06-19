/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'table', 'form', 'restclient', 'env', 'tpl', 'element','laydate'], function (exports) {
    var layer = layui.layer,
        client = layui.restclient,
        env = layui.env,
        tpl = layui.tpl,
        element = layui.element,
        platforms = {1: '饿了么', 2: '百度', 3: '美团'},
        dimensions = {"order_id": '订单数', "customer_id": '用户数', "origin_fee": '营业额',"bonus_fee":"补贴额","real_fee":"净入额"};

    var Compare = function () {
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

    Compare.prototype = {
        constructor: Compare,




        platform_compare: function () {
            var self = this;
            $('#mainDiv').tpl('/compare/main', {
                'dimensions': dimensions
            }, function (data) {

                element.render();

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
                    console.log(searchParam);
                    client.iGet("/api/v1/compares/platform?" + parseParam(searchParam), {}, function (ret) {

                        var names =[];
                        var seriesList = [];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                            seriesList.push(  {
                                name:row.name,
                                type:'bar',
                                barWidth : 30,
                                data:[row.value]
                            });
                        });

                        var option = {
                            tooltip : {
                                trigger: 'axis',
                                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                }
                            },
                            legend: {
                                data:names
                            },
                            toolbox: {
                                show : true,
                                orient: 'vertical',
                                x: 'right',
                                y: 'center',
                                feature : {
                                    mark : {show: true},
                                    dataView : {show: true, readOnly: false},
                                    magicType : {show: true, type: [ 'bar', 'stack', 'tiled']},
                                    restore : {show: true},
                                    saveAsImage : {show: true}
                                }
                            },
                            calculable : true,
                            xAxis : [
                                {
                                    type : 'category',
                                    data : ['平台']
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value'
                                }
                            ],
                            series : seriesList
                        };


                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                };

                reloadChart({dimension:"order_id"});

            });
        },
        shop_compare: function () {
            var self = this;
            $('#mainDiv').tpl('/compare/main', {
                'dimensions': dimensions
            }, function (data) {

                element.render();

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
                    console.log(searchParam);
                    client.iGet("/api/v1/compares/shop_id?" + parseParam(searchParam), {}, function (ret) {

                        var names =[];
                        var seriesList = [];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                            seriesList.push(  {
                                name:row.name,
                                type:'bar',
                                barWidth : 30,
                                data:[row.value]
                            });
                        });

                        var option = {
                            tooltip : {
                                trigger: 'axis',
                                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                }
                            },
                            legend: {
                                data:names
                            },
                            toolbox: {
                                show : true,
                                orient: 'vertical',
                                x: 'right',
                                y: 'center',
                                feature : {
                                    mark : {show: true},
                                    dataView : {show: true, readOnly: false},
                                    magicType : {show: true, type: [ 'bar', 'stack', 'tiled']},
                                    restore : {show: true},
                                    saveAsImage : {show: true}
                                }
                            },
                            calculable : true,
                            xAxis : [
                                {
                                    type : 'category',
                                    data : ['商家']
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value'
                                }
                            ],
                            series : seriesList
                        };


                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                };

                reloadChart({dimension:"order_id"});

            });
        }


    };
    exports('compare', new Compare());






});