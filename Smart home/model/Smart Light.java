
package model;

public class Light extends Device {
    private int brightness;

    public Light(int id, String name, double powerConsumption, int brightness) {
        super(id, name, powerConsumption);
        this.brightness = brightness;
    }

    public void setBrightness(int level) {
        if (level >= 0 && level <= 100)
            this.brightness = level;
    }

    public int getBrightness() {
        return brightness;
    }
}
