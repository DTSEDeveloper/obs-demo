package com.huaweicloud.dtse.controller;

import com.huaweicloud.commons.RequestUtil;
import com.huaweicloud.commons.config.OBSParams;
import com.huaweicloud.commons.pojo.User;
import com.huaweicloud.commons.response.ProfileResult;
import com.huaweicloud.commons.response.Result;
import com.huaweicloud.commons.response.ResultCode;
import com.huaweicloud.dtse.service.OBSService;
import com.huaweicloud.dtse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 操作OBS接口OBSController类，如上传一个文件 upLoadOne()
 */
@CrossOrigin
@RestController
@Log4j2

public class OBSController {

    @Autowired
    RequestUtil requestUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private OBSService obsService;
    @Autowired
    private OBSParams obsParams;

    /**
     * @param multipartFile 上传文件接收参数
     * @param request       HttpServletRequest
     * @return 返回用户信息
     */

    @PostMapping("/upload")
    public Result upLoadOne(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {

        String filename = null;
        String dt = null;
        String originalFilename = null;

        //1、获取用户请求ID
        String id = null;
        try {
            id = requestUtil.getUserId(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2、获取请求用户对象
        User user = userService.getById(id);

        if (multipartFile.isEmpty()) {
            log.info("error", "空文件");
            return new Result(ResultCode.FAIL);
        }

        //4、获取上传文件名，拼接成带有时间戳的文件名
        originalFilename = multipartFile.getOriginalFilename();
        dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (originalFilename.contains(".")) {
            String[] tempFileName = originalFilename.split("[.]");
            filename = tempFileName[0].concat(dt).concat(".").concat(tempFileName[1]);

        } else {
            filename = originalFilename.concat(dt);
        }

        //5、从数据库中查询user icon地址
        String iconURL = user.getIconURL();
        String deleobjectname = null;
        if (iconURL != null) {
            deleobjectname = iconURL.substring(iconURL.indexOf("443/") + 4);
        }

        //6、设置obsParams 存储对象名参数
        obsParams.setObjectname("dtsetraining" + "/" + filename);
        String objURL = null;
        try {
            objURL = obsService.uploadOneFile(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setIconURL(objURL);
        boolean isSuccess = userService.update(user);

        //7、删除历史版本个人icon
        if (isSuccess && iconURL != null) {
            Result result = obsService.deleteOneFile(deleobjectname);
            if (result.isSuccess()) {
                log.info("文件" + deleobjectname + "删除成功");
            } else {
                log.info("文件" + deleobjectname + "删除失败");
            }
        }
        //8、返回icon更新结果
        return new Result(ResultCode.SUCCESS, new ProfileResult(user));
    }

}
