package com.datzm029.dem.controller;

import com.datzm029.dem.Services.EnergyService;
import com.datzm029.dem.Services.MarketService;
import com.datzm029.dem.dao.WalletAccessService;
import com.datzm029.dem.model.Energy;
import com.datzm029.dem.model.Market;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class MarketController {

    @Autowired
    MarketService service;

    @Autowired
    WalletAccessService walletAccessService;
    public MarketController(MarketService service) {
        this.service = service;
    }

    @PostMapping(value = "/add/addMarket")
    public void addMarket(@RequestParam("userId") String userId,
                            @RequestParam("region") String timestamp,
                            @RequestParam("energija") int serialNumber) throws Exception {

        service.addMarket(new Market(UUID.fromString(userId), timestamp, serialNumber, 1.2));
    }

    @GetMapping(value = "/getMarket")
    public List<Market> getUserEnergy(){
        List<Market> markets = service.getAllMarkets();
        System.out.println("markets " + markets);
        return markets;
    }

    @PostMapping(value = "/sellMarketEnergy")
    public void sellMarketEnergy(@RequestParam("userIdNo") String userIdNo,
                                 @RequestParam("userIdUz") String userId,
                                 @RequestParam("energija") int energija) throws Exception {
        service.sellMarketEnergy(walletAccessService, UUID.fromString(userIdNo),UUID.fromString(userId), energija);
        System.out.println("sold " + energija);
    }

}
