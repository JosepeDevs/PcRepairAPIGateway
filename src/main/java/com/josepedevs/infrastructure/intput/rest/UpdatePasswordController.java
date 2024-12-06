package com.josepedevs.infrastructure.intput.rest;

import com.josepedevs.application.PatchPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdatePasswordController {
    private final PatchPassword patchPasswordUseCase;

    @PatchMapping("/newpassword")
    public ResponseEntity<Boolean> newpassword (@RequestHeader("Authorization") String jwtToken, @RequestParam String  newpsswrd){
        //"Bearer " are 7 digits, with this we get in a string the token value and replace white spaces, just in case
        jwtToken = jwtToken.substring(7).replace (" ","");

        boolean psswrdChanged = patchPasswordUseCase.patchPassword(jwtToken, newpsswrd);
        if(	psswrdChanged ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
    }
}
