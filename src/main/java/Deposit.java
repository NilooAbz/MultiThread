import exceptions.DepositNotExistException;
import exceptions.InitialBalancePassedUpperBoundException;
import exceptions.InitialBalanceBecameZeroException;

import java.math.BigDecimal;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Deposit {

    private String customerName;
    private String id;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    public void withdraw(BigDecimal draw) throws DepositNotExistException, InitialBalanceBecameZeroException {
        int compareValue = initialBalance.compareTo(draw);
        int upperBoundPossitive = upperBound.compareTo(BigDecimal.ZERO);
        //  initialBalance>=draw  &&  upperBound>0
        if ((compareValue >= 0) && (upperBoundPossitive > 0)){
            initialBalance = initialBalance.subtract(draw);
        }
        if (upperBoundPossitive <= 0){
            throw new DepositNotExistException("The deposit is closed");
        }
        if(compareValue < 0){
            throw new InitialBalanceBecameZeroException("Your initialBalance is not enough");
        }
    }


    public BigDecimal depositVerb(BigDecimal draw) throws DepositNotExistException, InitialBalancePassedUpperBoundException {

        int compareInitialBalanceToUpperBound = initialBalance.add(draw).compareTo(upperBound);
        int upperBoundPossitive = upperBound.compareTo(BigDecimal.ZERO);
        //  initial+draw<upper  && upperBound>0
        if((compareInitialBalanceToUpperBound <= 0) && (upperBoundPossitive > 0)){
            System.out.println(draw);
            initialBalance = initialBalance.add(draw);
            System.out.println(initialBalance);
        }
        if(upperBoundPossitive <= 0){
            throw new DepositNotExistException("The diposit is closed");
        }
        if (compareInitialBalanceToUpperBound > 0 ){
            throw new InitialBalancePassedUpperBoundException("You pass the upperBound");
        }
        return draw;
    }

}
