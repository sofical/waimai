/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'form', 'restclient', 'env', 'tpl', 'element', 'table'], function (exports) {
    var layer = layui.layer,
        client = layui.restclient,
        env = layui.env,
        tpl = layui.tpl,
        element = layui.element;

    var User = function () {
        this.baseUrl = "tpl/"
    };

    User.prototype = {
        constructor : User,

        /**
         * 主入口
         */
        enter : function() {
            var self = this,
                urlUserList = env.server + "/v1/users",
                urlUserDelete = env.server + "/v1/users/{user_id}",
                urlUserReset = env.server + "/v1/users/{user_id}/actions/reset";
            $('#mainDiv').tpl('/user/list', {}, function () {
                element.render();
                var table = layui.table,
                    barHtml = '<script type="text/html" id="barDemo">' +
                        '<a class="layui-btn layui-btn-xs" lay-event="reset">重置密码</a>' +
                       '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>' +
                        '</script>';
                $('#mainDiv').append(barHtml);

                //第一个实例
                table.render({
                    elem: '#user_list'
                    // ,height: 315
                    ,url: urlUserList //数据接口
                    ,page: true //开启分页
                    ,cols: [[ //表头
                        {field: 'account', title: '账号', sort: true, fixed: 'left'}
                        ,{field: 'name', title: '姓名'},
                        {fixed: 'right', title: '操作', width:200, align:'center', toolbar: '#barDemo'}
                    ]]
                });
                table.on('tool(user)', function(obj){
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象

                    if(layEvent === 'del'){ //删除
                        layer.confirm('真的删除行么', function(index){
                            layer.close(index);
                            client.iDelete(urlUserDelete.replace('{user_id}', data.user_id), {}, function () {
                                layer.msg("删除成功");
                                table.reload('user_list');
                            });
                        });
                    } else if (layEvent === 'reset') {
                        layer.confirm('真的需要将该账密码重置为123456吗？', function(index){
                            layer.close(index);
                            client.iPost(urlUserReset.replace('{user_id}', data.user_id),{}, {}, function () {
                                layer.msg("重置成功");
                            });
                        });
                    }
                });

                $('#user_add').on('click', function () {
                    self.add();
                });
            });
        },
        
        add : function () {
            var self = this;
            //添加
            $('#mainDiv').tpl('/user/add', {}, function () {
                var form = layui.form,
                    urlAddUser = env.server + '/v1/users';
                element.render();
                form.render();
                form.on('submit(*)', function(data){
                    console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
                    console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                    console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}

                    client.iPost(urlAddUser, data.field, {}, function (data) {
                        layer.msg('添加成功');
                        self.enter();
                    });

                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
            });
        }
    };

    var user = new User();
    user.enter();

    exports('user', new User());
});