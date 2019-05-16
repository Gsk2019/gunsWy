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
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.web.entity.Params;
import cn.stylefeng.guns.modular.web.service.ParamsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

/**
 * 参数管理的控制器
 */
@Controller
@RequestMapping("/params")
public class ParamsController extends BaseController {

    private static String PREFIX = "/modular/business/params/";

    @Autowired
    private ParamsService paramsService;

    /**
     * 跳转到后台管理的首页
     *
     * @author gsk
     * @Date 20190514
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "params.html";
    }

    /**
     * 查询课程列表
     *
     * @author gsk
     * @Date 20190514
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list() {

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();

        //根据条件查询课程
       List<Map<String,Object>>  result = paramsService.queryList(page);

        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 跳转到添加页面
     * @author gsk
     * @Date 20190514
     */
    @RequestMapping(value = "/params_add")
    public String courseAdd() {
        return PREFIX + "params_add.html";
    }

    /**
     * 新增
     * @author gsk
     * @Date 20190514
     */
    @RequestMapping(value = "/add")
//    @BussinessLog(value = "菜单课程", key = "name", dict = MenuDict.class)
    @ResponseBody
    public ResponseData add(Params params) {
        this.paramsService.addParams(params);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改页面
     *
     * @author gsk
     * @Date 20190514
     */
//    @Permission
    @RequestMapping("/params_update/{id}")
    public String courseUpdate(@PathVariable Integer id,Model model) {

        if (ToolUtil.isEmpty(id)) {
            throw new RequestEmptyException();
        }
        //缓存部门修改前详细信息
        Params params = paramsService.getById(id);
//        model.addAttribute("course",course);
//        LogObjectHolder.me().set(course);
        model.addAllAttributes(BeanUtil.beanToMap(params));
        LogObjectHolder.me().set(params);
        if (params.getParamType()==2)
            return PREFIX + "params_edit2.html";
        return PREFIX + "params_edit.html";
    }

    /**
     * 修改
     * @author gsk
     * @Date 20190514
     */
//    @BussinessLog(value = "修改课程", key = "simpleName", dict = Course.class)
//    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(Params params) {

        if (ToolUtil.isOneEmpty(params, params.getId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        paramsService.editParams(params);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     * @author gsk
     * @Date 20190514
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData remove(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        paramsService.delParams(id);
        return SUCCESS_TIP;
    }



}
