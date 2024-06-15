public enum PrizeMoney {
    TwoB(2000000000),
    ThirtyM(30000000),
    OneFiftyK(150000),
    FiftyK(50000),
    FiveK(5000),
    Zero(0);

    private final int amount;

    PrizeMoney(int amount) {
        this.amount = amount;
    }
}
