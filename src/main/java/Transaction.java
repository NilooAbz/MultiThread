import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Transaction implements Serializable{

    private Integer transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;
    private String deposit;


    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
