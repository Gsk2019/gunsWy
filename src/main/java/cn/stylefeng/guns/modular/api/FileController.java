/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.core.util.OSSClientUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 * @author gsk
 * @Date 20190428 16:49
 *
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    /**
     * 富文本图片上传
     */
    @RequestMapping("/layedit/upload")
    public Object auth(@Param("file") MultipartFile file) {

        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> map2 = new HashMap<String,Object>();

        try {
           String url= OSSClientUtil.uploadImg2Oss(file,OSSClientUtil.COURSE_LAYEDITIMAGE);
            map.put("code",0);//0表示成功，1失败
            map.put("msg","上传成功");//提示消息
            map2.put("src",url);//图片url
            map2.put("title","");//图片名称，这个会显示在输入框里
            map.put("data",map2);
            return new JSONObject(map).toString();
        }catch (Exception e){
            map.put("code",1);
            map.put("msg","图片上传失败");
            map2.put("src","");
            map2.put("title","");
            map.put("data",map2);
            return  new JSONObject(map).toString();
        }
    }


    /**
     * 图片上传
     */
    @RequestMapping("/upload")
    public Object upload(@Param("type") String type,@Param("file") MultipartFile file) {

        Map<String,Object> map = new HashMap<String,Object>();

        try {
            String dir=OSSClientUtil.COURSE_IMAGE;
            String lastUrl="";
            if("3".equals(type)){
                dir=OSSClientUtil.COURSE_JOINFILE;
                lastUrl="?response-content-type=application%2Foctet-stream";
            }else if("2".equals(type)){
                dir=OSSClientUtil.COURSE_DESCFILE;
                lastUrl="?response-content-type=application%2Foctet-stream";
            }else if("4".equals(type)){
                dir=OSSClientUtil.COURSE_DESCIMAGES;
            }else if("5".equals(type)){
                dir=OSSClientUtil.COURSE_TABLEIMAGES;
            }
            Map<String,String> mapFile= OSSClientUtil.uploadImg2OssMap(file,dir);
            String url=mapFile.get("url");
            url=url.substring(0,url.indexOf("?"))+lastUrl;
            map.put("code",0);//0表示成功，1失败
            map.put("src",url);//图片url
            map.put("name",mapFile.get("name"));//图片名称
            return new JSONObject(map).toString();
        }catch (Exception e){
            map.put("code",1);
            map.put("src","");
            return  new JSONObject(map).toString();
        }
    }
}

