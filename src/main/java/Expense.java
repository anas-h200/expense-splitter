import java.time.LocalDateTime;

public class Expense {
    private final int id;
    private final String description;
    private final long amount;
    private final User paidBy;
    private final SplitType splitType;
    private final LocalDateTime createdAt;

    Expense(int id, String description, long amount, User paidBy, SplitType splitType) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitType = splitType;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public long getAmount() { return amount; }
    public User getPaidBy() { return paidBy; }
    public SplitType getSplitType() { return splitType; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Expense{id=" + id
                + ", description='" + description + "'"
                + ", amount=" + amount + " paise"
                + ", paidBy=" + paidBy.getName()
                + ", splitType=" + splitType
                + ", createdAt=" + createdAt + "}";
    }


}
