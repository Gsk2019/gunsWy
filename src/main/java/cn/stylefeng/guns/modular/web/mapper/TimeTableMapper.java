package cn.stylefeng.guns.modular.web.mapper;

import cn.stylefeng.guns.modular.web.entity.TimeTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface TimeTableMapper extends BaseMapper<TimeTable> {

    /**
     * 获取课程列表
     */
    List<Map<String,Object>> queryList(@Param("page") Page page, @Param("courseId") Integer courseId);

    List<Map<String,Object>> queryAppListByCourseId(@Param("page") Page page, @Param("courseId") Integer courseId);


}
