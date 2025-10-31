package model;

public abstract class Device {
    protected int id;
    protected String name;
    protected boolean status;
    protected double powerConsumption;

    public Device(int id, String name, double powerConsumption) {
        this.id = id;
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.status = false;
    }

    public void turnOn() {
        status = true;
    }

    public void turnOff() {
        status = false;
    }

    public double getPowerUsage() {
        return status ? powerConsumption : 0;
    }

    
    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isOn() { return status; }
    public double getPowerConsumption() { return powerConsumption; }

    @Override
    public String toString() {
        return name + " [status=" + (status ? "ON" : "OFF") + "]";
    }
}
