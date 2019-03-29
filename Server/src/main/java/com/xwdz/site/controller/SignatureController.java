package com.xwdz.site.controller;

import com.xwdz.site.SpringContext;
import com.xwdz.site.mapper.ApiChannelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019-03-25
 */
@RestController
public class SignatureController {

    private static SignatureController signatureController;

    public synchronized static SignatureController getImpl() {
        if (signatureController == null) {
            signatureController = new SignatureController();
        }
        return signatureController;
    }


    private ApplicationContext applicationContext = SpringContext.getApplicationContext();
    /* 手动实现Mapper类 */
    private ApiChannelMapper mApiChannelMapper = applicationContext.getBean(ApiChannelMapper.class);


    public boolean checkSignatureFail(String key) {
        return mApiChannelMapper.findByKey(key) == null;
    }
}
