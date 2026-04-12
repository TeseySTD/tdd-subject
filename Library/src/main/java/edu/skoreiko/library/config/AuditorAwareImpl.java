package edu.skoreiko.library.config;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class AuditorAwareImpl
 * @since 12.04.2026 - 11.51
 */

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(System.getProperty("user.name"));
    }
}