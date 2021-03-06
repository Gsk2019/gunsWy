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
import cn.stylefeng.guns.modular.web.entity.Params;
import cn.stylefeng.guns.modular.web.service.ParamsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


/**
 * 参数APi
 *
 * @author gsk
 * @Date 201900514
 */
@RestController
@RequestMapping("/api/params")
public class ParamsApi extends BaseController {

    @Autowired
    private ParamsService paramsService;

    /**
     * 获取列表不分页
     *
     */
    @GetMapping("")
    public Object getCouponList(Integer site) {

        if (site==null)
            ApiResponseUtil.fail409();

        List<Map<String,Object>> couponList=paramsService.queryList(site);
         return ApiResponseUtil.ok(couponList);
    }

    /**
     * 获取轮播图列表
     *
     */
    @GetMapping("getBannerList")
    public Object getBannerList(Integer site) {

        if (site==null)
            return ApiResponseUtil.fail409();

        List<Map<String,Object>> bannerList=paramsService.queryListLunBo(site);
        return ApiResponseUtil.ok(bannerList);
    }

}

