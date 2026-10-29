import java.util.ArrayList;
import java.util.List;

public class PercentageSplit implements SplitStrategy {
    private final List<Integer> percentages;

    public PercentageSplit(List<Integer> percentages) {
        this.percentages = percentages;
    }

    @Override
    public List<Long> calculateShares(long totalAmount, List<User> participants) {
        int n = participants.size();

        if (percentages.size() != participants.size()) {
            throw new InvalidSplitException("Percentages count does not match with participants count.");
        }

        long total = 0;
        for (int i=0; i<n; i++) {
            total += percentages.get(i);
        }

        if (total != 100) {
            throw new InvalidSplitException("Percentage must sum to 100, got " + total);
        }

        List<Long> shares = new ArrayList<>();

        for (int i=0; i<n; i++) {
            long share = (totalAmount*percentages.get(i))/100;
            shares.add(share);
        }

        long sum = 0;

        for (long share: shares) {
            sum += share;
        }

        long remainder = totalAmount - sum;
        shares.set(0, shares.getFirst()+remainder);

        return shares;
    }
}
