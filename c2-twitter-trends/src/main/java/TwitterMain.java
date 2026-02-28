import java.util.*;
import java.io.*;

/**
 * Main class which calls {@link TwitterBot} and {@link TwitterTrends} methods.
 */
public class TwitterMain {
    public static void main(String[] args) throws FileNotFoundException {
        // Create and print a list of Tweet objects from the cat folder
        List<Tweet> catTweets = scrapeTweets();
        for (Tweet tweet : catTweets) {
            System.out.println(tweet);
            System.out.println();
        }

        // Instantiate TweetBot
        TweetBot catBot = new TweetBot(catTweets);

        // TODO: Instantiate TwitterTrends Object
        TwitterTrends trends = new TwitterTrends(catBot);

        // TODO: Call TwitterTrends getMostFrequentWord() here
        String mostFrequentWord = trends.getMostFrequentWord();

        // TODO: Print out output from getMostFrequentWord()
        System.out.println(mostFrequentWord);

        // TODO: Call your TwitterTrends extension here:
        Tweet mostTrending = trends.getMostTrendingTweet();

        // TODO: Uncomment the code below to print out tweets after your extension is
        // implemented
        Map<Tweet, Double> trending = trends.trendingTweets();
        trending.get(mostTrending);

        System.out.println("The most trending tweet has " + mostTrending.getLikes() + " likes and "
                + mostTrending.getRetweets() + " retweets with a trending score of "
                + trending.get(mostTrending));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // PROVIDED SETUP CODE
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs a new list with the Tweet objects based on the content. found in
     * the cats folder and tweet_info file.
     *
     * @return the list of tweets
     */
    @SuppressWarnings("checkstyle:JavadocMethod")
    public static List<Tweet> scrapeTweets() throws FileNotFoundException {
        List<Tweet> tweets = new ArrayList<>();
        Scanner instagramScraper = new Scanner(new File("cats/tweet_info.txt"));
        while (instagramScraper.hasNext()) {
            String title = instagramScraper.nextLine();
            String caption = "";
            String date = "";
            while (!hasDate(date) && instagramScraper.hasNextLine()) {
                String line = instagramScraper.nextLine();
                if (hasDate(line)) { // end of tweet in file
                    date += line;
                    instagramScraper.nextLine();
                } else {
                    caption += "\n" + line;
                }
            }
            caption = caption.substring(1); // remove starting \n
            tweets.add(new Tweet("cats/" + title + ".jpg", caption, date));
        }
        return tweets;
    }

    /**
     * Checks if the provided String line case-insenseitivly contains one of the
     * twelve months of the year.
     *
     * @param line
     *            The provided line of text to search through
     *
     * @return boolean result for if at least one month is in provided String
     */
    public static boolean hasDate(String line) {
        line = line.toUpperCase();
        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST",
                "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        for (String month : months) {
            if (line.contains(month)) {
                return true;
            }
        }
        return false;
    }
}
