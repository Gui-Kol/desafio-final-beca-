package com.nttdata.infra.service.brasilapi;

import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.presentation.dto.transaction.ExchangeRateDto;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

public class ApplyExchangeRateService {
    private final RestTemplate restTemplate;

    public ApplyExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ExchangeRateDto apply(Transaction transaction) {
        if (!transaction.getCurrency().equals("BRL")) {
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
                        return ptaxFechamentoOpt.get();
                    } else {
                        throw new RuntimeException("'PTAX CLOSING' quote not found in the API response for the currency " + currency + " in date " + date);
                    }
                } else {
                    throw new RuntimeException("Empty exchange API response or no quotes for the currency " + currency + " in date " + date);
                }
            } catch (RestClientException e) {
                throw new RuntimeException("Error calling the exchange API for " + currency + " in " + date + ": " + e.getMessage());
            }
        }

        return null;

    }
}