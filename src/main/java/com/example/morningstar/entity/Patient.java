package com.example.morningstar.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
@Entity
public class Patient {
    @Id
            @SequenceGenerator(name = "patient",
            sequenceName = "patientSequence"
            )
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientSequence")

private Long id;
private String firstName;
private String lastName;
private boolean memoryCare;
//we are using cascadeType.persist and cascadeType.merge only because we dont want to affect/delete guardian while deleting the patient
@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinTable(
        name = "patient_guardian",
        joinColumns = @JoinColumn(name = "Student_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "Guardian_id", referencedColumnName = "g_id")

)
//This will say it is managed by json but not jackson
@JsonManagedReference
private Set<Guardian> guardians;
}
