package com.nttdata.infra.controller;

import com.nttdata.application.usecase.Pay;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final Pay pay;

    public TransactionController(Pay pay) {
        this.pay = pay;
    }

    @PostMapping("/{sourceAccountId}")
    public ResponseEntity pay(@PathVariable Long sourceAccountId){
           pay.transaction(sourceAccountId);
           return ResponseEntity.ok("Teste");
    }
}
