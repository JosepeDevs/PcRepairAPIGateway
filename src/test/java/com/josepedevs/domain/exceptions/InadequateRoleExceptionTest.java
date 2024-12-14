package com.josepedevs.domain.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InadequateRoleExceptionTest {

    @Test
    void testException_GivenData_ConstructorWorksCorrectly() {
        final var exception = new InadequateRoleException("attribute", "msg");
        assertNotNull(exception);
    }
}