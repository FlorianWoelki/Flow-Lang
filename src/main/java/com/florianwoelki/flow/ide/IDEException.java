package com.florianwoelki.flow.ide;

import com.alee.laf.optionpane.WebOptionPane;

/**
 * Created by Florian Woelki on 10.12.17.
 */
public class IDEException extends RuntimeException {

    public IDEException(String message) {
        super(message);
    }

    public void displayException() {
        WebOptionPane.showMessageDialog(null, getMessage(), "An Exception occurred", WebOptionPane.ERROR_MESSAGE);
    }

}
