package com.bukoudai.qpgame.thirdapi.cosapi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.io.Serializable;

/**
 *
 */
@Data
public class UploadFileResult implements Serializable {
    private static final long serialVersionUID = 1L;


    private Boolean isSuccess;
    private String url;
    private String bucketName;
    private String picName;
    private String bucketKey;
    private String dateStr;


}
