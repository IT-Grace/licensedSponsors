package com.CsvAppApplication.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CsvAppApplication.model.Organisation;
import com.CsvAppApplication.repository.OrganisationRepository;

@RestController
@RequestMapping("/api/organisations")
@CrossOrigin(origins = "*") // Allow frontend access
public class OrganisationController {

    @Autowired
    private OrganisationRepository organisationRepository;

    @GetMapping
    public Page<Organisation> getPaginatedOrganisations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String town
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("organisationName"));

        if (search != null && !search.isBlank() && town != null && !town.isBlank()) {
            return organisationRepository.findByOrganisationNameContainingIgnoreCaseAndTownContainingIgnoreCase(search, town, pageable);
        }

        if (search != null && !search.isBlank()) {
            return organisationRepository.findByOrganisationNameContainingIgnoreCase(search, pageable);
        }

        if (town != null && !town.isBlank()) {
            return organisationRepository.findByOrganisationNameContainingIgnoreCase(town, pageable);
        }

        return organisationRepository.findAll(pageable);
    }

    @GetMapping("/dashboard/summary")
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> response = new HashMap<>();

        response.put("totalSponsors", organisationRepository.countAll());

        response.put("routes", organisationRepository.countByRoute().stream().map(row
                -> Map.of("label", row[0], "count", row[1])
        ).toList());

        response.put("towns", organisationRepository.top5Towns().stream().map(row
                -> Map.of("label", row[0], "count", row[1])
        ).toList());

        response.put("ratings", organisationRepository.countByRating().stream().map(row
                -> Map.of("label", row[0], "count", row[1])
        ).toList());

        return response;
    }

}
