/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'table', 'form', 'restclient', 'env', 'tpl', 'element'], function (exports) {
    var layer = layui.layer,
        client = layui.restclient,
        env = layui.env,
        tpl = layui.tpl,
        element = layui.element,
        platforms = {1: '饿了么', 2: '百度', 3: '美团'};

    var Shop = function () {
        this.baseUrl = "tpl/"
    };

    Shop.prototype = {
        constructor: Shop,

        /**
         * 主入口
         */
        enter: function () {
            var self = this,
                urlShopList = env.server + "/v1/shops",

                urlShopDelete = env.server + "/v1/shops/{shop_id}";
            $('#mainDiv').tpl('/shop/list', {'platforms': platforms}, function (data) {

                element.render();
                // 搜索表单
                var searchForm = layui.form;
                searchForm.render();
                searchForm.on('submit(*)', function (data) {
                    table.reload('shop_list', {where: data.field});
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
                    elem: '#shop_list'
                    // , height: 315
                    , url: urlShopList //数据接口
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'code', title: '店铺编码', sort: true, fixed: 'left'}
                        , {field: 'name', title: '店铺名称'}
                        , {field: 'platform', title: '平台'},
                        {fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#operate-row'}
                    ]]
                    , done: function (res, page, count) {
                        $("[data-field='platform']").children().each(function () {
                            var platform = parseInt($(this).text());
                            $(this).text(platforms[platform]);
                        });
                    }
                });
                table.on('tool(shop)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象
                    if (layEvent === 'shop_del') { //删除
                        layer.confirm('真的删除行么', function (index) {
                            layer.close(index);
                            client.iDelete(urlShopDelete.replace('{shop_id}', data.shop_id), {}, function () {
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
        add: function (isEdit,param) {
            var self = this;

            var formatData = function (shopData) {

                for (var index in shopData){
                    if(shopData.hasOwnProperty(index)){
                        var arr = index.split(".");
                        if(arr.length > 1){
                            if(shopData[arr[0]] === undefined ){
                                shopData[arr[0]] = {};
                            }
                            shopData[arr[0]][arr[1]] = shopData[index];

                        }
                    }

                }
                return shopData;
            };
            //添加
            $('#mainDiv').tpl('/shop/add', {'platforms': platforms,shop:param}, function () {
                var form = layui.form,
                    urlMdfShop = env.server + "/v1/shops/{shop_id}",
                    urlAddShop = env.server + '/v1/shops';
                element.render();
                form.render("select");

                // 填充数据
                for(var index in param){
                    console.log(param[index]);
                    if( param[index].__proto__.constructor== Object){
                        for(var key in param[index]) {
                            $('#editShopForm').find('input[name="' + index+'.' +key+ '"]').val(param[index][key]);
                        }
                    }
                    $('#editShopForm').find('input[name="'+index+'"]').val(param[index]);
                    // $('#editShopForm').find('select[name="'+index+'"]').find('option').removeAttr("selected");
                    $('#editShopForm').find('select[name="'+index+'"]').val(param[index]).attr("disabled",true);

                    form.render("select");

                    // $('#editShopForm').find('select[name="'+index+'"]').find('option[value="'+param[index]+'"]').attr("selected","true");
                }
                if(isEdit){
                    $('.extra-data').hide();
                    $('.extra-data-'+param.platform).show();
                }else {
                    $('.extra-data').hide();
                    $('.extra-data-1').show();
                }
                form.on('select(platform)', function(data){
                    console.log(data);
                    $('.extra-data').hide();
                    $('.extra-data-'+data.value).show();
                });

                form.on('submit(*)', function (data) {
                    var shopData = data.field;
                    console.log(shopData);
                    if(isEdit){
                        client.iPut(urlMdfShop.replace('{shop_id}', param.shop_id), formatData(shopData), {}, function (data) {
                            layer.msg('修改成功');
                            self.enter();
                        });
                    }else {
                        client.iPost(urlAddShop, formatData(shopData), {}, function (data) {
                            layer.msg('添加成功');
                            self.enter();
                        });
                    }

                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
            });
        },

        /**
         * 菜品
         * @param shop
         */
        dish: function (shop) {
            var self = this,
                urlDishList = env.server + "/v1/dishes",

                urlDishDelete = env.server + "/v1/dishes/{dish_id}";
            $('#mainDiv').tpl('/shop/dish', {}, function (data) {

                element.render();
                // 搜索表单
                var searchForm = layui.form;
                searchForm.render();
                searchForm.on('submit(*)', function (data) {
                    var searchParam = data.field;
                    searchParam.shop_id = shop.shop_id;
                    table.reload('dish_list', {where: data.field});
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });

                var table = layui.table,
                    barHtml = '<script type="text/html" id="operate-row">' +
                        '<a class="layui-btn layui-btn-xs" lay-event="dish_mdf">修改</a>' +
                        '<a class="layui-btn layui-btn-xs" lay-event="dish_del">删除</a>' +
                        '</script>';
                $('#mainDiv').append(barHtml);

                //列表
                table.render({
                    elem: '#dish_list'
                    // , height: 315
                    , url: urlDishList //数据接口
                    ,where:{shop_id:shop.shop_id}
                    , page: true //开启分页
                    , cols: [[ //表头
                        {field: 'code', title: '菜品编码', sort: true, fixed: 'left'}
                        , {field: 'name', title: '菜品名称'}
                        , {field: 'style', title: '菜系'}
                        , {field: 'taste', title: '口味'}
                        , {field: 'tag', title: '分类'}
                        ,{fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#operate-row'}
                    ]]
                    , done: function (res, page, count) {

                    }
                });
                table.on('tool(dish)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象
                    if (layEvent === 'dish_del') { //删除
                        layer.confirm('真的删除行么', function (index) {
                            layer.close(index);
                            client.iDelete(urlDishDelete.replace('{dish_id}', data.dish_id), {}, function () {
                                layer.msg("删除成功");
                                table.reload('dish_list');
                            });
                        });
                    } else if (layEvent === 'dish_mdf') { //查看
                        self.dishAdd(true,data);
                    }
                });

                $('#shop_add').on('click', function () {
                    self.dishAdd(false,{shop_id:shop.shop_id});
                });
                $('#shop_return').on('click', function () {
                    self.enter();
                });
            });
        },
        dishAdd: function (isEdit,param) {
            var self = this;

            // var formatData = function (shopData) {
            //
            //     for (var index in shopData){
            //         if(shopData.hasOwnProperty(index)){
            //             var arr = index.split(".");
            //             if(arr.length > 1){
            //                 shopData[arr[0]][arr[1]] = shopData[index];
            //             }
            //         }
            //
            //     }
            //     return shopData;
            // };
            //添加
            $('#mainDiv').tpl('/shop/dishAdd', {}, function () {
                var form = layui.form,
                    urlMdf = env.server + "/v1/dishes/{dish_id}",
                    urlAdd = env.server + '/v1/dishes';
                element.render();
                form.render();

                // 填充数据
                for(var index in param){
                    $('#editForm').find('input[name="'+index+'"]').val(param[index]);
                }


                form.on('submit(*)', function (data) {
                    var dishData = data.field;
                    dishData.shop_id = param.shop_id;
                    if(isEdit){
                        client.iPut(urlMdf.replace('{dish_id}', param.dish_id), dishData, {}, function (data) {
                            layer.msg('修改成功');
                            self.dish({shop_id:param.shop_id});
                        });
                    }else {
                        client.iPost(urlAdd, dishData, {}, function (data) {
                            layer.msg('添加成功');
                            self.dish({shop_id:param.shop_id});
                        });
                    }

                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
            });
        }
    };
    exports('shop', new Shop());






});