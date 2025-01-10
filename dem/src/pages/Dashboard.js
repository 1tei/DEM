import React, { useState } from "react";
// import axios from "axios";
import { ethers } from "ethers";

const Dashboard = () => {
	const [walletAddress, setWalletAddress] = useState(null);
	const [balance, setBalance] = useState(null);
	const [errorMessage, setErrorMessage] = useState(null);

	const connectWallet = async () => {
		if (!window.ethereum) {
			setErrorMessage(
				"MetaMask is not installed. Please install it and try again."
			);
			return;
		}

		try {
			// Request account access
			const accounts = await window.ethereum.request({
				method: "eth_requestAccounts",
			});
			const wallet = accounts[0];
			setWalletAddress(wallet);

			// Set up provider and fetch balance
			const provider = new ethers.providers.Web3Provider(window.ethereum);
			const balance = await provider.getBalance(wallet);
			const formattedBalance = ethers.utils.formatEther(balance);
			setBalance(formattedBalance);

			setErrorMessage(null); // Clear any previous errors
		} catch (error) {
			setErrorMessage(`Failed to connect wallet: ${error.message}`);
		}
	};

	// const saveWallet = async (walletAddress) => {
	// 	try {
	// 		const response = await axios.post("http://localhost:5000/api/wallet", {
	// 			walletAddress,
	// 		});
	// 		console.log("Wallet saved successfully:", response.data);
	// 	} catch (error) {
	// 		console.error("Error saving wallet:", error);
	// 	}
	// };

	return (
		<div className="flex flex-col items-center min-h-screen bg-gray-100">
			<header className="bg-white shadow-md w-full max-w-6xl p-4 rounded">
				<h1 className="text-3xl font-bold text-center">Your Wallet</h1>
			</header>

			<section className="bg-white p-8 rounded shadow-md w-full max-w-6xl my-6">
				<div className="text-center">
					<h2 className="text-2xl font-bold mb-4">Connect Your Wallet</h2>
					{walletAddress ? (
						<div className="bg-gray-50 p-6 rounded shadow-md">
							<p className="text-gray-700">
								<strong className="text-indigo-600">Wallet Address:</strong>{" "}
								{walletAddress}
							</p>
							<p className="text-gray-700 mt-2">
								<strong className="text-indigo-600">Balance:</strong> {balance}{" "}
								ETH
							</p>
						</div>
					) : (
						<button
							onClick={connectWallet}
							className="bg-indigo-600 text-white px-6 py-2 rounded-full mt-4"
						>
							Connect Wallet
						</button>
					)}
					{errorMessage && (
						<p className="text-red-600 mt-4 font-semibold">{errorMessage}</p>
					)}
				</div>
			</section>
		</div>
	);
};

export default Dashboard;
