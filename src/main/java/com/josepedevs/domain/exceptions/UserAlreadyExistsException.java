package com.josepedevs.domain.exceptions;

import java.io.Serial;

public class UserAlreadyExistsException  extends MyRuntimeException{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException(String myErrorMessage, String illegalAttributeValue ) {
        super(myErrorMessage, illegalAttributeValue );
    }
	
}