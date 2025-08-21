package com.neighbourly.user.web.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements org.springframework.data.domain.AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes instanceof org.springframework.web.context.request.ServletRequestAttributes servletRequestAttributes) {
            String userId = (String) servletRequestAttributes.getRequest().getHeader("user_id");
            if (userId != null) {
                return Optional.of(userId);
            }
        }


        return Optional.empty();
    }
}
