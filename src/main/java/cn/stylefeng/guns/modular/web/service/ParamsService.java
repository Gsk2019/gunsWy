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
    public List<Map<String,Object>> queryList(Page page,Integer site) {
        return this.baseMapper.queryListAdmin(page,site);
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


    /**
     * 前台获取轮播图列表
     */
    public List<Map<String,Object>> queryListLunBo(Integer site ) {
        List<Map<String,Object>> listMap=this.baseMapper.queryListLunBo(site);
//        补全图片地址
//        for (int i = 0; i < listMap.size(); i++) {
//            Map<String, Object> map =  listMap.get(i);
//            //遍历map中的键
//            for (String key : map.keySet()) {
//                if (map.get(key).toString().indexOf("http")==-1){
//                    map.put(key,"https://app.gaoduanpeixun.cn"+map.get(key));
//                }
//            }
//        }
        return listMap;
    }

    /**
     * 前台获取列表
     */
    public List<Map<String,Object>> queryList(Integer site) {

        List<Map<String,Object>> MapList=this.baseMapper.queryList(site);
//        dealMapList(MapList);
        return MapList;
    }


    public  List<Map<String,Object>> dealMapList(List<Map<String,Object>> mapList){

        for (int i = 0; i < mapList.size(); i++) {
            Map<String, Object> map =  mapList.get(i);
            for (String key : map.keySet()) {
                if("paramValue".equals(key)){
                    if(map.get(key).toString().indexOf("http")==-1){
                        map.put(key,"https://app.gaoduanpeixun.cn"+map.get(key));
                    }
                }
            }
        }
        return mapList;
    }
}
