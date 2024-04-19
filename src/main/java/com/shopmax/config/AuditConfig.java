package com.shopmax.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //JPA의 auditing 기능을 활성화시키는 어노테이션
public class AuditConfig {

    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
