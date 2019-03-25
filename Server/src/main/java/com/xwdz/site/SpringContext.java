package com.xwdz.site;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019-03-25
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext sApplicationContext = null;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(sApplicationContext == null){
            sApplicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return sApplicationContext;
    }
}
