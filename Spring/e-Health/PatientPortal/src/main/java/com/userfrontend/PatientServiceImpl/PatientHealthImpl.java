package com.userfrontend.PatientServiceImpl;

import com.userfrontend.dao.PatientHealthDao;
import com.userfrontend.domain.PatientHealth;
import com.userfrontend.service.PatientHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientHealthImpl implements PatientHealthService {

    @Autowired
    private PatientHealthDao patientHealthDao;

    @Override
    public void createPatientHealth(PatientHealth patientHealth) {
        patientHealthDao.save(patientHealth);

    }

    @Override
    public List<PatientHealth> findAll() {
        return patientHealthDao.findAll();
    }

}
