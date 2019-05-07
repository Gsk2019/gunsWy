layui.use(['layer', 'form', 'table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 优惠券领取管理
     */
    var wxUserCoupon = {
        tableId: "wxUserCouponTable"    //表格id
    };

    /**
     * 初始化表格的列
     */
    wxUserCoupon.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'nick', sort: true, title: '用户昵称'},
            {field: 'couponName', sort: true, title: '优惠券名称'},
            {field: 'isUse', sort: true, title: '是否使用'},
            {field: 'createTime', sort: true, title: '领取时间'},
            /*{align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}*/
        ]];
    };



    // 渲染表格
    var tableResult = table.render({
        elem: '#' + wxUserCoupon.tableId,
        url: Feng.ctxPath + '/coupon/getWxUserCouponList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: wxUserCoupon.initColumn()
    });



    // 工具条点击事件
    table.on('tool(' + wxUserCoupon.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            wxUserCoupon.onEditCoupon(data);
        } else if (layEvent === 'delete') {
            wxUserCoupon.onDeleteCoupon(data);
        }else if (layEvent === 'getDesc') {
            wxUserCoupon.getDesc(data);
        }

    });
});
