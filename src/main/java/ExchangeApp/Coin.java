package ExchangeApp;

public class Coin {
    private String name;
    private double value;
    private String changes;
    private double maxValue;
    private double minValue;
    private int count;

    public Coin(String name, double value, String changes, double maxValue, double minValue, int count) {
        this.setName(name);
        this.setValue(value);
        this.setChanges(changes);
        this.setMax_value(maxValue);
        this.setMin_value(minValue);
        this.setCount(count);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public double getMax_value() {
        return maxValue;
    }

    public void setMax_value(double max_value) {
        this.maxValue = max_value;
    }

    public double getMin_value() {
        return minValue;
    }

    public void setMin_value(double min_value) {
        this.minValue = min_value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
