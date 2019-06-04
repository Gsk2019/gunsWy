package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.modular.web.entity.Course;
import cn.stylefeng.guns.modular.web.entity.TimeTable;
import cn.stylefeng.guns.modular.web.mapper.CourseMapper;
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
    @Resource
    private CourseMapper courseMapper;

    /**
     * 后台分页获取课程列表
     */
    public List<Map<String,Object>> queryList(Page page,  Integer courseId) {
        return  timeTableMapper.queryList(page,courseId);
    }

    /**
     * 小程序按课程分页获取课表列表
     */
    public List<Map<String,Object>> queryAppListByCourseId(Page page,Integer courseId) {
        List<Map<String,Object>> listMap= timeTableMapper.queryAppListByCourseId(page,courseId);
//        dealMapList(listMap);
        return listMap;
    }


    /**
     * 添加课表
     * @author gsk
     * @Date 20190501
     */
    @Transactional
    public void addTimeTable(TimeTable timeTable) {
        Course course=courseMapper.selectById(timeTable.getCourseId()) ;
        timeTable.setSite(course.getSite());
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

    public  List<Map<String,Object>> dealMapList(List<Map<String,Object>> mapList){

        for (int i = 0; i < mapList.size(); i++) {
            Map<String, Object> map =  mapList.get(i);
            for (String key : map.keySet()) {
                if("tableImages".equals(key)){
                    String strs=map.get(key).toString();
                    String[] strArr=strs.split(";");
                    String newStr="";
                    for (int j = 0; j < strArr.length; j++) {
                        if (strArr[j].indexOf("http")==-1){
                            newStr+="https://app.gaoduanpeixun.cn"+strArr[j]+";";
                        }else {
                            newStr+=strArr[j]+";";
                        }
                    }
                    if(!"".equals(newStr)){
                        map.put(key,newStr);
                    }
                }
            }
        }
        return mapList;
    }

}
