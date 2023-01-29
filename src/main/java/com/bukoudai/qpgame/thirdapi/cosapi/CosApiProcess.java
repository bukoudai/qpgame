package com.bukoudai.qpgame.thirdapi.cosapi;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UploadResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.Serializable;

/**
 *
 */
@Service
public class CosApiProcess {
    @Resource
    private TxCosApiProcess txCosApiProcess;

    public UploadFileResult uploadFile(InputStream inputStream, String fileName) {
        return txCosApiProcess.upload(inputStream, fileName);
    }

}
