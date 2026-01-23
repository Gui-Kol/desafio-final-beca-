package com.nttdata.infra.service.brasilapi;

import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.presentation.dto.transaction.ExchangeRateDto;

import java.math.BigDecimal;

public class ExchangeRatePurchase {
    private final ApplyExchangeRateService applyExchangeRateService;

    public ExchangeRatePurchase(ApplyExchangeRateService applyExchangeRateService) {
        this.applyExchangeRateService = applyExchangeRateService;
    }

    public Transaction purchase(Transaction transaction){
        if (!transaction.getCurrency().equals("BRL")) {
            ExchangeRateDto ptaxDto = applyExchangeRateService.apply(transaction);

            BigDecimal purchaseQuotation = ptaxDto.PurchaseQuotation(); //"cotacao_compra"

            var value = transaction.getValue();
            var result = value
                    .multiply(purchaseQuotation);
            transaction.setConvertedValue(result);
            transaction.setAppliedExchangeRate(purchaseQuotation);
        }else {
            transaction.setConvertedValue(transaction.getValue());
            transaction.setAppliedExchangeRate(BigDecimal.valueOf(1));
        }
        return transaction;
    }
}
