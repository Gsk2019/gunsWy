package cn.stylefeng.guns.modular.web.service;

import cn.hutool.core.date.DateTime;
import cn.stylefeng.guns.core.util.ApiResponseUtil;
import cn.stylefeng.guns.core.util.HttpClientUtil;
import cn.stylefeng.guns.modular.web.entity.WxUser;
import cn.stylefeng.guns.modular.web.mapper.WxUserMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.security.sasl.Provider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

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
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public Object doLogin(String code){

        Map<String,Object> map = new HashMap<String, Object>();
        Integer isFirst=0;

        //post请求获取的SessionAndopenId
        JSONObject SessionKeyAndOpenIdJson = getSessionKeyOrOpenId( code );
        String openid = SessionKeyAndOpenIdJson.getString("openid" );
        String sessionKey = SessionKeyAndOpenIdJson.getString( "session_key" );
        if (openid==null)
            return ApiResponseUtil.fail();
        //uuid生成唯一key
        String skey = UUID.randomUUID().toString();

        //通过openId从数据库获取用户
        WxUser wxUser=getByOpenId(openid);
        if (wxUser==null){//插入一条新数据
            WxUser wu=new WxUser();
            wu.setOpenId(openid);
            wu.setCreateTime(new Date());
            wu.setUpdateTime(new Date());
            wu.setSessionKey(sessionKey);
            wu.setSkey(skey);
            wxUserMapper.insert(wu);
            isFirst=1;
        }
        //根据openid查询skey是否存在
        String skey_redis = (String) redisTemplate.opsForValue().get( openid );
        if(StringUtils.isNotBlank( skey_redis )){
            //存在 删除 skey 重新生成skey 将skey返回
            redisTemplate.delete( skey_redis );
        }
        //  缓存一份新的
        JSONObject sessionObj = new JSONObject(  );
        sessionObj.put( "openId",openid );
        sessionObj.put( "sessionKey",sessionKey );
        redisTemplate.opsForValue().set( skey,sessionObj.toJSONString() );
        redisTemplate.opsForValue().set( openid,skey );

        map.put( "skey",skey );
        map.put( "isFirst",isFirst );
        return ApiResponseUtil.ok(map);
    }


    public Object updateWxUser(String rawData,String encrypteData, String iv,String skey){

        JSONObject rawDataJson = JSON.parseObject( rawData );
        String skey_redis = (String) redisTemplate.opsForValue().get( skey );
        JSONObject OpenIdJson=JSON.parseObject( skey_redis );

        String openId=OpenIdJson.getString("openId");
        WxUser wxUser=getByOpenId(openId);
        if (wxUser==null)
            return ApiResponseUtil.fail();
        String nickName = rawDataJson.getString( "nickName" );
        String avatarUrl = rawDataJson.getString( "avatarUrl" );
        String gender  = rawDataJson.getString( "gender" );
        String city = rawDataJson.getString( "city" );
        String country = rawDataJson.getString( "country" );
        String province = rawDataJson.getString( "province" );

        wxUser.setAddress(country+" "+province+" "+city);
        wxUser.setAvatar(avatarUrl);
        wxUser.setGender(gender);
        wxUser.setNick(nickName);
        wxUser.setUpdateTime(new Date());
        wxUserMapper.updateById(wxUser);
        return ApiResponseUtil.ok();
    }


    public WxUser getByOpenId(String openid){
        List<WxUser> wxUserList=wxUserMapper.queryByOpenId(openid);
        if (wxUserList!=null&&wxUserList.size()>0)
            return wxUserList.get(0);
        return null;
    }

    public WxUser getWxUserBySkey(String skey){
        String skey_redis = (String) redisTemplate.opsForValue().get( skey );
        JSONObject OpenIdJson=JSON.parseObject( skey_redis );

        if (skey_redis==null)
            return null;
        String openId=OpenIdJson.getString("openId");
        WxUser wxUser=getByOpenId(openId);
        return wxUser;
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
        requestUrlParam.put( "secret","d19ca6e65c1f8a7184c0eda4fda8e738" );//你的小程序appSecret
        requestUrlParam.put( "js_code",code );//小程序端返回的code
        requestUrlParam.put( "grant_type","authorization_code" );//默认参数

        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject( HttpClientUtil.doPost( requestUrl,requestUrlParam ));
        return jsonObject;
    }


}
