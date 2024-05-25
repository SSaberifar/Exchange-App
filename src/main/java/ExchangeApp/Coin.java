package ExchangeApp;

public class Coin {
    private String name;
    private double value;
    private String changes;
    private double max_value;
    private double min_value;
    private int Count;

    public Coin(String name, double value, String changes, double max_value, double min_value, int count) {
        this.setName(name);
        this.setValue(value);
        this.setChanges(changes);
        this.setMax_value(max_value);
        this.setMin_value(min_value);
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
        return max_value;
    }

    public void setMax_value(double max_value) {
        this.max_value = max_value;
    }

    public double getMin_value() {
        return min_value;
    }

    public void setMin_value(double min_value) {
        this.min_value = min_value;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
