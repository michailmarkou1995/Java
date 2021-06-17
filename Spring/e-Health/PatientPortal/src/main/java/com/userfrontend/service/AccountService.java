package com.userfrontend.service;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.userfrontend.domain.MedicationAccount;
import com.userfrontend.domain.Patient;


public interface AccountService {
	MedicationAccount createMedicationAccount();
	MedicationAccount findByAccountNumberA(int accountNumber);

	
public void patientAccountUpdate(String firstName, String lastName, String city, String phone, String street, Long id);//void or patient and parameters should or should not?

    
    public static boolean isNumeric(String string) {
        int intValue;
    	boolean dValue;
    	boolean numVal;
        System.out.println(String.format("Parsing string: \"%s\"", string));
    		
        if(string == null || string.isEmpty()) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }
       
        return NumberUtils.isCreatable(string);
    }
    
}
