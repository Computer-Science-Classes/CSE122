// import java.io.*;
//
// /**
// * Stonks class runs a simulation of stock trading.
// *
// * Within the stock simulation, one can add or subtract shares from their
// * portfolio, display their portfolio's current standing, or save their
// * portfolio to a file of choice.
// */
// public class Stonks {
// /**
// * The available stock tickers, e.g. AAPL.
// */
// private String[] stocks;
//
// /**
// * The buying prices of all the {@code stocks}.
// */
// private double[] prices;
//
// /**
// * The amount of stock owned.
// */
// private double[] portfolio;
//
// /**
// * Constructs a {@code Stonk}.
// *
// * @param stocks
// * the available stock tickers, e.g. AAPL.
// * @param prices
// * the buying price of all the {@code stocks}.
// */
// Stonks(String[] stocks, double[] prices) {
// this.stocks = stocks;
// this.prices = prices;
// portfolio = setupPortfolio();
// }
//
// /**
// * Buys an amount of shares of stock based on {@code budget}.
// *
// * <p>
// * Calculates the amount of shares from the budget / price.
// *
// * @param symbol
// * stock ticker, e.g. AAPL.
// * @param budget
// * amount of the shares of stock bought.
// *
// * @return {@code true} if buying was successful and {@code false} if not.
// */
// public boolean buy(String symbol, double budget) {
// if (budget < 5) {
// System.out.println("Budget must be at least $5");
// return false;
// }
//
// // Calculate the amount of stock that can be bought with budget
// // and add it to portfolio
// double price = getPrice(symbol);
// if (price <= 0) {
// System.out.println("Invalid stock symbol: " + symbol);
// return false;
// }
// double shares = budget / price;
// if (!addSharesToPortfolio(symbol, shares)) {
// System.out.println("Invalid stock symbol: " + symbol);
// return false;
// }
//
// System.out.println("You successfully bought " + symbol + ".");
//
// return true;
// }
//
// /**
// * Sells the user provided amount of shares of a stock.
// *
// * @param symbol
// * stock ticker, e.g. AAPL.
// * @param sharesToSell
// * desired amount of shares to be sold.
// *
// * @return {@code true} if buying was successful and {@code false} if not.
// */
// public boolean sell(String symbol, double sharesToSell) {
// if (sharesToSell <= 0) {
// System.out.println("Shares to sell must be positive.");
// return false;
// }
//
// if (!subtractSharesFromPortfolio(symbol, sharesToSell)) {
// System.out.println("You do not have enough shares of " + symbol + " to sell "
// + sharesToSell + " shares.");
// return false;
// }
//
// System.out.println("You successfully sold " + sharesToSell + " shares of " +
// symbol + ".");
// return true;
// }
//
// /**
// * Displays the current state of the portfolio without showing 0.0 values.
// */
// public void display() {
// System.out.println();
// System.out.println("Portfolio:");
// for (int i = 0; i < portfolio.length; i++) {
// if (portfolio[i] != 0.0) { // Do not display entries without added values
// System.out.println(stocks[i] + " " + Double.toString(portfolio[i]));
// }
// }
// }
//
// /**
// * Saves the current state of the portfolio to a file specified by the user.
// *
// * @param filename
// * filename of file to save portfolio to.
// *
// * @throws FileNotFoundException
// * when user inputted file is not found.
// */
// public void save(String filename) throws FileNotFoundException {
// PrintStream ps = new PrintStream(new File(filename));
//
// for (int i = 0; i < portfolio.length; i++) {
// if (portfolio[i] != 0.0) {
// ps.println(stocks[i] + " " + Double.toString(portfolio[i]));
// }
// }
// }
//
// /**
// * Gets the valuation of the given portfolio by getting the shares and
// * converting them to dollars and adding them up.
// *
// * @return valuation of portfolio based on its owned shares
// */
// public double getPortfolioValuation() {
// double valuation = 0;
//
// for (int i = 0; i < portfolio.length; i++) {
// double shares = portfolio[i];
// if (shares != 0.0) {
// double price = prices[i];
// valuation += shares * price;
// }
// }
//
// return valuation;
// }
//
// /**
// * Initializes and populates the portfolio 2d array with the available symbols
// * and 0.0 shares.
// *
// * @return the standard portfolio setup
// */
// private double[] setupPortfolio() {
// double[] portfolio = new double[stocks.length];
//
// // Populate portfolio
// for (int i = 0; i < portfolio.length; i++) {
// portfolio[i] = 0.0;
// }
// return portfolio;
// }
//
// /**
// * Add shares to the portfolio.
// *
// * @param symbol
// * stock ticker, e.g. AAPL
// * @param shares
// * amount of the shares of stock bought
// * @return {@code true} if succeeded and {@code false} if not
// */
// private boolean addSharesToPortfolio(String symbol, double shares) {
// int idx = findSymbolIndex(symbol);
// if (idx == -1) {
// return false;
// }
//
// portfolio[idx] = portfolio[idx] + shares;
// return true;
// }
//
// /**
// * Subtract shares from the portfolio.
// *
// * @param symbol
// * stock ticker, e.g. AAPL
// * @param sharesToSell
// * amount of shares of stock to be sold
// * @return true if succeeded and false if not
// */
// private boolean subtractSharesFromPortfolio(String symbol, double
// sharesToSell) {
// int idx = findSymbolIndex(symbol);
// if (idx == -1) {
// return false;
// }
//
// double subtracted = portfolio[idx] - sharesToSell;
// if (subtracted >= 0) {
// portfolio[idx] = subtracted;
// return true;
// }
//
// return false;
// }
//
// /**
// * Gets the price of a stock from its symbol.
// *
// * @param symbol
// * stock ticker, e.g. AAPL
// * @return the price of stock given its symbol
// */
// private double getPrice(String symbol) {
// int idx = findSymbolIndex(symbol);
//
// if (idx == -1) {
// return -1.0;
// }
//
// return prices[idx];
// }
//
// /**
// * Finds the index of a symbol in the given symbols array.
// *
// * @param symbol
// * stock ticker, e.g. AAPL
// * @return the index of the stock symbol in the stocks array
// */
// private int findSymbolIndex(String symbol) {
// for (int i = 0; i < stocks.length; i++) {
// if (stocks[i].equalsIgnoreCase(symbol)) {
// return i;
// }
// }
// return -1;
// }
//
// }
