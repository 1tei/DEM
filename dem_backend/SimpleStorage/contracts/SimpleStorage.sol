// SPDX-License-Identifier: MIT
pragma solidity 0.5.16;

contract SimpleStorage {
    uint256 private data;

    function set(uint256 _data) public {
        data = _data;
    }

    function get() public view returns (uint256) {
        return data;
    }
    // Function to send Ether to another address
    function sendEther(address payable _recipient, uint256 _amount) public payable {
        // Ensure the contract has enough balance
        require(address(this).balance >= _amount, "Insufficient balance in contract");

        // Transfer the Ether
        _recipient.transfer(_amount);
    }

    // Function to receive Ether into the contract
    function() external payable {}
}