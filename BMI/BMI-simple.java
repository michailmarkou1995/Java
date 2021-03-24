package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;

    @FunctionalInterface
    interface Ioptions{
        public String getControlOptions(String btnName);
    }
    class Patient implements Ioptions { 

    private String fullname;
    private String OptionsHeight;
    private String OptionsWeight;
    private int age;
    private double height;
    private double weight;

    public Patient(int age , double height , double weight , String fullname,String OptionsHeight, String OptionsWeight) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.fullname= fullname;
        this.OptionsHeight= OptionsHeight;
        this.OptionsWeight= OptionsWeight;
    }

    public String getControlOptions(String btnName) {
        String text = "";
        //switch (btnName){
           // case
       // }
        return btnName;
    }

    //declaring a getter will avoid the non-static field being called from a static function
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double computeBMI(Patient person) {
        if (person.OptionsHeight.equals("Meters") && person.OptionsWeight.equals("Kg"))
            return weight / (height*height);
        else if(person.OptionsHeight.equals("cm") && person.OptionsWeight.equals("Kg"))
            return (100*100*weight)/(height*height);
        else if(person.OptionsHeight.equals("Meters") && person.OptionsWeight.equals("Pounds/lbs"))
            return weight / (height*height); //wrong formula
         else return (100*100*weight)/(height*height); //wrong formula
    }

    public double setMaximumCardioIntensity(Patient person) {
        return 220-this.age;
    }

    public static String getPatientStatus(Patient person) {

        String message = " Patient: "+person.getFullname()+" has BMI of: "+person.computeBMI(person)+"\n Patient: " + person.getFullname() +" has Maximum Cardio Intensity of: "+person.setMaximumCardioIntensity(person)+"\n ";

        double bmi = person.computeBMI(person);
        String output;
        if(bmi<18) {
            output = message.concat("Patient: " +person.getFullname()+ " is underweight.");
        }
        else if(bmi>25 && bmi<29.9) {
            output = message.concat("Patient: " +person.getFullname()+ " is overweight.");
        }
        else if(bmi>30) {
            output = message.concat("Patient: " +person.getFullname()+ " is obese.");
        }
        else {
            output = message.concat("Patient: " +person.getFullname()+ " has normal weight.");
        }

        return output;
    }
}

public class Main extends Application{
    String testaa;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = createHealthAppForm();

        addUIControls(gridPane);

        Scene scene = new Scene(gridPane,800,500);
        gridPane.setStyle("-fx-background-color: rgb(200, 200, 200)");
        primaryStage.setTitle("BMI Event");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public GridPane createHealthAppForm() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    public void addUIControls(GridPane gridPane) {
        //Header
        Label headerLabel = new Label("BMI calculator");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));


        // Add Name Label
        Label nameLabel = new Label("Full Name : ");
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setText("Enter your First Name");
        nameField.setOnMousePressed(e -> nameField.clear());
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);


        // Add Age Label
        Label ageLabel = new Label("Age : ");
        gridPane.add(ageLabel, 0, 2);

        // Add Age Text Field
        TextField ageField = new TextField();
        ageField.setText("Enter your First Name");
        ageField.setOnMousePressed(e -> ageField.clear());
        ageField.setPrefHeight(40);
        gridPane.add(ageField, 1, 2);


        // Add Weight Label
        Label weightLabel = new Label("Weight : ");
        gridPane.add(weightLabel, 0, 3);

        // Add Weight Text Field
        TextField weightField = new TextField();
        weightField.setText("Enter your Weight");
        weightField.setOnMousePressed(e -> weightField.clear());
        weightField.setPrefHeight(40);
        gridPane.add(weightField, 1, 3);
        ObservableList<String> options1 =
                FXCollections.observableArrayList(
                        "Kg",
                        "Pounds/lbs"
                );
        final ComboBox comboBoxWeight = new ComboBox(options1);
        gridPane.add(comboBoxWeight, 2, 3);
        GridPane.setHalignment(comboBoxWeight, HPos.LEFT);


        // Add Height Label
        Label heightLabel = new Label("Height : ");
        gridPane.add(heightLabel, 0, 4);

        // Add Height Text Field
        TextField heightField = new TextField();
        heightField.setText("Enter your Height");
        heightField.setOnMousePressed(e -> heightField.clear());
        heightField.setPrefHeight(40);
        gridPane.add(heightField, 1, 4);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Meters",
                        "cm"
                );
        final ComboBox comboBoxHeight = new ComboBox(options);
        gridPane.add(comboBoxHeight, 2, 4);
        GridPane.setHalignment(comboBoxHeight, HPos.LEFT);

        // Add Submit Button
        Button submitButton = new Button("Get Patient Status");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(200);
        gridPane.add(submitButton, 0, 5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));


        // Button event Handler
        submitButton.setOnAction(event -> {

                //Creating a panel for the output message
                StackPane root = new StackPane();
                root.setStyle("-fx-background-color: rgb(200, 200, 200)");
                Scene scene = new Scene(root,1000,400);
                Stage stage = new Stage();
                stage.setTitle("Results");
                stage.setScene(scene);
                stage.show();

                //Getting the input from the application form
                int age = Integer.parseInt(ageField.getText());
                double height =Double.parseDouble(heightField.getText());
                double weight =Double.parseDouble(weightField.getText());
                String name = nameField.getText();

                //Creating patient object
                Patient patient = new Patient(age,height,weight,name,comboBoxHeight.getValue().toString(),comboBoxWeight.getValue().toString());


                //Creating a label with the results in StackPane
                Label output = new Label(Patient.getPatientStatus(patient));
                output.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                root.getChildren().add(output);
        });
    }
}