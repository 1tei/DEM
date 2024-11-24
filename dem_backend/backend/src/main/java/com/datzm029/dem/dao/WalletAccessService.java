package com.datzm029.dem.dao;

import com.datzm029.dem.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Wallet> selectAll() {
        return null;
    }

    @Override
    public Wallet checkIfExist(Wallet object) {
        return null;
    }
}
