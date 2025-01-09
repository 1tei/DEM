package com.datzm029.dem.dao;

import com.datzm029.dem.model.Market;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres_market")
public class MarketAccessService implements Dao<Market> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MarketAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Market insert(Market market) {
        final String sql = "INSERT INTO dem.market (user_id, region, energija, cena) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (user_id) " +
                "DO UPDATE SET energija = dem.market.energija + EXCLUDED.energija";


        jdbcTemplate.update(sql, market.getUserId(), market.getRegion(), market.getEnergija(), market.getCena());

        final String sqlExclude = "UPDATE dem.users set surplus = surplus - ?"
                + " where user_id = ?";
        jdbcTemplate.update(sqlExclude, market.getEnergija(), market.getUserId());

        return null;
    }

    @Override
    public List<Market> selectAll() {
        final String sql = "SELECT user_id, region, energija, cena FROM dem.market";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Market(
                    UUID.fromString(resultSet.getString("user_id")),  // Convert user_id to UUID
                    resultSet.getString("region"),                     // Get region as String
                    resultSet.getInt("energija"),                      // Get energija as int
                    resultSet.getDouble("cena")                        // Get cena as double
            );
        });
    }

    @Override
    public int selectAllById(String id) {
        return 0;
    }

    @Override
    public Market checkIfExist(Market object) {
        return null;
    }

    @Override
    public void update(UUID userId, int energija) {
        final String sqlUpdateUser = "UPDATE market " +
                "SET energija = energija - ? " +
                "WHERE user_id = ?";

        jdbcTemplate.update(sqlUpdateUser,
                energija,
                userId);

    }

}
