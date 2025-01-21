package com.example.morningstar;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

Long id;
String firstName;
String lastName;
boolean memoryCare;
}
