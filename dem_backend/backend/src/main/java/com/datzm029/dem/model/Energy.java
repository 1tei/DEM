package com.datzm029.dem.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Energy {

    private UUID userId;
    private Date datetime;
    private int amount;

}
