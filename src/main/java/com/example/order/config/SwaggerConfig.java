package com.example.order.config;

import com.example.order.exception.ExceptionResponse;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json;charset=UTF-8"));

    @Bean
    public Docket api(TypeResolver typeResolver) {
        // Docket: Swagger 설정의 핵심이 되는 Bean
        return new Docket(DocumentationType.SWAGGER_2)
                // 예외처리 클래스 추가 등록
                .additionalModels(
                        typeResolver.resolve(ExceptionResponse.class)
                )
                // useDefaultResponseMessage: Swagger 에서 제공해주는 기본 응답 코드(200, 401, 403, 404), false 로 설정하면 기본 응답 코드를 노출하지 않음
                .useDefaultResponseMessages(false)
                // apiInfo: Swagger UI 로 노출할 정보
                .apiInfo(apiInfo())
                // 응답 객체 반환 타입 설정
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                // HTTP 요청 시 타입 지정
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                // apis: api 스펙이 작성되어 있는 패키지를 지정
                .apis(RequestHandlerSelectors.basePackage("com.example.order.controller"))
                // paths: apis 에 있는 API 중 특정 path 를 선택
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 서비스명
                .title("Member API")
                // 서비스 설명
                .description("회원정보를 관리하는 REST API 서비스")
                // 서비스 버전
                .version("1.0")
                // 연락처
                .contact(new Contact("ABC Digital Expert", "https://www.abc.com", "abc@gmail.com"))
                // 라이선스
                .license("ABC 라이선스")
                // 라이선스 URL
                .licenseUrl("https://www.abclicense.com")
                .build();
    }
}
