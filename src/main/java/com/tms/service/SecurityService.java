package com.tms.service;

import com.tms.model.Security;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class SecurityService {
    
    public final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public Boolean registration(RegistrationRequestDto registrationRequestDto) {
        try {
            if (securityRepository.isLoginUsed(registrationRequestDto.getLogin())){
                return false;
            }
            return  securityRepository.registration(registrationRequestDto);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
