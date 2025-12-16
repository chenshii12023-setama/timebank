package com.chengpu.timebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TimeBank 主启动类
 * 
 * @author TimeBank Team
 * @version 1.0.0
 */
@SpringBootApplication
public class TimeBankApplication {

    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(TimeBankApplication.class, args);
    }
}

