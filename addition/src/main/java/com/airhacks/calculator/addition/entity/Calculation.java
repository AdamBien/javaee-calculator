
package com.airhacks.calculator.addition.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author airhacks.com
 */
@Entity
public class Calculation {

    @Id
    @GeneratedValue
    private long id;
    private int a;
    private int b;
    private int result;

    Calculation() {
        //jpa
    }

    public Calculation(int a, int b, int result) {
        this.a = a;
        this.b = b;
        this.result = result;
    }


}
