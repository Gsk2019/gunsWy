/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.entity.OperationLog;
import cn.stylefeng.guns.modular.system.warpper.LogWrapper;
import cn.stylefeng.guns.modular.web.entity.Coupon;
import cn.stylefeng.guns.modular.web.service.CouponService;
import cn.stylefeng.guns.modular.web.service.WxUserCouponService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.Map;

/**
 * 优惠券控制器
 *
 * @author gsk
 * @Date 20190507
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

    private String PREFIX = "/modular/business/coupon/";

    @Autowired
    private CouponService couponService;
    @Autowired
    private WxUserCouponService wxUserCouponService;

    /**
     * 跳转到列表首页
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coupon.html";
    }


    /**
     * 获取列表数据
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        Page<Map<String, Object>> list = this.couponService.getList();
//        Page<Map<String, Object>> wrap = new NoticeWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(list);
    }

    /**
     * 跳转到添加通知
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping("/coupon_add")
    public String couponAdd() {
        return PREFIX + "coupon_add.html";
    }

    /**
     * 跳转到修改通知
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping("/coupon_update/{id}")
    public String couponUpdate(@PathVariable Integer id, Model model) {
        Coupon coupon = this.couponService.getById(id);
        model.addAllAttributes(BeanUtil.beanToMap(coupon));
        LogObjectHolder.me().set(coupon);
        return PREFIX + "coupon_edit.html";
    }

    /**
     * 新增通知
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping(value = "/add")
    @ResponseBody
//    @BussinessLog(value = "新增", key = "title", dict = NoticeMap.class)
    public Object add(Coupon coupon) {
        if (ToolUtil.isOneEmpty(coupon, coupon.getCouponName())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        coupon.setCreateTime(new Date());
        coupon.setUpdateTime(new Date());
        this.couponService.save(coupon);
        return SUCCESS_TIP;
    }

    /**
     * 查询使用说明
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping("/getDesc/{id}")
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
        Coupon coupon = couponService.getById(id);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coupon);
        return stringObjectMap;
    }

    /**
     * 删除优惠券
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
//    @BussinessLog(value = "删除通知", key = "id", dict = NoticeMap.class)
    public Object delete(@RequestParam Integer id) {

        //缓存通知名称
//        LogObjectHolder.me().set(ConstantFactory.me().getc(id));

        Coupon old = this.couponService.getById(id);
        old.setStatus(0);
        this.couponService.updateById(old);
        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Coupon coupon) {
        if (ToolUtil.isOneEmpty(coupon, coupon.getId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Coupon old = this.couponService.getById(coupon.getId());
        old.setCouponName(coupon.getCouponName());

        old.setCouponDesc(coupon.getCouponDesc());
        old.setCouponCount(coupon.getCouponCount());
        old.setCouponMoney(coupon.getCouponMoney());
        old.setStartTime(coupon.getStartTime());
        old.setEndTime(coupon.getEndTime());
        old.setUpdateTime(new Date());
        this.couponService.updateById(old);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到领取记录页面
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping("getWxUserCoupon")
    public String getWxUserCoupon() {
        return PREFIX + "wxUser_coupon.html";
    }

    /**
     * 获取优惠券领取记录列表数据
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping(value = "/getWxUserCouponList")
    @ResponseBody
    public Object getWxUserCouponList() {
        Page<Map<String, Object>> list = wxUserCouponService.getWxUserCouponList();
//        Page<Map<String, Object>> wrap = new NoticeWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(list);
    }


}
