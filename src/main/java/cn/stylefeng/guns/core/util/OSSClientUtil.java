package cn.stylefeng.guns.core.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 阿里云 OSS工具类
 *
 * @author Monkey
 * @date 2017年9月30日下午3:38:09
 * @version 1.0
 */

public class OSSClientUtil {

    public static final Logger logger = LoggerFactory.getLogger(OSSClientUtil.class);
    // endpoint
    private static String endpoint;
    // accessKey
    private static String accessKeyId = "LTAIi00tqBTByKxk";
    private static String accessKeySecret = "I5qdmNS5ofIgRLbkXDQSNE9yPeHiH9";
    // 空间
    private static String bucketName = "wyqinghua";
    // 文件存储目录
    public static String COURSE_IMAGE = "course/image/";//课程图片
    public static String COURSE_DESCFILE = "course/descFile/";//课程简章
    public static String COURSE_JOINFILE = "course/joinFile/";//报名表
    public static String COURSE_LAYEDITIMAGE = "course/layeditImage/";//富文本图片
    public static String COURSE_DESCIMAGES = "course/descImages/";//小程序详情图片
    public static String COURSE_TABLEIMAGES = "course/tableImages/";//小程序课表详情图片

    private static OSSClient ossClient;

   static  {
        endpoint = "http://oss-cn-qingdao.aliyuncs.com";
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     * @throws Exception
     */
    public static void uploadImg2Oss(String url,String filedir) throws Exception {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            uploadFile2OSS(fin, split[split.length - 1],filedir);
        } catch (FileNotFoundException e) {
            throw new Exception("图片上传失败");
        }
    }

    /**
     *
     * @param file
     * @param filedir 固定两个:OSSClientUtil.NEWSDIR //存储新闻图片  OSSClientUtil.LICENCEDIR//存储认证信息图片
     * @return
     * @throws Exception
     */
    public static Map<String,String> uploadImg2OssMap(MultipartFile file, String filedir) throws Exception {

        Map<String,String> map=new HashMap<>();
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new Exception("上传文件大小不能超过10M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;//originalFilename.substring(0,originalFilename.indexOf("."))+"_"+
        try {
            InputStream inputStream = file.getInputStream();
            uploadFile2OSS(inputStream, name,filedir);
            String url=getImgUrl(name,filedir);
            url = URLDecoder.decode(url, "utf-8");
            map.put("url",url) ;
            map.put("name",name) ;
            inputStream.close();

            return  map;
//            return url.replace("rmw-image.oss-cn-zhangjiakou.aliyuncs.com","img.rumaiwang.com");
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }

    /**
     *
     * @param file
     * @param filedir 固定两个:OSSClientUtil.NEWSDIR //存储新闻图片  OSSClientUtil.LICENCEDIR//存储认证信息图片
     * @return
     * @throws Exception
     */
    public static String uploadImg2Oss(MultipartFile file, String filedir) throws Exception {

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new Exception("上传文件大小不能超过10M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = originalFilename.substring(0,originalFilename.indexOf("."))+"_"+random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            uploadFile2OSS(inputStream, name,filedir);
            String url=getImgUrl(name,filedir);
            return  url;
//            return url.replace("rmw-image.oss-cn-zhangjiakou.aliyuncs.com","img.rumaiwang.com");
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }

    /**
     *
     * @param file
     * @param filedir 固定两个:OSSClientUtil.NEWSDIR //存储新闻图片  OSSClientUtil.LICENCEDIR//存储认证信息图片
     * @return
     * @throws Exception
     */
    public static String uploadImg2Oss(File file,String filedir) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        if (file.length() > 10 * 1024 * 1024) {
            throw new Exception("上传图片大小不能超过10M！");
        }
        String originalFilename = file.getName();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
//            InputStream inputStream = fileInputStream.getInputStream();
            uploadFile2OSS(fileInputStream, name,filedir);
            String url=getImgUrl(name,filedir);
//            return url.replace("rmw-image.oss-cn-zhangjiakou.aliyuncs.com","img.rumaiwang.com");
            return  url;
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }finally {
            file.delete();
        }
    }

    /**
     *
     * @param filedir 固定两个:OSSClientUtil.NEWSDIR //存储新闻图片  OSSClientUtil.LICENCEDIR//存储认证信息图片
     * @return
     * @throws Exception
     */
    public static String uploadImg2Oss(InputStream inputStream, long maxSize, String originalFilename, String filedir) throws Exception {
        if (maxSize > 10 * 1024 * 1024) {
            throw new Exception("上传图片大小不能超过10M！");
        }

        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            uploadFile2OSS(inputStream, name,filedir);
            String url=getImgUrl(name,filedir);
            return url.replace("rmw-image.oss-cn-zhangjiakou.aliyuncs.com","img.rumaiwang.com");
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }


    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    private static String getImgUrl(String fileUrl,String filedir) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return getUrl(filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream
     *            文件流
     * @param fileName
     *            文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    private static String uploadFile2OSS(InputStream instream, String fileName,String filedir) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            instream.close();

            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     *            文件后缀
     * @return String
     */
    private static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    private static String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        //初始化OSSClient
        //上传文件
//        String files="D:\\1.jpg";
//        uploadImg2Oss(files,OSSClientUtil.LICENCEDIR);
    }


}