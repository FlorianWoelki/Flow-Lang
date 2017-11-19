package com.florianwoelki.flow;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Florian Woelki on 01.08.17.
 */
public class FlowLangTest {

    @Test
    public void implode() throws Exception {

    }

    @Test
    public void changeCommas() throws Exception {
        String testString = "\"This is a test, String.\"";
        String result = FlowLang.changeCommas(testString);
        Assert.assertEquals(result, "Thisisatest__comma__String.");
    }

    @Test
    public void unchangeCommas() throws Exception {
        String testString = "Thisisatest,String.";
        Assert.assertEquals(testString, "Thisisatest,String.");
    }

}