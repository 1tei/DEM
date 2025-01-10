import React, { useState, useEffect } from "react";
import axios from "axios";
// import { useNavigate } from "react-router-dom";

const MarketPage = () => {
	const [marketData, setMarketData] = useState([]);
	const [surplusEnergy, setSurplusEnergy] = useState(0);
	const [sellAmount, setSellAmount] = useState(0);
	const [sold, setSold] = useState(0);
	const [bought, setBought] = useState(0);
	const [inputAmounts, setInputAmounts] = useState({});
	const [transactions, setTransactions] = useState([]);
	const [showTransactions, setShowTransactions] = useState(false);
	// const navigate = useNavigate();

	const handleBuy = async (userIdNo, energija) => {
		try {
			// Get current userId
			const userId = localStorage.getItem("userId");
			if (!userId) {
				console.error("Current user ID not found in localStorage!");
				return;
			}

			// POST request to sellMarketEnergy
			await axios.post("http://localhost:8080/sellMarketEnergy", null, {
				params: {
					userIdNo: userIdNo,
					userId: userId,
					energija: energija,
				},
			});

			alert(`Successfully sold ${energija} kW to user ${userIdNo}`);
		} catch (error) {
			console.error("Error selling market energy:", error);
			alert("Failed to sell market energy.");
		}
	};

	const handleSell = () => {
		const userId = localStorage.getItem("userId");
		const region = localStorage.getItem("region");

		if (!userId || sellAmount <= 0) {
			alert("Enter a valid sell amount!");
			return;
		}

		// if (sellAmount > currentSurplus) {
		// 	alert("Sell amount exceeds available surplus energy!");
		// 	return;
		// }

		const url = `http://localhost:8080/addMarket?userId=${encodeURIComponent(
			userId
		)}
		&region=${encodeURIComponent(region)}
		&energija=${encodeURIComponent(sellAmount)}`;

		axios
			.post(url)
			.then(() => {
				alert(`Successfully added ${sellAmount}kW to market!`);
				setSellAmount(0);
				fetchMarketData();
				fetchSurplusEnergy();
				fetchUserData();
				fetchTransactions();
			})
			.catch((error) => {
				console.error("Error selling energy:", error);
				alert("Failed to add energy to market.");
			});
	};

	const fetchMarketData = async () => {
		try {
			const response = await axios.get("http://localhost:8080/getMarket");
			setMarketData(response.data);
		} catch (error) {
			console.error("Error fetching market data:", error);
		}
	};

	const fetchSurplusEnergy = async () => {
		try {
			const userId = localStorage.getItem("userId");
			if (!userId) {
				console.error("User ID not found in localStorage!");
				return;
			}
			const response = await axios.get(`http://localhost:8080/getUserEnergy`, {
				params: { userId },
			});
			setSurplusEnergy(response.data);
		} catch (error) {
			console.error("Error fetching surplus energy:", error);
		}
	};

	const fetchUserData = async () => {
		try {
			const userId = localStorage.getItem("userId");
			if (!userId) {
				console.error("User ID not found in localStorage!");
				return;
			}

			const url = `http://localhost:8080/getUser?userId=${encodeURIComponent(
				userId
			)}`;

			const response = await axios.get(url);

			setSold(response.data.sold);
			setBought(response.data.bought);
		} catch (error) {
			console.error("Error fetching user data:", error);
		}
	};

	const fetchTransactions = async () => {
		try {
			const userId = localStorage.getItem("userId");
			const response = await axios.get(`/getTransactions`, {
				params: { userId },
			});
			setTransactions(response.data);
		} catch (error) {
			console.error("Error fetching transactions:", error);
		}
	};

	useEffect(() => {
		fetchMarketData();
		fetchSurplusEnergy();
		fetchUserData();
		fetchTransactions();
	}, []);

	return (
		<div className="flex flex-col items-center min-h-screen bg-gray-100">
			<header className="bg-white shadow-md w-full max-w-6xl p-4 rounded">
				<h1 className="text-3xl font-bold text-center">⚡ DEM</h1>
			</header>

			<section className="bg-white p-8 rounded shadow-md w-full max-w-6xl my-6">
				<div className="flex justify-between mb-4">
					<div>
						<p className="text-gray-700">
							Energy sold:{" "}
							<span className="text-indigo-600 font-bold">{sold}kW</span>
						</p>
						<p className="text-gray-700">
							Energy bought:{" "}
							<span className="text-indigo-600 font-bold">{bought}kW</span>
						</p>
					</div>
					<div>
						<p className="text-gray-700">
							Surplus energy:{" "}
							<span className="text-indigo-600 font-bold">
								{surplusEnergy}kW
							</span>
						</p>
						<input
							type="number"
							placeholder="Enter sell amount"
							value={sellAmount}
							onChange={(e) => setSellAmount(Number(e.target.value))}
							className="border border-gray-300 p-2 rounded mt-2 w-full"
						/>
						<button
							onClick={handleSell}
							className="bg-indigo-600 text-white px-4 py-2 rounded-full mt-2"
						>
							SELL
						</button>
					</div>
				</div>
			</section>

			<section className="bg-white p-8 rounded shadow-md w-full max-w-6xl my-6">
				<h2 className="text-xl font-bold mb-6">Market</h2>
				<table className="w-full border-collapse border border-gray-300">
					<thead>
						<tr className="bg-gray-200">
							<th className="p-4 border border-gray-300">Region</th>
							<th className="p-4 border border-gray-300">Energy</th>
							<th className="p-4 border border-gray-300">Price (kWh)</th>
							<th className="p-4 border border-gray-300">Amount</th>
						</tr>
					</thead>
					<tbody>
						{marketData.map((entry) => (
							<tr key={entry.userId} className="even:bg-gray-100">
								<td className="p-4 border border-gray-300">{entry.region}</td>
								<td className="p-4 border border-gray-300">
									{entry.energija}kW
								</td>
								<td className="p-4 border border-gray-300">{entry.cena} ETH</td>
								<td className="p-4 border border-gray-300">
									<input
										type="number"
										placeholder="Enter kW amount you want to buy"
										value={inputAmounts[entry.userId] || ""}
										onChange={(e) =>
											setInputAmounts({
												...inputAmounts,
												[entry.userId]: e.target.value,
											})
										}
										className="border border-gray-300 p-2 rounded w-full"
									/>
									<button
										onClick={() =>
											handleBuy(
												entry.userId,
												parseInt(inputAmounts[entry.userId] || 0)
											)
										}
										className="bg-indigo-600 text-white px-4 py-2 rounded-full mt-2 w-full"
										disabled={!inputAmounts[entry.userId]}
									>
										BUY
									</button>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			</section>

			<section className="bg-white p-8 rounded shadow-md w-full max-w-6xl my-6">
				<div
					className="flex justify-between items-center cursor-pointer"
					onClick={() => setShowTransactions(!showTransactions)}
				>
					<h2 className="text-xl font-bold">Transactions</h2>
					<button className="text-gray-700">
						{showTransactions ? "▲" : "▼"}
					</button>
				</div>
				{showTransactions && (
					<table className="w-full border-collapse border border-gray-300 mt-4">
						<thead>
							<tr className="bg-gray-200">
								<th className="p-4 border border-gray-300">Transaction ID</th>
								<th className="p-4 border border-gray-300">From</th>
								<th className="p-4 border border-gray-300">To</th>
								<th className="p-4 border border-gray-300">Value</th>
							</tr>
						</thead>
						<tbody>
							{transactions.map((transaction) => (
								<tr
									key={transaction.transactionId}
									className="even:bg-gray-100"
								>
									<td className="p-4 border border-gray-300">
										{transaction.transactionId}
									</td>
									<td className="p-4 border border-gray-300">
										{transaction.userFrom}
									</td>
									<td className="p-4 border border-gray-300">
										{transaction.userTo}
									</td>
									<td className="p-4 border border-gray-300">
										{transaction.value} kW
									</td>
								</tr>
							))}
						</tbody>
					</table>
				)}
			</section>
		</div>
	);
};

export default MarketPage;
