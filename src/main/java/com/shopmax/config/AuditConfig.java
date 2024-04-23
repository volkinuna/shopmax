package com.shopmax.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration //하나 이상의 Bean을 정의하고 관리하는 클래스임을 나타냄(스프링에서 의존성 관리를 해줌)
@EnableJpaAuditing //JPA의 auditing 기능을 활성화시키는 어노테이션
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
