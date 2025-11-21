package model;

public class Refrigerator extends Device {
    private int internalTemp;

    public Refrigerator(int id, String name, double powerConsumption, int internalTemp) {
        super(id, name, powerConsumption);
        this.internalTemp = internalTemp;
    }

    public void setInternalTemp(int temp) {
        this.internalTemp = temp;
    }

    public int getInternalTemp() {
        return internalTemp;
    }
}
