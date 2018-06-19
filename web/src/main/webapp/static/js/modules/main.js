/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'form', 'restclient', 'env'], function (exports) {
    var layer = layui.layer,
        form = layui.form,
        client = layui.restclient,
        env = layui.env;

    var Main = function () {
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

    Main.prototype = {
        constructor : Main,

        /**
         * 主入口
         */
        enter : function() {
            var self = this;
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
            if ($.cookie("access_token") == "") {
                this.goToLogin();
            }


            //注册鉴听方法
            this.logout();
            this.menu();
        },

        /**
         * 进入登陆页
         */
        goToLogin : function() {
            location.href = "/";
        },

        menu : function() {
            var self = this,
                uriMenu = env.server + "/v1/cfg/menus";
            client.iGet(uriMenu, {}, function(rsp) {
                var menuText = "";
                for(var i=0; i<rsp.length; i++) {
                    var menu = rsp[i];
                    var func = menu.func;
                    var cssLi = "", child = "";
                    if (menu._child) {
                        cssLi = " layui-nav-itemed";
                        child += "<dl class=\"layui-nav-child\">";
                        for (var n=0; n<menu._child.length; n++) {
                            var menuSub = menu._child[n];
                            var funcSub = menuSub.func;
                            child += "<dd><a href=\"javascript:;\" func=\"" + funcSub + "\">" + menuSub.name + "</a></dd>";
                        }
                        child += "</dl>";
                    }
                    menuText += "<li class=\"layui-nav-item" + cssLi + "\"><a class=\"\" func=\"" + func + "\" href=\"javascript:;\">" + menu.name + "</a> " + child + "</li>";
                }
                $('.layui-nav-tree').html(menuText);
                layui.use('element');

                $('.layui-nav-tree').find('a').on('click', function () {
                    var func = $(this).attr('func');
                    if (func != undefined) {
                        //layui.use(func);
                        var callArr = func.split(':');
                        if (!self._callCache[callArr[0]]) {
                            layui.use(callArr[0], function () {
                                var callFunc = layui[callArr[0]];
                                self._callCache[callArr[0]] = callFunc;
                                callFunc[callArr[1]]();
                            });
                        } else {
                            self._callCache[callArr[0]][callArr[1]]();
                        }

                    }
                });

            });
        },

        _callCache : {},

        /**
         * 退出登陆
         */
        logout : function() {
            var self = this;
            $('._button-logout').on('click', function() {
                client.helper.clearCookie();
                self.goToLogin();
            });
        }
    };

    layer.msg("欢迎回来!");

    var main = new Main();
    main.enter();

    exports('main', new Main());
});