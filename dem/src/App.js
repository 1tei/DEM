import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Wallet from "./components/Wallet";
import Dashboard from "./pages/Dashboard";
import Register from "./pages/RegisterPage";
import Login from "./pages/LoginPage";

const App = () => (
	<Router>
		<div className="min-h-screen bg-gray-100">
			<Navbar />
			<div className="container mx-auto p-4">
				<Routes>
					<Route path="/" element={<Dashboard />} />
					<Route path="/wallet" element={<Wallet />} />
					<Route path="/register" element={<Register />} />
					<Route path="/login" element={<Login />} />
				</Routes>
			</div>
		</div>
	</Router>
);

export default App;
