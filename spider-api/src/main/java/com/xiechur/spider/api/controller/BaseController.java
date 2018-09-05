package com.xiechur.spider.api.controller;

import com.xiechur.spider.util.Base64EncryptUtil;
import com.xiechur.spider.util.EnvUtil;
import com.xiechur.spider.util.JsonUtil;

public abstract class BaseController {
    public static final String ENCRYPT_RESULT_KEY = "1Oq5Jw5Ny4Vl0Ks1Tt5Gr2Ov";

    public String encryptData(Object data, Integer debug) {
        if (data == null) {
            return null;
        }

        // 开发和测试环境允许不加密结果
        if ((EnvUtil.isDev() || EnvUtil.isTest()) && (debug != null && debug == 1)) {
            return JsonUtil.toJson(data);
        }

        return Base64EncryptUtil.encrypt(JsonUtil.toJson(data), ENCRYPT_RESULT_KEY);
    }
}
