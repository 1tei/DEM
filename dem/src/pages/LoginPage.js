import React, { useState } from "react";
import axios from "axios";

const Login = () => {
	const [formData, setFormData] = useState({
		username: "",
		password: "",
	});

	const handleChange = (e) => {
		const { name, value } = e.target;
		setFormData({
			...formData,
			[name]: value,
		});
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		try {
			const loginUrl = `http://localhost:8080/loginUser`;
			const loginResponse = await axios.post(loginUrl, {
				username: formData.username,
				// password: formData.password
			});
			const userId = loginResponse.data;
			localStorage.setItem("userId", userId);

			const userUrl = `http://localhost:8080/getUser?userId=${encodeURIComponent(
				userId
			)}`;
			const userResponse = await axios.get(userUrl);

			const { region } = userResponse.data;
			localStorage.setItem("region", region);

			alert("User logged in successfully");
		} catch (error) {
			console.error("There was an error during login!", error);
			alert("Invalid username or password!");
		}
	};

	// const getUserInfo = async () => {
	// 	try {
	// 		const userId = localStorage.getItem("userId");
	// 		if (!userId) {
	// 			console.error("User ID not found in localStorage!");
	// 			return;
	// 		}
	// 		const userUrl = `http://localhost:8080/getUser?userId=${encodeURIComponent(
	// 			userId
	// 		)}`;
	// 		const response = await axios.get(userUrl);
	// 		console.log("User information retrieved:", response.data);
	// 		const { region, username, name } = response.data;
	// 		console.log(`Region: ${region}, Username: ${username}, Name: ${name}`);
	// 		localStorage.setItem("region", region);
	// 		return response.data;
	// 	} catch (error) {
	// 		console.error("Error fetching user information:", error);
	// 		alert("Failed to fetch user information!");
	// 	}
	// };

	return (
		<div className="flex justify-center items-center min-h-screen bg-gray-100">
			<form
				className="bg-white p-8 rounded shadow-md w-full max-w-md"
				onSubmit={handleSubmit}
			>
				<h2 className="text-2xl font-bold mb-6">Login</h2>
				<div className="mb-4">
					<label className="block text-gray-700 mb-2" htmlFor="username">
						Username
					</label>
					<input
						type="text"
						id="username"
						name="username"
						value={formData.username}
						onChange={handleChange}
						className="w-full p-2 border border-gray-300 rounded"
						required
					/>
				</div>
				<div className="mb-6">
					<label className="block text-gray-700 mb-2" htmlFor="password">
						Password
					</label>
					<input
						type="password"
						id="password"
						name="password"
						value={formData.password}
						onChange={handleChange}
						className="w-full p-2 border border-gray-300 rounded"
						required
					/>
				</div>
				<button
					type="submit"
					className="w-full bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
				>
					Login
				</button>
			</form>
		</div>
	);
};

export default Login;
