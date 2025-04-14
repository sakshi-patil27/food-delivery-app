package com.example.demo.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkingHoursDTO {
    private String day; // e.g., "MONDAY", "TUESDAY"
    private String openingTime; // Format: "09:00"
    private String closingTime; // Format: "18:00"
}
