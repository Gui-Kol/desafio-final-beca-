package com.nttdata.infra.presentation.controller;

import com.nttdata.domain.client.Client;
import com.nttdata.infra.excel.ExcelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientImportControllerTest {

    @Mock
    private ExcelService excelService;

    @InjectMocks
    private ClientImportController clientImportController;

    @Test
    void shouldReturnCreatedStatusWhenClientsImportedSuccessfully() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "filename.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "some excel data".getBytes());
        List<Client> mockClients = Arrays.asList(new Client(), new Client());

        when(excelService.importClientsFromExcel(any(MultipartFile.class))).thenReturn(mockClients);

        ResponseEntity<String> response = clientImportController.importClients(file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("2 clients imported successfully!", response.getBody());
    }

    @Test
    void shouldReturnBadRequestWhenNoValidClientsFound() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "filename.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "some excel data".getBytes());

        when(excelService.importClientsFromExcel(any(MultipartFile.class))).thenReturn(Collections.emptyList());

        ResponseEntity<String> response = clientImportController.importClients(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No valid client found in the Excel file for import.", response.getBody());
    }

    @Test
    void shouldReturnBadRequestWhenIllegalArgumentExceptionOccurs() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "filename.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "invalid excel data".getBytes());
        String errorMessage = "Invalid file format.";

        when(excelService.importClientsFromExcel(any(MultipartFile.class))).thenThrow(new IllegalArgumentException(errorMessage));

        ResponseEntity<String> response = clientImportController.importClients(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error in request: " + errorMessage, response.getBody());
    }

    @Test
    void shouldReturnInternalServerErrorWhenGenericExceptionOccurs() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "filename.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "corrupt excel data".getBytes());
        String errorMessage = "Something went wrong.";

        when(excelService.importClientsFromExcel(any(MultipartFile.class))).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<String> response = clientImportController.importClients(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal error when importing clients: " + errorMessage, response.getBody());
    }
}