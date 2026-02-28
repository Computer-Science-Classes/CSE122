import java.util.*;

/**
 * A FIFO based wrapper over a collection of {@link Tweet}s that supports
 * iteration, insertion, removal, and reset to the initial state.
 *
 * <p>
 * This class maintains two queues:
 * <ul>
 * <li><b>unviewedTweets</b>: tweets yet to be seen</li>
 * <li><b>viewedTweets</b>: tweets returned via {@link #nextTweet()}</li>
 * </ul>
 *
 * <p>
 * Calling {@link #nextTweet()} moves a tweet from unviewed to viewed. Calling
 * {@link} {@link #reset()} restores the original ordering present at
 * construction.
 */
public class TweetBot {
    /**
     * {@link Tweet}s that have not been viewed yet, in FIFO ordering.
     */
    private final Queue<Tweet> unviewedTweets = new LinkedList<>();

    private final Queue<Tweet> viewedTweets = new LinkedList<>();

    /**
     * Constructs a {@code TweetBot} from an initial {@code List} of {@link Tweets}.
     *
     * <p>
     * All tweets are initially placed in {@code unviewedTweets}.
     *
     * @param tweets
     *            the initial tweets.
     *
     * @throws IllegalArgumentException
     *             if the given {@code tweets} is of size smaller than 1.
     */
    public TweetBot(List<Tweet> tweets) {
        if (tweets.size() < 1) {
            throw new IllegalArgumentException();
        }

        for (Tweet tweet : tweets) {
            this.unviewedTweets.add(tweet);
        }
    }

    /**
     * Returns the total number of tweets.
     *
     * @return the number, as an {@code int}, of {@code unviewedTweets} and
     *         {@code viewedTweets} added.
     */
    public int numTweets() {
        return unviewedTweets.size() + viewedTweets.size();
    }

    /**
     * Adds a tweet to the end of the {@code unviewed} {@code Queue}.
     *
     * @param tweet
     *            the {@link Tweet} that is to be added to {@code unviewedTweets}.
     */
    public void addTweet(Tweet tweet) {
        unviewedTweets.add(tweet);
    }

    /**
     * Returns the next unviewed {@link Tweet} in FIFO order.
     *
     * <p>
     * The returned tweet is moved to {@code viewedTweets}.
     *
     * @throws IllegalStateException
     *             if {@code unviewedTweets} is empty.
     * @return the next {@link Tweet}.
     */
    public Tweet nextTweet() {
        if (unviewedTweets.isEmpty()) {
            throw new IllegalStateException();
        }

        Tweet tweet = unviewedTweets.remove();
        viewedTweets.add(tweet);

        return tweet;
    }

    /**
     * Removes a tweet from the {@code viewedTweets} {@code Queue}.
     *
     * @param tweet
     *            the {@link Tweet} to be removed.
     *
     * @throws IllegalArgumentException
     *             if {@code viewedTweets} does not contain the given {@link Tweet}.
     */
    public void removeTweet(Tweet tweet) {
        boolean removed = false;

        int n = viewedTweets.size();
        for (int i = 0; i < n; i++) {
            Tweet cur = viewedTweets.remove();

            if (!removed && tweet.equals(cur)) {
                removed = true;
            } else {
                viewedTweets.add(cur);
            }
        }

        if (!removed) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the {@code viewedTweets} and {@code unviewedTweets} to their original
     * states.
     *
     * @implNote because of the needed rotation, time complexity is O(n).
     */
    public void reset() {
        int n = viewedTweets.size();
        for (int i = 0; i < n; i++) {
            unviewedTweets.add(viewedTweets.remove());
        }

        // Rotate left by unviewedTweets size - viewedTweets size
        int rotate = unviewedTweets.size() - n;
        for (int i = 0; i < rotate; i++) {
            unviewedTweets.add(unviewedTweets.remove());
        }
    }
}
