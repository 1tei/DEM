package com.datzm029.dem.dao;

import com.datzm029.dem.model.Energy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres_energy")
public class EnergyAccessService implements Dao<Energy> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EnergyAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Energy insert(Energy energy) {
        final String sql = "INSERT INTO energy (" +
                "user_id, " +
                "datetime, " +
                "amount" +
                ") VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                energy.getUserId(),
                energy.getDatetime(),
                energy.getAmount());

        return energy;

    }

    @Override
    public List<Energy> selectAll() {
        return null;
    }

    @Override
    public int selectAllById(String id) {
        final String sql = "" +
                "SELECT " +
                "    SUM(amount) AS amount_count " +
                "FROM " +
                "    energy " +
                "WHERE " +
                "    user_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{UUID.fromString(id)}, Integer.class);
    }

    @Override
    public Energy checkIfExist(Energy object) {
        return null;
    }
}
