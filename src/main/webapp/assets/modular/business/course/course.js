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
    var course = {
        tableId: "courseTable" ,  //表格id
        condition: {
            id: ""
        }
    };

    /**
     * 初始化表格的列
     */
    course.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'courseName', sort: true, title: '课程名称'},
            {field: 'courseType', sort: true, title: '课程类型', templet: '#courseTypeStr'},
            {field: 'price', sort: true, title: '价格'},
            {field: 'startTime', sort: true, title: '开课时间'},
            {field: 'courseAddr', sort: true, title: '上课地点'},
            {field: 'createTime', sort: true, title: '添加时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 100}
        ]];
    };


    /**
     * 点击查询按钮
     */
    course.search = function () {
        var queryData = {};
        queryData['courseName'] = $("#courseName").val();
        queryData['courseType'] = $("#courseType").val();
        table.reload(course.tableId, {where: queryData});
    };

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
       course.openAddCourse();
    });

    /**
     * 弹出添加课程对话框
     */
  course.openAddCourse = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加课程',
            area: ['60%', '80%'],
            content: Feng.ctxPath + '/course/course_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(course.tableId);
            }
        });
    };

    /**
     * 点击编辑课程
     *
     * @param data 点击按钮时候的行数据
     */
    course.onEditCourse = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改课程',
            area: ['60%', '80%'],
            content: Feng.ctxPath + '/course/course_update?id=' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(course.tableId);
            }
        });
    };

    /**
     * 点击删除课程按钮
     *
     * @param data 点击按钮时候的行数据
     */
    course.onDeleteCourse = function (data) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/course/remove", function () {
                Feng.success("删除成功!");
                course.condition.id = "";
                table.reload(course.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除此课程" + "?", operation);
    };


    // 工具条点击事件
    table.on('tool(' + course.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'detail') {
            course.courseDetail(data);
        }else if (layEvent === 'delete') {
            course.onDeleteCourse(data);
        }else if (layEvent === 'getTimeTable') {
            course.getTimeTable(data);
        }else if (layEvent === 'edit') {
            course.onEditCourse(data);
        }/*else if(layEvent === 'view'){
            course.onViewCourse(data);
        }*/
    });

    /**
     * 查看课表
     */
    course.getTimeTable = function (param) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '查看课程列表',
            area: ['90%', '90%'],
            content: Feng.ctxPath + '/course/toTimeTable?courseId='+param.id
        });
    };


    /**
     * 导出excel按钮
     */
    course.exportExcel = function () {
        var checkRows = table.checkStatus(course.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 课程详情
     */
    course.courseDetail = function (param) {
        var ajax = new $ax(Feng.ctxPath + "/course/" + param.id, function (data) {
            Feng.infoDetail("课程详情", data.regularMessage);
        }, function (data) {
            Feng.error("获取详情失败!");
        });
        ajax.start();
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + course.tableId,
        url: Feng.ctxPath + '/course/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: course.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        course.search();
    });





});
