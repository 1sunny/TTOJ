package one.sunny.problem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("one.sunny.problem.mapper")
@ComponentScan(basePackages = {"one.sunny"})
public class ProblemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProblemApplication.class,args);
    }
}
