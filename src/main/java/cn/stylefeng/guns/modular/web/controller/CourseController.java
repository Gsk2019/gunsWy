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

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.dictmap.DeptDict;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.entity.Dept;
import cn.stylefeng.guns.modular.web.entity.Course;
import cn.stylefeng.guns.modular.web.entity.TimeTable;
import cn.stylefeng.guns.modular.web.service.CourseService;
import cn.stylefeng.guns.modular.web.service.TimeTableService;
import cn.stylefeng.guns.modular.web.wrapper.CourseWrapper;
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
 * 课程管理的控制器
 */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {

    private static String PREFIX = "/modular/business/course/";

    @Autowired
    private CourseService courseService;
    @Autowired
    private TimeTableService timeTableService;

    /**
     * 跳转到课程管理的首页
     *
     * @author gsk
     * @Date 20190425
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "course.html";
    }

    /**
     * 查询课程列表
     *
     * @author gsk
     * @Date 20190425
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(Course course) {

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();

        //根据条件查询课程
       List<Map<String,Object>>  result = courseService.queryList(page, course.getCourseType(),course.getCourseName());

        page.setRecords(new CourseWrapper(result).wrap());
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 跳转到添加课程页面
     * @author fengshuonan
     * @Date 2018/12/23 5:53 PM
     */
    @RequestMapping(value = "/course_add")
    public String courseAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 新增课程
     * @author gsk
     * @Date 20190429
     */
    @RequestMapping(value = "/add")
//    @BussinessLog(value = "菜单课程", key = "name", dict = MenuDict.class)
    @ResponseBody
    public ResponseData add(Course course) {
        this.courseService.addCourse(course);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改课程
     *
     * @author gsk
     * @Date 20190502
     */
//    @Permission
    @RequestMapping("/course_update")
    public String courseUpdate(@RequestParam("id") Integer id,Model model) {

        if (ToolUtil.isEmpty(id)) {
            throw new RequestEmptyException();
        }

        //缓存部门修改前详细信息
        Course course = courseService.getById(id);
        model.addAttribute("course",course);
//        LogObjectHolder.me().set(course);
        return PREFIX + "course_edit.html";
    }

    /**
     * 修改课程
     *
     * @author gsk
     * @Date 20190502
     */
//    @BussinessLog(value = "修改课程", key = "simpleName", dict = Course.class)
    @RequestMapping(value = "/update")
//    @Permission
    @ResponseBody
    public ResponseData update(Course course) {
        courseService.editCourse(course);
        return SUCCESS_TIP;
    }

    /**
     * 删除课程
     * @author gsk
     * @Date 20190430
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData remove(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        courseService.delCourse(id);

        return SUCCESS_TIP;
    }


    /**
     * 查询课程详情
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:34 PM
     */
    @RequestMapping("/{id}")
    @ResponseBody
    public Object detail(@PathVariable Long id) {
        Course course = courseService.getById(id);
        return course;
    }


    /***************************************************************课表*************************************************************************/

    /**
     * 跳转到课程课表列表
     *
     * @author gsk
     * @Date 20190501
     */
    @RequestMapping("toTimeTable")
    public String toTimeTable(Integer courseId,Model model) {
        model.addAttribute("courseId",courseId);
        return PREFIX + "timeTable.html";
    }

    /**
     * 查询课表列表
     *
     * @author gsk
     * @Date 20190501
     */
    @RequestMapping("/timeTableList")
    @ResponseBody
    public Object list(Integer courseId) {

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        //根据条件查询课表
        List<Map<String,Object>>  result = timeTableService.queryList(page, courseId);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 跳转到添加课表页面
     * @author gsk
     * @Date 20190502 PM
     */
    @RequestMapping(value = "/timeTable_add")
    public String TimeTableAdd(Integer courseId,Model model) {

        model.addAttribute("courseId",courseId);
        return PREFIX + "timeTable_add.html";
    }

    /**
     * 新增课表
     * @author gsk
     * @Date 20190502
     */
    @RequestMapping(value = "/addTimeTable")
//    @BussinessLog(value = "菜单课程", key = "name", dict = MenuDict.class)
    @ResponseBody
    public ResponseData addTimeTable(TimeTable timeTable) {
        this.timeTableService.addTimeTable(timeTable);
        return SUCCESS_TIP;
    }

    /**
     * 删除课表
     * @author gsk
     * @Date 20190502
     */
    @RequestMapping(value = "/removeTimeTable")
    @ResponseBody
    public ResponseData removeTimeTable(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        timeTableService.delTimeTable(id);

        return SUCCESS_TIP;
    }


}
