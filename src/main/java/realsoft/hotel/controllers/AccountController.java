package realsoft.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import realsoft.hotel.dto.AccountDto;
import realsoft.hotel.service.AccountService;

import java.util.ArrayList;
import java.util.List;
@RestController
public class AccountController {



    @GetMapping("/show")
    public String show(){
        return "im controlleeeeer";
    }
}
