/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'table', 'restclient', 'env', 'tpl', 'form', 'element', 'tree'], function (exports) {
    var layer = layui.layer,
        client = layui.restclient,
        env = layui.env,
        element = layui.element,
        tpl = layui.tpl;

    var Role = function () {
        this.baseUrl = "tpl/"
    };

    Role.prototype = {
        constructor : Role,

        /**
         * 主入口
         */
        enter : function() {
            var self = this,
                urlRoleList = env.server + "/v1/roles",
                urlRoleDelete = env.server + "/v1/roles/{role_id}";
            $('#mainDiv').tpl('/role/list', {}, function (data) {
                element.render();
                var table = layui.table,
                    barHtml = '<script type="text/html" id="barDemo">' +
                        '<a class="layui-btn layui-btn-xs" lay-event="user_set">用户分配</a>' +
                '{{#  if(d.code != "SUPPER_MAN"){ }}' +
                '<a class="layui-btn layui-btn-xs" lay-event="role_manager">权限分配</a>' +
                        '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>' +
                    '{{#  } }}' +
                '</script>';
                $('#mainDiv').append(barHtml);

                //第一个实例
                table.render({
                    elem: '#role_list'
                    // ,height: 315
                    ,url: urlRoleList //数据接口
                    ,page: true //开启分页
                    ,cols: [[ //表头
                        {field: 'code', title: '角色编码', sort: true, fixed: 'left'}
                        ,{field: 'name', title: '角色名称'},
                        {fixed: 'right', title: '操作', width:200, align:'center', toolbar: '#barDemo'}
                    ]]
                });
                table.on('tool(test)', function(obj){
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象

                    if(layEvent === 'user_set'){ //查看
                        self.user(data);
                    } else if(layEvent === 'del'){ //删除
                        layer.confirm('真的删除行么', function(index){
                            layer.close(index);
                            client.iDelete(urlRoleDelete.replace('{role_id}', data.role_id), {}, function () {
                                layer.msg("删除成功");
                                table.reload('role_list');
                            });
                        });
                    } else if(layEvent === 'role_manager'){ //编辑
                        self.role(data);
                    }
                });

                $('#role_add').on('click', function () {
                    self.add();
                });
            });
        },

        add : function () {
            var self = this;
            //添加
            $('#mainDiv').tpl('/role/add', {}, function () {
                var form = layui.form,
                    urlAddRole = env.server + '/v1/roles';
                element.render();
                form.render();
                form.on('submit(*)', function(data){
                    console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
                    console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                    console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}

                    client.iPost(urlAddRole, data.field, {}, function (data) {
                        layer.msg('添加成功');
                        self.enter();
                    });

                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
            });
        },

        role : function (data) {
            var self = this,
                _data = data;
            $('#mainDiv').tpl('/role/right', {}, function () {
                element.render();
                var urlRoleCodes = env.server + "/v1/roles/rights",
                    urlRoleRightsSave = env.server + "/v1/roles/{role_id}/actions/rights".replace("{role_id}", _data.role_id);
                client.iGet(urlRoleCodes, {}, function (data) {
                    var hasRights = _data.rights.split(',');
                    console.log(hasRights);
                    $.each(data, function (i, row) {
                        console.log(row);
                        if ($.inArray(row.code, hasRights) > -1) {
                            data[i]['checked'] = true;
                        }
                    });
                    var setting = {
                        check: {
                            enable: true
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        }
                    };
                    $.fn.zTree.init($("#right_tree"), setting, data);
                });

                $('#rights_save').on('click', function () {
                    var zTree = $.fn.zTree.getZTreeObj("right_tree"),
                        data = zTree.getCheckedNodes();
                    client.iPut(urlRoleRightsSave, data, {}, function (data) {
                        layer.msg("修改成功");
                        self.enter();
                    });
                });

                $('#rights_cancel').on("click", function () {
                    self.enter();
                })
            });
        },
        
        user : function (data) {
            var self = this,
                urlRoleUserList = env.server + '/v1/roles/{role_id}/users',
                urlRoleUserDelete = env.server + '/v1/roles/{role_id}/users/{user_id}',
                _data = data;
            $('#mainDiv').tpl('/role/users', {}, function () {
                element.render();
                var table = layui.table,
                    barHtml = '<script type="text/html" id="barDemo">' +
                        '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>' +
                        '</script>';
                $('#mainDiv').append(barHtml);

                //第一个实例
                table.render({
                    elem: '#role_user_list'
                    // ,height: 315
                    ,url: urlRoleUserList.replace("{role_id}", _data.role_id) //数据接口
                    ,page: true //开启分页
                    ,cols: [[ //表头
                        {field: 'account', title: '账号', sort: true, fixed: 'left'}
                        ,{field: 'name', title: '姓名'},
                        {fixed: 'right', title: '操作', width:200, align:'center', toolbar: '#barDemo'}
                    ]]
                });
                table.on('tool(role_user)', function(obj){
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象

                    if(layEvent === 'del'){ //删除
                        layer.confirm('真的删除行么', function(index){
                            layer.close(index);
                            client.iDelete(urlRoleUserDelete.replace('{role_id}', _data.role_id).replace('{user_id}', data.user_id), {}, function () {
                                layer.msg("删除成功");
                                table.reload('role_user_list');
                            });
                        });
                    }
                });

                $('#role_user_add').on('click', function () {
                    self.userAdd(_data);
                });

                $('#role_user_cancel').on("click", function () {
                    self.enter();
                })
            });
        },

        userAdd : function (data) {
            var self = this,
                _data = data;
            //添加
            $('#mainDiv').tpl('/role/user_add', {}, function () {
                var form = layui.form,
                    urlAddRoleUser = env.server + '/v1/roles/{role_id}/users'.replace("{role_id}", _data.role_id);
                element.render();
                form.render();
                form.on('submit(*)', function(data){
                    console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
                    console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                    console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}

                    client.iPost(urlAddRoleUser, data.field, {}, function (data) {
                        layer.msg('添加成功');
                        self.user(_data);
                    });

                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });

                $('#role_user_add_cancel').on('click', function () {
                    self.user(_data);
                });
            });
        }
    };

    exports('role', new Role());
});