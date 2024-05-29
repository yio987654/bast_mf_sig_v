package ${packageName};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @author ${author}
* @version 1.0
* @description
* @date ${createTime}
*/
@SpringBootApplication
@EnableDiscoveryClient  //nacos注册
@EnableFeignClients
@ComponentScan(basePackages = {"com.lanf"})
@MapperScan(basePackages = "${packageName}.mapper")
public class ${mainName}Application {
public static void main(String[] args) {
    SpringApplication.run(FrontApplication.class, args);
}
}
