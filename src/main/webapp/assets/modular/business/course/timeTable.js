
layui.use(['layer', 'table', 'ax', 'laydate','admin'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 课程管理--课表列表
     */
    var timeTable = {
        tableId: "timeTableTable" ,  //表格id
        condition: {
            id: ""
        }
    };

    /**
     * 初始化表格的列
     */
    timeTable.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'tableName', sort: true, title: '课表名称'},
            {field: 'tableSummary', sort: true, title: '课表简介'},
            {field: 'createTime', sort: true, title: '添加时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 100}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + timeTable.tableId,
        url: Feng.ctxPath + '/course/timeTableList?courseId='+courseId,
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: timeTable.initColumn()
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        timeTable.openAddTimeTable();
    });

    /**
     * 弹出添加课表对话框
     */
    timeTable.openAddTimeTable = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加课表',
            area: ['50%', '60%'],
            content: Feng.ctxPath + '/course/timeTable_add?courseId='+courseId,
            end: function () {
                admin.getTempData('formOk') && table.reload(timeTable.tableId);
            }
        });
    };

    /**
     * 点击编辑课表
     *
     * @param data 点击按钮时候的行数据
     */
    timeTable.onEditCourse = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改课表',
            area: ['60%', '80%'],
            content: Feng.ctxPath + '/course/timeTable_update/' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(timeTable.tableId);
            }
        });
    };


    /**
     * 点击删除菜单按钮
     *
     * @param data 点击按钮时候的行数据
     */
    timeTable.onDeleteTimeTable = function (data) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/course/removeTimeTable", function () {
                Feng.success("删除成功!");
                timeTable.condition.id = "";
                table.reload(timeTable.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除此课表" + "?", operation);
    };


    // 工具条点击事件
    table.on('tool(' + timeTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'detailTimeTable') {
            timeTable.timeTableDetail(data);
        }else if (layEvent === 'deleteTimeTable') {
            timeTable.onDeleteTimeTable(data);
        }else if (layEvent === 'editTimeTable') {
            timeTable.onEditCourse(data);
        }
    });



    /**
     * 导出excel按钮
     */
    timeTable.exportExcel = function () {
        var checkRows = table.checkStatus(timeTable.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

});
