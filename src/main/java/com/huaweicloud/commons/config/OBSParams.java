package com.huaweicloud.commons.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * OBS访问基础参数配置类
 * OBS桶名: bucketname
 * 存储文件名: objectname
 * OBS访问地址: endPoint
 * 华为云AK: ak
 * 华为云SK: sk
 */
@Data
@Getter
@Setter
@Configuration
//@EnableConfigurationProperties(OBSParams.class)
//@ConfigurationProperties("obs.config")
public class OBSParams implements Serializable {
    private static final long serialVersionUID = 594829320797158219L;
    /**
     * OBS桶名
     */
    @Value("${obs.config.bucketname}")
    private String bucketname = null;
    /**
     * 上传至OBS桶的存储对象名
     */
    //@Value("${obs.config.objectname}")
    private String objectname = null;
    /**
     * 连接OBS的endPoint
     */
    @Value("${obs.config.endPoint}")
    private String endPoint = null;
    /**
     * AK
     */
    @Value("${obs.config.ak}")
    private String ak = null;
    /**
     * SK
     */
    @Value("${obs.config.sk}")
    private String sk = null;
}
