package com.datzm029.dem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Wallet {
    private String publicKey;
    private UUID userId;
    private String walletAddress;
    private String privateKeyHash;
    private double balance;
    private Date creationDate;

    //web3 automatiski izveidot waletu, frontendam vajag fnc get wallet
    public Wallet(String publicKey, String walletAddress, String privateKeyHash, Date creationDate) {
        this.publicKey = publicKey;
        this.walletAddress = walletAddress;
        this.privateKeyHash = privateKeyHash;
        this.creationDate = creationDate;
    }
}
