package com.CsvAppApplication.service;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.CsvAppApplication.model.Organisation;
import com.CsvAppApplication.repository.OrganisationRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class CsvLoaderService {

    @Autowired
    private OrganisationRepository organisationRepository;

    public void reloadCsvData() {
        try (
                CSVReader reader = new CSVReader(new InputStreamReader(
                        new ClassPathResource("data.csv").getInputStream()
                ))) {
            organisationRepository.deleteAll(); // Optional: wipe existing data

            String[] line;
            boolean firstLine = true;

            while ((line = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Organisation org = new Organisation();
                org.setOrganisationName(line[0]);
                org.setTown(line[1]);
                org.setCounty(line[2]);
                org.setTypeAndRating(line[3]);
                org.setRoute(line[4]);

                organisationRepository.save(org);
            }

            System.out.println("CSV data reloaded successfully.");

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}
