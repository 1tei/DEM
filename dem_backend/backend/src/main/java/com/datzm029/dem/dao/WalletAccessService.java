package com.datzm029.dem.dao;

import com.datzm029.dem.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres_wallet")
public class WalletAccessService implements Dao<Wallet> {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WalletAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Wallet insert(Wallet wallet) {
        final String sql = "INSERT INTO wallets (" +
                "public_key, " +
                "user_id, " +
                "walletAddress, " +
                "private_key_hash, " +
                "balance, " +
                "creation_date" +
                ") VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                wallet.getPublicKey(),
                wallet.getUserId(),
                wallet.getWalletAddress(),
                wallet.getPrivateKeyHash(),
                wallet.getBalance(),
                wallet.getCreationDate());

        return wallet;
    }

    @Override
    public Wallet selectById(UUID userId) {
        final String sql = "SELECT " +
                "public_key, " +
                "user_id, " +
                "walletaddress, " +
                "private_key_hash, " +
                "balance, " +
                "creation_date " +
                "FROM wallets " +
                "WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (resultSet, i) -> {
            Wallet wallet = new Wallet(
                    resultSet.getString("public_key"),
                    resultSet.getString("walletaddress"),
                    resultSet.getString("private_key_hash"),
                    resultSet.getTimestamp("creation_date") // Assuming TIMESTAMP for `creation_date`
            );
            wallet.setUserId(UUID.fromString(resultSet.getString("user_id")));
            return wallet;
        });
    }

    @Override
    public List<Wallet> selectAll() {
        return null;
    }

    @Override
    public Wallet checkIfExist(Wallet object) {
        return null;
    }
}
