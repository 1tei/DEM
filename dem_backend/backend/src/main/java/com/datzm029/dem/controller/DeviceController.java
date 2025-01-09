package com.datzm029.dem.controller;

import com.datzm029.dem.Services.DeviceService;
import com.datzm029.dem.model.IOTDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.UUID;
@RestController
public class DeviceController {

    @Autowired
    DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping(value = "/add/device")
    public IOTDevice addDevice(@RequestParam("userId") String userId,
                          @RequestParam("serialNumber") String serialNumber) {
        return service.addDevice(new IOTDevice(UUID.randomUUID(), UUID.fromString(userId),
                new BigInteger(serialNumber)));
    }
}
