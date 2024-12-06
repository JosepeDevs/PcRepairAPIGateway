package com.josepedevs.domain.dto.valueobjects;

import com.josepedevs.domain.exceptions.PasswordNotValidException;
import lombok.Builder;
import lombok.Data;

import java.security.SecureRandom;
import java.util.Random;

@Data
@Builder(toBuilder = true)
public class PsswrdVo {
		
    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
	
    public static byte[] getNextSalt() {
        byte[] saltArray = new byte[16];
        RANDOM.nextBytes(saltArray);
        return saltArray;
    }
    
	private String psswrd;
	
    public PsswrdVo(String psswrd) {
        if (psswrd.length() <= 5) {
            throw new PasswordNotValidException("Password should have more than 5 characters", psswrd);
        }
        if (!psswrd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,}$")) {
            throw new PasswordNotValidException("Password should have at least one number, one lowercase letter, one uppercase letter, one special character and no spaces", psswrd);
        }
        this.psswrd = psswrd;
    }
   

}
