import java.util.*;
import java.io.*;

/**
 * Stonks class runs a simulation of stock trading.
 *
 * Within the stock simulation, one can add or subtract shares from their
 * portfolio, display their portfolio's current standing, or save their
 * portfolio to a file of choice.
 */
public class Stonks {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter stocks file name: ");
        String filename = sc.next();

        String[] stocks = getSymbolsFromFile(filename);
        double[] prices = getPricesFromFile(filename);

        System.out.println();
        System.out.println("Welcome to the CSE 122 Stocks Simulator!");

        int stockEntries = stocks.length;
        System.out.println("There are " + stockEntries + " stocks on the market:");
        for (int i = 0; i < stocks.length; i++) {
            System.out.println(stocks[i] + ": " + prices[i]);
        }

        double[] portfolio = setupPortfolio(stocks);
        boolean quit = false;

        while (!quit) {
            System.out.println();
            System.out.println("Menu: (B)uy, (Se)ll, (D)isplay, (S)ave, (Q)uit");
            System.out.print("Enter your choice: ");
            String input = sc.next();

            if (input.equalsIgnoreCase("B")) {
                buyingSimulation(stocks, prices, portfolio, sc);
            } else if (input.equalsIgnoreCase("Se")) {
                sellingSimulation(stocks, portfolio, sc);
            } else if (input.equalsIgnoreCase("D")) {
                displaySimulation(stocks, portfolio);
            } else if (input.equalsIgnoreCase("S")) {
                saveSimulation(stocks, portfolio, sc);
            } else if (input.equalsIgnoreCase("Q")) {
                quit = true;
                double portfolioValuation = getPortfolioValuation(prices, portfolio);
                System.out.println();
                System.out.println("Your portfolio is currently valued at: $" + portfolioValuation);
            } else {
                System.out.println("Invalid choice: " + input);
                System.out.println("Please try again");
            }
        }

        sc.close();
    }

    /**
     * @brief Initializes and populates the portfolio 2d array with the available
     *        symbols and 0.0 shares.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @return the standard portfolio setup
     */
    public static double[] setupPortfolio(String[] stocks) {
        double[] portfolio = new double[stocks.length];

        // Populate portfolio
        for (int i = 0; i < portfolio.length; i++) {
            portfolio[i] = 0.0;
        }
        return portfolio;
    }

    /**
     * @brief Buys an amount of shares of stock based on the user provided budget.
     *        Calculates the amount of shares from the budget / price.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param prices
     *        the buying price of one of all the given stocks
     * @param portfolio
     *        the amount of stock owned by the owner of the portfolio
     * @param input
     *        user input scanner
     * @return true if buying was successful and false if not
     */
    private static boolean buyingSimulation(String[] stocks, double[] prices, double[] portfolio,
            Scanner input) {
        System.out.print("Enter the stock ticker: ");
        String symbol = input.next();

        System.out.print("Enter your budget: ");
        double budget = input.nextDouble();

        if (budget < 5) {
            System.out.println("Budget must be at least $5");
            return false;
        }

        // Calculate the amount of stock that can be bought with budget
        // and add it to portfolio
        double price = getPrice(stocks, prices, symbol);
        if (price <= 0) {
            System.out.println("Invalid stock symbol: " + symbol);
            return false;
        }
        double shares = budget / price;
        if (!addSharesToPortfolio(stocks, portfolio, symbol, shares)) {
            System.out.println("Invalid stock symbol: " + symbol);
            return false;
        }

        System.out.println("You successfully bought " + symbol + ".");

        return true;
    }

    /**
     * Sells the user provided amount of shares of a stock.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param portfolio
     *        the amount of stock owned by the owner of the portfolio
     * @param input
     *        user input scanner
     * @return true if selling was successful and false if not
     */
    private static boolean sellingSimulation(String[] stocks, double[] portfolio, Scanner input) {
        System.out.print("Enter the stock ticker: ");
        String symbol = input.next();

        System.out.print("Enter the number of shares to sell: ");
        double sharesToSell = input.nextDouble();

        if (sharesToSell <= 0) {
            System.out.println("Shares to sell must be positive.");
            return false;
        }

        if (!subtractSharesFromPortfolio(stocks, portfolio, symbol, sharesToSell)) {
            System.out.println("You do not have enough shares of " + symbol + " to sell "
                    + sharesToSell + " shares.");
            return false;
        }

        System.out.println("You successfully sold " + sharesToSell + " shares of " + symbol + ".");
        return true;
    }

    /**
     * Displays the current state of the portfolio without showing 0.0 values.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param portfolio
     *        the amount of stock owned by the owner of the portfolio
     */
    private static void displaySimulation(String[] stocks, double[] portfolio) {
        System.out.println();
        System.out.println("Portfolio:");
        for (int i = 0; i < portfolio.length; i++) {
            if (portfolio[i] != 0.0) { // Do not display entries without added values
                System.out.println(stocks[i] + " " + Double.toString(portfolio[i]));
            }
        }
    }

    /**
     * Saves the current state of the portfolio to a file specified by the user.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param portfolio
     *        the amount of stock owned by the owner of the portfolio
     * @param input
     *        scanner object to read users file name
     * @throws FileNotFoundException
     *         when user inputted file is not found
     */
    private static void saveSimulation(String[] stocks, double[] portfolio, Scanner input)
            throws FileNotFoundException {
        System.out.print("Enter new portfolio file name: ");
        String filename = input.next();

        PrintStream ps = new PrintStream(new File(filename));

        for (int i = 0; i < portfolio.length; i++) {
            if (portfolio[i] != 0.0) {
                ps.println(stocks[i] + " " + Double.toString(portfolio[i]));
            }
        }

        ps.close();
    }

    /**
     * Loads the symbols from a TSV file.
     *
     * @param filename
     *        the name of the file to fetch the stock symbols from
     * @return an array of strings representing the stock symbols
     * @throws FileNotFoundException
     *         when user inputted file is not found
     */
    public static String[] getSymbolsFromFile(String filename) throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File(filename));

        int entries = fileScan.nextInt();
        String[] symbols = new String[entries];

        fileScan.nextLine(); // finish count line
        fileScan.nextLine(); // skip header line

        for (int line = 0; line < entries; line++) {
            symbols[line] = fileScan.next(); // symbol
            fileScan.next(); // price
            fileScan.next(); // p/e
            fileScan.next(); // yield
        }

        fileScan.close();
        return symbols;
    }

    /**
     * Loads the prices from the TSV file.
     *
     * @param filename
     *        the name of the file to fetch the stock symbols from
     * @return an array of strings representing the stock prices
     * @throws FileNotFoundException
     *         when user inputted file is not found
     */
    public static double[] getPricesFromFile(String filename) throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File(filename));

        int entries = fileScan.nextInt();
        double[] prices = new double[entries];

        fileScan.nextLine(); // finish count line
        fileScan.nextLine(); // skip header line

        for (int line = 0; line < entries; line++) {
            fileScan.next(); // symbol
            prices[line] = fileScan.nextDouble(); // price
            fileScan.next(); // p/e
            fileScan.next(); // y yield
        }

        fileScan.close();
        return prices;
    }

    /**
     * Finds the index of a symbol in the given symbols array.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param symbol
     *        stock ticker, e.g. AAPL
     * @return the index of the stock symbol in the stocks array
     */
    public static int findSymbolIndex(String[] stocks, String symbol) {
        for (int i = 0; i < stocks.length; i++) {
            if (stocks[i].equalsIgnoreCase(symbol)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Add shares to the portfolio.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param portfolio
     *        the amount of stock owned by the owner of the portfolio
     * @param symbol
     *        stock ticker, e.g. AAPL
     * @param shares
     *        amount of the shares of stock bought
     * @return true if succeeded and false if not
     */
    public static boolean addSharesToPortfolio(String[] stocks, double[] portfolio, String symbol,
            double shares) {
        int idx = findSymbolIndex(stocks, symbol);
        if (idx == -1) {
            return false;
        }

        portfolio[idx] = portfolio[idx] + shares;
        return true;
    }

    /**
     * Subtract shares from the portfolio.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param portfolio
     *        the amount of stock owned by the owner of the portfolio
     * @param symbol
     *        stock ticker, e.g. AAPL
     * @param sharesToSell
     *        amount of shares of stock to be sold
     * @return true if succeeded and false if not
     */
    public static boolean subtractSharesFromPortfolio(String[] stocks, double[] portfolio,
            String symbol, double sharesToSell) {
        int idx = findSymbolIndex(stocks, symbol);
        if (idx == -1) {
            return false;
        }

        double subtracted = portfolio[idx] - sharesToSell;
        if (subtracted >= 0) {
            portfolio[idx] = subtracted;
            return true;
        }

        return false;
    }

    /**
     * Gets the valuation of the given portfolio by getting the shares and
     * converting them to dollars and adding them up
     *
     * @param prices
     *        the buying price of one of all the given stocks
     * @param portfolio
     *        the amount of stock owned by the owner of the portfolio
     * @return valuation of portfolio based on its owned shares
     */
    public static double getPortfolioValuation(double[] prices, double[] portfolio) {
        double valuation = 0;

        for (int i = 0; i < portfolio.length; i++) {
            double shares = portfolio[i];
            if (shares != 0.0) {
                double price = prices[i];
                valuation += shares * price;
            }
        }

        return valuation;
    }

    /**
     * Gets the price of a stock from its symbol.
     *
     * @param stocks
     *        the available stock tickers, e.g. AAPL
     * @param prices
     *        the buying price of one of all the given stocks
     * @param symbol
     *        stock ticker, e.g. AAPL
     * @return the price of stock given its symbol
     */
    public static double getPrice(String[] stocks, double[] prices, String symbol) {
        int idx = findSymbolIndex(stocks, symbol);

        if (idx == -1) {
            return -1.0;
        }

        return prices[idx];
    }
}
