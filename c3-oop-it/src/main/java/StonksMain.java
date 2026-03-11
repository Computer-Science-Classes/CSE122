// import java.io.*;
// import java.util.*;
//
// /**
// * Main class for {@link Stonks}.
// */
// public class StonksMain {
//
// public static void main(String[] args) throws FileNotFoundException {
// Scanner sc = new Scanner(System.in);
// System.out.print("Enter stocks file name: ");
// String filename = sc.next();
//
// Scanner fileScan = new Scanner(new File(filename));
// int entries = fileScan.nextInt();
//
// String[] stocks = new String[entries];
// double[] prices = new double[entries];
//
// getStocksFromFile(filename, stocks, prices);
//
// Stonks stonks = new Stonks(stocks, prices);
//
// System.out.println();
// System.out.println("Welcome to the CSE 122 Stocks Simulator!");
//
// System.out.println("There are " + entries + " stocks on the market:");
// for (int i = 0; i < entries; i++) {
// System.out.println(stocks[i] + ": " + prices[i]);
// }
//
// boolean quit = false;
// while (!quit) {
// System.out.println();
// System.out.println("Menu: (B)uy, (Se)ll, (D)isplay, (S)ave, (Q)uit");
// System.out.print("Enter your choice: ");
// String input = sc.next();
//
// if (input.equalsIgnoreCase("B")) {
// System.out.print("Enter the stock ticker: ");
// String symbol = sc.next();
//
// System.out.print("Enter your budget: ");
// double budget = sc.nextDouble();
//
// stonks.buy(symbol, budget);
// } else if (input.equalsIgnoreCase("Se")) {
// System.out.print("Enter the stock ticker: ");
// String symbol = sc.next();
//
// System.out.print("Enter the number of shares to sell: ");
// double sharesToSell = sc.nextDouble();
//
// stonks.sell(symbol, sharesToSell);
// } else if (input.equalsIgnoreCase("D")) {
// stonks.display();
// } else if (input.equalsIgnoreCase("S")) {
// System.out.print("Enter new portfolio file name: ");
// filename = sc.next();
// stonks.save(filename);
// } else if (input.equalsIgnoreCase("Q")) {
// quit = true;
// double portfolioValuation = stonks.getPortfolioValuation();
// System.out.println();
// System.out.println("Your portfolio is currently valued at: $" +
// portfolioValuation);
// } else {
// System.out.println("Invalid choice: " + input);
// System.out.println("Please try again");
// }
// }
// }
//
// /**
// * Loads the symbols from a TSV file.
// *
// * <p>
// * This reads the symbol and price for each entry and stores them into the
// * corresponding indices of {@code symbols} and {@code prices}. The P/E ratio
// * and yield values are ignored.
// *
// * @param filename
// * the name of the file to fetch the stock symbols from
// * @param stocks
// * the array that will be populated with stock ticker symbols
// * @param prices
// * the array that will be populated with stock prices
// * @throws FileNotFoundException
// * when user inputted file is not found
// */
// private static void getStocksFromFile(String filename, String[] stocks,
// double[] prices)
// throws FileNotFoundException {
// Scanner fileScan = new Scanner(new File(filename));
//
// int entries = fileScan.nextInt();
//
// fileScan.nextLine(); // finish count line
// fileScan.nextLine(); // skip header line
//
// for (int line = 0; line < entries; line++) {
// stocks[line] = fileScan.next(); // symbol
// prices[line] = fileScan.nextDouble(); // price
// fileScan.nextDouble(); // p/e
// fileScan.nextDouble(); // yield
// }
// }
//
// }
