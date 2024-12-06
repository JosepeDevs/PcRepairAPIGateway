package com.josepedevs.domain.exceptions;

import java.io.Serial;

public class InadequateRoleException  extends MyRuntimeException{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	public InadequateRoleException(String myErrorMessage, String illegalAttributeValue ) {
        super(myErrorMessage, illegalAttributeValue );
    }
	
}
