import java.util.*;

// TODO: Your Code Here

/**
 * Lints a line of source code to ensure it does not exceed a maximum length of
 * 100 {@code Characters}.
 *
 * <p>
 * Lines longer than 100 {@code Characters} are considered a violation.
 *
 * <p>
 * Implements {@link Check}.
 */
public final class LongLineCheck implements Check {
    /**
     * Error code associated with this check.
     */
    public static final int CODE = 1;

    /**
     * Analyzes a single line of source code to determine whether it exceeds the
     * maximum allowed length of 100 characters.
     *
     * @param line
     *            the line of source code to analyze.
     * @param lineNumber
     *            the line number associated with the input line.
     * @return an {@link Error} if the line exceeds 100 characters; otherwise,
     *         {@code null}
     */
    @Override
    public Error lint(String line, int lineNumber) {
        int lineLength = line.length();
        if (lineLength <= 100) {
            return null;
        }

        String message = "Make line less than 100 Character. Currently line length is " + lineLength
                + " Characters.";
        return new Error(CODE, lineNumber, message);
    }
}
