package com.userfrontend.dao;

import org.springframework.data.repository.CrudRepository;

import com.userfrontend.domain.MedicationAccount;

public interface MedicationAccountDao extends CrudRepository<MedicationAccount, Long> {
	
	MedicationAccount findByAccountNumber (int accountNumber);
	MedicationAccount findById (long id);
	MedicationAccount findFirstById (long id);

}
