package com.CsvAppApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organisation_name")
    private String organisationName;

    @Column(name = "town_or_city")
    private String town;

    private String county;

    @Column(name = "type_and_rating")
    private String typeAndRating;

    private String route;

}
