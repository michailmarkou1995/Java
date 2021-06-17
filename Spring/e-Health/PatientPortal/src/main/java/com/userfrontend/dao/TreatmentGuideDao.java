package com.userfrontend.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.userfrontend.domain.TreatmentGuide;

public interface TreatmentGuideDao extends JpaRepository <TreatmentGuide, Long>{
	
    @Modifying
    @Transactional
    @Query(value = "delete from treatment_guide where id = :id",  nativeQuery = true)
    void deleteTguideById(@Param("id") Long id);

}
