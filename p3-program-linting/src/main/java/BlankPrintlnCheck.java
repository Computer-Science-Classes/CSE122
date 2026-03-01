import java.util.*;

// TODO: Your Code Here

/**
 * Lints a line of source code for usage of an empty
 * {@code System.out.println("")} statement.
 *
 * <p>
 * This check enforces the use of {@code System.out.println()} instead of
 * printing an empty string.
 */
public final class BlankPrintlnCheck implements Check {
    /**
     * Error code associated with this check.
     */
    public static final int CODE = 3;

    /**
     * Message reported when a violation is detected.
     */
    public static final String MESSAGE = "System.out.println(\"\") forbidden."
            + "Prefer System.out.println().";

    /**
     * Analyzes a single line of source code for usage of
     * {@code System.out.println("")}.
     *
     * @param line
     *            the line of source code to analyze.
     * @param lineNumber
     *            the line number associated with the input line.
     * @return an {@link Error} if a violation is found; otherwise, {@code null}
     */
    @Override
    public Error lint(String line, int lineNumber) {
        if (line.contains("System.out.println(\"\")")) {
            return new Error(CODE, lineNumber, MESSAGE);
        }

        return null;
    }
}
