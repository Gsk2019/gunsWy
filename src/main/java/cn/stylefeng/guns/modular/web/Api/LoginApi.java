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
import cn.stylefeng.guns.modular.web.service.LoginService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 登录APi
 *
 * @author gsk
 * @Date 20190429 晚
 */
@RestController
@RequestMapping("/api/login")
public class LoginApi extends BaseController {

    @Autowired
    private LoginService loginService;


    /**
     * 小程序登录
     *
     */
    @PostMapping("")
    public Object doLogin(  @RequestParam(value = "code",required = true) String code,Integer site) {

        if (site==null)
            return ApiResponseUtil.fail409();

        if (StringUtils.isBlank(code))
            return ApiResponseUtil.fail409();

       return loginService.doLogin(code,site);
    }

    /**
     * 用户首次登录是需要更新用户信息
     *
     */
    @PostMapping("updateWxUser")
    public Object updateWxUser(  @RequestParam(value = "skey",required = true) String skey,
                                 @RequestParam(value = "rawData",required = false) String rawData,
                                 @RequestParam(value = "signature",required = false) String signature,
                                 @RequestParam(value = "encrypteData",required = false) String encrypteData,
                                 @RequestParam(value = "iv",required = false) String iv
                                ) {

        if (StringUtils.isBlank(skey))
            return ApiResponseUtil.fail409();

        return loginService.updateWxUser(rawData,encrypteData,iv,skey);
    }





}

