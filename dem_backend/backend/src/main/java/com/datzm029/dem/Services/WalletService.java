package com.datzm029.dem.Services;

import com.datzm029.dem.dao.Dao;
import com.datzm029.dem.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class WalletService {

    private final Dao dao;

    public WalletService(@Qualifier("postgres_wallet") Dao dao) {
        this.dao = dao;
    }

    public Wallet addWallet(UUID userId){
        Wallet wallet = generateWallet();
        wallet.setUserId(userId);
        wallet.setBalance(0);
        return (Wallet) dao.insert(wallet);
    }

    private Wallet generateWallet() {
        try {

            return createWallet();
        } catch (Exception e) {
            System.err.println("Error generating wallet: " + e.getMessage());
        }
        return null;
    }

    public Wallet getWallet(UUID userId){
        return (Wallet) dao.selectById(userId);
    }

    public Wallet createWallet() {
        org.web3j.crypto.Credentials credentials = null;
        try {
            // Directory to store wallet files
            String walletDirectory = "C:\\wallets";
            File directory = new File(walletDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String aToZ="ABCD.....1234"; // 36 letter.
            String randomStr=generateRandom(aToZ);
            // Generate wallet file and save it
            String walletFileName = WalletUtils.generateNewWalletFile(randomStr, directory, false);
            System.out.println("New Ethereum Wallet created!");
            System.out.println("Wallet File: " + walletFileName);

            // Load credentials from the wallet file
            credentials = WalletUtils.loadCredentials(randomStr, new File(directory, walletFileName));

            // Print Ethereum address
            System.out.println("Ethereum Address: " + credentials.getAddress());

            String privateKey = Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPrivateKey());
            String publicKey =  Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPublicKey());
            String address = credentials.getAddress();
            System.out.println("Private Key (MetaMask-compatible): " + privateKey);

            return new Wallet(publicKey, address,privateKey, new Date());
        } catch (Exception e) {
            System.out.println("Error creating the wallet: " + e.getMessage());
        }
        return null;
    }


    private static String generateRandom(String aToZ) {
        Random rand=new Random();
        StringBuilder res=new StringBuilder();
        for (int i = 0; i < 17; i++) {
            int randIndex=rand.nextInt(aToZ.length());
            res.append(aToZ.charAt(randIndex));
        }
        return res.toString();
    }
}
