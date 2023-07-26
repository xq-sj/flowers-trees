package com.sj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 闪光灯
 */
@SpringBootApplication
@MapperScan("com.sj.mapper")
public class FlowersTreesEntranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowersTreesEntranceApplication.class, args);
    }

}
