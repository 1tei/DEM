// SPDX-License-Identifier: MIT
pragma solidity 0.5.16;

contract EnergyMarket {
    // Struct to store details of energy transactions
    struct EnergyTransaction {
        address account_from;
        address account_to;
        uint256 energy_price;
        uint256 timestamp;
        uint256 amount;
    }

    // Event to log energy transactions
    event EnergyPurchased(
        address indexed account_from,
        address indexed account_to,
        uint256 energy_price,
        uint256 timestamp,
        uint256 amount
    );

    // Function to buy energy
    function sendEther(address payable account_to, uint256 energy_price) public payable {
        require(msg.value >= energy_price, "Insufficient funds to buy energy");

        // Transfer the Ether to the recipient
        account_to.transfer(msg.value);

        // Emit the transaction event
        emit EnergyPurchased(
            msg.sender,
            account_to,
            energy_price,
            block.timestamp,
            msg.value
        );
    }

    // Fallback function to receive Ether into the contract
    function() external payable {}
}