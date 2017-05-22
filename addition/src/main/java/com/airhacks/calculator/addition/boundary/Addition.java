
package com.airhacks.calculator.addition.boundary;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

@Stateless
public class Addition {

    public int add(int a, int b) {
        int result = a + b;
        if (result == 42) {
            thinkLonger();
        }
        return result;
    }

    void thinkLonger() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Addition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
