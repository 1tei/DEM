package com.datzm029.dem.controller;

import com.datzm029.dem.Services.EnergyService;
import com.datzm029.dem.Services.GanacheService;
import com.datzm029.dem.Services.MarketService;
import com.datzm029.dem.dao.WalletAccessService;
import com.datzm029.dem.model.Energy;
import com.datzm029.dem.model.Market;
import com.datzm029.dem.model.Transaction;
import com.datzm029.dem.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.io.IOException;
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

    @PostMapping(value = "/addMarket")
    public void addMarket(@RequestParam("userId") String userId,
                          @RequestParam("region") String region,
                          @RequestParam("energija") int serialNumber) throws Exception {
        System.out.println("called addMarket");
        service.addMarket(new Market(UUID.fromString(userId), region, serialNumber, 1.2));
    }

    @GetMapping(value = "/getMarket")
    public List<Market> getUserEnergy() {
        List<Market> markets = service.getAllMarkets();
        System.out.println("markets " + markets);
        return markets;
    }

    @PostMapping(value = "/sellMarketEnergy")
    public void sellMarketEnergy(@RequestParam("userIdNo") String userIdNo,
                                 @RequestParam("userIdUz") String userId,
                                 @RequestParam("energija") int energija) throws Exception {
        service.sellMarketEnergy(walletAccessService, UUID.fromString(userIdNo), UUID.fromString(userId), energija);
        System.out.println("sold " + energija);
    }

    @GetMapping(value = "/getTransactions")
    public List<Transaction> getAllTransactions(@RequestParam("userId") String userId) throws IOException {
        System.out.println("called getTransactions");
        Wallet wallet = walletAccessService.selectById(UUID.fromString(userId));
        System.out.println("wallet" + wallet.getWalletAddress());
        GanacheService ganacheService = new GanacheService(wallet.getPrivateKeyHash(),
                "0x80d2DB2E7C1F8d638069DB7e36dB1a73f570fe24", "HTTP://127.0.0.1:7545",
                1337);
        return ganacheService.getTransactionsByAddress(wallet.getWalletAddress());
    }

}
