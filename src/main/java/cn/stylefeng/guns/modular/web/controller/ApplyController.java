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

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.web.entity.Apply;
import cn.stylefeng.guns.modular.web.service.ApplyService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * 报名控制器
 *
 * @author gsk
 * @Date 20190507
 */
@Controller
@RequestMapping("/apply")
public class ApplyController extends BaseController {

    private String PREFIX = "/modular/business/apply/";

    @Autowired
    private ApplyService applyService;

    /**
     * 跳转到列表首页
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "apply.html";
    }


    /**
     * 获取列表数据
     *
     * @author gsk
     * @Date 20190507
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        Long did=ShiroKit.getUser().getDeptId();//部门id
        Integer site=1;
        if (did.equals(1129594787394957314L))//北清总裁在线  1129593519301668865 北清总裁中心
            site=2;
        Page<Map<String, Object>> list = this.applyService.queryList(site);
//        Page<Map<String, Object>> wrap = new NoticeWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(list);
    }

    /**
     * 删除报名信息
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

        Apply old = this.applyService.getById(id);
        old.setStatus(0);
        this.applyService.updateById(old);
        return SUCCESS_TIP;
    }

}
