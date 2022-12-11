package com.example.newestlinen.service.audit;

import com.example.newestlinen.jwt.JWTUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        String currentUser = JWTUtils.getCurrentUserJWT();
        return Optional.of(currentUser!=null?currentUser:"anonymous");
    }
}
