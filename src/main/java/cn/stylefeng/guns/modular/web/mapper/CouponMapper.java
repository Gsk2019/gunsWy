package cn.stylefeng.guns.modular.web.mapper;

import cn.stylefeng.guns.modular.web.entity.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 获取
     */
    List<Coupon> queryList();

    /**
     * 后台获取列表
     */
    Page<Map<String, Object>> queryList(@Param("page") Page page);

}
