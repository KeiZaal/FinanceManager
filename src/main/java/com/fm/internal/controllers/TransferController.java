package com.fm.internal.controllers;

import com.fm.internal.dtos.TransferDto;
import com.fm.internal.services.AccountService;
import com.fm.internal.validation.util.ValidErrors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class TransferController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageSource messages;

    @RequestMapping(value = "/transfer/to", method =  RequestMethod.POST)
    @ResponseBody
    public Object submitTransfer(@Valid @RequestBody TransferDto transferDto, BindingResult result) {


        if (!result.hasErrors()) {
            accountService.makeTransferTo(transferDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/transfer/from", method = RequestMethod.POST)
    @ResponseBody
    public Object makeTransfer(@Valid @RequestBody TransferDto transferDto, BindingResult result) {

        if (!result.hasErrors()) {
            accountService.makeTransferFrom(transferDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
