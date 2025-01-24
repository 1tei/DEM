package com.datzm029.dem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Market {

    private UUID userId;
    private String region;
    private int energija;
    private double cena;

}
