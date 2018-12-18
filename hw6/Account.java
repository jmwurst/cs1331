import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A representation of a bank account. Records are stored of each transaction
 * that takes place, and subsets of these values based on different predicates
 * are accessible through different method calls.
 *
 * @author Justin Wurst
 * @version 11-16-18
 */
public class Account {
    private List<Transaction> pastTransactions;

    /**
     * Initializes an instance of Account given a list of past transactions.
     *
     * @param pastTransactions A List of past transactions, represented by
     *                         Transaction objects.
     */
    public Account(List<Transaction> pastTransactions) {
        this.pastTransactions = pastTransactions;
    }

    /**
     * Returns a specific transaction from the list of past transactions based
     * on a given index.
     *
     * @param n The index of the transaction to return.
     * @return a Transaction found at the given index in pastTransactions
     */
    public Transaction getTransaction(int n) {
        return pastTransactions.get(n);
    }

    /**
     * Generates a list of transactions from the list of past transactions which
     * are valid based on a given predicate.
     *
     * @param predicate The predicate that is used to check the validity of the
     *                  entries in pastTransactions.
     * @return a subset of pastTransactions containing only the Transaction
     *         instances which align with the provided Predicate.
     */
    public List<Transaction> findTransactionsByPredicate(Predicate<Transaction>
                                                         predicate) {
        List<Transaction> output = new ArrayList<>();
        for (int i = 0; i < pastTransactions.size(); i++) {
            if (predicate.test(pastTransactions.get(i))) {
                output.add(pastTransactions.get(i));
            }
        }
        return output;
    }

    /**
     * The private inner class used by the getTransactionsByAmount method which
     * implements the Predicate interface with type Transaction.
     *
     * @author Justin Wurst
     * @version 11-16-18
     */
    class TransactionsByAmount implements Predicate<Transaction> {
        private double amount;

        /**
         * Initializes a TransactionsByAmount using the given amount to compare
         * transactions to.
         *
         * @param amount The amount to check against given Transaction instances
         */
        public TransactionsByAmount(double amount) {
            this.amount = amount;
        }

        /**
         * Checks the validity of a given transaction based on if it is equal to
         * an amount.
         *
         * @param t The Transaction instance to test the validity of.
         * @return a boolean representing the validity of the Transaction
         */
        public boolean test(Transaction t) {
            return t.getAmount() == amount;
        }
    }

    /**
     * Filters the list of past transactions based on a predicate that checks
     * for transactions of amounts equal to a given amount. Only the
     * transactions that are equal to the given amount are output. An inner
     * class that implements the Predicate interface is used.
     *
     * @param amount A double representing the target transaction amount to
     *               search for in the list of past transactions.
     * @return a List of only those Transaction instances in pastTransactions
     *         that have a greater transaction amount than the double amount
     */
    public List<Transaction> getTransactionsByAmount(double amount) {
        return findTransactionsByPredicate(new TransactionsByAmount(amount));
    }

    /**
     * Filters the list of past transactions based on a predicate that checks
     * for withdrawals. Only the transactions that are withdrawals are output.
     * An anonymous inner class is used with the Predicate functional interface.
     *
     * @return a List of only those Transaction instances in pastTransactions
     *         that are of transaction type WITHDRAWAL
     */
    public List<Transaction> getWithdrawals() {
        return findTransactionsByPredicate(new Predicate<Transaction>() {
            public boolean test(Transaction transaction) {
                return transaction.getType()
                       .equals(TransactionType.valueOf("WITHDRAWAL"));
            }
        });
    }

    /**
     * Filters the list of past transactions based on a predicate that checks
     * for deposits. Only the transactions that are deposits are output. A
     * lambda expression is used with the Predicate functional interface.
     *
     * @return a List of only those Transaction instances in pastTransactions
     *         that are of transaction type DEPOSIT
     */
    public List<Transaction> getDeposits() {
        return findTransactionsByPredicate(t -> t.getType()
                .equals(TransactionType.valueOf("DEPOSIT")));
    }

    /**
     * Filters the list of past transactions based on a predicate that checks
     * for the existence of comments that are longer than a given length. Only
     * the transactions with such comments are output. A lambda expression is
     * used with the Predicate functional interface.
     *
     * @param length The lower bound, exclusive, of valid comment lengths.
     * @return a List of only those Transaction instances in pastTransactions
     *         that have comments longer than length
     */
    public List<Transaction> getTransactionsWithCommentLongerThan(int length) {
        return findTransactionsByPredicate(t -> t.hasComment()
                                                ? t.getComment()
                                                  .get().length() > length
                                                : false);
    }

    /**
     * Filters the list of past transactions based on a predicate that checks
     * for the existence of comments. Only the transactions with comments are
     * output. A method reference is used with the Predicate functional
     * interface.
     *
     * @return a List of only those Transaction instances in pastTransactions
     *         that have comments
     */
    public List<Transaction> getTransactionsWithComment() {
        return findTransactionsByPredicate(Transaction::hasComment);
    }

    /**
     * A getter method for the list of past transactions.
     *
     * @return the list of past transactions (pastTransactions)
     */
    public List<Transaction> getPastTransactions() {
        return pastTransactions;
    }
}
