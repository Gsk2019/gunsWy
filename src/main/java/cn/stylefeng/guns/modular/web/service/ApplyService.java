package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.web.entity.Apply;
import cn.stylefeng.guns.modular.web.mapper.ApplyMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 报名处
 * </p>
 * @author
 * @since
 */
@Service
public class ApplyService extends ServiceImpl<ApplyMapper, Apply> {

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
     * 后台获取报名取记录
     * @param
     * @return
     */
    public Page<Map<String, Object>> queryList(Integer site){
        Page page = LayuiPageFactory.defaultPage();

        return  applyMapper.queryList(page,site);
    }
}
