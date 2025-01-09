// Import necessary libraries
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const MarketPage = () => {
	const [energyData, setEnergyData] = useState([]);
	const [surplusEnergy, setSurplusEnergy] = useState(0);
	const [energySold, setEnergySold] = useState(0);
	const [energyBought, setEnergyBought] = useState(0);
	const navigate = useNavigate();

	const handleBuy = (region, amount) => {
		if (!amount || amount <= 0) {
			alert("Enter a valid buy amount!");
			return;
		}

		// POST req to buy
		axios
			.post("/buy", { region, amount })
			.then((response) => {
				alert(`Successfully bought ${amount}kW from ${region}`);
				setEnergyBought((prev) => prev + amount);
			})
			.catch((error) => {
				console.error("Error buying energy:", error);
				alert("Failed to buy energy.");
			});
	};

	const handleSell = () => {
		// POST req to sell surplus
		axios
			.post("/sell", { amount: surplusEnergy })
			.then((response) => {
				alert(`Successfully sold ${surplusEnergy}kW!`);
				setEnergySold((prev) => prev + surplusEnergy);
				setSurplusEnergy(0);
			})
			.catch((error) => {
				console.error("Error selling energy:", error);
				alert("Failed to sell energy.");
			});
	};

	useEffect(() => {
		const fetchUserEnergy = async () => {
			try {
				// userId
				const userId = localStorage.getItem("userId");
				if (!userId) {
					console.error("User ID not found in localStorage!");
					return;
				}

				// Fetch surplus
				const response = await axios.get(`/getUserEnergy`, {
					params: { userId },
				});
				setSurplusEnergy(response.data);
			} catch (error) {
				console.error("Error fetching user energy:", error);
			}
		};

		fetchUserEnergy();
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
							<span className="text-indigo-600 font-bold">{energySold}kW</span>
						</p>
						<p className="text-gray-700">
							Energy bought:{" "}
							<span className="text-indigo-600 font-bold">
								{energyBought}kW
							</span>
						</p>
					</div>
					<div>
						<p className="text-gray-700">
							Surplus energy:{" "}
							<span className="text-indigo-600 font-bold">
								{surplusEnergy}kW
							</span>
						</p>
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
				<button
					onClick={() => navigate("/transactions")}
					className="bg-gray-200 text-gray-700 px-6 py-3 rounded-full"
				>
					TRANSACTIONS
				</button>
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
						{energyData.map((entry) => (
							<tr key={entry.region} className="even:bg-gray-100">
								<td className="p-4 border border-gray-300">{entry.region}</td>
								<td className="p-4 border border-gray-300">{entry.energy}kW</td>
								<td className="p-4 border border-gray-300">
									{entry.price} ETH
								</td>
								<td className="p-4 border border-gray-300">
									<input
										type="number"
										placeholder="Enter buy amount"
										onChange={(e) => (entry.buyAmount = e.target.value)}
										className="border border-gray-300 p-2 rounded w-full"
									/>
									<button
										onClick={() =>
											handleBuy(entry.region, parseInt(entry.buyAmount))
										}
										className="bg-indigo-600 text-white px-4 py-2 rounded-full mt-2 w-full"
									>
										BUY
									</button>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			</section>
		</div>
	);
};

export default MarketPage;
