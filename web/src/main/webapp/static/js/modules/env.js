/**
 * 环镜变量相关
 * Created by zj on 2017/5/11.
 */
layui.define(function(exports){
    var envParams = {
        dev : {
            server : '/api'
        },
        test : {
            server : ''
        },
        prepro : {
            server : ''
        },
        pro : {
            server : ''
        }
    };

    var host = document.location.host.toString();
    var env = 'dev';
    //测试环镜
    if (host.indexOf('test.com') > -1) {
        env = 'test';
    } else if (host.indexOf('prepro.com') > - 1) {
        env = 'prepro'
    } else if (host.indexOf('pro.com') > -1) {
        env = 'pro';
    }

    exports('env', envParams[env]); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});
