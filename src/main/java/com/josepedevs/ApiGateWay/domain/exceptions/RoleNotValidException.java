package com.josepedevs.apigateway.domain.exceptions;

public class RoleNotValidException extends MyRuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	//pasamos el mensaje a excepción padre y logeamos el error
	public RoleNotValidException(String myErrorMessage, String illegalAttributeName ) {
        super(myErrorMessage, RoleNotValidException.class.getName() );
    }
	
}
