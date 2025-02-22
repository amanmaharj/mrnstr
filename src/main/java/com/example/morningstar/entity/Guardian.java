package com.example.morningstar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "guardian")
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "g_id")
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
    @ManyToMany(mappedBy = "guardians" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)

    private Set<Patient> patients;

}
