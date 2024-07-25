package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    public GridPane pane = new GridPane();
    private TableView<Person> leaderBoard = new TableView<>();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Jayson", "40"),
                    new Person("Ben", "36"));
    @Override
    public void start(Stage primaryStage) throws Exception{
        Board board = new Board(pane);
        BorderPane root = new BorderPane();

        //Set game pane in center and set background color
        pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);

        //Create menu bar
        final Menu menu1 = new Menu("File");
        MenuBar menuBar = new MenuBar();

        //open function to load CSV file with previous games
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(e -> {
            try {
                leaderBoard.getItems().clear();
                readCSV();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //save function to save leaderboard data to CSV file
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> {
            try {
                writeCSV();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //exit function to quit the application
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> {
            try {
                writeCSV();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Platform.exit();
        });

        menu1.getItems().addAll(openMenuItem, saveMenuItem, exitMenuItem);
        menuBar.getMenus().add(menu1);

        //Create side tab to right of GridPane
        Text title = new Text();
        title.setText("Player Scores");
        title.setFont(Font.font("Century Gothic", FontWeight.BOLD, 40));
        title.setFill(Color.WHITE);
        title.setUnderline(true);

        //Setup for black score
        Text bT = new Text();
        bT.setText("Black Player:");
        bT.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        bT.setFill(Color.BLACK);

        Circle bC = new Circle(20);
        bC.setFill(Color.BLACK);

        Text bCount = new Text();
        bCount.setText("Count = ");
        bCount.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        bCount.setFill(Color.BLACK);

        int blackScore = board.scoreB;
        Text bScore = new Text();
        bScore.setText(Integer.toString(blackScore));
        bScore.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        bScore.setFill(Color.BLACK);

        HBox bRow = new HBox(10, bC, bCount, bScore);
        VBox black = new VBox(30, title, bT, bRow);
        black.setAlignment(Pos.BASELINE_LEFT);

        //Setup for white score
        Text wT = new Text();
        wT.setText("White Player:");
        wT.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        wT.setFill(Color.WHITE);

        Circle wC = new Circle(20);
        wC.setFill(Color.WHITE);

        Text wCount = new Text();
        wCount.setText("Count = ");
        wCount.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        wCount.setFill(Color.WHITE);

        int whiteScore = board.scoreW;
        Text wScore = new Text();
        wScore.setText(Integer.toString(whiteScore));
        wScore.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        wScore.setFill(Color.WHITE);

        HBox wRow = new HBox(10, wC, wCount, wScore);
        VBox white = new VBox(30, wT, wRow);
        white.setAlignment(Pos.BASELINE_LEFT);

        //Leader board setup
        Text lB = new Text();
        lB.setText("Leaderboard:");
        lB.setFont(Font.font("Century Gothic", FontWeight.BOLD, 40));
        lB.setFill(Color.WHITE);
        lB.setUnderline(true);

        TableColumn nameCol = new TableColumn("Player Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("Name"));

        //Edit Function of Name Column
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }});

        TableColumn scoreCol = new TableColumn("Player Score");
        scoreCol.setMinWidth(150);
        scoreCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("Score"));

        //Edit Function of Score Column
        scoreCol.setCellFactory(TextFieldTableCell.forTableColumn());
        scoreCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setScore(t.getNewValue());
                    }});

        //ADD NEW PLAYER NAMES AND SCORES
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("Enter Name");
        addFirstName.setMaxWidth(nameCol.getPrefWidth());

        final TextField addScore = new TextField();
        addScore.setMaxWidth(scoreCol.getPrefWidth());
        addScore.setPromptText("Enter Score");

        // Button to add a new Person
        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            data.add(new Person(
                    addFirstName.getText(),
                    addScore.getText()
            ));
            addFirstName.clear();
            addScore.clear();
        });

        final Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                writeCSV();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        final Button loadButton = new Button("Load");
        loadButton.setOnAction(e -> {
            try {
                leaderBoard.getItems().clear();
                readCSV();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(addFirstName, addScore, addButton, saveButton, loadButton);
        hb.setSpacing(5);

        leaderBoard.setItems(data);
        leaderBoard.setEditable(true);
        leaderBoard.setMinHeight(200);
        leaderBoard.getColumns().addAll(nameCol, scoreCol);

        VBox lBoard = new VBox(15, lB, leaderBoard, hb);
        lBoard.setAlignment(Pos.CENTER_LEFT);

        VBox sideTab = new VBox(15, black, white, lBoard);
        sideTab.setMinWidth(300);
        sideTab.setAlignment(Pos.TOP_CENTER);
        sideTab.setPadding(new Insets(15, 20, 50, 20));
        sideTab.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        //Add all panes to scene
        root.setCenter(pane);
        root.setTop(menuBar);
        root.setRight(sideTab);
        Scene scene = new Scene(root, 1050, 750);

        //Placing disks and updating score
        int c = 0;
        while(c < 1) {
            pane.setOnMouseClicked(e -> {
                int colIndex = (int) Math.round(e.getX()) / 90;
                int rowIndex = (int) Math.round(e.getY()) / 90;
                board.placement(pane, colIndex, rowIndex);

                //count integration to increment every placement
                int bS = count(blackScore) - 2;
                int wS = count(whiteScore) - 2;

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board.pieces.get(i).get(j).isPlaced && board.pieces.get(i).get(j).isWhite == 1) { wS++; }
                        else if (board.pieces.get(i).get(j).isPlaced && board.pieces.get(i).get(j).isWhite == 0) { bS++; }
                    }
                }
                bScore.setText(Integer.toString(bS));
                wScore.setText(Integer.toString(wS));
            });
            c++;
        }
        //Setup stage
        primaryStage.setTitle("Othello");
        primaryStage.getIcons().add(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT3pUrDq671YtRKXsaUpVNoi6MIL6S9k1KbRWmxAo64MR44EDyu"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public int count(int n){
        return n++;
    }

    public void writeCSV() throws Exception {
        Writer writer = null;
        try {
            File file = new File("D:\\LeaderBoard.csv.");
            writer = new BufferedWriter(new FileWriter(file));
            for (Person person : data) {
                String text = person.getName() + "," + person.getScore() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            assert writer != null;
            writer.flush();
            writer.close();
        }
    }

    private void readCSV() {
        String CsvFile = "D:\\LeaderBoard.csv.";
        String FieldDelimiter = ",";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(CsvFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);
                Person person = new Person(fields[0], fields[1]);
                data.add(person);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public class Person {
        private final SimpleStringProperty name;
        private final SimpleStringProperty score;

        private Person(String nm, String sc) {
            this.name = new SimpleStringProperty(nm);
            this.score = new SimpleStringProperty(sc);
        }
        public String getName() {
            return name.get();
        }
        public void setName(String nm) {
            name.set(nm);
        }
        public String getScore() {
            return score.get();
        }
        public void setScore(String sc) {
            score.set(sc);
        }
    }
}