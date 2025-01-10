import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const RegisterPage = () => {
	const navigate = useNavigate();

	const [formData, setFormData] = useState({
		username: "",
		name: "",
		region: "",
		address: "",
		email: "",
		total_energy_produced: "",
		total_energy_consumed: "",
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
			name: formData.name,
			region: formData.region,
			address: formData.address,
			email: formData.email,
			password: formData.password,
		};

		axios
			.post("http://localhost:8080/add/user", requestBody)
			.then((response) => {
				console.log("User registered successfully", response.data);
				alert("User registered successfully");
				navigate("/login");
			})
			.catch((error) => {
				console.error("Error during registration!", error);
				alert("Error during registration!");
			});
	};

	return (
		<div className="flex justify-center items-center bg-gray-100">
			<form
				className="bg-white p-8 rounded w-full max-w-md"
				onSubmit={handleSubmit}
			>
				<h2 className="text-2xl font-bold mb-6">Register</h2>
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
				<div className="mb-4">
					<label className="block text-gray-700 mb-2" htmlFor="name">
						Name
					</label>
					<input
						type="text"
						id="name"
						name="name"
						value={formData.name}
						onChange={handleChange}
						className="w-full p-2 border border-gray-300 rounded"
						required
					/>
				</div>
				<div className="mb-4">
					<label className="block text-gray-700 mb-2" htmlFor="region">
						Region
					</label>
					<input
						type="text"
						id="region"
						name="region"
						value={formData.region}
						onChange={handleChange}
						className="w-full p-2 border border-gray-300 rounded"
						required
					/>
				</div>
				<div className="mb-4">
					<label className="block text-gray-700 mb-2" htmlFor="address">
						Address
					</label>
					<input
						type="text"
						id="address"
						name="address"
						value={formData.address}
						onChange={handleChange}
						className="w-full p-2 border border-gray-300 rounded"
						required
					/>
				</div>
				<div className="mb-4">
					<label className="block text-gray-700 mb-2" htmlFor="email">
						Email
					</label>
					<input
						type="email"
						id="email"
						name="email"
						value={formData.email}
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
					className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
				>
					Register
				</button>
			</form>
		</div>
	);
};

export default RegisterPage;
