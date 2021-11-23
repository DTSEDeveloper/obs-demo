package com.huaweicloud.dtse.service;

import com.huaweicloud.commons.config.OBSParams;
import com.huaweicloud.commons.response.Result;
import com.huaweicloud.commons.response.ResultCode;
import com.huaweicloud.dtse.service.impl.IOBSService;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * OBS service接口
 */
@Service
public class OBSService implements IOBSService {

    @Autowired
    OBSParams obsParams;

    /**
     * @param inputStream 上传至OBS数据流参数
     * @return 返回存入OBS请求参数
     */
    @Override
    public String uploadOneFile(InputStream inputStream) {

        //1、从obsParams中获取OBS 访问配置参数
        String endPoint = obsParams.getEndPoint();
        String ak = obsParams.getAk();
        String sk = obsParams.getSk();
        String bucketname = obsParams.getBucketname();
        String objectname = obsParams.getObjectname();

        //2、创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        //3、使用obsClient 的putObject方法，将数据存储至对象存储 OBS
        PutObjectResult putObjectResult = obsClient.putObject(bucketname, objectname, inputStream);

        return putObjectResult.getObjectUrl();
    }

    /**
     * @param deleobjectname 需要删除对象的请求参数
     * @return 返回删除状态
     */
    @Override
    public Result deleteOneFile(String deleobjectname) {

        //1、从obsParams中获取OBS 访问配置参数
        String endPoint = obsParams.getEndPoint();
        String ak = obsParams.getAk();
        String sk = obsParams.getSk();
        String bucketname = obsParams.getBucketname();

        //2、创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        //3、使用obsClient 的deleteObject方法，删除OBS中对象
        try {
            obsClient.deleteObject(bucketname, URLDecoder.decode(deleobjectname, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new Result(ResultCode.SUCCESS);
    }
}
