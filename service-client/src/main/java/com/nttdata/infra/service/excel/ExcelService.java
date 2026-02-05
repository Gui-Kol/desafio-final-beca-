package com.nttdata.infra.service.excel;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;
import com.nttdata.domain.client.ClientFactory;
import com.nttdata.infra.presentation.dtos.address.AddressDto;
import com.nttdata.infra.presentation.dtos.client.ClientDto;
import com.nttdata.infra.service.ClientValidator;
import com.nttdata.infra.service.excel.usecases.GetCellValueAsLocalDate;
import com.nttdata.infra.service.excel.usecases.GetCellValueAsString;
import com.nttdata.infra.service.excel.usecases.IsRowEmpty;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.validator.constraints.br.CPF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {
    private final ClientValidator clientValidator;
    private static final Logger log = LoggerFactory.getLogger(ExcelService.class);
    private final ClientFactory clientFactory;
    private final ClientRepository clientRepository;
    private final GetCellValueAsString getCellValueAsString;
    private final IsRowEmpty isRowEmpty;
    private final GetCellValueAsLocalDate getCellValueAsLocalDate;

    public ExcelService(ClientValidator clientValidator, ClientFactory clientFactory, ClientRepository clientRepository, GetCellValueAsString getCellValueAsString, IsRowEmpty isRowEmpty, GetCellValueAsLocalDate getCellValueAsLocalDate) {
        this.clientValidator = clientValidator;
        this.clientFactory = clientFactory;
        this.clientRepository = clientRepository;
        this.getCellValueAsString = getCellValueAsString;
        this.isRowEmpty = isRowEmpty;
        this.getCellValueAsLocalDate = getCellValueAsLocalDate;
    }


    @Transactional
    public List<Client> importClientsFromExcel(MultipartFile file) throws IOException {
        List<Client> importedClients = new ArrayList<>();

        if (file.isEmpty()) {
            throw new IllegalArgumentException("The file sent is empty.");
        }
        if (!file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Invalid file type. Only .xlsx files are supported.");
        }

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); //Pega a primeira aba (índice 0)
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            int rowNum = 1;
            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                rowNum++;

                if (isRowEmpty.is(currentRow)) {
                    continue;
                }

                try {
                    String name = getCellValueAsString.get(currentRow.getCell(0));
                    String email = getCellValueAsString.get(currentRow.getCell(1));
                    String cpf = getCellValueAsString.get(currentRow.getCell(2));
                    LocalDate birthDay = getCellValueAsLocalDate.get(currentRow.getCell(3));
                    String username = getCellValueAsString.get(currentRow.getCell(4));
                    String password = getCellValueAsString.get(currentRow.getCell(5));
                    String telephone = getCellValueAsString.get(currentRow.getCell(6));
                    //Address
                    String street = getCellValueAsString.get(currentRow.getCell(7));
                    String number = getCellValueAsString.get(currentRow.getCell(8));
                    String addressDetails = getCellValueAsString.get(currentRow.getCell(9));
                    String neighborhood = getCellValueAsString.get(currentRow.getCell(10));
                    String city = getCellValueAsString.get(currentRow.getCell(11));
                    String state = getCellValueAsString.get(currentRow.getCell(12));
                    String postcode = getCellValueAsString.get(currentRow.getCell(13));
                    String country = getCellValueAsString.get(currentRow.getCell(14));

                    clientValidator.validate(new ClientDto(name,email,new AddressDto(street, number, addressDetails, neighborhood,
                            city, state,postcode,country),username,password,cpf,birthDay,telephone));
                    Client client = clientFactory.factory(name, email, username, password, cpf, birthDay, telephone, street, number, addressDetails, neighborhood, city, state, postcode, country);

                    if (name.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
                        log.error("❌ Error in line '{}' : Name, Email, or CPF cannot be empty. Client '{}' ignored.", rowNum, name);
                        continue;
                    }
                    log.info("✅ Line processed successfully, client {} saved!", name);
                    clientRepository.registerClientRoleClient(client);
                    importedClients.add(client);

                } catch (RuntimeException e) {
                    throw new IllegalArgumentException("❌ Serious error saving the client on the line: " + rowNum + "\n" + e.getMessage());
                }catch (Exception e){
                    log.error("❌ Error processing line {} {}" ,rowNum, e.getMessage());
                }
            }
        }

        return importedClients;
    }

}

