package com.josepedevs.domain.exceptions;

import java.io.Serial;

public class LongInputException extends MyRuntimeException {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	public LongInputException(String myErrorMessage, String illegalAttributeValue) {
        super(myErrorMessage, illegalAttributeValue);
    }

}
