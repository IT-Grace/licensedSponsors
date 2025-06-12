package com.CsvAppApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CsvAppApplication.service.CsvLoaderService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private CsvLoaderService csvLoaderService;

    @PostMapping("/load-csv")
    public ResponseEntity<String> loadCsv() {
        csvLoaderService.reloadCsvData();
        return ResponseEntity.ok("CSV data loaded successfully.");
    }
}
