import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class BalanceSheet {
    private final Map<User, Long> balances = new HashMap<>();

    public void adjust(User user, long delta) {
        long currentBalance = balances.getOrDefault(user, 0L);
        currentBalance += delta;
        balances.put(user, currentBalance);
    }

    public long getBalance(User user) {
        return balances.getOrDefault(user, 0L);
    }

    public void addExpense(Expense expense, List<User> participants, SplitStrategy strategy) {
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
}
