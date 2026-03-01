// TODO: Your Code Here

/**
 * Represents a single linting violation detected in a source file.
 *
 * <p>
 * An {@code Error} includes:
 *
 * <ul>
 * <li>{@code Integer} error code identifying the rule that was violated.</li>
 * <li>{@code Integer} line number where the violation occurred.</li>
 * <li>{@code String} message describing the issue.</li>
 * </ul>
 */
public final class Error {
    /**
     * The integer code identifying which check produces this error.
     */
    private int code;

    /**
     * The 1 based line number in the input file where the error occurred.
     */
    private int lineNumber;

    /**
     * A human readable message describing the violation.
     */
    private String message;

    /**
     * Constructs a new {@code Error} with the provided code, line number, and
     * message.
     *
     * @param code
     *            the integer error code associated with violated rule.
     * @param lineNumber
     *            the line number associated with the input line.
     * @param message
     *            a description of the violation.
     */
    public Error(int code, int lineNumber, String message) {
        this.code = code;
        this.lineNumber = lineNumber;
        this.message = message;
    }

    /**
     * Returns a 1 based line number where the violation occurred.
     *
     * @return the line number of the violation.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Returns the integer error code associated with this violation.
     *
     * @return the error code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the human readable message describing this violation.
     *
     * @return the error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a formatted {@code String} representation of this {@code Error}.
     *
     * @return a formatted description of the error including line number, code, and
     *         message.
     */
    public String toString() {
        String out = ("(Line: " + lineNumber + ") has error code " + code + ": " + message);
        return out;
    }
}
