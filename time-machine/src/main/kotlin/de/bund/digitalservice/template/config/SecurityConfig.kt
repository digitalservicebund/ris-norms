package de.bund.digitalservice.template.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springSecurityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.invoke {
            authorizeExchange {
                authorize("/.well-known/security.txt", permitAll)
                authorize("/actuator/health", permitAll)
                authorize(anyExchange, denyAll)
            }
        }
    }
}
