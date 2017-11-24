
package com.airhacks.ping.boundary;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author airhacks.com
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class Pings {

    @Resource
    UserTransaction ut;

    @Resource
    SessionContext sc;

    public String echo(long blockTime, String input) {
        try {
            ut.begin();
            ut.setTransactionTimeout(1);
        } catch (NotSupportedException | SystemException ex) {
        }
        heavyProcessing(blockTime);
        try {
            if ("rollback".equalsIgnoreCase(input)) {
                ut.rollback();
            } else {
                ut.commit();
            }
        } catch (RollbackException | HeuristicMixedException
                | HeuristicRollbackException | SecurityException
                | IllegalStateException | SystemException ex) {
        }
        return "echo" + input;
    }

    void heavyProcessing(long blockTime) {
        try {
            Thread.sleep(blockTime);
        } catch (InterruptedException ex) {
        }
    }
}
