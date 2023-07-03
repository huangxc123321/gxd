package com.gxa.jbgsw.user.config;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 图形验证码的配置类
 */

@Configuration
public class CaptchaProducerConfig {

    @Bean
    public DefaultKaptcha producer(){
        Properties properties = new Properties();
        properties.put("kaptcha.border","yes");
        properties.put("kaptcha.border.color","105,179,90");
        properties.put("kaptcha.textproducer.font.color","72,118,255");
        properties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.WaterRipple");
        properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
        properties.put("kaptcha.noise.color","72,118,255");
        properties.put("kaptcha.image.width","125");
        properties.put("kaptcha.image.height","50");
        properties.put("kaptcha.textproducer.font.size","40");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.textproducer.char.font.names","Arial, Courier");
        properties.put("kaptcha.textproducer.char.space","4");
        properties.put("kaptcha.textproducer.impl","com.google.code.kaptcha.text.impl.DefaultTextCreator");
        properties.put("kaptcha.session.key","code");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
