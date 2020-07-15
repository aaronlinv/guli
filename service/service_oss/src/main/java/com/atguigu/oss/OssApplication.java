package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
// 解决报错
//  Failed to determine a suitable driver class
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

@ComponentScan(basePackages = {"com.atguigu"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
    
}
