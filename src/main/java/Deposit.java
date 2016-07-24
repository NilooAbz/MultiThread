
import exceptions.DepositNotExistException;
import exceptions.InitialBalancePassedUpperBoundException;
import exceptions.InitialBalanceBecameZeroException;

import java.math.BigDecimal;

/**
 * Created by Niloofar on 7/20/2016.
 */
class Deposit {

    private String customerName;
    private String id;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;


    void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    void withdraw(BigDecimal draw) throws DepositNotExistException, InitialBalanceBecameZeroException {

        synchronized (this) {
            int compareValue = initialBalance.compareTo(draw);
            int upperBoundPossitive = upperBound.compareTo(BigDecimal.ZERO);
            //  initialBalance>=draw  &&  upperBound>0
            if ((compareValue >= 0) && (upperBoundPossitive > 0)) {
                initialBalance = initialBalance.subtract(draw);
            }
            if (upperBoundPossitive <= 0) {
                throw new DepositNotExistException("The deposit is closed");
            }
            if (compareValue < 0) {
                throw new InitialBalanceBecameZeroException("Your initialBalance is not enough");
            }
        }
        System.out.println(initialBalance);
    }

    void depositVerb(BigDecimal draw) throws DepositNotExistException, InitialBalancePassedUpperBoundException {

        synchronized (this) {
            int compareInitialBalanceToUpperBound = initialBalance.add(draw).compareTo(upperBound);
            int upperBoundPossitive = upperBound.compareTo(BigDecimal.ZERO);
            //  initial+draw<upper  && upperBound>0
            if ((compareInitialBalanceToUpperBound <= 0) && (upperBoundPossitive > 0)) {
                System.out.println(draw);
                initialBalance = initialBalance.add(draw);
            }
            if (upperBoundPossitive <= 0) {
                throw new DepositNotExistException("The deposit is closed");
            }
            if (compareInitialBalanceToUpperBound > 0) {
                throw new InitialBalancePassedUpperBoundException("You pass the upperBound");
            }
        }
        System.out.println(initialBalance);
    }

}
