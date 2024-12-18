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

	const handleSubmit = (e) => {
		e.preventDefault();

		const requestBody = {
			username: formData.username,
			password: formData.password,
		};

		axios
			.post("http://localhost:8080/login", requestBody)
			.then((response) => {
				console.log("User logged in successfully", response.data);
				alert("User logged in successfully");
				// Handle successful login, such as saving a token or redirecting
			})
			.catch((error) => {
				console.error("There was an error logging in!", error);
				alert("Invalid username or password!");
			});
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
