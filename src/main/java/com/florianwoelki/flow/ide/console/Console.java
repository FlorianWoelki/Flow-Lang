package com.florianwoelki.flow.ide.console;

import com.alee.laf.text.WebTextPane;

/**
 * Created by Florian Woelki on 10.12.17.
 */
public class Console extends WebTextPane {

    private ConsoleOutputType consoleOutputType;

    public Console() {
        consoleOutputType = ConsoleOutputType.OUTPUT;
    }

    public void logOutput() {
        consoleOutputType = ConsoleOutputType.OUTPUT;
    }

    public void logInfo() {
        consoleOutputType = ConsoleOutputType.INFO;
    }

    public void logError() {
        consoleOutputType = ConsoleOutputType.ERROR;
    }

}
