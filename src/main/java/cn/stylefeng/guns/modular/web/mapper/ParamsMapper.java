package cn.stylefeng.guns.modular.web.mapper;

import cn.stylefeng.guns.modular.web.entity.Params;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ParamsMapper extends BaseMapper<Params> {

    /**
     * 获取列表
     */
    List<Map<String,Object>> queryListAdmin(@Param("page") Page page,@Param("site") Integer site);

    List<Map<String,Object>> queryListLunBo(@Param("site") Integer site);

    List<Map<String,Object>> queryList(@Param("site") Integer site);


}
