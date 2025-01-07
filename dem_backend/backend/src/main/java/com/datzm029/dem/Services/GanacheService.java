package com.datzm029.dem.Services;

import com.datzm029.dem.Contracts.SimpleStorage_sol_SimpleStorage;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

public class GanacheService {
    private final Web3j web3j;
    private final Credentials credentials;
    private final SimpleStorage_sol_SimpleStorage contract;

    public GanacheService(String privateKey, String contractAddress, String rpcEndpoint, long chainId) throws IOException {
        this.web3j = Web3j.build(new HttpService(rpcEndpoint));
        this.credentials = Credentials.create(privateKey);
        EthGasPrice gasPriceResponse = web3j.ethGasPrice().send();
        BigInteger gasPrice = gasPriceResponse.getGasPrice();
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials, chainId);
        this.contract = SimpleStorage_sol_SimpleStorage.load(
                contractAddress, web3j, txManager, gasPrice, new BigInteger("6721975"));
        //contractAddress, web3j, txManager, gasPrice, new BigInteger("100000"));
    }

    public void getBalance(Web3j web3, String address) {
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

    public void getAllAccounts() {
        try {
            // Call eth_accounts to get the list of accounts
            EthAccounts accounts = web3j.ethAccounts().send();

            // Print the list of accounts from Ganache
            System.out.println("Accounts in Ganache:");
            for (String account : accounts.getAccounts()) {
                System.out.println(account);
            }

        } catch (IOException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        } finally {
            web3j.shutdown();  // Close the Web3j connection after use
        }
    }


    public Credentials createWallet2(String password) {
        Credentials credentials = null;
        try {
            // Directory to store wallet files
            String walletDirectory = "C:\\wallets";
            File directory = new File(walletDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate wallet file and save it
            String walletFileName = WalletUtils.generateNewWalletFile(password, directory, false);
            System.out.println("New Ethereum Wallet created!");
            System.out.println("Wallet File: " + walletFileName);

            // Load credentials from the wallet file
            credentials = WalletUtils.loadCredentials(password, new File(directory, walletFileName));

            // Print Ethereum address
            System.out.println("Ethereum Address: " + credentials.getAddress());

            // Extract private key in hexadecimal format (MetaMask-compatible)
            String privateKey = Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPrivateKey());
            System.out.println("Private Key (MetaMask-compatible): " + privateKey);

        } catch (Exception e) {
            System.out.println("Error creating the wallet: " + e.getMessage());
        }
        return credentials;
    }

    //not works for ganache
    public Credentials createWallet(String password) {
        Credentials credentials = null;
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
            credentials = WalletUtils.loadCredentials(password, new File(directory, walletFileName));
            System.out.println("Ethereum Address: " + credentials.getAddress());
        } catch (CipherException | IOException | InvalidAlgorithmParameterException | NoSuchAlgorithmException |
                 NoSuchProviderException e) {
            System.out.println("Error creating the wallet: " + e.getMessage());
        }
        return credentials;

    }

    public void rawTransaction(String url, String toAddress, String privateKey) throws IOException {
        final String PRIVATE_KEY = "0xa32680ff0ec76ea6e47c63e0ff947fa9b9f221345091e32eae108238fa3e8fa7";
        final String TO_ADDRESS = "0xa00194D67a8E5E84E933E4EAb48eB1EfE13a4033";
        final String INFURA_URL = "HTTP://127.0.0.1:7545";
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

    public void createP2PTrade(String privateKey, String addressTo, String rpcEndpoint, Long chainId) throws Exception {
        GanacheService manager = new GanacheService(privateKey, addressTo, rpcEndpoint, chainId);
        manager.createTrade(addressTo);
    }
    public void createContractTrade(String privateKey, String addressTo, String contractAddress, String rpcEndpoint, Long chainId) throws Exception {
        GanacheService manager = new GanacheService(privateKey, contractAddress, rpcEndpoint, chainId);
        manager.createTrade(addressTo);
    }
    private void createTrade(String addressTo) throws Exception {
        BigInteger energyAmount = BigInteger.valueOf(1000); // 100 units of energy
        BigInteger weiValue = Convert.toWei("2.1", Convert.Unit.ETHER).toBigInteger(); // 0.1 ETH in wei


        TransactionReceipt createTradeReceipt = createTrade(addressTo, energyAmount, weiValue);
        System.out.println("Trade created successfully: " + createTradeReceipt.getTransactionHash());

    }
    /**
     * Creates a new trade on the blockchain.
     *
     * @param energyAmount The amount of energy for the trade.
     * @param pricePerUnit The price per unit of energy.
     * @return The transaction receipt.
     * @throws Exception If the transaction fails.
     */
    private TransactionReceipt createTrade(String adressTo, BigInteger energyAmount, BigInteger pricePerUnit) throws Exception {
        System.out.println("adressTo" + adressTo);
        System.out.println("Creating trade with energyAmount: " + energyAmount + ", pricePerUnit: " + pricePerUnit);
        return contract.sendEther(adressTo, energyAmount, pricePerUnit).send();
    }

    public List<EthBlock.TransactionResult> getTransactionsByAddress(String address) throws IOException {
        BigInteger startBlock = BigInteger.ONE; // Starting block number
        BigInteger endBlock = web3j.ethBlockNumber().send().getBlockNumber(); // Latest block number
        List<EthBlock.TransactionResult> result = new ArrayList<>();
        System.out.println("Scanning blocks from " + startBlock + " to " + endBlock);

        for (BigInteger i = startBlock; i.compareTo(endBlock) <= 0; i = i.add(BigInteger.ONE)) {
            EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(i), true).send();
            // Get transactions in the block
            List<EthBlock.TransactionResult> transactions = block.getBlock().getTransactions();
            if (transactionsToAndFromIsntNull(transactions)
                    && transactionContainsAdressed(transactions, address)) {
                result.add(transactions.get(0));
            }
            System.out.println(transactions);
        }
        return result;
    }

    private boolean transactionsToAndFromIsntNull(List<EthBlock.TransactionResult> transactions) {
        return ((EthBlock.TransactionObject) transactions.get(0)).getFrom() != null &&
                ((EthBlock.TransactionObject) transactions.get(0)).getTo() != null;
    }

    private boolean transactionContainsAdressed(List<EthBlock.TransactionResult> transactions, String address) {
        return ((EthBlock.TransactionObject) transactions.get(0)).getFrom().equals(address.toLowerCase())
                || ((EthBlock.TransactionObject) transactions.get(0)).getTo().equals(address.toLowerCase());
    }

    public static void main(String[] args) {
        try {
            // Replace these values with your own
            String privateKey = "0x11e4977166cb1e01c387dd990b4467e5d162d5d770764f991f88ce7af2f6312c";
            String addressTo = "0x255FfE4a4797E0873C08525D0f5D4051211df939";
            String contractAddress = "0x5A24267205aA7db2BB27F1A6615fe6F80bA6E1ec";
            String rpcEndpoint = "HTTP://127.0.0.1:7545"; // Use this for Ganache or localhost
            long chainId = 1337;  // Chain ID for Ganache
GanacheService service = new GanacheService(privateKey,contractAddress,rpcEndpoint,chainId);
service.createContractTrade(privateKey,addressTo, contractAddress, rpcEndpoint,chainId);

//manager.getTransactionsByAddress(addressTo);
             Credentials pk = service.createWallet2("asd");
            service.createP2PTrade(privateKey,pk.getAddress(),rpcEndpoint,chainId);

            //TransactionReceipt createTradeReceipt2 = manager.createTrade("0x255FfE4a4797E0873C08525D0f5D4051211df939", energyAmount, weiValue);
            // System.out.println("Trade created successfully2: " + createTradeReceipt2.getTransactionHash());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
