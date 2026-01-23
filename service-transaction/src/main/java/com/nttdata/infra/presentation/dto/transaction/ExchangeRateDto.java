package com.nttdata.infra.presentation.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ExchangeRateDto(
        @JsonProperty("paridade_compra")
        BigDecimal purchaseParity,
        @JsonProperty("paridade_venda")
        BigDecimal saleParity,
        @JsonProperty("cotacao_compra")
        BigDecimal PurchaseQuotation,
        @JsonProperty("cotacao_venda")
        BigDecimal saleQuotation,
        @JsonProperty("data_hora_cotacao")
        String quotationDateTime,
        @JsonProperty("tipo_boletim")
        String reportType

) {
}
