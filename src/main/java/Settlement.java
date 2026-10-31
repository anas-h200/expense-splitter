public class Settlement {
    private final User from;
    private final User to;
    private final long amount;

    Settlement(User from, User to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public User getFrom() { return from; }
    public User getTo() { return to; }
    public long getAmount() { return amount; }

    @Override
    public String toString() { return from.getName() + " pays " + to.getName() + " " + amount + " paise";  }
}
