package com.github.tianjing.baidu.map.common.config.annotation;


import com.github.tianjing.baidu.map.common.config.TgtoolsBaiduMapPropertyConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * @author
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@ImportAutoConfiguration({
        TgtoolsBaiduMapPropertyConfig.class})
public @interface EnableTgtoolsBaiduMapProperty {
}
