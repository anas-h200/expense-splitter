import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class BalanceSheet {
    private final Map<User, Long> balances = new HashMap<>();

    private void adjust(User user, long delta) {
        long currentBalance = balances.getOrDefault(user, 0L);
        currentBalance += delta;
        balances.put(user, currentBalance);
    }

    public long getBalance(User user) {
        return balances.getOrDefault(user, 0L);
    }

    public void addExpense(Expense expense, List<User> participants, SplitStrategy strategy) {
        if (expense.getAmount() <= 0) {
            throw new InvalidExpenseException("Amount must be positive");
        }
        if (participants.isEmpty()) {
            throw new InvalidExpenseException("Participants cannot be empty");
        }

        List<Long> shares = strategy.calculateShares(expense.getAmount(), participants);
        User payer = expense.getPaidBy();

        int n = participants.size();

        for (int i=0; i<n; i++) {
            User participant = participants.get(i);
            long share = shares.get(i);

            adjust(participant, -share);
            adjust(payer, share);
        }
    }

    public boolean isBalanced() {
        long total = 0;
        for (long balance: balances.values()) {
            total += balance;
        }

        return total == 0;
    }

    public void printBalances() {
        for (Map.Entry<User, Long> entry: balances.entrySet()) {
            User user = entry.getKey();
            long balance = entry.getValue();

            if (balance > 0) {
                System.out.println(user.getName() + " is owed " + balance + " paise");
            }
            else if (balance < 0) {
                System.out.println(user.getName() + " owes " + Math.abs(balance) + " paise");
            }
            else {
                System.out.println(user.getName() + " is settled up.");
            }
        }
    }

    public List<Settlement> getSettlements() {
        List<User> creditors = new ArrayList<>();
        List<User> debtors = new ArrayList<>();

        for (Map.Entry<User, Long> entry: balances.entrySet()) {
            User user = entry.getKey();
            long balance = entry.getValue();

            if (balance > 0) {
                creditors.add(user);
            }
            else if (balance < 0) {
                debtors.add(user);
            }
        }

        System.out.println("creditors: " + creditors.size() + " and " + "debtors: " + debtors.size());
        return new ArrayList<>();
    }
}
