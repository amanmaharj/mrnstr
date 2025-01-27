package com.example.morningstar.exceptionEntity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ErrorMSg {
    private String details;
    private LocalDateTime errorTime;
    private String error;

}
