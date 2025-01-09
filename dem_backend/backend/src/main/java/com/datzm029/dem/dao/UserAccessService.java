package com.datzm029.dem.dao;

import com.datzm029.dem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository("postgres_user")
public class UserAccessService implements Dao<User> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User insert(User user) {
        final String sql = "INSERT INTO users (" +
                "user_id, " +
                "username, " +
                "name, " +
                "region, " +
                "address, " +
                "email, " +
                "surplus, " +
                "total_energy_produced, " +
                "total_energy_consumed, " +
                "password" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getUserId(),
                user.getUsername(),
                user.getName(),
                user.getRegion(),
                user.getAddress(),
                user.getEmail(),
                user.getSurplus(),
                user.getTotalEnergyProduced(),
                user.getTotalEnergyConsumed(),
                user.getPassword());

        return user;
    }

    @Override
    public List<User> selectAll() {
        return null;
    }

    @Override
    public User selectById(UUID userId) {

        final String sql = "SELECT " +
                "user_id, " +
                "username, " +
                "name, " +
                "region, " +
                "address, " +
                "email, " +
                "surplus, " +
                "total_energy_produced, " +
                "total_energy_consumed, " +
                "password " +
                "FROM users " +
                "WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (resultSet, i) -> {
            return new User(
                    UUID.fromString(resultSet.getString("user_id")),
                    resultSet.getString("username"),
                    resultSet.getString("name"),
                    resultSet.getString("region"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getInt("surplus"), // Surplus as integer
                    new BigInteger(resultSet.getString("total_energy_produced")),
                    new BigInteger(resultSet.getString("total_energy_consumed")),
                    resultSet.getString("password")
            );
        });
    }

    @Override
    public User checkIfExist(User object) {
        return null;
    }

    @Override
    public UUID getId(String username) {
        String sql = "SELECT user_id " +
                "FROM users " +
                "WHERE username = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{username}, (resultSet, i) -> {
            return UUID.fromString(resultSet.getString("user_id"));
        });
    }
}
