
package com.airhacks.calculator.addition.control;

import com.airhacks.calculator.addition.entity.Calculation;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author airhacks.com
 */
public class CalculationsArchiver {

    @PersistenceContext
    EntityManager em;

    public void archive(int a, int b, int result) {
        this.em.persist(new Calculation(a, b, result));
    }


}
