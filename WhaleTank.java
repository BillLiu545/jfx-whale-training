import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
public class WhaleTank extends LinkedList<KillerWhale> {
    private final int MAX_TRAIN_LVL = 5;
    // Convert to observable list necessary for use in JavaFX table
    public ObservableList<KillerWhale> toObservableList() {
        ObservableList<KillerWhale> obsList = FXCollections.observableArrayList();
        for (KillerWhale whale : this) {
            obsList.add(whale);
        }
        return obsList;
    }
    // Find a whale by name
    private KillerWhale findWhale(String name) {
        for (KillerWhale whale : this) {
            if (whale.getName().equalsIgnoreCase(name)) {
                return whale;
            }
        }
        return null;
    }
    // Add a new whale by input dialog
    public KillerWhale add_dialog()
    {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Killer Whale");
        nameDialog.setHeaderText("Enter the name of the new Killer Whale:");
        Optional<String> nameResult = nameDialog.showAndWait();
        if (!nameResult.isPresent()) {
            return null; // User cancelled
        }
        String name = nameResult.get().trim();
        if (name.isEmpty()) {
            return null; // Invalid name
        }
        TextInputDialog genderDialog = new TextInputDialog();
        genderDialog.setTitle("Add Killer Whale");
        genderDialog.setHeaderText("Enter the gender of the new Killer Whale (M/F):");
        Optional<String> genderResult = genderDialog.showAndWait();
        if (!genderResult.isPresent()) {
            return null; // User cancelled
        }
        String gender = genderResult.get().trim().toUpperCase();
        if (!(gender.equals("M") || gender.equals("F"))) {
            return null; // Invalid gender
        }
        return addWhale(name, gender);
    }
    // Add a new whale
    public KillerWhale addWhale(String name, String gender)
    {
        KillerWhale added = new KillerWhale(name, gender, 0, false);
        add(added);
        return added;
    }
    // Remove a whale by dialog input
    public KillerWhale remove_dialog()
    {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Remove Killer Whale");
        nameDialog.setHeaderText("Enter the name of the Killer Whale to remove:");
        Optional<String> nameResult = nameDialog.showAndWait();
        if (!nameResult.isPresent()) {
            return null; // User cancelled
        }
        String name = nameResult.get().trim();
        return removeWhale(name);
    }
    // Remove a whale by name
    public KillerWhale removeWhale(String name) {
        KillerWhale whale = findWhale(name);
        if (whale != null) {
            remove(whale);
            return whale;
        }
        return null;
    }
    // Breed two whales based on user input
    public KillerWhale breedWhales_dialog()
    {
        TextInputDialog maleDialog = new TextInputDialog();
        maleDialog.setTitle("Breed Killer Whales");
        maleDialog.setHeaderText("Enter the name of the male Killer Whale:");
        Optional<String> maleResult = maleDialog.showAndWait();
        if (!maleResult.isPresent()) {
            return null; // User cancelled  
        }
        String maleName = maleResult.get().trim();
        KillerWhale male = findWhale(maleName);
        if (male == null || !male.getGender().equals("M")) {
            return null; // Invalid male
        }
        TextInputDialog femaleDialog = new TextInputDialog();
        femaleDialog.setTitle("Breed Killer Whales");
        femaleDialog.setHeaderText("Enter the name of the female Killer Whale:");
        Optional<String> femaleResult = femaleDialog.showAndWait();
        if (!femaleResult.isPresent()) {
            return null; // User cancelled
        }
        String femaleName = femaleResult.get().trim();
        KillerWhale female = findWhale(femaleName);
        if (female == null || !female.getGender().equals("F")) {
            return null; // Invalid female
        }
        return breedWhales(male, female);
    }
    // Breed two whales to create a new whale (male + female only)
    public KillerWhale breedWhales(KillerWhale male, KillerWhale female) {
        if (male.getGender().equals("M") && female.getGender().equals("F")) {
            String newName = male.getName() + " and " + female.getName();
            Random rand = new Random();
            String newGender = rand.nextBoolean() ? "M" : "F";
            KillerWhale babyWhale = new KillerWhale(newName, newGender, 0, false);
            add(babyWhale);
            return babyWhale;
        }
        return null;
    }
    // Train a whale based on dialog input
    public KillerWhale train_dialog()
    {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Train Killer Whale");
        nameDialog.setHeaderText("Enter the name of the Killer Whale to train:");
        Optional<String> nameResult = nameDialog.showAndWait();
        if (!nameResult.isPresent()) {
            return null; // User cancelled
        }
        String name = nameResult.get().trim();
        return trainWhale(name);
    }
    // Train a whale to increase its level - a higher level means higher chance of attacking
    public KillerWhale trainWhale(String name)
    {
        KillerWhale whale = findWhale(name);
        if (whale != null) {
            Iterator<KillerWhale> iterator = this.iterator();
            while (iterator.hasNext()) {
                KillerWhale current = iterator.next();
                if (current.getName().equalsIgnoreCase(name)) {
                    int currentLevel = whale.getTrainLvl();
                    if (currentLevel < MAX_TRAIN_LVL) {
                        current.setTrainLvl(currentLevel + 1);
                    }
                    Random rand = new Random();
                    int attackChance = current.getTrainLvl() * 20; // 10% chance per level
                    if (rand.nextInt(100) < attackChance) {
                        current.setAttacked(true);
                    }
                    whale = current;
                    break;
                }
            }
            return whale;
        }
        return null;
    }
    // Main method to ensure data structure works
    public static void main(String[] args) {
        WhaleTank tank = new WhaleTank();
        tank.addWhale("Orca1", "M");
        tank.addWhale("Orca2", "F");
        KillerWhale baby = tank.breedWhales(tank.findWhale("Orca1"), tank.findWhale("Orca2"));
        System.out.println("Bred new whale: " + baby);
        KillerWhale trained = tank.trainWhale("Orca1");
        System.out.println("Trained whale: " + trained);
    }
}