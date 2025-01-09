const SimpleStorage = artifacts.require("EnergyMarket");

module.exports = function (deployer) {
  deployer.deploy(SimpleStorage);
};