package com.zhangkehui.voaserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhangkehui.voaserver.mapper")
public class VoaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoaServerApplication.class, args);
    }
}
