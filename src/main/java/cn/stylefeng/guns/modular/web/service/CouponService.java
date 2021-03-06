package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.web.entity.Apply;
import cn.stylefeng.guns.modular.web.entity.Coupon;
import cn.stylefeng.guns.modular.web.mapper.ApplyMapper;
import cn.stylefeng.guns.modular.web.mapper.CouponMapper;
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
 * 优惠券
 * </p>
 * @author
 * @since
 */
@Service
public class CouponService extends ServiceImpl<CouponMapper, Coupon> {

    @Resource
    private ApplyMapper applyMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void  doApply(Apply apply){

        apply.setCreateTime(new Date());
        apply.setUpdateTime(new Date());
        applyMapper.insert(apply);
    }

    /**
     * 后台获取列表
     *
     * @author gsk
     * @Date 20190507
     */
    public Page<Map<String, Object>> getList(Integer site) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.queryList(page,site);
    }

    /**
     * 后台获取列表
     *
     * @author gsk
     * @Date 20190507
     */
    public List<Map<String, Object>> getListApp(Integer site) {
        return this.baseMapper.queryList(site);
    }


}
