package com.spstudio.configuration;

import com.spstudio.service.SightPointService;
import com.spstudio.service.SightPointServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sp on 15/01/2017.
 */
@Configuration
public class SightConfig {

    @Bean
    public SightPointService sightPointService(){
        return new SightPointServiceImpl();
    }
}
