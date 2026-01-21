package com.nttdata.infra.controller;

import com.nttdata.infra.excel.ExcelService;
import com.nttdata.infra.persistence.client.ClientEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientImportController{
    private final ExcelService excelService;

    public ClientImportController(ExcelService excelService) {
        this.excelService = excelService;
    }
    @PostMapping("/import")
    public ResponseEntity<String> importClients(@RequestParam("file") MultipartFile file) {
        try {
            List<ClientEntity> importedClients = excelService.importClientsFromExcel(file);
            if (importedClients.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid client found in the Excel file for import.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(importedClients.size() + " clients imported successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error when importing clients: " + e.getMessage());
        }
    }
}
