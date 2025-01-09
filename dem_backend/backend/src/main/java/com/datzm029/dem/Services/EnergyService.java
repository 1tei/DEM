package com.datzm029.dem.Services;

import com.datzm029.dem.dao.Dao;
import com.datzm029.dem.model.Energy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EnergyService {
    private final Dao dao;

    public EnergyService(@Qualifier("postgres_energy") Dao dao) {
        this.dao = dao;
    }

    public Energy addEnergy(Energy energy) {
        return (Energy) dao.insert(energy);
    }

    public int getUserEnergy(String userId) {
        return dao.selectAllById(userId);
    }

}
