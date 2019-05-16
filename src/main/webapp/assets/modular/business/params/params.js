layui.use(['layer', 'table', 'ax', 'laydate','admin'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 课程管理--课程列表
     */
    var params = {
        tableId: "paramsTable" ,  //表格id
        condition: {
            id: ""
        }
    };

    /**
     * 初始化表格的列
     */
    params.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'paramName', sort: true, title: '参数名称'},
            {field: 'paramValue', sort: true, title: '参数值'},
            {field: 'createTime', sort: true, title: '添加时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 100}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + params.tableId,
        url: Feng.ctxPath + '/params/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: params.initColumn()
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        params.openAddParams();
    });

    /**
     * 弹出添加课程对话框
     */
    params.openAddParams = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加',
            area: ['35%', '60%'],
            content: Feng.ctxPath + '/params/params_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(params.tableId);
            }
        });
    };

    /**
     * 点击编辑课程
     *
     * @param data 点击按钮时候的行数据
     */
    params.onEditParams = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改',
            area: ['35%', '60%'],
            content: Feng.ctxPath + '/params/params_update/' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(params.tableId);
            }
        });
    };

    /**
     * 点击删除按钮
     *
     * @param data 点击按钮时候的行数据
     */
    params.onDeleteParams = function (data) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/params/remove", function () {
                Feng.success("删除成功!");
                table.reload(params.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除此参数" + "?", operation);
    };


    // 工具条点击事件
    table.on('tool(' + params.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'delete') {
            params.onDeleteParams(data);
        }else if (layEvent === 'edit') {
            params.onEditParams(data);
        }
    });

    /**
     * 导出excel按钮
     */
    params.exportExcel = function () {
        var checkRows = table.checkStatus(params.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };


});
