package dev.anilerc.securitywithredis.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class RedisSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public RedisSecurityInitializer() {
        super(SecurityConfiguration.class, RedisSessionConfig.class);
    }

}
