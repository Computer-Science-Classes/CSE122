import java.util.*;

/// Contains CRUD and view methods for the music playlist utilizing the Queue
/// and Stack data structures
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
     * Adds a song to the playlist `Queue`
     *
     * @param input:
     *            user input scanner
     * @param playlist:
     *            the `Queue` of songs to add a song too
     */
    private static void addSong(Scanner input, Queue<String> playlist) {
        System.out.print("Enter song name: ");
        String song = input.nextLine();
        playlist.add(song);
        System.out.println("Successfully added " + song);
    }

    /**
     * Removes the head of the playlist `Queue` and pushes it to the history stack.
     *
     * @param playlist:
     *            the `Queue` of songs to play a song from
     * @param history:
     *            the `Stack` of songs which have been played
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
     * Prints the history `Stack` in reverse chronological order.
     *
     * @param history:
     *            the `Stack` of songs which have been played
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
     * Clears the history `Stack` by popping all its elements.
     *
     * @param history:
     *            the `Stack` of songs which have been played
     */
    private static void clearHistory(Stack<String> history) {
        while (!history.isEmpty()) {
            history.pop();
        }
    }

    /**
     * Deletes the history `Stack` in either chronological or reverse chronological
     * order.
     *
     * The user inputted number is the amount to delete. If the number is signed the
     * deletion happens in chronological order, otherwise elements are deleted in
     * reverse chronological order
     *
     * @param input:
     *            user input scanner
     * @param history:
     *            the `Stack` of songs which have been played
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
     * Prints the playlist `Queue` order.
     *
     * @param playlist:
     *            the `Queue` of songs to play a song from
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
