package com.nttdata.infra.service.brasilapi;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.infra.presentation.dto.ExchangeRateDto;
import java.util.List;


public class BrasilApiExchangeRateResponse {
    @JsonProperty("cotacoes")
    private List<ExchangeRateDto> cotacoes;
    @JsonProperty("moeda")
    private String moeda;
    @JsonProperty("data")
    private String data;
    public BrasilApiExchangeRateResponse() {
    }

    public List<ExchangeRateDto> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<ExchangeRateDto> cotacoes) {
        this.cotacoes = cotacoes;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}