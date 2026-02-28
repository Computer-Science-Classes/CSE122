import java.util.*;

/**
 * CLI music playlist application.
 *
 * <p>
 * The playlist is modeled as a {@code Queue} of song names in FIFO order.
 * Played songs are recorded in a {@code Stack} as a LIFO history.
 *
 * <p>
 * This program supports:
 * <ul>
 * <li>Adding songs to the end of the playlist with
 * {@link MusicPlaylist#addSong(Scanner, Queue)}</li>
 * <li>Playing the next song (implicitly moves it from playlist to history) with
 * {@link MusicPlaylist#playSong(Queue, Stack)}</li>
 * <li>Viewing the current playlist order
 * {@link MusicPlaylist#viewPlaylist(Queue)}</li>
 * <li>Printing the play history in most recent order with
 * {@link MusicPlaylist#printHistory(Stack)}</li>
 * <li>Clearing history with {@link MusicPlaylist#clearHistory(Stack)}</li>
 * <li>Deleting a specified number of songs from history (ordered from most
 * recent to oldest) with
 * {@link MusicPlaylist#deleteHistory(Scanner, Stack)}</li>
 * </ul>
 *
 * <p>
 * All songs are represented as {@code String} values and are compared and
 * printed as entered.
 */
public class MusicPlaylist {
    public static void main(String[] args) {
        System.out.println("Welcome to the CSE 122 Music Playlist!");
        Scanner input = new Scanner(System.in);

        Queue<String> playlist = new LinkedList<>();
        Stack<String> history = new Stack<>();
        boolean quit = false;

        while (!quit) {
            System.out.println();
            System.out.println("(A) Add song");
            System.out.println("(P) Play song");
            System.out.println("(H) Print history");
            System.out.println("(V) View playlist");
            System.out.println("(C) Clear history");
            System.out.println("(D) Delete from history");
            System.out.println("(Q) Quit");
            System.out.println();

            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            if (choice.equalsIgnoreCase("A")) {
                addSong(input, playlist);
            } else if (choice.equalsIgnoreCase("P")) {
                playSong(playlist, history);
            } else if (choice.equalsIgnoreCase("H")) {
                printHistory(history);
            } else if (choice.equalsIgnoreCase("V")) {
                viewPlaylist(playlist);
            } else if (choice.equalsIgnoreCase("C")) {
                clearHistory(history);
            } else if (choice.equalsIgnoreCase("D")) {
                deleteHistory(input, history);
            } else if (choice.equalsIgnoreCase("Q")) {
                quit = true;
            }
        }

        input.close();
    }

    /**
     * Prompts the user for a song name and appends it to the end of the
     * {@code playlist}.
     *
     * <p>
     * The playlist is a FIFO structure.
     *
     * @param input
     *            input scanner used to read user input.
     * @param playlist
     *            playlist {@code Queue} that stores the upcoming songs.
     */
    private static void addSong(Scanner input, Queue<String> playlist) {
        System.out.print("Enter song name: ");
        String song = input.nextLine();
        playlist.add(song);
        System.out.println("Successfully added " + song);
    }

    /**
     * Plays the next song in the {@code playlist}.
     *
     * <p>
     * Removes the song at the head of the {@code playlist} onto {@code history}.
     *
     * @param playlist
     *            playlist {@code Queue} that stores the upcoming songs.
     * @param history
     *            {@code Stack} storing previously played songs.
     * @throws IllegalStateException
     *             if {@code playlist} is empty.
     */
    private static void playSong(Queue<String> playlist, Stack<String> history) {
        if (playlist.isEmpty()) {
            throw new IllegalStateException();
        }

        String song = playlist.remove();
        history.push(song);
        System.out.println("Playing song: " + song);
    }

    /**
     * Prints the play {@code history} in reverse chronological order.
     *
     * <p>
     * This methods reserves {@code history}.
     *
     * @param history
     *            {@code Stack} storing previously played songs.
     * @throws IllegalStateException
     *             if {@code history} is empty.
     */
    private static void printHistory(Stack<String> history) {
        if (history.isEmpty()) {
            throw new IllegalStateException();
        }

        Stack<String> aux = new Stack<>();
        while (!history.isEmpty()) {
            String song = history.pop();
            System.out.println("    " + song);
            aux.push(song);
        }

        while (!aux.isEmpty()) {
            history.push(aux.pop());
        }
    }

    /**
     * Removes all songs from the play {@code history}.
     *
     * <p>
     * After this methods returns, {@code history} is empty.
     *
     * @param history
     *            {@code Stack} storing previously played songs.
     */
    private static void clearHistory(Stack<String> history) {
        while (!history.isEmpty()) {
            history.pop();
        }
    }

    /**
     * Deletes a user specified number of songs from the play {@code history}.
     *
     * <p>
     * The user enters an integer:
     * <ul>
     * <li>If the number is positive, the method deletes from the most recent
     * history.</li>
     * <li>If the number if negative, the method deletes from the oldest
     * history.</li>
     * <li>If the number is zero, no changes are made.</li>
     * </ul>
     *
     * @param input
     *            input scanner used to read the delete count.
     * @param history
     *            {@code Stack} storing previously played songs.
     * @throws IllegalArgumentException
     *             if {@code normalizedDelete} is greater than the size of
     *             {@code history.size()}.
     */
    private static void deleteHistory(Scanner input, Stack<String> history) {
        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");
        String delete = input.nextLine();
        int deleteNum = Integer.parseInt(delete);

        int normalizedDelete = Math.abs(deleteNum);

        if (normalizedDelete > history.size()) {
            throw new IllegalArgumentException();
        }

        if (deleteNum > 0) {
            // Delete reverse chronological
            for (int i = 0; i < normalizedDelete; i++) {
                history.pop();
            }
        } else if (deleteNum < 0) {
            // Delete chronological
            Stack<String> aux = new Stack<>();

            while (!history.isEmpty()) {
                aux.push(history.pop());
            }

            int iteration = 0;
            while (!aux.isEmpty()) {
                iteration++;

                String song = aux.pop();

                if (iteration > normalizedDelete) {
                    history.push(song);
                }
            }
        }
    }

    /**
     * Prints the current {@code playlist} in play order.
     *
     * <p>
     * This method preserves {@code playlist}.
     *
     * @param playlist
     *            playlist {@code Queue} that stores the upcoming songs.
     * @throws IllegalStateException
     *             if {@code playlist} is empty.
     */
    private static void viewPlaylist(Queue<String> playlist) {
        if (playlist.isEmpty()) {
            throw new IllegalStateException();
        }

        int size = playlist.size();

        for (int i = 0; i < size; i++) {
            String song = playlist.remove();
            System.out.println("    " + song);
            playlist.add(song);
        }
    }
}
