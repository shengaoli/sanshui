package com.example.snashuitraverl.demos.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class PhotoUtils implements WebMvcConfigurer {

	//获取本地图片地址
	@Value("${bmp.path:{null}}")
    private String bmpPath;
    
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
           /**
             * 访问路径：http://localhost:8086/image/2022001.png
             * "/image/**" 为前端URL访问路径
             * "file:" + bmpPath 是本地磁盘映射
             */
       registry.addResourceHandler("/image/**").addResourceLocations("file:" + bmpPath);
     }
}

