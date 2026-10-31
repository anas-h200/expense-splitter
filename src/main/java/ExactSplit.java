import java.util.ArrayList;
import java.util.List;

public class ExactSplit implements SplitStrategy {
    private final List<Long> exactShares;

    public ExactSplit(List<Long> exactShares) {
        this.exactShares = exactShares;
    }

    @Override
    public List<Long> calculateShares(long totalAmount, List<User> participants) {
        int n = participants.size();

        if (exactShares.size() != n) {
            throw new InvalidSplitException("Exact shares count does not match with the participants count");
        }

        List<Long> shares = new ArrayList<>();

        for (int i=0; i<n; i++) {
            shares.add(exactShares.get(i));
        }

        long sumOfShares = 0;

        for (int i=0; i<n; i++) {
            sumOfShares += shares.get(i);
        }

        if (sumOfShares != totalAmount) {
            throw new InvalidSplitException("Exact shares sum to " + sumOfShares + " but expense total is " + totalAmount);
        }

        return shares;
    }
}
