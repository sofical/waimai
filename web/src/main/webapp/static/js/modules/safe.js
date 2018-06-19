/**
 * Created by Administrator on 2017/11/21.
 */
layui.define(['layer', 'form', 'restclient', 'env', 'tpl', 'element'], function (exports) {
    var layer = layui.layer,
        client = layui.restclient,
        env = layui.env,
        tpl = layui.tpl,
        element = layui.element;

    var Safe = function () {
        this.baseUrl = "tpl/"
    };

    Safe.prototype = {
        constructor : Safe,

        /**
         * 主入口
         */
        enter : function() {
            var self = this,
                urlEdit = env.server + "/v1/safe/reset";
            $('#mainDiv').tpl('/safe/index', {}, function (data) {
                element.render();
                var form = layui.form;
                form.render();
                form.verify({
                    old_pass : [
                        /^[\S]{6,16}$/
                        ,'旧密码不能为空'
                        ],
                    new_pass : [
                        /^[\S]{6,16}$/
                        ,'新密码必须6到12位，且不能出现空格'
                    ],
                    sure_pass : [
                        /^[\S]{6,16}$/
                        ,'确认密码必须6到12位，且不能出现空格'
                    ]
                }).on('submit(*)', function(data){
                    console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
                    console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                    console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                    if (data.field.new_pass != data.field.sure_pass) {
                        layer.msg('确认密码与新密码不一致');
                        return false;
                    }

                    client.iPost(urlEdit, data.field, {}, function (data) {
                        layer.msg('密码重置成功');
                    });

                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
            });
        }
    };

    exports('safe', new Safe());
});