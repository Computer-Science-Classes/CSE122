import java.util.*;

/// The MusicBox class orchestrates the user lead composition of songs
/// and their internal melodies
///
/// It features global NOTES, SHARP, and FLAT variables for convenience.
public class MusicBox {
    public static final String NOTES = "CDEFGAB";
    public static final String SHARP = "♯";
    public static final String FLAT = "♭";

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String[][] song = composeSong(console);
        System.out.println("Returned song 2D array:");
        for (int i = 0; i < song.length; i++) {
            for (int j = 0; j < song[0].length; j++) {
                System.out.print(song[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prompts the user to enter N melodies and constructs a 2D array representing
     * the full song
     *
     * @param console:
     *            used to read user input
     * @return a 2D array where each row is a melody and each column a note
     */
    public static String[][] composeSong(Scanner console) {
        System.out.print("Enter the number of melodies: ");
        int numberOfMelodies = console.nextInt();

        System.out.print("Enter the length of each melody: ");
        int lengthOfMelodies = console.nextInt();
        System.out.println();

        String[][] song = new String[numberOfMelodies][lengthOfMelodies];

        for (int i = 0; i < numberOfMelodies; i++) {
            composeMelody(console, song[i], i);
        }

        return song;
    }

    /**
     * Prompts the user to fill in the notes for a single melody
     *
     * @param console:
     *            used to read user input
     * @param melody:
     *            array to store the melody notes
     * @param melodyIndex:
     *            index of the melody being composed
     */
    private static void composeMelody(Scanner console, String[] melody, int melodyIndex) {
        System.out.println("Composing melody #" + (melodyIndex + 1));

        for (int j = 0; j < melody.length; j++) {
            System.out.print("  Enter note #" + (j + 1) + ": ");
            melody[j] = console.next();
        }

        System.out.println();
    }

    /**
     * Determines the most common natural note in each melody. Sharps and flats are
     * ignored completely.
     *
     * @param song:
     *            a 2D array representing a song
     * @return an array where each element is the most common natural note in the
     *         corresponding melody
     */
    public static String[] mostCommonNaturals(String[][] song) {
        String[] result = new String[song.length];

        for (int i = 0; i < song.length; i++) {
            int[] counts = countNaturals(song[i]);
            int maxIdx = indexOfMax(counts);
            result[i] = String.valueOf(NOTES.charAt(maxIdx));
        }

        return result;
    }

    /**
     * Counts natural notes in a single melody row
     *
     * @param melody:
     *            array to store the melody notes
     * @return the counts of each natural
     */
    private static int[] countNaturals(String[] melody) {
        int[] counts = new int[NOTES.length()];

        for (int j = 0; j < melody.length; j++) {
            String curr = melody[j];

            if (curr.length() == 1) { // i.e no flats or sharps
                int idx = NOTES.indexOf(curr.charAt(0)); // returns -1 if not natural
                if (idx != -1) {
                    counts[idx]++;
                }
            }
        }

        return counts;
    }

    /**
     * Finds the index of the largest value in an array
     *
     * @param arr
     * @return the index of the largest value in an array
     */
    private static int indexOfMax(int[] arr) {
        int maxIdx = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIdx]) {
                maxIdx = i;
            }
        }

        return maxIdx;
    }
}
