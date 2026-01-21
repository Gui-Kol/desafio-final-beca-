package com.nttdata.infra.excel;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.infra.excel.usecases.GetCellValueAsLocalDate;
import com.nttdata.infra.excel.usecases.GetCellValueAsString;
import com.nttdata.infra.excel.usecases.IsRowEmpty;
import com.nttdata.infra.gateway.client.ClientMapper;
import com.nttdata.infra.persistence.address.AddressEntity;
import com.nttdata.infra.persistence.client.ClientEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {
    private final ClientRepository clientRepository;
    private final GetCellValueAsString getCellValueAsString;
    private final IsRowEmpty isRowEmpty;
    private final GetCellValueAsLocalDate getCellValueAsLocalDate;
    private final ClientMapper clientMapper;

    public ExcelService(ClientRepository clientRepository, GetCellValueAsString getCellValueAsString, IsRowEmpty isRowEmpty, GetCellValueAsLocalDate getCellValueAsLocalDate, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.getCellValueAsString = getCellValueAsString;
        this.isRowEmpty = isRowEmpty;
        this.getCellValueAsLocalDate = getCellValueAsLocalDate;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public List<ClientEntity> importClientsFromExcel(MultipartFile file) throws IOException {
        List<ClientEntity> importedClients = new ArrayList<>();

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

            int rowNum = 1; // Contador para linhas de dados (começa após o cabeçalho)
            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                rowNum++;

                if (isRowEmpty.is(currentRow)) {
                    System.out.println("Linha " + rowNum + " vazia, pulando.");
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

                    String street = getCellValueAsString.get(currentRow.getCell(7));
                    String number = getCellValueAsString.get(currentRow.getCell(8));
                    String addressDetails = getCellValueAsString.get(currentRow.getCell(9));
                    String neighborhood = getCellValueAsString.get(currentRow.getCell(10));
                    String city = getCellValueAsString.get(currentRow.getCell(11));
                    String state = getCellValueAsString.get(currentRow.getCell(12));
                    String postcode = getCellValueAsString.get(currentRow.getCell(13));
                    String country = getCellValueAsString.get(currentRow.getCell(14));

                    AddressEntity address = new AddressEntity(street, number, addressDetails, neighborhood, city, state, postcode, country);
                    var date = LocalDate.ofYearDay(0, 1);
                    var loginDate = LocalDateTime.of(date, LocalTime.ofSecondOfDay(0));

                    ClientEntity client = new ClientEntity(null, name, email, address, username, password,
                            cpf, birthDay, telephone, LocalDate.now(), date, true, loginDate);

                    if (name.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
                        System.err.println("Erro na linha " + rowNum + ": Nome, Email ou CPF não podem ser vazios. Cliente '" + name + "' ignorado.");
                        continue;
                    }

                    clientRepository.registerClientRoleClient(clientMapper.toClient(client));
                    importedClients.add(client);
                    System.out.println("✅ Cliente '" + client.getName() + "' da linha " + rowNum + " importado com sucesso.");

                } catch (Exception e) {
                    System.err.println("❌ Erro ao processar linha " + rowNum + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return importedClients;
    }
}

