import java.util.Optional;

/**
 * A representation of a bank account transaction. Denoted as either a deposit
 * or withdrawal, a transaction contains an amount transacted and may contain a
 * comment regarding the nature of the transaction.
 *
 * @author Justin Wurst
 * @version 11-16-18
 */
public class Transaction {
    private TransactionType type;
    private double amount;
    private Optional<String> comment;

    /**
     * Initializes an instance of Transaction based on a given transaction type,
     * amount, and comment on the nature of the transaction.
     *
     * @param type The type of transaction, either a deposit or withdrawal.
     * @param amount The amount of money transacted.
     * @param comment A comment regarding the transaction.
     */
    public Transaction(TransactionType type, double amount, String comment) {
        this.type = type;
        this.amount = amount;
        this.comment = Optional.of(comment);
    }

    /**
     * Initializes an instance of Transaction based on a given transaction type
     * and amount. Since no comment is provided, the instance variable which
     * would store the comment is initialized as an empty Optional.
     *
     * @param type The type of transaction, either a deposit or withdrawal.
     * @param amount The amount of money transacted.
     */
    public Transaction(TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
        comment = Optional.empty();
    }

    /**
     * A getter for the type of transaction, either deposit or withdrawal.
     *
     * @return the TransactionType which has the type of Transaction
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * A getter for the amount of money transacted.
     *
     * @return a double which is the amount transacted
     */
    public double getAmount() {
        return amount;
    }

    /**
     * A getter for the instance variable which would possess a comment.
     *
     * @return an Optional with parameter String which contains the comment on
     *         the transaction
     */
    public Optional<String> getComment() {
        return comment;
    }

    /**
     * Checks if there exists a comment on the transaction.
     *
     * @return a boolean if a comment exists.
     */
    public boolean hasComment() {
        return comment.isPresent();
    }
}
