import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.Alert.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.TableColumn.*;
import javafx.application.*;
public class WhaleTraining extends Application {
    private WhaleTank whaleTank = new WhaleTank();
    private ObservableList<KillerWhale> whaleList;
    public void start(Stage primaryStage) {
        // Set up main layout
        // Root
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 700, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Killer Whale Training Management");
        // VBox to hold main content
        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(10);
        mainLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(mainLayout);
        // Title Label
        Label titleLabel = new Label("Killer Whale Training Management System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        mainLayout.getChildren().add(titleLabel);
        // Table to display all whales and their info
        TableView<KillerWhale> whaleTable = new TableView<>();
        whaleTable.setStyle("-fx-font-size: 15px;");
        mainLayout.getChildren().add(whaleTable);
        // Add the columns to the table
        TableColumn<KillerWhale, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<KillerWhale, String> genderCol = new TableColumn<>("Gender");
        genderCol.setMinWidth(150);
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        TableColumn<KillerWhale, String> lvlCol = new TableColumn<>("Training Level");
        lvlCol.setMinWidth(150);
        lvlCol.setCellValueFactory(new PropertyValueFactory<>("trainLvl"));
        TableColumn<KillerWhale, String> atkCol = new TableColumn<>("Attacked a Trainer?");
        atkCol.setMinWidth(150);
        atkCol.setCellValueFactory(new PropertyValueFactory<>("attacked"));
        whaleTable.getColumns().addAll(nameCol, genderCol, lvlCol, atkCol);
        whaleTable.setMaxWidth(600);
        whaleTable.setMaxHeight(400);
        primaryStage.show(); 
        // Button HBox for adding and removing whales
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        // Add Whale Button
        Button addWhaleBtn = new Button("Add Killer Whale");
        addWhaleBtn.setOnAction(e -> {
            KillerWhale newWhale = whaleTank.add_dialog();
            if (newWhale != null) {
                whaleList = whaleTank.toObservableList();
                whaleTable.setItems(whaleList);
            }
        });
        buttonBox.getChildren().add(addWhaleBtn);
        // Remove Whale Button
        Button removeWhaleBtn = new Button("Remove Killer Whale");
        removeWhaleBtn.setOnAction(e -> {
            KillerWhale removed = whaleTank.remove_dialog();
            if (removed != null) {
                whaleList = whaleTank.toObservableList();
                whaleTable.setItems(whaleList);
            }
        });
        buttonBox.getChildren().add(removeWhaleBtn);
        mainLayout.getChildren().add(buttonBox);

        // Top menu bar with options
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        // Train Whale Item
        MenuItem trainItem = new MenuItem("Train Whale");
        trainItem.setOnAction(e -> {
            KillerWhale trained = whaleTank.train_dialog();
            if (trained != null)
            {
                Iterator<KillerWhale> iterator = whaleTank.iterator();
                while (iterator.hasNext()) {
                    KillerWhale whale = iterator.next();
                    if (whale.getName().equalsIgnoreCase(trained.getName())) {
                        whale.setTrainLvl(trained.getTrainLvl());
                        whale.setAttacked(trained.getAttacked());
                        break;
                    }
                }
            }
            whaleTable.refresh();
        });
        fileMenu.getItems().add(trainItem);
        // Breed Whales Item
        MenuItem breedItem = new MenuItem("Breed Whales");
        breedItem.setOnAction(e -> {
            KillerWhale bred = whaleTank.breedWhales_dialog();
            if (bred != null) {
                whaleList = whaleTank.toObservableList();
                whaleTable.setItems(whaleList);
            }
        });
        fileMenu.getItems().add(breedItem);
        // Clear item
        MenuItem clearItem = new MenuItem("Reset");
        clearItem.setOnAction(e -> {
            whaleTank.clear();
            whaleList = whaleTank.toObservableList();
            whaleTable.setItems(whaleList);
        });
        fileMenu.getItems().add(clearItem);
        // Exit item
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> { primaryStage.close(); });
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().add(fileMenu);
        root.setTop(menuBar);
    }
}
