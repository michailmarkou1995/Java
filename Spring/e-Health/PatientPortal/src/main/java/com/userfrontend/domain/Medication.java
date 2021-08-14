package com.userfrontend.domain;


import javax.persistence.*;
import java.util.List;


@Entity
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String medicationName;
    private String medicationType;

    @ManyToMany()
    @JoinTable(name = "medication_treatment",
            joinColumns = {@JoinColumn(name = "fk_medication")},
            inverseJoinColumns = {@JoinColumn(name = "fk_treatment")})
    private List<TreatmentGuide> treatmentGuide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }


    public List<TreatmentGuide> getTreatmentGuide() {
        return treatmentGuide;
    }

    public void setTreatmentGuide(List<TreatmentGuide> treatmentGuide) {
        this.treatmentGuide = treatmentGuide;
    }

    @Override
    public String toString() {
        return "Medication [id=" + id + ", medicationName=" + medicationName + ", medicationType=" + medicationType
                + ", treatmentGuide=" + treatmentGuide + "]";
    }


}
