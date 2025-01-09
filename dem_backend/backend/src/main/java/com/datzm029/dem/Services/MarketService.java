package com.datzm029.dem.Services;

import com.datzm029.dem.dao.Dao;
import com.datzm029.dem.dao.WalletAccessService;
import com.datzm029.dem.model.Market;
import com.datzm029.dem.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Service
public class MarketService {

    private final Dao dao;

    @Autowired
    public MarketService(@Qualifier("postgres_market")Dao dao) {
        this.dao = dao;
    }

    public void addMarket(Market market) {
        dao.insert(market);
    }

    public List<Market> getAllMarkets()  {
        return dao.selectAll();
    }

    public void sellMarketEnergy(WalletAccessService walletAccessService, UUID userIdNo, UUID userIdUz,int energija) throws Exception {

        Wallet walletNo =  walletAccessService.selectById(userIdNo);
        Wallet walletUz = walletAccessService.selectById(userIdUz);
        String rpcEndpoint = "HTTP://127.0.0.1:7545";
        GanacheService ganacheService = new GanacheService(walletNo.getPrivateKeyHash(),"0x80d2DB2E7C1F8d638069DB7e36dB1a73f570fe24",rpcEndpoint,0L);
        ganacheService.createTransaction(walletNo.getPrivateKeyHash(),walletUz.getWalletAddress(), BigInteger.valueOf(energija));
        dao.update(userIdNo, energija);
        dao.updateUz(userIdUz, energija);
        System.out.println("transaction success no" + walletNo.getWalletAddress() + " uz " + walletUz.getWalletAddress());
    }
}
