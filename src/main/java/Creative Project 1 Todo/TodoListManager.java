import java.io.*;
import java.util.*;

/// Gives CRUD operations for a todos Arraylist of type string.
///
/// Prompts the user in a sentinel while loop for their input and acts on their
/// input if valid.
public class TodoListManager {
    public static final boolean EXTENSION_FLAG = true;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to your TODO List Manager!");
        Scanner console = new Scanner(System.in);

        List<String> todos = new ArrayList<String>();
        boolean quit = false;

        while (!quit) {
            System.out.println("What would you like to do?");
            System.out.print(
                    "(A)dd TODO, (M)ark TODO as done, (L)oad TODOs," + "(S)ave TODOs, (Q)uit? ");
            String input = console.nextLine();

            if (input.equalsIgnoreCase("A")) {
                addItem(console, todos);
            } else if (input.equalsIgnoreCase("M")) {
                markItemAsDone(console, todos);
            } else if (input.equalsIgnoreCase("L")) {
                loadItems(console, todos);
            } else if (input.equalsIgnoreCase("S")) {
                saveItems(console, todos);
            } else if (input.equalsIgnoreCase("Q")) {
                quit = true;
            } else {
                System.out.println("Unknown input: " + input);
            }

            if (!quit) {
                printTodos(todos);
            }
        }

        console.close();
    }

    /**
     * Prints the `todos` List to standard out indexed by numbers, starting at 1.
     *
     * @param todos:
     *            ArrayList of todo items of type String
     */
    public static void printTodos(List<String> todos) {
        System.out.println("Today's TODOs:");
        if (todos.isEmpty()) {
            System.out.println("  You have nothing to do yet today! Relax!");
        } else {
            for (int i = 0; i < todos.size(); i++) {
                System.out.println("  " + (i + 1) + ": " + todos.get(i).toString());
            }
        }
    }

    /**
     * Take in user input to add an item to the `todos` List. Adds to the end of the
     * list on 'enter' Otherwise specify an index to add an item
     *
     * @param console:
     *            Scanner object to parse user input
     * @param todos:
     *            ArrayList of todo items of type String
     */
    public static void addItem(Scanner console, List<String> todos) {
        System.out.print("What would you like to add? ");
        String todo = console.nextLine();

        if (todos.isEmpty()) {
            todos.add(todo);
        } else {
            System.out.print("Where in the list should it be (1-" + (todos.size() + 1)
                    + ")? (Enter for end): ");
            String idx = console.nextLine();

            boolean onlySpaces = true;
            for (int i = 0; i < idx.length(); i++) {
                if (idx.charAt(i) != ' ') {
                    onlySpaces = false;
                }
            }

            // Enter character is ""
            if (idx.equals("") || onlySpaces) {
                todos.add(todo);
            } else {
                todos.add(Integer.parseInt(idx) - 1, todo);
            }
        }
    }

    /**
     * Take in user input to remove an item from the `todos` List. Optionally, can
     * remove a range of items from `todos`.
     *
     * @param console:
     *            Scanner object to parse user input
     * @param todos:
     *            ArrayList of todo items of type String
     */
    public static void markItemAsDone(Scanner console, List<String> todos) {
        if (todos.isEmpty()) {
            System.out.println("All done! Nothing left to mark as done!");
        } else {
            if (EXTENSION_FLAG) {
                System.out.print("What is the first item you completed (1-" + todos.size() + ")? ");
                String firstIdx = console.nextLine();
                System.out.print("What is the last item you completed (1-" + todos.size() + ")? ");
                String lastIdx = console.nextLine();

                int normFirstIdx = Integer.parseInt(firstIdx) - 1;
                int normLastIdx = Integer.parseInt(lastIdx) - 1;

                for (int i = normLastIdx; i >= normFirstIdx; i--) {
                    todos.remove(i);
                }
            } else {
                System.out.print("Which item did you complete (1-" + todos.size() + ")? ");
                String idx = console.nextLine();
                todos.remove(Integer.parseInt(idx) - 1);
            }
        }
    }

    /**
     * @brief Adds the items to the `todos` List via a user specified file. Clears
     *        old todo's in the todos List on load.
     *
     * @param console:
     *            Scanner object to parse user input
     * @param todos:
     *            ArrayList of todo items of type String
     * @throws FileNotFoundException
     *             when the user inputted file is not found
     */
    public static void loadItems(Scanner console, List<String> todos) throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();

        Scanner file = new Scanner(new File(fileName));

        todos.clear();
        while (file.hasNextLine()) {
            todos.add(file.nextLine());
        }

        file.close();
    }

    /**
     * @brief Prints `todos` items to a user specified file
     *
     * @param console:
     *            Scanner object to parse user input
     * @param todos:
     *            ArrayList of todo items of type String
     * @throws FileNotFoundException
     *             when the user inputted file is not found
     */
    public static void saveItems(Scanner console, List<String> todos) throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();

        PrintStream ps = new PrintStream(new File(fileName));

        for (int i = 0; i < todos.size(); i++) {
            ps.println(todos.get(i));
        }

        ps.close();
    }
}
