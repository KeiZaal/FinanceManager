package com.fm.internal.controllers;

import com.fm.internal.dtos.*;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.OutcomeService;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import com.fm.internal.validation.AccountValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AccountController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @Autowired
    private AccountValidator accountValidator;

    @RequestMapping(value = "/add-account", method = RequestMethod.GET)
    public ModelAndView getList() {
        return new ModelAndView("account", "accountDto", new AccountDto());
    }

    @RequestMapping(value = "/add-account", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("accountDto") AccountDto accountDto, BindingResult result) {
        accountValidator.validate(accountDto, result);
        User loggedUser = userService.getLoggedUser();
        if (!result.hasErrors() && loggedUser != null) {
            accountService.createAccount(accountDto, loggedUser);
            LOGGER.info("New account was create:" + accountDto.getName());
            return new ModelAndView("redirect:" + "/index");
        }
        return new ModelAndView("account");
    }

    @RequestMapping(value = "/account/page", method = RequestMethod.GET)
    public ModelAndView getAccountPage(@RequestParam("name") String nameOfAccount) {
        ModelAndView modelAndView = setDefaultMavForAccountByName(nameOfAccount);
        modelAndView.setViewName("accountpage");
        return modelAndView;
    }

    @RequestMapping(value = "/account/page", method = RequestMethod.POST)
    public ModelAndView setPieChart(@ModelAttribute("rangeDto") RangeDto rangeDto, BindingResult result) {
        Account accountByName = accountService.findUserAccountByName(userService.getLoggedUser(), rangeDto.getAccountName());
        List<Outcome> outcomes = outcomeService.findOutcomesInAccountByDate(accountByName,
                LocalDate.parse(rangeDto.getStart()), LocalDate.parse(rangeDto.getEnd()));
        Map<String, Double> outcomeSum = countTypeAmount(outcomes);
        ModelAndView modelAndView = setDefaultMavForAccountByName(rangeDto.getAccountName());
        modelAndView.addObject("outcomes", outcomeSum);
        modelAndView.setViewName("accountpage");
        return modelAndView;
    }

    private ModelAndView setDefaultMavForAccountByName(String accountName) {
        ModelAndView modelAndView = new ModelAndView();
        Account account = getAccountByName(accountName);
        RangeDto rangeDto = new RangeDto();
        rangeDto.setAccountName(accountName);
        TransferDto transferDto = new TransferDto();
        transferDto.setAccountId(account.getId());
        modelAndView.addObject("rangeDto", rangeDto);
        modelAndView.addObject("account", account);
        modelAndView.addObject("incomeDto", new IncomeDto());
        modelAndView.addObject("outcomeDto", new OutcomeDto());
        modelAndView.addObject("transferDto", transferDto);
        modelAndView.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(userService.getLoggedUser()));
        modelAndView.addObject("accounts",
                accountService.findAllUserAccounts(accountService.findAccountById(transferDto.getAccountId()).getUser()));
        return modelAndView;
    }

    private Account getAccountByName(String accountName) {
        return accountService.findUserAccountByName(userService.getLoggedUser(), accountName);
    }

    private Map<String, Double> countTypeAmount(List<Outcome> outcomes) {
        Map<String, Double> outcomeSum = new HashMap<>();
        for (Outcome outcome : outcomes) {
            if (!outcomeSum.containsKey(outcome.getOutcomeType().getName())) {
                outcomeSum.put(outcome.getOutcomeType().getName(), outcome.getAmount().doubleValue());
            } else {
                Double oldValue = outcomeSum.get(outcome.getOutcomeType().getName());
                outcomeSum.replace(outcome.getOutcomeType().getName(), oldValue + outcome.getAmount().doubleValue());
            }
        }
        return outcomeSum;
    }
}