package model;

public class AC extends Device {
    private int temperature;

    public AC(int id, String name, double powerConsumption, int temperature) {
        super(id, name, powerConsumption);
        this.temperature = temperature;
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
    }

    public int getTemperature() {
        return temperature;
    }
}
