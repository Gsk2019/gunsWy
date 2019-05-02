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

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.web.entity.Course;
import cn.stylefeng.guns.modular.web.entity.TimeTable;
import cn.stylefeng.guns.modular.web.service.CourseService;
import cn.stylefeng.guns.modular.web.service.TimeTableService;
import cn.stylefeng.guns.modular.web.wrapper.CourseWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * 课表管理的控制器
 */
@Controller
@RequestMapping("/timeTable")
public class TimeTableController extends BaseController {

    private static String PREFIX = "/modular/business/timeTable/";

    @Autowired
    private TimeTableService timeTableService;

    /**
     * 跳转到课程课表列表
     *
     * @author gsk
     * @Date 20190501
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "timeTable.html";
    }


    /**
     * 跳转到添加课表页面
     * @author gsk
     * @Date 20190501
     */
    @RequestMapping(value = "/timeTable_add")
    public String courseAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 新增课表
     * @author gsk
     * @Date 20190501
     */
    @RequestMapping(value = "/add")
//    @BussinessLog(value = "菜单课程", key = "name", dict = MenuDict.class)
    @ResponseBody
    public ResponseData add(TimeTable timeTable) {
        this.timeTableService.addTimeTable(timeTable);
        return SUCCESS_TIP;
    }

    /**
     * 删除课表
     * @author gsk
     * @Date 20190501
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData remove(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        timeTableService.delTimeTable(id);

        return SUCCESS_TIP;
    }

    /**
     * 查询课表详情
     *
     * @author gsk
     * @Date 20190501
     */
    @RequestMapping("/{id}")
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
        TimeTable timeTable = timeTableService.getById(id);
        return timeTable;
    }

}
