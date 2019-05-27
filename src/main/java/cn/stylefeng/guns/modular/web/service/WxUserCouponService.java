package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.web.entity.WxUser;
import cn.stylefeng.guns.modular.web.entity.WxUserCoupon;
import cn.stylefeng.guns.modular.web.mapper.WxUserCouponMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户优惠券表
 * </p>
 * @author
 * @since
 */
@Service
public class WxUserCouponService extends ServiceImpl<WxUserCouponMapper, WxUserCoupon> {

    @Resource
    private WxUserCouponMapper wxUserCouponMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 后台获取优惠券领取记录
     * @param
     * @return
      */
    public  Page<Map<String, Object>>  getWxUserCouponList(Integer site){
        Page page = LayuiPageFactory.defaultPage();

        return  this.baseMapper.getWxUserCouponList(page,site);
    }

    /**
     * 领取优惠券
     * @param wxUser
     * @param couponId
     * @return
     */
    public  boolean  getCoupon(WxUser wxUser, Integer couponId,Integer site){

        List<Map> couponList=wxUserCouponMapper.queryByUserIdAndCouponId(wxUser.getId(),couponId);
        if (couponList!=null && couponList.size()>0)
            return false;

        WxUserCoupon wxUserCoupon=new WxUserCoupon();
        wxUserCoupon.setUserId(wxUser.getId());
        wxUserCoupon.setCouponId(couponId);
        wxUserCoupon.setSite(site);
        wxUserCoupon.setCreateTime(new Date());
        wxUserCoupon.setUpdateTime(new Date());
        wxUserCouponMapper.insert(wxUserCoupon);
        return true;
    }

    /**
     * 获取我的优惠券
     * @param wxUser
     * @return
     */
    public  List<Map>  getCouponByUserId(WxUser wxUser){

        List<Map> couponList=wxUserCouponMapper.queryByUserId(wxUser.getId());
        return couponList;
    }

}
