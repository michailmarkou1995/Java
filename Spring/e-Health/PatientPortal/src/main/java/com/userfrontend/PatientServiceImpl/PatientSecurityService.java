package com.userfrontend.PatientServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userfrontend.dao.PatientDao;
import com.userfrontend.domain.Patient;

@Service
public class PatientSecurityService implements UserDetailsService{

	/** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(PatientSecurityService.class);

    @Autowired
    private PatientDao patientDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = patientDao.findUsernameN(username);//  findUsernameN
        if (null == patient) {
            LOG.warn("Username {} not found", username);
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return patient;
    }
}
