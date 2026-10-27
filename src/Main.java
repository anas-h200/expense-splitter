import java.util.List;

public class Main {
    public static void main(String[] args) {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");

        List<User> users = List.of(u1, u2, u3);
        List<SplitStrategy> strategies = List.of(new EqualSplit(), new PercentageSplit(List.of(50,30,20)), new ExactSplit(List.of(5000L, 3000L, 2000L)));

        for (SplitStrategy strategy: strategies) {
            List<Long> shares = strategy.calculateShares(10001, users);

            long sum = 0;
            for (long share: shares) {
                sum += share;
            }

            System.out.println(strategy.getClass().getSimpleName() + ": " + shares + " sum=" + sum);
        }
    }
}