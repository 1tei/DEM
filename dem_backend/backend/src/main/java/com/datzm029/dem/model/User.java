package com.datzm029.dem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    UUID userId;
    String username;
    String name;
    String region;
    String address;
    String email;
    int surplus;
    BigInteger totalEnergyProduced;
    BigInteger totalEnergyConsumed;
    String password;

    public User(String username, String name, String region, String address, String email, String password) {
        this.username = username;
        this.name = name;
        this.region = region;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public User(UUID userId, String username, String name, String region, String address, String email, BigInteger totalEnergyProduced, BigInteger totalEnergyConsumed, String password) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.region = region;
        this.address = address;
        this.email = email;
        this.totalEnergyProduced = totalEnergyProduced;
        this.totalEnergyConsumed = totalEnergyConsumed;
        this.password = password;
    }
}
