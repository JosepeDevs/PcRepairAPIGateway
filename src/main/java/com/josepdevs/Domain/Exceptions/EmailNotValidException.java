package com.josepdevs.Domain.Exceptions;

public class EmailNotValidException extends MyRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmailNotValidException(String myErrorMessage, String illegalAttributeValue) {
        super(myErrorMessage, illegalAttributeValue );
    }
	
}
