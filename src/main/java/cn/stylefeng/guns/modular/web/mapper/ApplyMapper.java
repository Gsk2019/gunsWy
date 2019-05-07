package cn.stylefeng.guns.modular.web.mapper;

import cn.stylefeng.guns.modular.web.entity.Apply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApplyMapper extends BaseMapper<Apply> {

    /**
     * 获取报名列表
     */
    Page<Map<String, Object>> queryList(@Param("page") Page page);


}
