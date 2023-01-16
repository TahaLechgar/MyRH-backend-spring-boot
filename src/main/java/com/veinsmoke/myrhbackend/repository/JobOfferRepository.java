package com.veinsmoke.myrhbackend.repository;

import com.veinsmoke.myrhbackend.entity.JobOffer;
import com.veinsmoke.myrhbackend.entity.projections.JobOfferDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = JobOfferDetail.class)
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    @Query("SELECT j FROM JobOffer j WHERE" +
            " (:profile IS NULL OR j.profile LIKE %:profile% ) " +
            "AND (:country IS NULL OR j.country LIKE %:country%)")
    List<JobOffer> advancedSearch(@Param("profile") String profile,
                                  @Param("country") String country);

}

