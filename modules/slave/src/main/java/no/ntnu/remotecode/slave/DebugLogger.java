package no.ntnu.remotecode.slave;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The DebugLogger class allows for a quick, easy and toggelable debug print interface.
 */
public class DebugLogger {

    private final boolean print;
    private static final SyncPrinter printer = SyncPrinter.getInstance();


    private static class SyncPrinter {

        private static SyncPrinter instance = new SyncPrinter();

        public static SyncPrinter getInstance() {
            return instance;
        }

        private SyncPrinter() {
            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        String printStr = printQue.take();
                        System.out.println(printStr);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

        private BlockingQueue<String> printQue = new LinkedBlockingQueue<>();

        // locks instead of synchronized to avoid something slipping in to a multiline print
        private Lock lock = new ReentrantLock();

        public void print(String printStr) {
            try {
                lock.lock();
                printQue.add(printStr);
            } finally {
                lock.unlock();
            }
        }

        public synchronized void print(List<String> strings) {
            try {
                lock.lock();
                printQue.addAll(strings);
            } finally {
                lock.unlock();
            }
        }
    }


    /**
     * Makes a debug logger
     *
     * @param print whether or not the logger should print
     */
    public DebugLogger(boolean print) {
        this.print = print;
    }


    /**
     * Prints ping and the usual call location from the stack. used for debugging progression
     */
    public void log() {
        if (this.print) {
            this.log("<ping>");
        }
    }

    /**
     * prints arbitrary list of objects using the objects toString method
     * the location where the print statement where printed from is also shown.
     *
     * @param tolog the objects to log
     */
    public void log(Object... tolog) {
        if (this.print) {
            ArrayList<String> printList   = new ArrayList<>();
            String            printString = Stream.of(tolog).map(Objects::toString).collect(Collectors.joining(" "));

            printList.add(String.format("%-70s\t\t", printString) + this.getFormattedCallerStackPosString());

            printer.print(printList);
        }
    }

    /**
     * prints arbitrary list of objects using the objects toString method
     * this is s(imple)Log that is without the stack location of the print
     *
     * @param tolog the objects to log
     */
    public void sLog(Object... tolog) {
        if (this.print) {
            ArrayList<String> printList   = new ArrayList<>();
            String            printString = Stream.of(tolog).map(Objects::toString).collect(Collectors.joining(" "));
            printList.add(printString);
            printer.print(printList);
        }
    }


    public void dumpStackHere() {
        if (this.print) {
            ArrayList<String> printList = new ArrayList<>();
            printList.add(String.format("##### dumping stack at: %s #####\n", this.getFormattedCallerStackPosString()));
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                printList.add(stackTraceElement.toString());
            }
            printList.add("##### dumping stack end #####");
            printer.print(printList);
        }

    }


    /**
     * A more specilized loggging for files where the file name, parent, type and so on is shown.
     *
     * @param file the file to debug
     */
    public void fileLog(File file) {
        if (print) {
            ArrayList<String> printList = new ArrayList<>();
            try {
                printList.add(String.format("-- Debug file log at %s --\n", this.getFormattedCallerStackPosString()));
                printList.add(String.format("File name      : %s\n" + "File parent    : %s\n" + "Caonical path  : %s\n",
                                            file.getName(),
                                            file.getParent(),
                                            file.getCanonicalPath()));
                if (!file.exists()) {
                    printList.add("- file dont exist -");
                } else if (file.isDirectory()) {
                    printList.add(String.format("- file is directory -\n" + "Num children    : %s\n",
                                                file.listFiles().length));
                } else if (file.isFile()) {
                    printList.add(String.format("- file is file -\n" + "Size             : %s\n", file.length()));
                }

                printList.add("-- log end --");
                printer.print(printList);
            } catch (IOException e) {
                System.out.println("debug err IO exeption");
            }
        }
    }

    /**
     * returns the current stack pos color formatted
     *
     * @return the current stack pos color formatted
     */
    private String getFormattedCallerStackPosString() {
        StackTraceElement tracePos = this.getCallerStackPoisson();
        if (tracePos != null) {
            return String.format("\u001B[32m%s.%s\u001B[0m(\u001B[36m%s:%d\u001B[0m)",
                                 tracePos.getClassName(),
                                 tracePos.getMethodName(),
                                 tracePos.getFileName(),
                                 tracePos.getLineNumber());
        } else {
            return " ";
        }
    }


    /**
     * Returns the first stack trace element outside this class.
     * Used to find where the log methode is called from
     *
     * @return the first stack trace element outside this class.
     */
    private StackTraceElement getCallerStackPoisson() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (!stackTraceElement.getClassName()
                                  .equals(this.getClass().getName()) && !stackTraceElement.getMethodName()
                                                                                          .equals("getStackTrace")) {
                return stackTraceElement;
            }
        }
        return null;
    }


}
