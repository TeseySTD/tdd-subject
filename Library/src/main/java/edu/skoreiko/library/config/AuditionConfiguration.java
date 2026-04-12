package edu.skoreiko.library.config;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class AuditionConfiguration
 * @since 12.04.2026 - 11.51
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@Configuration
public class AuditionConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}