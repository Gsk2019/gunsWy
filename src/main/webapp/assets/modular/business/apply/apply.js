layui.use(['layer', 'form', 'table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 优惠券管理
     */
    var Apply = {
        tableId: "applyTable"    //表格id
    };

    /**
     * 初始化表格的列
     */
    Apply.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'avatar',height:150, sort: true, title: '微信头像',templet: '<div><img src="{{d.avatar}}" style="height: 100%;width: 100%"/></div>'},
            {field: 'nick', sort: true, title: '微信昵称'},
            {field: 'userName', sort: true, title: '姓名'},
            {field: 'courseName', sort: true, title: '报名课程'},
            {field: 'phone', sort: true, title: '电话'},
            {field: 'idCard', sort: true, title: '身份证'},
            {field: 'manCount', sort: true, title: '人数'},
            {field: 'company', sort: true, title: '公司名称'},
            {field: 'remark', sort: true, title: '备注'},
            {field: 'createTime', sort: true, title: '报名时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Apply.tableId,
        url: Feng.ctxPath + '/apply/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Apply.initColumn()
    });

    /**
     * 点击删除通知
     *
     * @param data 点击按钮时候的行数据
     */
    Apply.onDeleteApply = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/apply/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Apply.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除此报名信息  ?", operation);
    };

    // 工具条点击事件
    table.on('tool(' + Apply.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Apply.onDeleteApply(data);
        } else if (layEvent === 'delete') {
            Apply.onDeleteApply(data);
        }
    });
});
