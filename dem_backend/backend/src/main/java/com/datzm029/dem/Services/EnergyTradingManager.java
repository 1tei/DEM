package com.datzm029.dem.Services;

import com.datzm029.dem.Contracts.SimpleStorage_sol_SimpleStorage;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;

public class EnergyTradingManager {

    private final Web3j web3j;
    private final Credentials credentials;
    private final SimpleStorage_sol_SimpleStorage contract;

    public EnergyTradingManager(String privateKey, String contractAddress, String rpcEndpoint, long chainId) throws IOException {
        this.web3j = Web3j.build(new HttpService(rpcEndpoint));
        this.credentials = Credentials.create(privateKey);
        EthGasPrice gasPriceResponse = web3j.ethGasPrice().send();
        BigInteger gasPrice = gasPriceResponse.getGasPrice();
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials, chainId);
        this.contract = SimpleStorage_sol_SimpleStorage.load(
                contractAddress, web3j, txManager, new BigInteger("20000000000"), new BigInteger("6721975"));
                //contractAddress, web3j, txManager, gasPrice, new BigInteger("100000"));

    }

    /**
     * Creates a new trade on the blockchain.
     * @param energyAmount The amount of energy for the trade.
     * @param pricePerUnit The price per unit of energy.
     * @return The transaction receipt.
     * @throws Exception If the transaction fails.
     */
    public TransactionReceipt createTrade(String adressTo, BigInteger energyAmount, BigInteger pricePerUnit) throws Exception {
        System.out.println("Creating trade with energyAmount: " + energyAmount + ", pricePerUnit: " + pricePerUnit);
        return contract.sendEther(adressTo, energyAmount, pricePerUnit).send();
    }



    public static void main(String[] args) {
        try {
            // Replace these values with your own
            String privateKey = "0xf501066afd30be7aceadc132bc1b4aaf37245426a533a5e7e03a6a4553ded9ec";
            String contractAddress = "0xdBEA68Aa5e4dA22543fdd69BD749Bab4De0be6fA";
            String rpcEndpoint = "HTTP://127.0.0.1:7545"; // Use this for Ganache or localhost
            long chainId = 1337;  // Chain ID for Ganache

            EnergyTradingManager manager = new EnergyTradingManager(privateKey, contractAddress, rpcEndpoint, chainId);

            // Create a new trade
            BigInteger energyAmount = BigInteger.valueOf(1000); // 100 units of energy
            BigInteger weiValue = Convert.toWei("0.01", Convert.Unit.ETHER).toBigInteger(); // 0.1 ETH in wei


            TransactionReceipt createTradeReceipt = manager.createTrade(contractAddress,energyAmount, weiValue);
            System.out.println("Trade created successfully: " + createTradeReceipt.getTransactionHash());

            // Get the trade counter
            //BigInteger tradeCounter = manager.getTradeCounter();
           // System.out.println("Current number of trades: " + tradeCounter);

            // Buy energy from the first trade
            BigInteger tradeId = new BigInteger("5777"); // Assuming the first trade has ID 0
            //BigInteger weiValue = BigInteger.valueOf(10_000_000_000_000_000L); // 0.01 ETH
           // TransactionReceipt buyEnergyReceipt = manager.(tradeId, weiValue);
            //System.out.println("Energy purchased successfully: " + buyEnergyReceipt.getTransactionHash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
