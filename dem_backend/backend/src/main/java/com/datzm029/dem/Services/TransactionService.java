package com.datzm029.dem.Services;

import com.datzm029.dem.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

@Service
public class TransactionService {
    private final Dao dao;
    @Autowired
    public TransactionService(@Qualifier("postgres_transaction")Dao dao) {
        this.dao = dao;
    }
    public void sendEther(String privateKey, String toAddress, BigInteger amountWei) throws Exception {
    }

}
