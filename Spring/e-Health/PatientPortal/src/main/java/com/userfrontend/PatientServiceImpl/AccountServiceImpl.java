package com.userfrontend.PatientServiceImpl;

import com.userfrontend.dao.MedicationAccountDao;
import com.userfrontend.dao.PatientDao;
import com.userfrontend.domain.MedicationAccount;
import com.userfrontend.domain.Patient;
import com.userfrontend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber = 11223145;
    @Autowired
    PatientDao patientDao;
    @Autowired
    private MedicationAccountDao medicationAccountDao;

    public MedicationAccount createMedicationAccount() {
        MedicationAccount medicationAccount = new MedicationAccount();
        medicationAccount.setAccountNumber(accountGen());
        System.out.println("gen");
        System.out.println(medicationAccount.getAccountNumber());

        medicationAccountDao.save(medicationAccount);

//        return medicationAccountDao.findFirstById(medicationAccount.getId());
        return medicationAccountDao.findByAccountNumber(medicationAccount.getAccountNumber());
    }


    private int accountGen() {
        Random rand = new Random(System.currentTimeMillis());
        int rand_int1 = rand.nextInt(1000);
        return ++nextAccountNumber + rand_int1;
    }


    @Override
    public void patientAccountUpdate(String firstName, String lastName, String city, String phone, String street, Long id) {
        Patient patient = new Patient();
        patient.getPatientID();
        patientDao.setAccountUpdate(firstName, lastName, city, phone, street, id);
    }


    @Override
    public MedicationAccount findByAccountNumberA(int accountNumber) {
        return medicationAccountDao.findByAccountNumber(accountNumber);
    }


}
