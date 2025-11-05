package model;

public class Door extends Device {
    private boolean isLocked;

    public Door(int id, String name, double powerConsumption, boolean isLocked) {
        super(id, name, powerConsumption);
        this.isLocked = isLocked;
    }

    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
