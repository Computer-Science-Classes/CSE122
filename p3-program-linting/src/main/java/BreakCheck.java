import java.util.*;

// TODO: Your Code Here

/**
 * Lints a line of source code for usage of the {@code break} keyword.
 *
 * <p>
 * Any occurrence of {@code break} outside of a line comment (denoted by
 * {@code //}) is considered a violation. Content appearing after {@code //} is
 * ignored.
 *
 * <p>
 * Implements {@link Check}.
 */
public final class BreakCheck implements Check {
    /**
     * Error code associated with this check.
     */
    public static final int CODE = 2;

    /**
     * Message reported when a violation is detected.
     */
    public static final String MESSAGE = "break keyword forbidden.";

    /**
     * Analyzes a single line of source code for usage of the {@code break} keyword.
     *
     * <p>
     * The method ignores any text appearing after a line comment marker of
     * {@code //}. If {@code break} appears in the remaining code portion, an
     * {@link Error} is returned.
     *
     * @param line
     *            the line of source code to analyze.
     * @param lineNumber
     *            the line number associated with the input line.
     * @return an {@link Error} if a violation is found; otherwise, {@code null}
     */
    @Override
    public Error lint(String line, int lineNumber) {
        String code = "";

        int comment = line.indexOf("//");
        if (comment == -1) {
            code = line;
        } else {
            code = line.substring(0, comment);
        }

        if (code.contains("break")) {
            return new Error(CODE, lineNumber, MESSAGE);
        }

        return null;
    }
}
