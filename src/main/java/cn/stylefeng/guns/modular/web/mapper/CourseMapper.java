package cn.stylefeng.guns.modular.web.mapper;

import cn.stylefeng.guns.modular.web.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;


public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 获取课程列表
     */
    List<Map<String,Object>> queryList(@Param("page") Page page, @Param("courseType") Integer courseType, @Param("courseName") String courseName);

    List<Map<String,Object>> queryListApp(@Param("courseType") Integer courseType);

    List<Map<String,Object>> queryListAppByType(@Param("page") Page page,@Param("courseType") Integer courseType);


}
