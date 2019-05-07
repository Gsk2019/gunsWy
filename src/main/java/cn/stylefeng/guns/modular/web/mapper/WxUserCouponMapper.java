package cn.stylefeng.guns.modular.web.mapper;

import cn.stylefeng.guns.modular.web.entity.WxUserCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface WxUserCouponMapper extends BaseMapper<WxUserCoupon> {

    /**
     * 获取优惠券领取记录
     */
    Page<Map<String, Object>> getWxUserCouponList(@Param("page") Page page);

    /**
     * 查询是否领取优惠券
     */
    List<Map> queryByUserIdAndCouponId(@Param("userId") Integer userId, @Param("couponId") Integer couponId);

    /**
     * 查询我的优惠券
     */
    List<Map> queryByUserId(@Param("userId") Integer userId);

}
