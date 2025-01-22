package com.example.morningstar;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    Long g_id;
    String firstName;
    String lastName;
    String address;
    String phone;

}
