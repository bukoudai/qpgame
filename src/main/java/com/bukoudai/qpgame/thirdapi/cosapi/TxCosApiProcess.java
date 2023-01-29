package com.bukoudai.qpgame.thirdapi.cosapi;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.Serializable;

/**
 *
 */
@Component
@Slf4j
public class TxCosApiProcess {
    private static String secret_id;

    @Value("${tx.secret_id2}")
    private void setSecretId(String secretId) {
        secret_id = secretId;
    }

    private static String secret_key;


    @Value("${tx.secret_key2}")
    private void setSecretKey(String secretKey) {
        secret_key = secretKey;
    }
    private static String COS_REGION;
    @Value("${tx.cos_region}")
    private void setCosRegion(String cosRegion) {
        COS_REGION = cosRegion;
    }
    private static String BUCKET_NAME;
    @Value("${tx.bucket_name}")
    private void setBucketName(String bucketName) {
        BUCKET_NAME = bucketName;
    }
    private static String BUCKETKEY_PREFIX;
    @Value("${tx.bucket_key_prefix}")
    private void setBucketKeyPrefix(String bucketKeyPrefix) {
        BUCKETKEY_PREFIX = bucketKeyPrefix;
    }

    // 上传对象
    public UploadFileResult upload(InputStream inputStream,String fileName) throws CosServiceException, CosClientException {
        // 使用高级接口必须先保证本进程存在一个 TransferManager 实例，如果没有则创建
// 详细代码参见本页：高级接口 -> 创建 TransferManager
        TransferManager transferManager = createTransferManager();

// 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式

// 对象键(Key)是对象在存储桶中的唯一标识。
        String key = BUCKETKEY_PREFIX + fileName;


        ObjectMetadata objectMetadata = new ObjectMetadata();


        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, inputStream, objectMetadata);

// 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
// 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
        putObjectRequest.setStorageClass(StorageClass.Standard);

        UploadFileResult result = new UploadFileResult();



        result.setIsSuccess(Boolean.FALSE);
        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回 UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult  uploadResult = upload.waitForUploadResult();
            //https://qq-bot-1257067547.cos.ap-guangzhou.myqcloud.com/qq-bot-01/4483AD41-5A69-E07F-CD2D-433358DDF29D.gif
            String picUrl = "https://" + uploadResult.getBucketName() + ".cos." +COS_REGION + ".myqcloud.com/" + uploadResult.getKey();
            result.setUrl(picUrl);
            result.setBucketName(uploadResult.getBucketName());
            result.setPicName( uploadResult.getKey());
            result.setBucketKey( uploadResult.getKey());
            result.setDateStr(uploadResult.getDateStr());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件失败",e);
        } finally {
            // 关闭 TransferManger
            transferManager.shutdownNow();
        }

        return result;
    }

    private TransferManager createTransferManager() {
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
         //用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
       //用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
        COSCredentials cred = new BasicCOSCredentials(secret_id, secret_key);
// 2 设置 bucket 的地域
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
        Region region = new Region(COS_REGION); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(region);
// 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return new TransferManager( cosClient );
    }

}
