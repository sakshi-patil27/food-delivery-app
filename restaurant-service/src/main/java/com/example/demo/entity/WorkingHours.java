package com.example.demo.entity;

import java.time.LocalTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WorkingHours {

    private String openingTime;
    private String closingTime;
}
