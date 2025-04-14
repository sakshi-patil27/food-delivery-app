package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPackagingRuleRequest {

    private Long restaurantId;

    private boolean isFixed;
    private double fixedCharge;

    // If not fixed
    private double range1Price; // 0-5 items
    private double range2Price; // 6-10 items
    private double range3Price; // >10 items
}
