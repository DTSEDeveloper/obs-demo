package com.huaweicloud.dtse.service.impl;


import com.huaweicloud.commons.response.Result;

import java.io.InputStream;

public interface IOBSService {

    String uploadOneFile(InputStream inputStream);

    Result deleteOneFile(String deleobjectname);
}
