import java.time.*;
import java.time.temporal.*;
import java.util.*;

/**
 * An analysis helper over a {@link TweetBot} that computes simple trending and
 * word frequency statistics from the bot's body of tweets.
 *
 * <p>
 * This class consumes tweets by calling {@link TweetBot#nextTweet()}, which
 * moves tweets from the bot's unviewed queue into its viewed queue. Each public
 * analysis method restores the bot back to its original ordering by calling
 * {@link TweetBot#reset()}.
 *
 * <p>
 * This class maintains two maps:
 * <ul>
 * <li><b>wordCount</b>: maps each lowercased token in tweet captions to its
 * frequency (i.e. word count)</li>
 * <li><b>trending</b>: maps each {@link Tweet} to its computed trending
 * score</li>
 * </ul>
 *
 * @implNote The maps are instance fields and are not cleared inside the
 *           analysis methods.
 */
public class TwitterTrends {
    private final TweetBot bot;
    private final Map<String, Integer> wordCount = new HashMap<>();
    private final Map<Tweet, Double> trending = new HashMap<>();

    /**
     * Constructs a {@code TwitterTrends} analyzer which draws tweets from the given
     * {@link TweetBot}.
     *
     * @param bot
     *            the tweet source used for analysis.
     */
    public TwitterTrends(TweetBot bot) {
        this.bot = bot;
    }

    /**
     * Computes and returns the most frequent word appearing in tweet captions.
     *
     * <p>
     * Captions are lowercased and tokenized using {@code Scanner} whitespace rules.
     * The {@link TweetBot} is restored to its initial ordering before returning.
     *
     * @return the most frequent lowercased token observed.
     */
    public String getMostFrequentWord() {
        for (int i = 0; i < bot.numTweets(); i++) {
            String caption = bot.nextTweet().getCaption().toLowerCase();

            Scanner word = new Scanner(caption);
            while (word.hasNext()) {
                String curWord = word.next();
                if (!wordCount.containsKey(curWord)) {
                    wordCount.put(curWord, 1);
                } else {
                    wordCount.put(curWord, wordCount.get(curWord) + 1);
                }
            }
        }

        String mostFreqWord = "";
        int bestCount = -1;

        for (String key : wordCount.keySet()) {
            int count = wordCount.get(key);
            if (count > bestCount) {
                bestCount = count;
                mostFreqWord = key;
            }
        }

        bot.reset();
        return mostFreqWord;
    }

    /**
     * Computes and returns the single most "trending" tweet according to the
     * scoring rule implemented below.
     *
     * <p>
     * For each tweet, this method computes a score using its likes, retweets, and
     * age. The {@link TweetBot} is restored to its initial ordering before
     * returning.
     *
     * <p>
     * Scoring:
     *
     * <pre>
     * d = months_between(tweetDate, today) / 12.0
     * e = likes + 3 * retweets
     * s = log((e * 2^(-d)) / 1000)
     * trending = 100 * (1 - exp(-s / 2))
     * </pre>
     *
     * @return the tweet with the highest computed trending score, or {@code null}
     *         if no tweets are processed.
     */
    public Tweet getMostTrendingTweet() {

        for (int i = 0; i < bot.numTweets(); i++) {
            Tweet cur = bot.nextTweet();
            String date = cur.getDate();
            int likes = cur.getLikes();
            int retweets = cur.getRetweets();

            LocalDate today = LocalDate.now();
            double d = ChronoUnit.MONTHS.between(formatStringDate(date), today) / 12.0;

            double e = likes + 3.0 * retweets;
            double s = Math.log((e * Math.pow(2.0, -d)) / 1000.0);
            double curtrending = 100.0 * (1.0 - Math.exp(-s / 2.0));

            trending.put(cur, curtrending);
        }

        Tweet trendingTweet = null;
        double trendingValue = -1;

        for (Tweet tweet : trending.keySet()) {
            double trend = trending.get(tweet);
            if (trend > trendingValue) {
                trendingValue = trend;
                trendingTweet = tweet;
            }
        }

        bot.reset();
        return trendingTweet;
    }

    /**
     * Returns the map of tweets to their computed trending scores.
     *
     * @return the trending score map.
     */
    public Map<Tweet, Double> trendingTweets() {
        return trending;
    }

    /**
     * Converts a date string in the tweet's date format into a {@code LocalDate}.
     *
     * @param date
     *            the tweet date string.
     * @return the parsed {@link LocalDate}.
     * @throws IllegalArgumentException
     *             if the month name is invalid.
     */
    private LocalDate formatStringDate(String date) {
        String formatted = "";
        Scanner word = new Scanner(date);

        while (word.hasNext()) {
            String month = word.next();
            String day = word.next();
            String year = word.next();

            String formattedMonth = getMonthNumberFromName(month);
            if (formattedMonth == null) {
                throw new IllegalArgumentException();
            }

            String formattedDay = day.substring(0, day.length() - 1);
            if (Integer.parseInt(formattedDay) < 9) {
                formattedDay = "0" + formattedDay;
            }

            formatted = year + "-" + formattedMonth + "-" + formattedDay;
        }

        return LocalDate.parse(formatted);
    }

    /**
     * Maps a named month to its two-digit month number.
     *
     * @param month
     *            the month name (e.g., "January").
     * @return a two-digit month number (e.g., "01") or {@code null} if invalid.
     * @throws IllegalArgumentException
     *             if {@code month} is {@code null}.
     */
    private static String getMonthNumberFromName(String month) {
        if (month == null) {
            throw new IllegalArgumentException();
        }

        if (month.equalsIgnoreCase("January")) {
            return "01";
        }

        if (month.equalsIgnoreCase("February")) {
            return "02";
        }

        if (month.equalsIgnoreCase("March")) {
            return "03";
        }

        if (month.equalsIgnoreCase("April")) {
            return "04";
        }

        if (month.equalsIgnoreCase("May")) {
            return "05";
        }

        if (month.equalsIgnoreCase("June")) {
            return "06";
        }

        if (month.equalsIgnoreCase("July")) {
            return "07";
        }

        if (month.equalsIgnoreCase("August")) {
            return "08";
        }

        if (month.equalsIgnoreCase("September")) {
            return "09";
        }

        if (month.equalsIgnoreCase("October")) {
            return "10";
        }

        if (month.equalsIgnoreCase("November")) {
            return "11";
        }

        if (month.equalsIgnoreCase("December")) {
            return "12";
        }

        return null; // invalid month
    }

}
