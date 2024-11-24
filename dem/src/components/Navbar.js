import React from "react";

const Navbar = () => (
	<nav className="p-4 bg-gray-800 text-white flex justify-between items-center">
		<span className="text-xl font-bold">Energy Blockchain</span>
		<div className="flex items-center">
			<a href="/" className="text-white font-bold py-1 px-4 rounded">
				Dashboard
			</a>
			<a href="/trading" className="text-white font-bold py-1 px-4 rounded">
				Trading
			</a>
			<div className="ml-4">
				<a href="/register">
					<button className="bg-blue-500 hover:text-gray-400 text-white font-bold py-1 px-4 rounded mx-2">
						Register
					</button>
				</a>
				<a href="/login">
					<button className="bg-green-500 hover:text-gray-400 text-white font-bold py-1 px-4 rounded mx-2">
						Login
					</button>
				</a>
			</div>
		</div>
	</nav>
);

export default Navbar;
