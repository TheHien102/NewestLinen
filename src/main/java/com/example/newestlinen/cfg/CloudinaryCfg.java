package com.example.newestlinen.cfg;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CloudinaryCfg {
    @Autowired
    Environment environment;

    @Bean
    Cloudinary cloudinary() {
        // Cloudinary
        Cloudinary cloudinary = new Cloudinary(environment.getProperty("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        System.out.println(cloudinary.config.cloudName);
        return cloudinary;
    }
}
