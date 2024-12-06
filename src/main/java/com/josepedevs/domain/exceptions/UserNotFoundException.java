package com.josepedevs.domain.exceptions;

import java.io.Serial;

public class UserNotFoundException  extends MyRuntimeException{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String myErrorMessage, String illegalAttributeValue ) {
        super(myErrorMessage, illegalAttributeValue );
    }
	
}