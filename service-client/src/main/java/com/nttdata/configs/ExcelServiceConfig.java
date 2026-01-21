package com.nttdata.configs;

import com.nttdata.infra.excel.usecases.GetCellValueAsLocalDate;
import com.nttdata.infra.excel.usecases.GetCellValueAsString;
import com.nttdata.infra.excel.usecases.IsRowEmpty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelServiceConfig {

    @Bean
    public GetCellValueAsString getCellValueAsString(){
        return new GetCellValueAsString();
    }
    @Bean
    public GetCellValueAsLocalDate getCellValueAsLocalDate(GetCellValueAsString getCellValueAsString){
        return new GetCellValueAsLocalDate(getCellValueAsString);
    }
    @Bean
    public IsRowEmpty isRowEmpty(GetCellValueAsString getCellValueAsString){
        return new IsRowEmpty(getCellValueAsString);
    }

}
