import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static org.hamcrest.CoreMatchers.*;

import java.util.*;
import java.lang.reflect.*;
import java.util.Set;
import java.util.HashSet;

public class ATMTests {

    public static void testCreateTransactionOrAccount() throws Throwable {
        try{
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, -2.f);
            Transaction t2 = new Transaction(TransactionType.DEPOSIT, -2.5f, null);
        }catch (NullPointerException e) {
            e.printStackTrace();
            Assert.fail("Transaction failed to be properly created. Check the tester");
        }

        try {
            List<Transaction> list = new ArrayList<>();
            list.add(new Transaction(TransactionType.DEPOSIT, 1, "asdf"));
            List<Transaction> clonedList = new ArrayList<>(list);
            Account t = new Account(list);
            Field f = t.getClass().getDeclaredField("pastTransactions");
            f.setAccessible(true);
            List<Transaction> currentTransactions = (List<Transaction>) f.get(t);
            Assert.assertThat("pastTransactions is not properly set", list, equalTo(currentTransactions));
        } catch (Exception e){
            e.printStackTrace();
            Assert.fail("Account constructor returns an error");

        }
    }

    private static boolean hasFailed = false;
    private static final int numberOfTests = 50;
    private static final int numberOfCases = 6;

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            hasFailed = true;
            super.failed(e, description);
        }
    };

    private TesterTransaction convertFromTransaction(Transaction t){
        if(t.hasComment()){
            return new TesterTransaction(t.getType(), t.getAmount(), t.getComment().get());
        }
        return new TesterTransaction(t.getType(), t.getAmount());
    }
    /**
     * Class that implements equals and toString in order to make test writing more manageable and the output
     * more constructive
     */
    private class TesterTransaction extends Transaction implements Comparable<Transaction> {

        public TesterTransaction(TransactionType type, double amount) {
            super(type, amount);
        }

        public TesterTransaction(TransactionType type, double amount, String s) {
            super(type,amount,s);
        }

        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(o == null || !(o instanceof TesterTransaction)) return false;
            TesterTransaction other = (TesterTransaction) o;
            return this.getType() == other.getType()
                    && this.getAmount() == other.getAmount()
                    && this.getComment().equals(other.getComment());
        }

        @Override
        public String toString() {
            String s = "";
            s = s + getType().name() + " " + getAmount();
            if(hasComment()) {
                s = s + " " + getComment().get();
            }
            return s;
        }

        @Override
        public int compareTo(Transaction o) {
            if(this.getType().ordinal() != o.getType().ordinal()) return (this.getType() == TransactionType.DEPOSIT) ? 1 : -1;
            if(this.getAmount() != o.getAmount()) return ((this.getAmount() - o.getAmount()) > 0) ? 1 : -1;
            if(this.hasComment() && !o.hasComment()) return 1;
            if(o.hasComment() && !this.hasComment()) return -1;
            if(!o.hasComment() && !this.hasComment()) return 0;
            return this.getComment().get().compareTo(o.getComment().get());
        }
    }

    /**
     * A class that allows for testing if two lists are equal via the equals method (allowing for use of JUnits equalTo)
     */
    private class TestList{
        private ArrayList<TesterTransaction> list;

        public ArrayList<TesterTransaction> getList(){
            return list;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof TestList)) return false;
            TestList other = (TestList) o;
            List<TesterTransaction> thisList = new ArrayList<>(list);
            List<TesterTransaction> otherList = new ArrayList<>(other.getList());
            if(otherList.size() != thisList.size()) return false;
            Collections.sort(otherList);
            Collections.sort(thisList);
            for(int i = 0; i < thisList.size(); i++){
                if(thisList.get(i) == otherList.get(i)) continue;
                if(thisList.get(i) == null) return false;
                if(!thisList.get(i).equals(otherList.get(i))) return false;
            }
            return true;
        }

        @Override
        public String toString(){
            return list.toString();
        }

        public TestList(List<Transaction> c){
            this.list = new ArrayList<>();
            for(Transaction t : c){
                list.add(convertFromTransaction(t));
            }
        }

        public TestList(){
            this.list = new ArrayList<>();
        }
    }

    /**
     * Creates an interface that allows for code efficient generation of random tests
     */
    private interface TransactionGenerator{
        Random r = new Random(0);
        /**
         * Gives the test generator something that should return true;
         * @return a correct TesterTransaction
         */
        TesterTransaction trueTransaction();
        /**
         * Gives the test generator something that should return false
         * @return an invalid TesterTransaction
         */
        TesterTransaction falseTransaction();

        /**
         * Given the account, return the proper response to the list test
         * @param account The account to run the test on
         * @return the filtered list given by the tested files
         */
        //List<Transaction> getOutput(Account account);
    }

    /**
     * Creates a object that can generator arbitrary tests based on the testGenerator interface
     */
    private static class RandomTestGenerator{
        private TransactionGenerator gen;
        private int numberOfCases;
        private List<Transaction> expected;
        public RandomTestGenerator(TransactionGenerator gen, int numberOfCases) {
            this.gen = gen;
            this.numberOfCases = numberOfCases;
        }


        public List<Transaction> getInputList(){
            return getInputList(new Random().nextInt(numberOfCases-1)+1);
        }



        public List<Transaction> getInputList(int dispersion) {
            List<Transaction> returnedList = new ArrayList<>();
            expected = new ArrayList<>();
            for(int i = 0; i < numberOfCases; i++) {
                if (i % dispersion == 0 && !(i == 0 && dispersion > numberOfCases)){
                    TesterTransaction t = gen.trueTransaction();
                    returnedList.add(t);
                    expected.add(t);
                } else {
                    returnedList.add(gen.falseTransaction());
                }
            }
            return returnedList;
        }

        public List<Transaction> getExpected(){
            return expected;
        }
    }
    @Test
    public void testTransactionThreeArgConstructor() throws Throwable {
        String methodName = "Transaction's three arg constructor: ";
        Transaction t1 = new Transaction(TransactionType.DEPOSIT, 100.5, "A test comment");
        Transaction t2 = new Transaction(TransactionType.WITHDRAWAL, 50, "asdf");
        Field type = t1.getClass().getDeclaredField("type");
        type.setAccessible(true);
        TransactionType t1type = (TransactionType) type.get(t1);
        TransactionType t2type = (TransactionType) type.get(t2);
        Assert.assertThat(methodName + "type field is not properly set", t1type, equalTo(TransactionType.DEPOSIT));
        Assert.assertThat(methodName + "type field is not properly set", t2type, equalTo(TransactionType.WITHDRAWAL));

        Field amount = t1.getClass().getDeclaredField("amount");
        amount.setAccessible(true);
        double t1amt = (Double) amount.get(t1);
        double t2amt = (Double) amount.get(t2);
        Assert.assertThat(methodName + "amount is not properly set", t1amt, equalTo(100.5));
        Assert.assertThat(methodName + "amount is not properly set", t2amt, equalTo(50.0));

        Field comment = t1.getClass().getDeclaredField("comment");
        comment.setAccessible(true);
        Optional<String> t1comment = (Optional<String>) comment.get(t1);
        Optional<String> t2comment = (Optional<String>) comment.get(t2);
        Assert.assertThat(methodName + "comment is null, should be Optional.Empty()", t1comment, is(notNullValue()));
        Assert.assertThat(methodName + "comment is improperly set", t1comment, equalTo(Optional.of("A test comment")));
        Assert.assertThat(methodName + "comment is improperly set", t2comment, equalTo(Optional.of("asdf")));

        //Test the null case (if this proves valid)
        try {
            Transaction t3 = new Transaction(TransactionType.WITHDRAWAL, 100, null);
            Optional<String> t3comment = (Optional<String>) comment.get(t3);
            Assert.assertThat("the comment field not set to Optional.empty for null", t3comment, equalTo(Optional.empty()));
        }catch (NullPointerException e){
            Assert.fail(methodName + "Three arg constructor failed to properly handle the comment being null; led to a NullPointerException");
            e.printStackTrace();
        }

    }

    @Test
    public void testTransactionTwoArgConstructor() throws Throwable{
        Transaction t1;
        try {
            t1 = new Transaction(TransactionType.WITHDRAWAL, 100.2);
        }catch (NullPointerException e){
            Assert.fail("Transaction returned null pointer exception");
        }
        Transaction t2 = new Transaction(TransactionType.DEPOSIT, 57);
        t1 = new Transaction(TransactionType.WITHDRAWAL, 100.2);
        Field type = t1.getClass().getDeclaredField("type");
        type.setAccessible(true);
        TransactionType t1type = (TransactionType) type.get(t1);
        TransactionType t2type = (TransactionType) type.get(t2);
        Assert.assertThat("type not properly set", t1type, equalTo(TransactionType.WITHDRAWAL));
        Assert.assertThat("type not properly set", t2type, equalTo(TransactionType.DEPOSIT));

        Field amt = t1.getClass().getDeclaredField("amount");
        amt.setAccessible(true);
        double t1amount = (Double) amt.get(t1);
        double t2amount = (Double) amt.get(t2);
        Assert.assertThat("amount not properly set", t1amount, equalTo(100.2));
        Assert.assertThat("amount not properly set", t2amount, equalTo(57.0));

        Field comment = t1.getClass().getDeclaredField("comment");
        comment.setAccessible(true);
        Optional<String> t1comment = (Optional<String>) comment.get(t1);
        Optional<String> t2comment = (Optional<String>) comment.get(t2);
        Assert.assertThat("comment not set properly" , t1comment, equalTo(Optional.empty()));
        Assert.assertThat("comment not set properly", t2comment, equalTo(Optional.empty()));
    }

    @Test
    public void testTransactionGetters(){
        Transaction t1 = new Transaction(TransactionType.WITHDRAWAL, 100.2, "asdf");
        Transaction t2 = new Transaction(TransactionType.DEPOSIT, 50.5);

        Assert.assertThat("getType() returns improper value", t1.getType(), equalTo(TransactionType.WITHDRAWAL));
        Assert.assertThat("getType() returns improper value", t2.getType(), equalTo(TransactionType.DEPOSIT));

        Assert.assertThat("amount() returns improper value", t1.getAmount(), equalTo(100.2));
        Assert.assertThat("amount() returns improper value", t2.getAmount(), equalTo(50.5));

        Assert.assertThat("comment() returns improper value", t1.getComment(), equalTo(Optional.of("asdf")));
        Assert.assertThat("comment() returns improper value", t2.getComment(), equalTo(Optional.empty()));
    }

    @Test
    public void testTransactionHasComment() {
        Transaction t1 = new Transaction(TransactionType.WITHDRAWAL, 100.2, "asdf");
        Transaction t2 = new Transaction(TransactionType.DEPOSIT, 50.5);

        Assert.assertThat("hasComment() returns improper value when there is a comment", t1.hasComment(), equalTo(true));
        Assert.assertThat("hasComment() returns improper value when there isn't a comment", t2.hasComment(), equalTo(false));
    }

    @Test
    public void testAccountConstructor() throws Throwable{
        List<Transaction> pastTransactions = new ArrayList<>();
        pastTransactions.add(new TesterTransaction(TransactionType.DEPOSIT, 100));
        pastTransactions.add(new TesterTransaction(TransactionType.WITHDRAWAL,100, "A comment"));

        Account testAccount = new Account(pastTransactions);
        Field f = testAccount.getClass().getDeclaredField("pastTransactions");
        f.setAccessible(true);
        List<Transaction> testAccountTransactions = (List<Transaction>) f.get(testAccount);
        Assert.assertThat("Constructor did not set List<Transactions> properly", testAccountTransactions , equalTo(pastTransactions));
    }

    @Test
    public void testAccountGetTransaction(){
        List<Transaction> testTransactions = new ArrayList<>();
        Random r = new Random(0); //keep the tests consistent
        for(int i = 0; i < 20; i++) {
            TransactionType randType = (r.nextBoolean()) ? TransactionType.WITHDRAWAL : TransactionType.DEPOSIT;
            double amount = r.nextDouble();
            String comment = (r.nextBoolean()) ? null : "A test comment";
            testTransactions.add(new TesterTransaction(randType, amount, comment));
        }

        Account testAccount = new Account(testTransactions);
        for(int i = 0; i <  testTransactions.size(); i++) {
            Transaction returned = testAccount.getTransaction(i);
            Transaction testTransaction = testTransactions.get(i);
            Assert.assertThat("getTransaction() failed to properly get the right transaction for n = " + i, returned, equalTo(testTransaction));
        }
    }

    public static boolean falsePredicate(Transaction t){ return false;}

    public static boolean truePredicate(Transaction t) {return true;}

    @Test
    public void testAccountFindTransactionByPredicate() throws Throwable{
        testCreateTransactionOrAccount();
        List<Transaction> pastTransactions = new ArrayList<>();
        pastTransactions.add(new TesterTransaction(TransactionType.DEPOSIT, 100));
        pastTransactions.add(new TesterTransaction(TransactionType.WITHDRAWAL,100, "A comment"));

        List<Transaction> clonedPastTransactions = new ArrayList<>(pastTransactions);

        Account testAccount = new Account(pastTransactions);
        List<Transaction> output;
        List<Transaction> expectedOutput;
        TestList expected;
        TestList actual;

        String failString = "findTransactionByPredicate failed to properly filter predicate ";
        String changedString = "findTransactionByPredicate changed pastTransactions field during execution";
        Field f = testAccount.getClass().getDeclaredField("pastTransactions");
        f.setAccessible(true);
        List<Transaction> currentTransactions;

        expectedOutput = new ArrayList<>();
        output = testAccount.findTransactionsByPredicate(ATMTests::falsePredicate);
        currentTransactions = (List<Transaction>) f.get(testAccount);
        expected = new TestList(expectedOutput);
        actual = new TestList(output);
        Assert.assertThat(changedString, currentTransactions, equalTo(clonedPastTransactions));
        Assert.assertThat(failString + "for all false", expected, equalTo(actual));

        output = testAccount.findTransactionsByPredicate(ATMTests::truePredicate);
        currentTransactions = (List<Transaction>) f.get(testAccount);
        expected = new TestList(clonedPastTransactions);
        actual = new TestList(output);
        Assert.assertThat(changedString, currentTransactions, equalTo(clonedPastTransactions));
        Assert.assertThat(failString + "for all true" , expected, equalTo(actual));
        for(int i = 0; i < numberOfTests; i++){
            doRandomPredicate();
        }
    }

    public static boolean randomTest(Transaction t){
        Random r = new Random(t.hashCode());
        return r.nextBoolean();
    }

    public void doRandomPredicate() throws Exception{
        List<Transaction> testList = new ArrayList<>();
        Account testAccount = new Account(testList);
        List<Transaction> correctList = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            testList.add(new TesterTransaction(TransactionType.WITHDRAWAL, 100, "A comment"));
        }
        List<Transaction> clonedTestList = new ArrayList<>(testList);

        for (Transaction t: testList){
            if (randomTest(t)) correctList.add(t);
        }

        List<Transaction> results = testAccount.findTransactionsByPredicate(ATMTests::randomTest);

        Field f = testAccount.getClass().getDeclaredField("pastTransactions");
        f.setAccessible(true);
        List<Transaction> currentTransaction = (List<Transaction>) f.get(testAccount);
        Assert.assertThat("findTransactionByPredicate modified the pastTransactions field", currentTransaction, equalTo(clonedTestList));
        Assert.assertThat("findTransactionByPredicate failed to return expected value", new TestList(results), equalTo(new TestList(correctList)));
    }

    private class GetTransactionsByAmountGenerator implements TransactionGenerator{
        int correctAmount;
        public GetTransactionsByAmountGenerator(){
            this.correctAmount = new Random().nextInt(100);
        }

        @Override
        public TesterTransaction trueTransaction() {
            TransactionType t = r.nextBoolean() ? TransactionType.WITHDRAWAL : TransactionType.DEPOSIT;
            int amount = correctAmount;
            if(r.nextBoolean()){
                return new TesterTransaction(t, amount, "A comment");
            }
            return new TesterTransaction(t, amount);
        }

        @Override
        public TesterTransaction falseTransaction(){
            TransactionType t = r.nextBoolean() ? TransactionType.WITHDRAWAL : TransactionType.DEPOSIT;
            int amount;
            do {
                amount = r.nextInt(100);
            }while(amount == correctAmount);
            if(r.nextBoolean()){
                return new TesterTransaction(t, amount, "A comment");
            }
            return new TesterTransaction(t, amount);
        }

        public int getCorrectAmount(){
            return correctAmount;
        }
    }

    @Test
    public void testAccountGetTransactionsByAmount() throws Throwable {
        testCreateTransactionOrAccount();
        Account testAccount = new Account(new ArrayList<>());
        Field f = testAccount.getClass().getDeclaredField("pastTransactions");
        f.setAccessible(true);
        TestList actual;
        TestList expected;

        String changedString = "getTransactionsByAmount changed the pastTransactions field";
        String failString = "getTransactionsByAmount failed to return expected list";

        for(int i = 0; i < numberOfTests; i++){
            GetTransactionsByAmountGenerator gen = new GetTransactionsByAmountGenerator();
            RandomTestGenerator rnd = new RandomTestGenerator(gen, numberOfCases);
            int dispersion = new Random().nextInt(numberOfCases-1)+1;
            if (i == 0) dispersion = 1;
            if (i == 1) dispersion = 2;
            if (i == 2) dispersion = numberOfCases+1;//forces a test on empty list
            List<Transaction> inputList = rnd.getInputList(dispersion);
            List<Transaction> correctList = rnd.getExpected();
            testAccount = new Account(inputList);
            List<Transaction> clonedInputList = new ArrayList<>(inputList);
            List<Transaction> output = testAccount.getTransactionsByAmount(gen.getCorrectAmount());
            List<Transaction> currentTransactions = (List<Transaction>) f.get(testAccount);
            Assert.assertThat(changedString, currentTransactions, equalTo(clonedInputList));
            actual = new TestList(output);
            expected = new TestList(correctList);
            Assert.assertThat(failString, actual, equalTo(expected));
        }
    }

    private class GetByTypeTransactionGenerator implements TransactionGenerator{
        private TransactionType correctType;

        public GetByTypeTransactionGenerator(TransactionType correctType){
            this.correctType = correctType;
        }

        @Override
        public TesterTransaction trueTransaction() {
            int amount = r.nextInt(100);
            if(r.nextBoolean()){
                return new TesterTransaction(correctType, amount, "A comment String");
            }
            return  new TesterTransaction(correctType, amount);
        }

        @Override
        public TesterTransaction falseTransaction() {
            TransactionType t = (correctType == TransactionType.DEPOSIT) ? TransactionType.WITHDRAWAL : TransactionType.DEPOSIT;
            int amount = r.nextInt(100);
            if(r.nextBoolean()){
                return new TesterTransaction(t, amount, "A comment String");
            }
            return  new TesterTransaction(t, amount);
        }
    }

    public void testAccountTypeGetters(TransactionType correctType, String methodName) throws Throwable{
        testCreateTransactionOrAccount();
        RandomTestGenerator rnd = new RandomTestGenerator(new GetByTypeTransactionGenerator(correctType), numberOfCases);
        Account testAccount = new Account(new ArrayList<>());
        Field f = testAccount.getClass().getDeclaredField("pastTransactions");
        f.setAccessible(true);

        for(int i = 0; i < numberOfTests; i++) {
            List<Transaction> inputList = rnd.getInputList(2);
            List<Transaction> clonedInputList = new ArrayList<>(inputList);
            List<Transaction> expectedOutput = rnd.getExpected();
            testAccount = new Account(inputList);
            List<Transaction> output = (correctType == TransactionType.WITHDRAWAL) ? testAccount.getWithdrawals() : testAccount.getDeposits();
            List<Transaction> currentTransactions = (List<Transaction>) f.get(testAccount);
            TestList actual = new TestList(output);
            TestList expected = new TestList(expectedOutput);
            Assert.assertThat(methodName + " modified pastTransactions field", currentTransactions, equalTo(clonedInputList));
            Assert.assertThat(methodName + " failed to return expected result", actual, equalTo(expected));
        }
    }

    @Test
    public void testAccountGetWithdrawals() throws Throwable {
        testAccountTypeGetters(TransactionType.WITHDRAWAL, "getWithdrawals");
    }


    @Test
    public void testAccountGetDeposits() throws Throwable{
        testAccountTypeGetters(TransactionType.DEPOSIT, "getDeposits");
    }

    private class GetTransactionsWithCommentLengthGenerator implements TransactionGenerator {
        private int correctLength;

        public GetTransactionsWithCommentLengthGenerator() {
            correctLength = r.nextInt(10) + 2;
        }

        public int getCorrectLength() {
            return correctLength;
        }

        private String createStringOfLength(int n){
            StringBuilder s = new StringBuilder();
            for(int i = 0; i < n; i++){
                s.append("a");
            }
            return s.toString();
        }

        @Override
        public TesterTransaction trueTransaction() {
            TransactionType t = (r.nextBoolean()) ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
            int amount = r.nextInt(100);
            String comment = createStringOfLength(r.nextInt(10)+correctLength+1);
            return new TesterTransaction(t, amount, comment);
        }

        @Override
        public TesterTransaction falseTransaction() {
            TransactionType t = (r.nextBoolean()) ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
            int amount = r.nextInt(100);
            int commentLength;
            do {
                commentLength = r.nextInt(20);
            }while(commentLength > correctLength);
            if(r.nextBoolean()) commentLength = correctLength; //ensures a test of > vs >=
            String comment = createStringOfLength(commentLength);
            return new TesterTransaction(t, amount, comment);
        }
    }

    @Test
    public void testAccountGetTransactionsWithCommentLongerThan() throws Throwable {
        testCreateTransactionOrAccount();
        Account testAccount = new Account(new ArrayList<>());
        Field f = testAccount.getClass().getDeclaredField("pastTransactions");
        f.setAccessible(true);

        for(int i = 0; i < numberOfTests; i++) {
            GetTransactionsWithCommentLengthGenerator gen = new GetTransactionsWithCommentLengthGenerator();
            RandomTestGenerator rnd = new RandomTestGenerator(gen, 6);
            List<Transaction> inputList = rnd.getInputList(2);
            List<Transaction> clonedInputList = new ArrayList<>(inputList);
            List<Transaction> correctList = rnd.getExpected();

            testAccount = new Account(inputList);
            List<Transaction> output = testAccount.getTransactionsWithCommentLongerThan(gen.getCorrectLength());
            List<Transaction> currentTransactions = (List<Transaction>) f.get(testAccount);
            Assert.assertThat("getTransactionsWithCommentLongerThan changed the pastTransactions field", currentTransactions, equalTo(clonedInputList));
            Assert.assertThat("getTransactionsWithCommentLongerThan failed to return the expected value", new TestList(correctList), equalTo(new TestList(output)));

        }
    }

    private class GetCommentWithTransactionCommentsGenerator implements TransactionGenerator {

        @Override
        public TesterTransaction trueTransaction() {
            Random r = new Random();
            TransactionType t = (r.nextBoolean()) ? TransactionType.WITHDRAWAL : TransactionType.DEPOSIT;

            double amount = r.nextDouble();

            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            alphabet = alphabet + alphabet.toUpperCase();
            StringBuilder s = new StringBuilder();
            for(int i = 0; i < r.nextInt(25); i++) {
                int index = r.nextInt(alphabet.length());
                s.append(alphabet.charAt(index));
            }

            return new TesterTransaction(t, amount, s.toString());
        }

        @Override
        public TesterTransaction falseTransaction() {
            Random r = new Random();
            TransactionType t = (r.nextBoolean()) ? TransactionType.WITHDRAWAL : TransactionType.DEPOSIT;

            double amount = r.nextDouble();

            if(r.nextBoolean()) {
                return new TesterTransaction(t, amount);
            } else {
                return new TesterTransaction(t, amount, null);
            }
        }
    }

    @Test
    public void testAccountGetTransactionWithComments() throws Throwable{
        testCreateTransactionOrAccount();
        Account testAccount = new Account(new ArrayList<>());
        Field f = testAccount.getClass().getDeclaredField("pastTransactions");
        f.setAccessible(true);

        for(int i = 0; i < numberOfTests; i++){
            RandomTestGenerator rnd = new RandomTestGenerator( new GetCommentWithTransactionCommentsGenerator(), numberOfCases);
            List<Transaction> pastTransactions = rnd.getInputList();
            List<Transaction> clonedPastTransactions = new ArrayList<>(pastTransactions);
            List<Transaction> expected = rnd.getExpected();

            testAccount = new Account(pastTransactions);
            List<Transaction> output = testAccount.getTransactionsWithComment();
            List<Transaction> currentTransactions = (List<Transaction>) f.get(testAccount);
            Assert.assertThat("getTransactionsWithComments modified the pastTransactions field", currentTransactions, equalTo(clonedPastTransactions));
            Assert.assertThat("getTransactionsWithComments did not return the expected value", new TestList(output), equalTo(new TestList(expected)));
        }
    }

    @AfterClass
    public static void present(){
        if(!hasFailed){
            System.out.println("All tests have succeeded. Please note that this does not test if you use the right expression, or checkstyle. If you succeed in those tasks, you shall pass.");
            printGandalf();
        }
    }

    public static void printGandalf(){
        System.out.println("                                  ....\n" +
                "                                .'' .'''\n" +
                ".                             .'   :\n" +
                "\\\\                          .:    :\n" +
                " \\\\                        _:    :       ..----.._\n" +
                "  \\\\                    .:::.....:::.. .'         ''.\n" +
                "   \\\\                 .'  #-. .-######'     #        '.\n" +
                "    \\\\                 '.##'/ ' ################       :\n" +
                "     \\\\                  #####################         :\n" +
                "      \\\\               ..##.-.#### .''''###'.._        :\n" +
                "       \\\\             :--:########:            '.    .' :\n" +
                "        \\\\..__...--.. :--:#######.'   '.         '.     :\n" +
                "        :     :  : : '':'-:'':'::        .         '.  .'\n" +
                "        '---'''..: :    ':    '..'''.      '.        :'\n" +
                "           \\\\  :: : :     '      ''''''.     '.      .:\n" +
                "            \\\\ ::  : :     '            '.      '      :\n" +
                "             \\\\::   : :           ....' ..:       '     '.\n" +
                "              \\\\::  : :    .....####\\\\ .~~.:.             :\n" +
                "               \\\\':.:.:.:'#########.===. ~ |.'-.   . '''.. :\n" +
                "                \\\\    .'  ########## \\ \\ _.' '. '-.       '''.\n" +
                "                :\\\\  :     ########   \\ \\      '.  '-.        :\n" +
                "               :  \\\\'    '   #### :    \\ \\      :.    '-.      :\n" +
                "              :  .'\\\\   :'  :     :     \\ \\       :      '-.    :\n" +
                "             : .'  .\\\\  '  :      :     :\\ \\       :        '.   :\n" +
                "             ::   :  \\\\'  :.      :     : \\ \\      :          '. :\n" +
                "             ::. :    \\\\  : :      :    ;  \\ \\     :           '.:\n" +
                "              : ':    '\\\\ :  :     :     :  \\:\\     :        ..'\n" +
                "                 :    ' \\\\ :        :     ;  \\|      :   .'''\n" +
                "                 '.   '  \\\\:                         :.''\n" +
                "                  .:..... \\\\:       :            ..''\n" +
                "                 '._____|'.\\\\......'''''''.:..'''\n" +
                "                            \\\\");
    }
}
