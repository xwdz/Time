package com.xwdz.site;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xwdz.site.mapper")
public class XwdzApplication {

	public static void main(String[] args) {
		SpringApplication.run(XwdzApplication.class, args);
	}

}
