package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.web.entity.Params;
import cn.stylefeng.guns.modular.web.mapper.ParamsMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
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
 * 参数
 * </p>
 * @author
 * @since
 */
@Service
public class ParamsService extends ServiceImpl<ParamsMapper, Params> {

    @Resource
    private ParamsMapper paramsMapper;

    /**
     * 后台分页获取列表
     */
    public List<Map<String,Object>> queryList(Page page) {
        return this.baseMapper.queryList(page);
    }

    /**
     * 添加
     * @author gsk
     * @Date 20190514
     */
    @Transactional
    public void addParams(Params params) {
        params.setCreateTime(new Date());
        params.setUpdateTime(new Date());
        this.save(params);
    }

    /**
     * 修改
     *
     * @author gsk
     * @Date 20190514
     */
    @Transactional(rollbackFor = Exception.class)
    public void editParams(Params params) {

        if (ToolUtil.isOneEmpty(params, params.getId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Params old=paramsMapper.selectById(params.getId());
//        old.setParamName(params.getParamName());
        old.setParamValue(params.getParamValue());
        old.setUpdateTime(new Date());
        this.updateById(old);
    }

    /**
     * 删除
     * @author gsk
     * @Date 20190514
     */
    @Transactional
    public void delParams(Integer id) {

        Params params=paramsMapper.selectById(id);
        params.setStatus(0);
        paramsMapper.updateById(params);
    }

}
