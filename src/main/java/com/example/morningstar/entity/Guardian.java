package com.example.morningstar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "guardian")
@Entity
public class Guardian {
    @SequenceGenerator(name = "guardianSequenceGenerator",
            sequenceName = "guardianSequenceGenerator",initialValue = 1,allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guardianSequenceGenerator")
    @Id
    private Long g_id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    @ManyToMany(mappedBy = "guardians" , fetch = FetchType.LAZY)
    //managed by json not jackson
    @JsonBackReference
    private Set<Patient> patients;

}
