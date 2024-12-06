package com.josepedevs.domain.dto.valueobjects;

import com.josepedevs.domain.exceptions.EmailNotValidException;
import com.josepedevs.domain.exceptions.LongInputException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EmailVo {
	
	private String email;
	
	public EmailVo(String email) {
		
		if ( ! email.matches("^[\\w-_\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
		    // algunos ejemplos aceptados hoola-adios@mundo.com , hola_adioos@mundo.com
			throw new EmailNotValidException("The email format was not adequate, before the @ we allow the use of -_. and - after the @", email);
		}
		
		if( email.length()>=255 ) {
			throw new LongInputException("The email length was too much, if saved, it has been truncated.", email);
		}
		
		this.email = email;
	}
	
}
