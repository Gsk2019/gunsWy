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
    var Coupon = {
        tableId: "couponTable"    //表格id
    };

    /**
     * 初始化表格的列
     */
    Coupon.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'couponName', sort: true, title: '名称'},
            {field: 'couponMoney', sort: true, title: '金额'},
            {field: 'couponCount', sort: true, title: '剩余数量'},
            {field: 'startTime', sort: true, title: '开始时间'},
            {field: 'endTime', sort: true, title: '结束时间'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 弹出添加通知
     */
    Coupon.openAddCoupon = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加优惠券',
            content: Feng.ctxPath + '/coupon/coupon_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Coupon.tableId);
            }
        });
    };

    /**
     * 点击编辑通知
     *
     * @param data 点击按钮时候的行数据
     */
    Coupon.onEditCoupon = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '优惠券修改',
            content: Feng.ctxPath + '/coupon/coupon_update/' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(Coupon.tableId);
            }
        });
    };

    /**
     * 查看使用说明
     */
    Coupon.getDesc = function (param) {
        var ajax = new $ax(Feng.ctxPath + "/coupon/getDesc/" + param.id, function (data) {
            Feng.infoDetail("使用说明", data.couponDesc);
        }, function (data) {
            Feng.error("获取使用说明失败!");
        });
        ajax.start();
    };

    /**
     * 点击删除通知
     *
     * @param data 点击按钮时候的行数据
     */
    Coupon.onDeleteCoupon = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/coupon/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Coupon.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除 " + data.couponName + "?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Coupon.tableId,
        url: Feng.ctxPath + '/coupon/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Coupon.initColumn()
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Coupon.openAddCoupon();
    });

    // 工具条点击事件
    table.on('tool(' + Coupon.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Coupon.onEditCoupon(data);
        } else if (layEvent === 'delete') {
            Coupon.onDeleteCoupon(data);
        }else if (layEvent === 'getDesc') {
            Coupon.getDesc(data);
        }

    });
});
