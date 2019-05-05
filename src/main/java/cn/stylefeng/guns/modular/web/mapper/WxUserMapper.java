package cn.stylefeng.guns.modular.web.mapper;

import cn.stylefeng.guns.modular.web.entity.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface WxUserMapper extends BaseMapper<WxUser> {

    /**
     * 获取
     */
    List<WxUser> queryByOpenId(@Param("openId") String openId);


}
