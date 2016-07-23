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

    public void withdraw(BigDecimal draw) {
        int compareValue = initialBalance.compareTo(draw);
        int upperBoundPossitive = upperBound.compareTo(BigDecimal.ZERO);
        //  initialBalance>=draw  &&  upperBound>0
        if ((compareValue >= 0) && (upperBoundPossitive > 0)){
            initialBalance = initialBalance.subtract(draw);
        }//upperBound<0
        else if (upperBoundPossitive <= 0){
            System.out.println("The deposit is closed");
        }//initialBalance<draw
        else{
            System.out.println("initialBalance is not enough");
        }
    }


    public BigDecimal depositVerb(BigDecimal draw) {

        int compareInitialBalanceToUpperBound = initialBalance.add(draw).compareTo(upperBound);
        int upperBoundPossitive = upperBound.compareTo(BigDecimal.ZERO);
        //  initial+draw<upper  && upperBound>0
        if((compareInitialBalanceToUpperBound == -1) && (upperBoundPossitive > 0)){
            System.out.println(draw);
            initialBalance = initialBalance.add(draw);
            System.out.println(initialBalance);
        }//upperBound<0
        else if (upperBoundPossitive <= 0){
            System.out.println("The deposit is closed");
        }//initialBalance>upperBound
        else{
            System.out.println("UpperBound Warning");
        }
        return draw;
    }

}
