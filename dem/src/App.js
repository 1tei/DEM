import React from "react";
import {
	BrowserRouter as Router,
	Routes,
	Route,
	Navigate,
} from "react-router-dom";
import Navbar from "./components/Navbar";
import Wallet from "./components/Wallet";
import Dashboard from "./pages/Dashboard";
import Register from "./pages/RegisterPage";
import Login from "./pages/LoginPage";
import Trading from "./pages/TradingPage";

const App = () => {
	const userId = localStorage.getItem("userId");

	return (
		<Router>
			<div className="min-h-screen bg-gray-100">
				<Navbar />
				<div className="container mx-auto p-4">
					<Routes>
						<Route path="/" element={<Dashboard />} />
						<Route path="/register" element={<Register />} />
						<Route path="/login" element={<Login />} />
						{/* Should be private, i do not have backend */}
						{/* <Route path="/wallet" element={<Wallet />} />
						<Route path="/trading" element={<Trading />} /> */}

						{/* Private routes */}
						<Route
							path="/wallet"
							element={userId ? <Wallet /> : <Navigate to="/login" />}
						/>
						<Route
							path="/trading"
							element={userId ? <Trading /> : <Navigate to="/login" />}
						/>
					</Routes>
				</div>
			</div>
		</Router>
	);
};

export default App;
