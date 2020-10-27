package com.lloyvet.gulimall.thirdparty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallThirdPartyApplicationTests {

    @Autowired
    OSSClient ossClient;

    @Test
    public void testUpload() throws FileNotFoundException {
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//        String accessKeyId = "LTAI4G74qL8tpdiNxDy3CaNU";
//        String accessKeySecret = "HDSHt9lU0giPOOQoeNW4A51gpbtbBZ";
//
//        //创建OssClient实例
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Shinelon\\Desktop\\项目资料\\u=1256872220,4145198653&fm=26&gp=0.jpg");

        ossClient.putObject("gulimall-lloyvet","bug.jpg",inputStream);
//        ossClient.shutdown();
        System.out.println("上传完成");

    }
}
