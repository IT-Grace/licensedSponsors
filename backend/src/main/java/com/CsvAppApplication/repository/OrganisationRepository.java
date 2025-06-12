package com.CsvAppApplication.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.CsvAppApplication.model.Organisation;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    Page<Organisation> findAll(Pageable pageable);

    Page<Organisation> findByOrganisationNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Organisation> findByOrganisationNameContainingIgnoreCaseAndTownContainingIgnoreCase(
            String name,
            String town,
            Pageable pageable
    );

    Page<Organisation> findByTownContainingIgnoreCase(String town, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Organisation o")
    long countAll();

    @Query("SELECT o.route, COUNT(o) FROM Organisation o GROUP BY o.route")
    List<Object[]> countByRoute();

    @Query("SELECT o.town, COUNT(o) FROM Organisation o GROUP BY o.town ORDER BY COUNT(o) DESC")
    List<Object[]> top5Towns();

    @Query("SELECT o.typeAndRating, COUNT(o) FROM Organisation o GROUP BY o.typeAndRating")
    List<Object[]> countByRating();

}
