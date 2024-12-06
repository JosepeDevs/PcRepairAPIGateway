package com.josepedevs.domain.dto.valueobjects;

import com.josepedevs.domain.exceptions.EmailNotValidException;
import com.josepedevs.domain.exceptions.LongInputException;
import com.josepedevs.domain.repository.AuthRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UsernameVo {

    private final String username;

    private final AuthRepository authRepository;

    public UsernameVo(String username, AuthRepository authRepository) {
        this.authRepository = authRepository;

        if (authRepository.findByUsername(username).isPresent()) {
            throw new EmailNotValidException("The username was already in use.", username);
        }

        if( username.length()<=255 && username.length()>=3 ) {
            throw new LongInputException("The length was not correct (more than 3 and bellow 255)", username);
        }
        this.username = username;
    }

}
