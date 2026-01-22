package com.nttdata.infra.service;

import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.presentation.dto.ExchangeRateDto;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

public class ApplyExchangeRateService {
    private final RestTemplate restTemplate;

    public ApplyExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Transaction apply(Transaction transaction) {
        String currency = transaction.getCurrency();
        String date = LocalDate.now().minusDays(1).toString();
        String url = "https://brasilapi.com.br/api/cambio/v1/cotacao/" + currency + "/" + date;

        try {

            BrasilApiExchangeRateResponse response = restTemplate.getForObject(url, BrasilApiExchangeRateResponse.class);

            if (response != null && response.getCotacoes() != null && !response.getCotacoes().isEmpty()) {
                Optional<ExchangeRateDto> ptaxFechamentoOpt = response.getCotacoes().stream()
                        .filter(cotacao -> "FECHAMENTO PTAX".equals(cotacao.reportType()))
                        .findFirst();

                if (ptaxFechamentoOpt.isPresent()) {
                    ExchangeRateDto ptaxDto = ptaxFechamentoOpt.get();

                    BigDecimal saleQuotation = ptaxDto.saleQuotation(); //"cotacao_venda"
                    BigDecimal saleParity = ptaxDto.saleParity(); //"paridade_venda"
                    BigDecimal purchaseParity = ptaxDto.purchaseParity(); //"paridade_compra"
                    BigDecimal purchaseQuotation = ptaxDto.PurchaseQuotation(); //"cotacao_compra"

                    var value = transaction.getValue();
                    var result = value
                            .multiply(purchaseQuotation);
                    transaction.setConvertedValue(result);
                    transaction.setAppliedExchangeRate(purchaseQuotation);

                } else {
                    System.out.println("Cotação de 'FECHAMENTO PTAX' não encontrada na resposta da API para a moeda " + currency + " na data " + date);
                }
            } else {
                System.out.println("Resposta da API de câmbio vazia ou sem cotações para a moeda " + currency + " na data " + date);
            }

        } catch (RestClientException e) {
            System.err.println("Erro ao chamar a API de câmbio para " + currency + " em " + date + ": " + e.getMessage());
        }

        return transaction;
    }
}