// import org.junit.jupiter.api.*;
// import static org.junit.jupiter.api.Assertions.*;
//
// import java.io.*;
// import java.util.*;
//
// // TODO: Your JUnit testing code here!
// /**
// * Tests for the Stonks class.
// */
// public class StonksTests {
// // @Test
// // TODO: Your JUnit testing code here!
//
// /**
// * Tests that buying a valid stock with a valid budget succeeds and that this
// * updates the portfolio valuation correctly.
// */
// @Test
// public void testBuyValidStock() {
// String[] stocks = {"AAPL", "MSFT"};
// double[] prices = {100.0, 200.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// boolean result = stonks.buy("AAPL", 500.0);
//
// assertTrue(result);
// assertEquals(500.0, stonks.getPortfolioValuation(), 0.0001);
// }
//
// /**
// * Tests that buying with a budget under 5 dollars fails.
// */
// @Test
// public void testBuyBudgetTooSmall() {
// String[] stocks = {"AAPL"};
// double[] prices = {100.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// boolean result = stonks.buy("AAPL", 4.0);
//
// assertFalse(result);
// assertEquals(0.0, stonks.getPortfolioValuation(), 0.0001);
// }
//
// /**
// * Tests that buying an invalid stock symbol fails.
// */
// @Test
// public void testBuyInvalidSymbol() {
// String[] stocks = {"AAPL"};
// double[] prices = {100.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// boolean result = stonks.buy("GOOG", 100.0);
//
// assertFalse(result);
// assertEquals(0.0, stonks.getPortfolioValuation(), 0.0001);
// }
//
// /**
// * Tests that selling valid shares succeeds and updates valuation.
// */
// @Test
// public void testSellValidShares() {
// String[] stocks = {"AAPL"};
// double[] prices = {100.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// stonks.buy("AAPL", 500.0);
// boolean result = stonks.sell("AAPL", 2.0);
//
// assertTrue(result);
// }
//
// /**
// * Tests that selling a non positive number of shares fails.
// */
// @Test
// public void testSellNonPositiveShares() {
// String[] stocks = {"AAPL"};
// double[] prices = {100.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// stonks.buy("AAPL", 500.0);
// boolean result = stonks.sell("AAPL", 0.0);
//
// assertFalse(result);
// assertEquals(500.0, stonks.getPortfolioValuation(), 0.0001);
// }
//
// /**
// * Tests that selling more shares than owned fails.
// */
// @Test
// public void testSellTooManyShares() {
// String[] stocks = {"AAPL"};
// double[] prices = {100.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// stonks.buy("AAPL", 200.0);
// boolean result = stonks.sell("AAPL", 3.0);
//
// assertFalse(result);
// assertEquals(200.0, stonks.getPortfolioValuation(), 0.0001);
// }
//
// /**
// * Tests the valuation of a portfolio with multiple stocks.
// */
// @Test
// public void testGetPortfolioValuationMultipleStocks() {
// String[] stocks = {"AAPL", "MSFT"};
// double[] prices = {100.0, 200.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// stonks.buy("AAPL", 300.0);
// stonks.buy("MSFT", 400.0);
//
// assertEquals(700.0, stonks.getPortfolioValuation(), 0.0001);
// }
//
// /**
// * Tests that saving writes the owned stocks and share counts to a file.
// *
// * @throws FileNotFoundException
// * when save file cannot be found.
// */
// @Test
// public void testSave() throws FileNotFoundException {
// String[] stocks = {"AAPL", "MSFT"};
// double[] prices = {100.0, 200.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// stonks.buy("AAPL", 300.0);
// stonks.buy("MSFT", 400.0);
//
// String filename = "testPortfolio.txt";
// stonks.save(filename);
//
// Scanner sc = new Scanner(new File(filename));
//
// String line1 = sc.nextLine();
// String line2 = sc.nextLine();
//
// assertEquals("AAPL 3.0", line1);
// assertEquals("MSFT 2.0", line2);
// }
//
// /**
// * Calls display explicitly to ensure the method runs.
// */
// @Test
// public void testDisplayCalled() {
// String[] stocks = {"AAPL"};
// double[] prices = {100.0};
// Stonks stonks = new Stonks(stocks, prices);
//
// stonks.buy("AAPL", 200.0);
// stonks.display();
//
// assertEquals(200.0, stonks.getPortfolioValuation(), 0.0001);
// }
// }
