import java.util.ArrayList;
import java.util.List;

/**
 * Tests for hw6
 * @author Tillson Galloway
 * @version 1.0.2
 */
public class Tests {

    public static void main(String[] args) {
        test();
        System.out.println("All tests passed!");
    }

    private static void test() throws AssertionError {
        List<Transaction> pastTransactions = new ArrayList<>();

        // Transaction tests
        Transaction transactionOne = new Transaction(TransactionType.DEPOSIT, 10.0);
        assertEquals(transactionOne.getType(), TransactionType.DEPOSIT);
        assertEquals(transactionOne.getAmount(), 10.0);
        assertBoolean(!transactionOne.getComment().isPresent());
        assertBoolean(!transactionOne.hasComment());

        String COMMENT_TEST_STRING_1 = "len is 8";
        String COMMENT_TEST_STRING_2 = "length is 12";
        String COMMENT_TEST_STRING_3 = "size5";

        Transaction transactionTwo = new Transaction(TransactionType.WITHDRAWAL, 10.0, COMMENT_TEST_STRING_1);
        assertEquals(transactionTwo.getType(), TransactionType.WITHDRAWAL);
        assertEquals(transactionTwo.getAmount(), 10.0);
        assertBoolean(transactionTwo.hasComment());
        assertEquals(transactionTwo.getComment().get(), COMMENT_TEST_STRING_1);

        pastTransactions.add(transactionOne);
        pastTransactions.add(transactionTwo);

        pastTransactions.add(new Transaction(TransactionType.DEPOSIT, 20.0, COMMENT_TEST_STRING_2));

        for (int i = 0; i < 11; i++) {
            pastTransactions.add(new Transaction(
                    i % 2 == 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL,
                    i));
        }
        int LIST_SIZE = pastTransactions.size();

        // Account tests
        Account account = new Account(pastTransactions);
        assertNotNull(account.getTransaction(0));
        assertNotNull(account.getTransaction(1));
        assertNotNull(account.getTransaction(11));
        assertEquals(account.getWithdrawals().size(), 6);
        assertEquals(account.getDeposits().size(), 8);
        assertEquals(account.getTransactionsByAmount(10.0).size(), 3);
        assertEquals(account.getTransactionsByAmount(50.0).size(), 0);
        assertEquals(account.findTransactionsByPredicate((t) -> t.getAmount() == 10.0).size(), 3);
        assertEquals(account.getTransactionsWithComment().size(), 2);
        assertEquals(account.getTransactionsWithCommentLongerThan(5).size(), 2);
        assertEquals(account.getTransactionsWithCommentLongerThan(8).size(), 1);
        assertEquals(account.getTransactionsWithCommentLongerThan(12).size(), 0);
        assertEquals(account.getTransactionsWithCommentLongerThan(10).get(0).getComment().get(), COMMENT_TEST_STRING_2);

        assertEquals(account.getPastTransactions().size(), LIST_SIZE);

        account.getPastTransactions().add(new Transaction(TransactionType.DEPOSIT, 15.0, COMMENT_TEST_STRING_3));
        assertEquals(account.getPastTransactions().size(), LIST_SIZE + 1);
        assertEquals(account.getTransactionsWithComment().size(), 3);

    }

    // Testing methods

    private static void assertEquals(Object obj1, Object obj2) throws AssertionError {
        if (!obj1.equals(obj2)) {
            throw new AssertionError();
        }
    }

    private static void assertBoolean(boolean b) {
        if (!b) {
            throw new AssertionError();
        }
    }

    private static void assertNotNull(Object obj) {
        if (obj == null) {
            throw new AssertionError();
        }
    }
}