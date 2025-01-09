package com.datzm029.dem.controller;

import com.datzm029.dem.Services.DeviceService;
import com.datzm029.dem.Services.EnergyService;
import com.datzm029.dem.model.Energy;
import com.datzm029.dem.model.IOTDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@RestController
public class EnergyController {
    @Autowired
    EnergyService service;

    public EnergyController(EnergyService service) {
        this.service = service;
    }

    @PostMapping(value = "/add/energy")
    public Energy addEnergy(@RequestParam("userId") String userId,
                               @RequestParam("timestamp") String timestamp,
                               @RequestParam("amount") int serialNumber) {

        LocalDateTime parsedTimestamp = LocalDateTime.parse(timestamp);
        Date date = Date.from(parsedTimestamp.atZone(ZoneId.systemDefault()).toInstant());

        return service.addEnergy(new Energy(UUID.fromString(userId), date,
                serialNumber));
    }

    @GetMapping(value = "/getUserEnergy")
    public int getUserEnergy(@RequestParam("userId") String userId) {
        int energy = service.getUserEnergy(userId);
        System.out.println("User has " + energy);
        return energy;
    }
}
