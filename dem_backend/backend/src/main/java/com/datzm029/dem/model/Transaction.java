package com.datzm029.dem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {

    String userFrom;
    String userTo;
    String transactionId;
    BigInteger value;

}
