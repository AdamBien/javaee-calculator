
package com.airhacks.calculator.addition.boundary;

import com.airhacks.calculator.addition.control.CalculationsArchiver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class Addition {

    @Inject
    CalculationsArchiver archiver;

    public int add(int a, int b) {
        int result = a + b;
        if (result == 42) {
            thinkLonger();
        }
        this.archiver.archive(a, b, result);
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
