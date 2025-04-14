package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuSetupRequest {

    private Long restaurantId;

    private List<String> cuisines; // e.g., "Chinese", "North Indian"
    private String foodType; // "VEG", "NON_VEG", or "BOTH"
    private String menuImageUrl; // You can use S3 / cloud link after upload
}
