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
import cn.stylefeng.guns.modular.web.entity.Apply;
import cn.stylefeng.guns.modular.web.entity.WxUser;
import cn.stylefeng.guns.modular.web.service.ApplyService;
import cn.stylefeng.guns.modular.web.service.LoginService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import io.netty.handler.codec.compression.Snappy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 报名处APi
 *
 * @author gsk
 * @Date 201900506晚
 */
@RestController
@RequestMapping("/api/apply")
public class ApplyApi extends BaseController {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private LoginService loginService;

    /**
     * 用户首次登录是需要更新用户信息
     *
     */
    @PostMapping("")
    public Object doApply(Apply apply,String skey) {

        if (apply.getCourseId()==null)
            return ApiResponseUtil.fail409();

        WxUser wxUser=loginService.getWxUserBySkey(skey);
        if (wxUser==null)
            return ApiResponseUtil.fail409();
        apply.setUserId(wxUser.getId());
         applyService.doApply(apply);
         return ApiResponseUtil.ok();
    }





}

