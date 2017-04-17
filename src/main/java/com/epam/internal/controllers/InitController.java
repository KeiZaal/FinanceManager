package com.epam.internal.controllers;

import com.epam.internal.models.*;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class InitController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private OutcomeService outcomeService;

    @RequestMapping("/init")
    public ModelAndView init() {
        User user = new User("user@email", "password", new UserInfo("name", "lastname"));
        userService.createUser(user);

        Account[] accounts = {
                new Account("Visa", new BigDecimal(29999), null, user),
                new Account("Mastercard", new BigDecimal(3000), null, user),
                new Account("Кошелек", new BigDecimal(5752), null, user)
        };
        for (Account account : accounts) {
            accountService.createAccount(account);
        }

        OutcomeType[] types = {
                new OutcomeType("Еда вне дома", new BigDecimal(3000), user),
                new OutcomeType("Транспорт", new BigDecimal(500), user),
                new OutcomeType("Интернет", new BigDecimal(1700), user),
                new OutcomeType("Развлечения", new BigDecimal(3000), user)
        };
        for (OutcomeType type : types) {
            outcomeTypeService.addOutcomeType(type);
        }

        Outcome[] outcomes = {
                new Outcome(new BigDecimal(2122), LocalDateTime.now(), accounts[0], types[0]),
                new Outcome(new BigDecimal(4542), LocalDateTime.now(), accounts[0], types[1]),
                new Outcome(new BigDecimal(1542), LocalDateTime.now(), accounts[0], types[2]),
        };
        for (Outcome outcome : outcomes) {
            outcomeService.addOutcome(outcome);
        }
        return new ModelAndView("index");
    }
}
