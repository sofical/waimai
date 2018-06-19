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

    var Comment = function () {
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

    Comment.prototype = {
        constructor: Comment,

        /**
         * 主入口
         */
        comment_list: function () {
            var self = this,
                urlCommentList = env.server + "/v1/comments";
            $('#mainDiv').tpl('/comment/list', {
                platforms: platforms,
                '_c': '/api/v1/shops?page=1&limit=1000'}, function (data) {

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
                        '<a class="layui-btn layui-btn-xs" lay-event="detail">详情</a>' +
                        '</script>';
                $('#mainDiv').append(barHtml);

                //列表
                table.render({
                    elem: '#list-table'
                    // , height: 315
                    , url: urlCommentList //数据接口
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'platform', title: '平台', sort: true, fixed: 'left'},
                        {field: 'shop_name', title: '店铺名称', sort: true, fixed: 'left'},
                        {field: 'content', title: '评论', sort: true, fixed: 'left'},
                        {field: 'order_score', title: '订单评分', sort: true, fixed: 'left'},
                        {field: 'food_score', title: '菜品评分', sort: true, fixed: 'left'},
                        {field: 'comment_time', title: '时间', sort: true, fixed: 'left'}
                    ]]
                    , done: function (res, page, count) {
                        $("[data-field='platform']").children().each(function () {
                            var platform = parseInt($(this).text());
                            $(this).text(platforms[platform]);
                        });
                    }
                });
                // table.on('tool(comment)', function (obj) {
                //     var data = obj.data; //获得当前行数据
                //     var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                //     var tr = obj.tr; //获得当前行 tr 的DOM对象
                //      if (layEvent === 'detail') { //查看
                //         self.detail(data);
                //     }
                // });


            });
        },
        comment_analysis: function () {
            var self = this;
            $('#mainDiv').tpl('/comment/analysis', {
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

                    client.iGet("/api/v1/comments/analysis?" + parseParam(searchParam), {}, function (ret) {
                        var names =[];
                        $.each(ret,function (i,row) {
                            names.push(row.name);
                        });
                        var option = {
                            title: {
                                text: '评价星级分析',
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
                                    name: '评价星级分析',
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
        }

    };
    exports('comment', new Comment());






});