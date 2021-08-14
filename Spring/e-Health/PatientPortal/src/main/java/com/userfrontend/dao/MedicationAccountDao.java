package com.userfrontend.dao;

import com.userfrontend.domain.MedicationAccount;
import org.springframework.data.repository.CrudRepository;

public interface MedicationAccountDao extends CrudRepository<MedicationAccount, Long> {

    MedicationAccount findByAccountNumber(int accountNumber);

    MedicationAccount findById(long id);

    MedicationAccount findFirstById(long id);

}
