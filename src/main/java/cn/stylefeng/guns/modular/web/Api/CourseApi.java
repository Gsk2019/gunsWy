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

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.ApiResponseUtil;
import cn.stylefeng.guns.modular.web.service.CourseService;
import cn.stylefeng.guns.modular.web.service.TimeTableService;
import cn.stylefeng.guns.modular.web.wrapper.CourseWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程APi
 *
 * @author gsk
 * @Date 20190429 晚
 */
@RestController
@RequestMapping("/api/course/")
public class CourseApi extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TimeTableService timeTableService;


    /**
     * 小程序首页获取数据接口
     */
    @GetMapping("/index")
    public Object index( ) {

        Map<String ,Object> map=new HashMap<>();

        List<Map<String,Object>> list1=courseService.queryListApp(1);
        List<Map<String,Object>> list2=courseService.queryListApp(2);
        List<Map<String,Object>> list3=courseService.queryListApp(3);
        List<Map<String,Object>> list4=courseService.queryListApp(4);
        List<Map<String,Object>> list5=courseService.queryListApp(5);
        map.put("list1",list1);
        map.put("list3",list3);
        map.put("list2",list2);
        map.put("list4",list4);
        map.put("list5",list5);
       return ApiResponseUtil.ok(map);
    }


    /**
     * 按分类分页获取课程列表
     */
    @GetMapping("/getByType")
    public Object getByType( Integer courseType) {

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        //根据条件查询课程
        List<Map<String,Object>>  result = courseService.queryListAppByType(page, courseType);
        page.setRecords(new CourseWrapper(result).wrap());

        return ApiResponseUtil.ok(page);
    }


    /**
     * 按课程分页获取课课表列表
     */
    @GetMapping("/getTimeTableByCourseId")
    public Object getTimeTableByCourseId( Integer courseId) {

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        //根据条件查询课程
        List<Map<String,Object>>  result = timeTableService.queryAppListByCourseId(page, courseId);
        page.setRecords(result);

        return ApiResponseUtil.ok(page);
    }

}

