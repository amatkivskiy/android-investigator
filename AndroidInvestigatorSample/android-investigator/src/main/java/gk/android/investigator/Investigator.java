package gk.android.investigator;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

/**
 * Simplifies adding ad hoc tracking logs to code during investigation.<p>
 * <p>
 * For tracking down asynchronous events, lifecycles simply adding <code>Investigator.log(this)</code> to every checkpoint will usually do.
 * (Set {@link Investigator#methodDepth} to 1 to see who is calling the watched method.)<p>
 * <p>
 * The varargs param can be used to print variable values.<p>
 * <p>
 * Printing elapsed time is also possible, see more at the methods.<p>
 *
 * @author Gabor_Keszthelyi
 */
public class Investigator {

    /**
     * The tag used for the logcat messages.
     */
    public static String tag = "Investigator";
    /**
     * Number of the extra stacktrace elements (class + method name) logged from the stacktrace created at the log() call. Zero means no extra method is logged, only the watched one.<p>
     * (It is exposed so it can be changed at individual checkpoints if needed.)
     */
    public static int methodDepth = 0;
    /**
     * If true, the name of the thread is printed at the beginning of the log message.
     */
    public static boolean threadNameEnabled = true;
    /**
     * The log level used for logcat.
     */
    public static int logLevel = Log.DEBUG;
    /**
     * If true, the package name from the instance's toString value is removed for easier readability of the logs.
     */
    public static boolean removePackageName = true;
    /**
     * When enabled, an extra word ({@link #anonymousClassHighlightWord}) is inserted into anonymous and nested inner class toString values to help notice them more easily.
     * <p>e.g.: <code>FirstFragment$1@1bf1abe3.onClick()</code> --&gt; <code>FirstFragment_INNNER_1@1bf1abe3.onClick()</code>
     */
    public static boolean highlightAnonymousClasses = true;
    public static String anonymousClassHighlightWord = "_ANONYMOUS_";

    public static String patternThreadName = "[%s] ";
    public static String patternInstanceAndMethod = "%s.%s()";
    public static String patternComment = " | %s";
    public static String patternVariableNameAndValue = " | %s = %s";
    public static String messageStopwatchStarted = " | 0 ms (STOPWATCH STARTED)";
    public static String patternElapsedTime = " | %s ms";
    public static String patternExtraStacktraceLine = "..." + patternInstanceAndMethod;
    public static String newLine = "\n";

    private static final int STACKTRACE_INDEX_OF_CALLING_METHOD = 3; // fixed value, need to update only if the 'location' of the stack trace fetching code changes
    private static final String INNER_CLASS_TOSTRING_SYMBOL = "$";

    private static boolean isStopWatchGoing;

    /**
     * Logs the calling instance and method name.
     * <p><b>Example</b>
     * <br>Code:
     * <br><code>Investigator.log(this);</code>
     * <br>Log:
     * <br><code>D/investigation﹕ MainActivity@788dc5c.onCreate()</code>
     *
     * @param instance the calling object instance
     */
    public static void log(Object instance) {
        doLog(instance, null, false, null);
    }

    /**
     * Logs the calling instance and method name, and the comment.
     * <p><b>Example</b>
     * <br>Code:
     * <br><code>Investigator.log(this, "some comment");</code>
     * <br>Log:
     * <br><code>D/investigation﹕ MainActivity@788dc5c.onCreate() | some comment</code>
     *
     * @param instance the calling object instance
     * @param comment  extra comment message
     */
    public static void log(Object instance, String comment) {
        doLog(instance, comment, false, null);
    }

    /**
     * Logs the calling instance and method name, and the variable names and values.
     * <p><b>Example</b>
     * <br>Code:
     * <br><code>Investigator.log(this, "fruit", fruit);</code>
     * <br><code>Investigator.log(this, "fruit", fruit, "color", color);</code>
     * <br>Log:
     * <br><code>D/investigation﹕ MainActivity@788dc5c.onCreate() | fruit = cherry</code>
     * <br><code>D/investigation﹕ MainActivity@788dc5c.onCreate() | fruit = cherry | color = red</code>
     *
     * @param instance               the calling object instance
     * @param variableNamesAndValues variable name and value pairs
     */
    public static void log(Object instance, Object... variableNamesAndValues) {
        doLog(instance, null, false, variableNamesAndValues);
    }

    /**
     * Starts an internal stopwatch and the consequent log calls will print the time elapsed since this call. Calling it multiple times restarts the stopwatch.
     * <p><b>Example</b>
     * <br>Code:
     * <br><code>Investigator.startStopWatch(this);</code>
     * <br><code>...</code>
     * <br><code>Investigator.log(this);</code>
     * <br>Log:
     * <br><code>D/investigation﹕ MainActivity@788dc5c.onCreate() | 0 ms (STOPWATCH STARTED)</code>
     * <br><code>D/investigation﹕ NetworkController@788dc5c.onJobFinished() | 126 ms</code>
     *
     * @param instance the calling object instance
     */
    public static void startStopWatch(Object instance) {
        StopWatch.startStopWatch();
        isStopWatchGoing = true;
        doLog(instance, null, true, null);
    }

    /**
     * Stop logging times (started by {@link Investigator#startStopWatch(Object)} from this point on.
     */
    public static void stopLoggingTimes() {
        isStopWatchGoing = false;
    }

    private static void doLog(Object instance, String comment, boolean hasStopWatchJustStarted, Object[] variableNamesAndValues) {
        StackTraceElement[] stackTrace = getStackTrace();
        StringBuilder msg = new StringBuilder();
        if (threadNameEnabled) {
            msg.append(threadName());
        }
        msg.append(instanceAndMethodName(instance, stackTrace));
        if (comment != null) {
            msg.append(commentMessage(comment));
        }
        if (variableNamesAndValues != null) {
            msg.append(variablesMessage(variableNamesAndValues));
        }
        if (hasStopWatchJustStarted) {
            msg.append(messageStopwatchStarted);
        }
        if (isStopWatchGoing && !hasStopWatchJustStarted) {
            msg.append(timeElapsedMessage());
        }
        if (methodDepth > 0) {
            msg.append(extraStackTraceLines(stackTrace));
        }
        logText(msg);
    }

    private static StackTraceElement[] getStackTrace() {
        // new Exception().getStackTrace() is faster than Thread.currentThread().getStackTrace(), see http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6375302
        return new Exception().getStackTrace();
    }

    private static String threadName() {
        return String.format(patternThreadName, Thread.currentThread().getName());
    }

    private static String instanceAndMethodName(Object instance, StackTraceElement[] stackTrace) {
        String methodName = stackTrace[STACKTRACE_INDEX_OF_CALLING_METHOD].getMethodName();
        String instanceName = instance.toString();
        if (removePackageName) {
            instanceName = removePackageName(instanceName);
        }
        instanceName = checkAndHighlightAnonymousClass(instanceName);
        return String.format(patternInstanceAndMethod, instanceName, methodName);
    }

    private static String removePackageName(String instanceName) {
        return instanceName.substring(instanceName.lastIndexOf(".") + 1);
    }

    @VisibleForTesting
    static String checkAndHighlightAnonymousClass(String instanceName) {
        if (!highlightAnonymousClasses) {
            return instanceName;
        }
        int symbolIndex = instanceName.indexOf(INNER_CLASS_TOSTRING_SYMBOL);
        boolean hasSymbolPlusDigit = symbolIndex > 0 && instanceName.length() > symbolIndex + 2 && Character.isDigit(instanceName.charAt(symbolIndex + 1));
        if (hasSymbolPlusDigit) {
            return new StringBuilder(instanceName).deleteCharAt(symbolIndex).insert(symbolIndex, anonymousClassHighlightWord).toString();
        } else {
            return instanceName;
        }
    }

    private static boolean isInnerClass(String instanceName) {
        return instanceName.contains(INNER_CLASS_TOSTRING_SYMBOL);
    }

    private static String insertInnerClassHighlight(String instanceName) {
        int insertionLocation = instanceName.indexOf(INNER_CLASS_TOSTRING_SYMBOL);
        return instanceName.substring(0, insertionLocation) + anonymousClassHighlightWord + instanceName.substring(insertionLocation + 1);
    }

    private static String commentMessage(String comment) {
        return String.format(patternComment, comment);
    }

    private static StringBuilder variablesMessage(Object... variableNamesAndValues) {
        try {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < variableNamesAndValues.length; i++) {
                Object variableName = variableNamesAndValues[i];
                Object variableValue = variableNamesAndValues[++i]; // Will fail on odd number of params deliberately
                String variableMessage = String.format(patternVariableNameAndValue, variableName, variableValue);
                result.append(variableMessage);
            }
            return result;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Missed to add variable names and values in pairs? There has to be an even number of the 'variableNamesAndValues' varargs parameters).", e);
        }
    }

    private static String timeElapsedMessage() {
        return String.format(patternElapsedTime, StopWatch.getElapsedTimeInMillis());
    }

    private static StringBuilder extraStackTraceLines(StackTraceElement[] stackTrace) {
        StringBuilder extraLines = new StringBuilder();
        for (int i = STACKTRACE_INDEX_OF_CALLING_METHOD + 1;
             i <= STACKTRACE_INDEX_OF_CALLING_METHOD + methodDepth && i < stackTrace.length;
             i++) {
            extraLines.append(newLine).append(stackTraceLine(stackTrace[i]));
        }
        return extraLines;
    }

    private static String stackTraceLine(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        className = checkAndHighlightAnonymousClass(className);
        return String.format(patternExtraStacktraceLine, className, stackTraceElement.getMethodName());
    }

    private static void logText(StringBuilder message) {
        Log.println(logLevel, tag, message.toString());
    }

    static class StopWatch {

        private static long startTimeInMillis;

        static void startStopWatch() {
            startTimeInMillis = System.currentTimeMillis();
        }

        static long getElapsedTimeInMillis() {
            return System.currentTimeMillis() - startTimeInMillis;
        }

    }
}













