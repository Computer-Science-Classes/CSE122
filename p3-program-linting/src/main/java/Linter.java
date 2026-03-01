import java.util.*;
import java.io.*;

/**
 * Aggregates multiple user-provided {@link Check} rules and applies them to a
 * file.
 *
 * <p>
 * The {@code Linter} reads a file line-by-line and runs each configured
 * {@link Check} on each line. Any non-null {@link Error} values returned by
 * checks are colleced and returned to the caller.
 */
public class Linter {
    // TODO: Your code here

    /**
     * The list of checks to apply.
     */
    private List<Check> checks;

    /**
     * Constructs an {@code Linter} configured with the provided list of checks.
     *
     * @param checks
     *            the list of {@link Check} rules to apply.
     */
    public Linter(List<Check> checks) {
        this.checks = checks;
    }

    /**
     * Lints the file located at {@code fileName} and returns all violations found.
     *
     * <p>
     * The file is processed line-by-line using 1-based line numbers. Each line is
     * passed to each {@link Check} in {@link #checks}. Any returned {@link Error}
     * objects are added to the result list.
     *
     * @param fileName
     *            the path to the file to lint.
     * @return a list of all {@link Error} objects produced while linting the file.
     * @throws FileNotFoundException
     *             if the file specified by {@code fileName} is not found.
     */
    public List<Error> lint(String fileName) throws FileNotFoundException {
        Scanner file = new Scanner(new File(fileName));
        List<Error> errors = new ArrayList<>();

        int lineNumber = 0;

        while (file.hasNextLine()) {
            String line = file.nextLine();
            lineNumber++;

            for (Check c : checks) {
                Error err = c.lint(line, lineNumber);
                if (err != null) {
                    errors.add(err);
                }
            }
        }

        return errors;
    }
}
