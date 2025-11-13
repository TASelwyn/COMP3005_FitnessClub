package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AccountService accService;

    public AdminController(AccountService accService) {
        this.accService = accService;
    }
}
