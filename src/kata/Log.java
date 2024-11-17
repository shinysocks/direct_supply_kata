package kata;

public class Log {
    public final static String DEFAULT = "\u001b[0m";
    private final static String BOLD_RED = "\u001b[31;1m";
    private final static String BOLD_YELLOW = "\u001b[33;1m";
    private final static String BOLD_CYAN = "\u001b[36;1m";

    public static void info(String message) {
        System.out.println(BOLD_CYAN + message + DEFAULT);
    }

    public static void fatal(String message) {
        System.err.println(BOLD_RED + message + DEFAULT);
        System.exit(1);
    }

    public static void warn(String message) {
        System.err.println(BOLD_YELLOW + message + DEFAULT);
    }
}
