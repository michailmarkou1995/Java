package com.userfrontend.service;

import com.userfrontend.domain.MedicationAccount;
import org.apache.commons.lang3.math.NumberUtils;


public interface AccountService {
    static boolean isNumeric(String string) {
        System.out.println(String.format("Parsing string: \"%s\"", string));

        if (string == null || string.isEmpty()) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        return NumberUtils.isCreatable(string);
    }

    MedicationAccount createMedicationAccount();

    MedicationAccount findByAccountNumberA(int accountNumber);

    void patientAccountUpdate(String firstName, String lastName, String city, String phone, String street, Long id);

}
