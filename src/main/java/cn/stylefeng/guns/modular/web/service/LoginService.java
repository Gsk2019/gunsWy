package cn.stylefeng.guns.modular.web.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.HttpClientUtil;
import cn.stylefeng.guns.modular.web.entity.Course;
import cn.stylefeng.guns.modular.web.entity.WxUser;
import cn.stylefeng.guns.modular.web.mapper.CourseMapper;
import cn.stylefeng.guns.modular.web.mapper.WxUserMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录
 * </p>
 * @author
 * @since
 */
@Service
public class LoginService extends ServiceImpl<WxUserMapper, WxUser> {

    @Resource
    private WxUserMapper wxUserMapper;



    public void doLogin(String code){


        //post请求获取的SessionAndopenId
        JSONObject SessionKeyAndOpenIdJson = getSessionKeyOrOpenId( code );
        String openid = SessionKeyAndOpenIdJson.getString("openid" );
        String sessionKey = SessionKeyAndOpenIdJson.getString( "session_key" );

        //通过openId从数据库获取用户
        WxUser wxUser=getByOpenId(openid);

        if (wxUser==null){

        }







    }

    public WxUser getByOpenId(String openid){

        List<WxUser> wxUserList=wxUserMapper.queryByOpenId(openid);
        if (wxUserList!=null&&wxUserList.size()>0)
            return wxUserList.get(0);
        return null;
    }



    /**
     * 通过code获取openid和sessionKey方法
     * @param code
     * @return
     */
    public static JSONObject getSessionKeyOrOpenId(String code){
        //微信端登录code
        String wxCode = code;
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> requestUrlParam = new HashMap<>(  );
        requestUrlParam.put( "appid","wx0ea2d600620c2346" );//小程序appId
        requestUrlParam.put( "secret","" );//你的小程序appSecret
        requestUrlParam.put( "js_code",code );//小程序端返回的code
        requestUrlParam.put( "grant_type","authorization_code" );//默认参数

        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject( HttpClientUtil.doPost( requestUrl,requestUrlParam ));
        return jsonObject;
    }





























}
