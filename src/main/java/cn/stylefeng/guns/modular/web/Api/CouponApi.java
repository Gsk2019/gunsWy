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
package cn.stylefeng.guns.modular.web.Api;

import cn.stylefeng.guns.core.util.ApiResponseUtil;
import cn.stylefeng.guns.modular.web.entity.Coupon;
import cn.stylefeng.guns.modular.web.entity.WxUser;
import cn.stylefeng.guns.modular.web.service.CouponService;
import cn.stylefeng.guns.modular.web.service.LoginService;
import cn.stylefeng.guns.modular.web.service.WxUserCouponService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


/**
 * 优惠券APi
 *
 * @author gsk
 * @Date 201900506
 */
@RestController
@RequestMapping("/api/coupon")
public class CouponApi extends BaseController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private WxUserCouponService wxUserCouponService;

    /**
     * 获取优惠券列表不分页
     *
     */
    @GetMapping("")
    public Object getCouponList() {

        List<Coupon> couponList=couponService.list();
         return ApiResponseUtil.ok(couponList);
    }


    /**
     * 领取优惠券
     *
     */
    @PostMapping("getCoupon")
    public Object getCoupon(String skey,Integer couponId) {

        WxUser wxUser=loginService.getWxUserBySkey(skey);
        if (wxUser==null)
            return ApiResponseUtil.fail409();

        boolean flag=wxUserCouponService.getCoupon(wxUser,couponId);
        if (!flag)
            return ApiResponseUtil.fail(-1,"不能重复领取");
        return ApiResponseUtil.ok();
    }


    /**
     * 获取我的优惠券
     * @param skey
     * @return
     */
    @GetMapping("getMyCouponList")
    public Object getCoupon(String skey) {

        if (StringUtils.isBlank(skey))
            return ApiResponseUtil.fail409();

        WxUser wxUser=loginService.getWxUserBySkey(skey);
        if (wxUser==null)
            return ApiResponseUtil.fail409();

        List<Map> myCouponList=wxUserCouponService.getCouponByUserId(wxUser);
        return ApiResponseUtil.ok(myCouponList);
    }





}

