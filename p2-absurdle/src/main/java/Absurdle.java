import java.io.*;
import java.util.*;

/**
 * Absurdle is a CLI based word guessing game inspired by Wordle.
 *
 * <p>
 * The game operates by:
 * <ul>
 * <li>Loading a dictionary of words from a file</li>
 * <li>Filtering words to a specified length</li>
 * <li>Partitioning candidate words into pattern buckets</li>
 * <li>Selecting the largest bucket to prolong the game</li>
 * </ul>
 *
 * <p>
 * Feedback is provided using colored tile representations:
 * <ul>
 * <li>{@link #GREEN}: correct letter in the correct position</li>
 * <li>{@link #YELLOW}: correct letter in the wrong position</li>
 * <li>{@link #GRAY}: letter not present in the word</li>
 * </ul>
 */
public class Absurdle {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    // [[ ALL OF MAIN PROVIDED ]]
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = prepDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = recordGuess(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }

    // [[ PROVIDED ]]
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    // [[ PROVIDED ]]
    // Returns true if the game is finished, meaning the user guessed the word.
    // Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    // [[ PROVIDED ]]
    // Loads the contents of a given file Scanner into a List<String> and returns
    // it.
    // - Scanner dictScan: contains file contents
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    // TODO: Write your code here!

    /**
     * Initializes the active dictionary for a new game.
     *
     * Filters the provided word list to include only words of the specified length
     * and resets all game state needed to begin play.
     *
     * @param contents
     *            the words used to prepare the dictionary.
     * @param wordLength
     *            the required word length for this round
     * @return the prepared dictionary of words for a new game.
     * @throws IllegalArgumentException
     *             if wordLength is less than 1.
     */
    public static Set<String> prepDictionary(List<String> contents, int wordLength) {
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }

        Set<String> dictionary = new HashSet<>();
        for (String word : contents) {
            if (word.length() == wordLength) {
                dictionary.add(word);
            }
        }

        return dictionary;
    }

    /**
     * Records a guess by partitioning the given words into pattern buckets and
     * selecting the largest bucket.
     *
     * The input set is modified to contain only the words belonging to the selected
     * largest pattern groups.
     *
     * @param guess
     *            the current guessed pattern
     * @param words
     *            the active set of candidate words
     * @param wordLength
     *            the required word length
     * @return the pattern corresponding to the largest bucket
     * @throws IllegalArgumentException
     *             if the word set is empty or guess length does not match
     *             wordLength
     */
    public static String recordGuess(String guess, Set<String> words, int wordLength) {
        if (words.isEmpty() || guess.length() != wordLength) {
            throw new IllegalArgumentException();
        }

        Map<String, Set<String>> buckets = buildBuckets(guess, words);

        String bestPattern = "";
        int bestSize = -1;

        for (String pattern : buckets.keySet()) {
            Set<String> bucket = buckets.get(pattern);
            int size = bucket.size();

            if (size > bestSize) {
                bestSize = size;
                bestPattern = pattern;
            }
        }

        words.clear();
        Set<String> bestBucket = buckets.get(bestPattern);
        for (String word : bestBucket) {
            words.add(word);
        }

        return bestPattern;
    }

    /**
     * Builds a mapping from pattern strings to the set of words that match each
     * pattern for the given guess.
     *
     * @param guess
     *            the current guessed pattern
     * @param words
     *            the active set of candidate words
     * @return a map where each key is a pattern and the value is the set of words
     *         matching that pattern
     */
    private static Map<String, Set<String>> buildBuckets(String guess, Set<String> words) {
        Map<String, Set<String>> buckets = new TreeMap<>();

        for (String word : words) {
            String pattern = patternFor(word, guess);

            Set<String> bucket = buckets.get(pattern);
            if (!buckets.containsKey(pattern)) {
                bucket = new HashSet<String>();
                buckets.put(pattern, bucket);
            }

            bucket.add(word);
        }

        return buckets;
    }

    /**
     * Returns a pattern string comparing character by character {@code guess} to
     * {@code word}.
     *
     * Each position in the returned {@code String} is one of: {@code GREEN} - the
     * character matches exactly, i.e. same letter and index {@code YELLOW} - the
     * character appears in the word but at a different index {@code GRAY} - the
     * character does not appear in the word
     *
     * @implNote word and guess must be the same length
     * @param word
     *            the target word being guessed
     * @param guess
     *            the current guess for the word
     * @return a {@code String} of colored tiles representing the match pattern
     */
    public static String patternFor(String word, String guess) {
        Map<Character, Integer> wordChars = new HashMap<>();

        // gray = 0, yellow = 1, green = 2
        int[] pattern = new int[guess.length()];
        String out = "";

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            // Initialize counts from word
            if (wordChars.containsKey(c)) {
                wordChars.put(c, wordChars.get(c) + 1);
            } else {
                wordChars.put(c, 1);
            }

            // Initialize pattern with all 0's
            pattern[i] = 0; // gray = 0
        }

        // Check for index and character match with guess (green)
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);

            if (c == word.charAt(i)) {
                pattern[i] = 2; // green = 2
                wordChars.put(c, wordChars.get(c) - 1);
            }
        }

        // Check for character match without index match (yellow)
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);

            if (pattern[i] != 2) {
                // Rest of non 0 chars must be yellows or grays
                if (wordChars.containsKey(c) && wordChars.get(c) != 0) {
                    pattern[i] = 1; // yellow = 1
                    wordChars.put(c, wordChars.get(c) - 1);
                }
            }
        }

        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == 2) {
                out = out + GREEN;
            } else if (pattern[i] == 1) {
                out = out + YELLOW;
            } else if (pattern[i] == 0) {
                out = out + GRAY;
            }
        }

        return out;
    }
}
