import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
	const navigate = useNavigate();

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
			const loginUrl = `http://localhost:8080/loginUser?login=${encodeURIComponent(
				formData.username
			)}`;
			const loginResponse = await axios.get(loginUrl);
			const userId = loginResponse.data;
			localStorage.setItem("userId", userId);

			const userUrl = `http://localhost:8080/getUser?userId=${encodeURIComponent(
				userId
			)}`;
			const userResponse = await axios.get(userUrl);
			const { region } = userResponse.data;

			// region
			localStorage.setItem("region", region);

			alert("User logged in successfully");

			navigate("/trading");
		} catch (error) {
			console.error("There was an error during login!", error);
			alert("Invalid username or password!");
		}
	};

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
