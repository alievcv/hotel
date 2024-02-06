package realsoft.hotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realsoft.hotel.dto.AccountDTO;
import realsoft.hotel.dto.AccountDTOForAdding;
import realsoft.hotel.dto.LoginDTO;
import realsoft.hotel.service.AccountService;
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")

public class RegistrationController {

    private final AccountService accountService;



    @PostMapping("/addAccount")
    public ResponseEntity<String> addAccount(@RequestBody AccountDTOForAdding accountDTOForAdding){
        return accountService.addAccount(accountDTOForAdding);
    }
}
