package com.flowergarden.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Andrew Bandura
 */

@Getter
@Setter
public class FlowerDto {

    private int id;
    private String name;
    private int lenght;
    private int freshness;
    private float price;
    private int petals;
    private boolean spike;
    private int bouquet_id;

}
