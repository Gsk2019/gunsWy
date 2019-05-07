package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.entity.Dept;
import cn.stylefeng.guns.modular.web.entity.Course;
import cn.stylefeng.guns.modular.web.mapper.CourseMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程
 * </p>
 * @author
 * @since
 */
@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Resource
    private CourseMapper courseMapper;

    /**
     * 后台分页获取课程列表
     */
    public List<Map<String,Object>> queryList(Page page,  Integer courseType, String courseName) {
        return this.baseMapper.queryList(page,courseType,courseName);
    }

    /**
     * 小程序首页获取分类列表
     */
    public List<Map<String,Object>> queryListApp(Integer courseType) {
        return this.baseMapper.queryListApp(courseType);
    }

    /**
     * 小程序按分类分页获取列表
     */
    public List<Map<String,Object>> queryListAppByType(Page page,Integer courseType) {
        return this.baseMapper.queryListAppByType(page,courseType);
    }

    /**
     * 按分类获取课程列表，不分页
     */
    public List<Map<String,Object>> queryCourseByType(Integer courseType) {
        return this.baseMapper.queryCourseByType(courseType);
    }


    /**
     * 添加课程
     * @author gsk
     * @Date 20190429
     */
    @Transactional
    public void addCourse(Course course) {
        course.setCreateTime(new Date());
        this.save(course);
    }


    /**
     * 修改课程
     *
     * @author gsk
     * @Date 20190502
     */
    @Transactional(rollbackFor = Exception.class)
    public void editCourse(Course course) {

        if (ToolUtil.isOneEmpty(course, course.getId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.updateById(course);
    }

    /**
     * 删除课程
     * @author gsk
     * @Date 20190430
     */
    @Transactional
    public void delCourse(Integer id) {

        Course course=courseMapper.selectById(id);
        course.setStatus(0);
        courseMapper.updateById(course);
    }

}
