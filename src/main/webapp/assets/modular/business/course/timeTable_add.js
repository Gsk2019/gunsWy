layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {

        var imageStr="";
        $("input[class='upload_img_id']").each(function(){
            imageStr=imageStr+$(this).val()+";";
        });

        var ajax = new $ax(Feng.ctxPath + "/course/addTimeTable", function (data) {
            Feng.success("课表添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("课表添加失败！" + data.responseJSON.message)
        });
        var postData=data.field;
        postData.tableImages=imageStr;
        ajax.set(postData);
        ajax.start();
    });
});