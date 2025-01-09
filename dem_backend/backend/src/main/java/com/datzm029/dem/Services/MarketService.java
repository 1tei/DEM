package com.datzm029.dem.Services;

import com.datzm029.dem.dao.Dao;
import com.datzm029.dem.model.Market;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    public void sellMarketEnergy(UUID userId, int energija){
        dao.update(userId, energija);
    }
}
