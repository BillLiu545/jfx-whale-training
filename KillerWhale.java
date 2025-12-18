
public class KillerWhale {
    private String name, gender;
    private int trainLvl;
    private boolean attacked;
    // Constructor
    public KillerWhale(String name, String gender, int trainLvl, boolean attacked) {
        this.name = name;
        this.gender = gender;
        this.trainLvl = trainLvl;
        this.attacked = attacked;
    }
    // Getters
    // Name
    public String getName() {
        return name;
    }
    // Gender
    public String getGender() {
        return gender;
    }
    // Training Level
    public int getTrainLvl() {
        return trainLvl;
    }
    // Has attacked a trainer?
    public boolean getAttacked()
    {
        return attacked;
    }
    // Set a new training level
    public void setTrainLvl(int trainLvl) {
        this.trainLvl = trainLvl;
    }
    // Set attacked status
    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }
    // toString method
    public String toString() {
        return "KillerWhale [name=" + name + ", gender=" + gender + ", trainLvl=" + trainLvl + ", attacked=" + attacked + "]";
    }
}
