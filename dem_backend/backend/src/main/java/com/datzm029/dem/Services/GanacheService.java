package com.datzm029.dem.Services;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class GanacheService {

    public void getBalance(Web3j web3, String address){
        try {
            // Query the balance of the account in Wei (the smallest unit of Ether)
            org.web3j.protocol.core.methods.response.EthGetBalance balanceWei =
                    web3.ethGetBalance(address, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();

            // Convert the balance from Wei to Ether for easier readability
            double balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Convert.Unit.ETHER).doubleValue();

            System.out.println("Balance of account " + address + ": " + balanceInEther + " ETH");

        } catch (IOException e) {
            System.out.println("Error fetching balance: " + e.getMessage());
        } finally {
            web3.shutdown(); // Close the Web3j connection after use
        }
    }

    public void getAllAccounts(Web3j web3){
        try {
            // Call eth_accounts to get the list of accounts
            EthAccounts accounts = web3.ethAccounts().send();

            // Print the list of accounts from Ganache
            System.out.println("Accounts in Ganache:");
            for (String account : accounts.getAccounts()) {
                System.out.println(account);
            }

        } catch (IOException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        } finally {
            web3.shutdown();  // Close the Web3j connection after use
        }
    }
//not works for ganache
    public void createWallet(String password){
        try {
            String walletDirectory = "C:\\wallets";
            File directory = new File(walletDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // Generate the wallet file and save it in the specified directory
            String walletFileName = WalletUtils.generateNewWalletFile(password, directory, false);
            // Print wallet file name for reference
            System.out.println("New Ethereum Wallet created!");
            System.out.println("Wallet File: " + walletFileName);
            // Optionally, load the credentials (for further use or interactions)
            Credentials credentials = WalletUtils.loadCredentials(password, new File(directory, walletFileName));
            System.out.println("Ethereum Address: " + credentials.getAddress());

        } catch (CipherException | IOException | InvalidAlgorithmParameterException | NoSuchAlgorithmException |
                 NoSuchProviderException e) {
            System.out.println("Error creating the wallet: " + e.getMessage());
        }
    }

    public void rawTransaction(String url, String toAddress, String privateKey) throws IOException {
         final String PRIVATE_KEY = "0xa32680ff0ec76ea6e47c63e0ff947fa9b9f221345091e32eae108238fa3e8fa7";
         final String TO_ADDRESS = "0xa00194D67a8E5E84E933E4EAb48eB1EfE13a4033";
        final String INFURA_URL  = "HTTP://127.0.0.1:7545";
        final BigInteger AMOUNT = Convert.toWei("0.1", Convert.Unit.ETHER).toBigInteger(); // Amount to send (0.1 ETH)

            // Step 1: Connect to Ethereum Network
            Web3j web3j = Web3j.build(new HttpService(INFURA_URL)); // Connect to the Ethereum network
            // Step 2: Load Credentials from MetaMask private key
            Credentials credentials = Credentials.create(PRIVATE_KEY); // Use the private key from MetaMask

            // Step 3: Get the current nonce (transaction count)
            EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = transactionCount.getTransactionCount();

            // Step 4: Get the current gas price
            EthGasPrice gasPriceResponse = web3j.ethGasPrice().send();
            BigInteger gasPrice = gasPriceResponse.getGasPrice();
            // Step 5: Set the gas limit (21000 for simple Ether transfers)
            BigInteger gasLimit = BigInteger.valueOf(21000); // Standard gas limit for sending Ether

            // Step 6: Create the RawTransaction (transaction object)
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    nonce, gasPrice, gasLimit, TO_ADDRESS, AMOUNT);
            // Step 7: Sign the transaction with the private key
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            // Step 8: Send the signed transaction to the Ethereum network
            EthSendTransaction transactionResponse = web3j.ethSendRawTransaction(hexValue).send();
            String transactionHash = transactionResponse.getTransactionHash();

            System.out.println("Transaction sent! Hash: " + transactionHash);
        }

}
