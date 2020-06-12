package cn.yuanfeisy.api;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Donghua.Chen on 2018/5/1.
 */
//七牛云的链接需要域名认证，太麻烦了，尝试换成阿里云的oss
@Component
public class QiniuCloudService {

    @Value("${qiniu.accesskey}")
    private String ACCESS_KEY;
    @Value("${qiniu.serectkey}")
    private String SECRET_KEY;
    /**
     * 仓库
     */
    @Value("${qiniu.bucket}")
    private String BUCKET;
    /**
     * 七牛云外网访问地址
     */
//    @Value("${qiniu.cdn.url}")
    public String QINIU_UPLOAD_SITE="";

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    String accessKeyId = "";
    String accessKeySecret = "";


    //    public String upload(MultipartFile file, String fileName) {
//
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone0());
//        //...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
//        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = null;
//        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//        String upToken = auth.uploadToken(BUCKET);
//        try {
//            Response response = null;
//
//            response = uploadManager.put(file.getInputStream(), fileName, upToken, null, null);
//
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//            return putRet.key;
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println(r.toString());
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
public String upload(MultipartFile file, String fileName) {


// 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
  //  InputStream inputStream = new FileInputStream("<yourlocalFile>");
  try {
      ossClient.putObject("yfsql", fileName, file.getInputStream());
      // 关闭OSSClient。

      ossClient.shutdown();

  }catch (QiniuException ex){
      Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;



}
}
