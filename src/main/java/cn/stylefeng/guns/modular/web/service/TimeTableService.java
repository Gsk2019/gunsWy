package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.modular.web.entity.TimeTable;
import cn.stylefeng.guns.modular.web.mapper.TimeTableMapper;
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
 * 课表
 * </p>
 * @author gsk
 * @since 20190501
 */
@Service
public class TimeTableService extends ServiceImpl<TimeTableMapper, TimeTable> {

    @Resource
    private TimeTableMapper timeTableMapper;

    /**
     * 后台分页获取课程列表
     */
    public List<Map<String,Object>> queryList(Page page,  Integer courseId) {
        return  timeTableMapper.queryList(page,courseId);
    }

    /**
     * 小程序按课程分页获取列表
     */
    public List<Map<String,Object>> queryAppListByCourseId(Page page,Integer courseId) {
        return timeTableMapper.queryAppListByCourseId(page,courseId);
    }


    /**
     * 添加课表
     * @author gsk
     * @Date 20190501
     */
    @Transactional
    public void addTimeTable(TimeTable timeTable) {
        timeTable.setCreateTime(new Date());
        this.save(timeTable);
    }

    /**
     * 删除课程
     * @author gsk
     * @Date 20190430
     */
    @Transactional
    public void delTimeTable(Integer id) {

        TimeTable timeTable=timeTableMapper.selectById(id);
        timeTable.setStatus(0);
        timeTableMapper.updateById(timeTable);
    }

}
