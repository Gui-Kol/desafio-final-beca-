package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.UpdateTransaction;
import com.nttdata.domain.transaction.Transaction;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateTransactionController {
    private final UpdateTransaction update;

    public UpdateTransactionController(UpdateTransaction update) {
        this.update = update;
    }

    @PutMapping
    public void updateTransaction(@RequestBody Transaction transaction){
        try {
            update.update(transaction);
        }catch (RuntimeException e){
            System.out.println(e.getMessage() + e);
        }
    }
}
