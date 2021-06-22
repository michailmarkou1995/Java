package bmi;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;

import java.awt.*;
import java.security.MessageDigest;

class Patient  { //

    private String fullname ,OptionsHeight, OptionsWeight;
    private int age, category;
    private double height, weight;
    private Image image1;
    private ImageView pic1;

    public Patient(int age , double height , double weight , String fullname,String OptionsHeight, String OptionsWeight) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.fullname= fullname;
        this.OptionsHeight= OptionsHeight;
        this.OptionsWeight= OptionsWeight;
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

    public int getCategory()
    {
        return this.category;
    }

    public double computeBMI(Patient person) {
        if (person.OptionsHeight.equals("Meters") && person.OptionsWeight.equals("Kg"))
            return weight / (height*height);
        else if(person.OptionsHeight.equals("cm") && person.OptionsWeight.equals("Kg"))
            return (100*100*weight)/(height*height);
        else if(person.OptionsHeight.equals("Meters") && person.OptionsWeight.equals("Pounds/lbs"))
            return weight / (height*height); //wrong formula find the correct
        else return (100*100*weight)/(height*height); //wrong formula find the correct
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
            person.category = 1;
        }
        else if(bmi>25 && bmi<29.9) {
            output = message.concat("Patient: " +person.getFullname()+ " is overweight.");
            person.category = 3;
        }
        else if(bmi>30) {
            output = message.concat("Patient: " +person.getFullname()+ " is obese.");
            person.category = 4;
        }
        else {
            output = message.concat("Patient: " +person.getFullname()+ " has normal weight.");
            person.category = 2;
        }

        return output;
    }
    public ImageView getPicture(Patient person, Boolean checked)
    {
        if (person.category == 1 && checked.equals(false)) {
            image1 = new Image("File:C:\\Users\\backt\\Downloads\\underweight.png"); // put a file path here
            pic1 = new ImageView();
            pic1.setVisible(true);
            pic1.setImage(image1);
            return pic1;
        }
        else if (person.category == 2 && checked.equals(false))
        {
            image1 = new Image("File:C:\\Users\\backt\\Downloads\\normalweight.png"); // put a file path here
            pic1 = new ImageView();
            pic1.setVisible(true);
            pic1.setImage(image1);
            return pic1;
        }
        else if (person.category == 3 && checked.equals(false))
        {
            image1 = new Image("File:C:\\Users\\backt\\Downloads\\overweight.png"); // put a file path here
            pic1 = new ImageView();
            pic1.setVisible(true);
            pic1.setImage(image1);
            return pic1;
        }
        else if (person.category == 4 && checked.equals(false))
        {
            image1 = new Image("File:C:\\Users\\backt\\Downloads\\obese.png"); // put a file path here
            //image1 = new Image("bmi/obese.png");
            pic1 = new ImageView();
            pic1.setVisible(true);
            pic1.setImage(image1);
            return pic1;
        }
        else if (person.category == 4 && checked == true)
        {
            image1 = new Image("File:C:\\Users\\backt\\Downloads\\supermanise.png"); // put a file path here
            pic1 = new ImageView();
            pic1.setVisible(true);
            pic1.setImage(image1);
            return pic1;
        }
        else {
            image1 = new Image("File:C:\\Users\\backt\\Downloads\\bmi-simple.png"); // put a file path here
            pic1 = new ImageView();
            pic1.setVisible(true);
            pic1.setImage(image1);
            return pic1;
        }
    }
}

public class Main extends Application{

    //class m fields saves us from function parameters
    Label headerLabel, nameLabel, ageLabel, weightLabel, heightLabel, output;
    TextField nameField, ageField, weightField, heightField;
    ComboBox comboBoxWeight, comboBoxHeight;
    Button submitButton, returnButton;
    VBox Vbox;
    Scene scene, scene1;
    GridPane gridPane;
    ColumnConstraints columnOneConstraints, columnTwoConstrains;
    Patient patient;
    CheckBox checkbox1;
    boolean isSelectedcheck = false;
    boolean check=false;
    //Stage stage, primaryStage;
    SVGPath svgPath = new SVGPath();
    String path = "M320.2 243.8l-49.7 99.4c-6 12.1-23.4 11.7-28.9-.6l-56.9-126.3-30 71.7H60.6l182.5 186.5c7.1 7.3 18.6 7.3 25.7 0L451.4 288H342.3l-22.1-44.2zM473.7 73.9l-2.4-2.5c-51.5-52.6-135.8-52.6-187.4 0L256 100l-27.9-28.5c-51.5-52.7-135.9-52.7-187.4 0l-2.4 2.4C-10.4 123.7-12.5 203 31 256h102.4l35.9-86.2c5.4-12.9 23.6-13.2 29.4-.4l58.2 129.3 49-97.9c5.9-11.8 22.7-11.8 28.6 0l27.6 55.2H481c43.5-53 41.4-132.3-7.3-182.1z";
    String errorMessageName="";
    Alert alert;
    final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = createHealthAppForm();
        scene = new Scene(gridPane,800,500);
        addUIControls (primaryStage); // raw code

        gridPane.getStylesheets().add("bmi/stylesheet.css"); // put a file path here
        primaryStage.setOpacity(0.8);
        primaryStage.setTitle("BMI Event");
        primaryStage.setResizable(false);
        scene.getStylesheets().add("bmi/stylesheet.css"); // put a file path here
        primaryStage.getIcons().add(new Image("File:C:\\Users\\backt\\Downloads\\icon-heart.png")); // put a file path here
        svgPath.setContent(path);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private GridPane createHealthAppForm() {
        // Instantiate a new Grid Pane
        gridPane = new GridPane();

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
        columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(Stage primaryStage) {
        //Header
        headerLabel = new Label("BMI calculator");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));


        // Add Name Label
        nameLabel = new Label("Full Name : ");
        gridPane.add(nameLabel, 0,1);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Add Name Text Field
        nameField = new TextField();
        gridPane.requestFocus();
        nameField.setPromptText("Enter your First Name");
        nameField.setOnMousePressed(e -> nameField.clear());
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);


        // Add Age Label
        ageLabel = new Label("Age : ");
        ageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gridPane.add(ageLabel, 0, 2);

        // Add Age Text Field
        ageField = new TextField("Enter your Age");
        ageField.setPromptText("Enter your Age");
        ageField.setOnMousePressed(e -> ageField.clear());
        ageField.setPrefHeight(40);
        gridPane.add(ageField, 1, 2);


        // Add Weight Label
        weightLabel = new Label("Weight : ");
        weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gridPane.add(weightLabel, 0, 3);

        // Add Weight Text Field
        weightField = new TextField("Enter your Weight");
        weightField.setPromptText("Enter your Weight");
        weightField.setOnMousePressed(e -> weightField.clear());
        weightField.setPrefHeight(40);
        gridPane.add(weightField, 1, 3);
        ObservableList<String> options1 =
                FXCollections.observableArrayList(
                        "Kg",
                        "Pounds/lbs"
                );
        comboBoxWeight = new ComboBox(options1);
        comboBoxWeight.getSelectionModel().selectFirst();
        gridPane.add(comboBoxWeight, 2, 3);
        GridPane.setHalignment(comboBoxWeight, HPos.LEFT);


        // Add Height Label
        heightLabel = new Label("Height : ");
        heightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gridPane.add(heightLabel, 0, 4);

        // Add Height Text Field
        heightField = new TextField("Enter your Height");
        heightField.setPromptText("Enter your Height");
        heightField.setOnMousePressed(e -> heightField.clear());
        heightField.setPrefHeight(40);
        gridPane.add(heightField, 1, 4);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Meters",
                        "cm"
                );
        comboBoxHeight = new ComboBox(options);
        comboBoxHeight.getSelectionModel().select(1);
        gridPane.add(comboBoxHeight, 2, 4);
        GridPane.setHalignment(comboBoxHeight, HPos.LEFT);

        // Add Submit Button
        submitButton = new Button("Get Patient Status");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(200);
        gridPane.add(submitButton, 0, 5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert");

        // BMI Button event Handler
        submitButton.setOnAction(result -> { //we don't reference it anywhere so its safe to call it like this
            //check =
            if (isNameOk() && isIntAge(ageField) && isDoubleWeight(weightField) && isDoubleheight(heightField))
            resultPrompt(primaryStage);
        });
    }
    private void resultPrompt(Stage primaryStage)
    {
        //Creating a panel for the output message
        Vbox = new VBox();
        Vbox.setSpacing(10);
        Vbox.getStylesheets().add("bmi/stylesheet.css"); // put a file path here
        checkbox1 = new CheckBox("Bitch please i'm Roidz User");
        isSelectedcheck = checkbox1.isSelected();

        //Creating a button
        Button button = new Button("Show Dialog");
        //Showing the dialog on clicking the button

        scene1 = new Scene(Vbox, 800,500);
        primaryStage.setTitle("Results");
        primaryStage.setOpacity(1);
        primaryStage.setScene(scene1);
        primaryStage.show();

        //Getting the input from the application form
        int age = Integer.parseInt(ageField.getText());
        double height =Double.parseDouble(heightField.getText());
        double weight =Double.parseDouble(weightField.getText());
        String name = nameField.getText();
        String dropmenuHeight = comboBoxHeight.getValue().toString();
        String dropmenuWeight = comboBoxWeight.getValue().toString();


        //Creating patient object
        patient = new Patient(age,height,weight,name,dropmenuHeight,dropmenuWeight);
        ImageView pics = patient.getPicture(patient, isSelectedcheck);

        //Creating a label with the results in StackPane
        output = new Label(Patient.getPatientStatus(patient));
        if (patient.getCategory() < 4) {checkbox1.setDisable(true); checkbox1.setVisible(false);} else { checkbox1.setDisable(false); checkbox1.setVisible(true); }
        Vbox.getChildren().addAll(checkbox1, patient.getPicture(patient, isSelectedcheck));

        //bad design here but that's how i ended up doing it anyway!
        checkbox1.setOnAction(recalc ->{
            Vbox.getChildren().clear();
            patient = new Patient(age,height,weight,name,dropmenuHeight,dropmenuWeight);
            output = new Label(Patient.getPatientStatus(patient));
            isSelectedcheck = checkbox1.isSelected();
            Vbox.getChildren().addAll(checkbox1, patient.getPicture(patient, isSelectedcheck));
            checkbox1.setDisable(true);
            output.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            Vbox.getChildren().add(output);
            VBox.setMargin(output, new Insets(20, 20, 20, 20));
            retBut(primaryStage);
        });

        output.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Vbox.getChildren().add(output);
        VBox.setMargin(output, new Insets(20, 20, 20, 20));

        retBut(primaryStage);
    }

    private void retBut(Stage primaryStage)
    {
        //Adding return Button
        returnButton = new Button("Return");
        returnButton.setPrefHeight(40);
        returnButton.setDefaultButton(true);
        returnButton.setPrefWidth(200);
        Vbox.setAlignment(Pos.CENTER);
        Vbox.getChildren().add(returnButton);
        //return Event handler
        returnButton.setOnAction(ret->{
            primaryStage.setScene(scene);
            primaryStage.setTitle("BMI event");
            primaryStage.setOpacity(0.8);
            nameField.setText("");
            ageField.setText("Enter your Age");
            heightField.setText("Enter your height");
            weightField.setText("Enter your weight");
        });
    }
    private boolean isNameOk(){

        errorMessageName="";
        if(nameField.getText().length()==0) {
            errorMessageName += "\nName is required";
            //return false;
        }
        if (errorMessageName.length()==0)
        {
            return true;
        }
        else {
            runnable.run();
            alert.setContentText(errorMessageName); alert.showAndWait();
            errorMessageName="";
            return false;
        }
    }
    private boolean isIntAge(TextField ageField)
    {
        try {
            int temp=Integer.parseInt(ageField.getText());
            if (!(temp < 0))
            return true;
            else {
                runnable.run();
                alert.setContentText("Data Entry Error Age: No Negative numbers");
                alert.showAndWait();
                return false;}
        }
        catch (NumberFormatException e)
        {
            runnable.run();
            alert.setContentText("Data Entry Error: Age as Integer only");
            alert.showAndWait();
            return false;
        }
    }
    private boolean isDoubleheight(TextField heightField)
    {
        try {
            double temp=Double.parseDouble(heightField.getText());
            if (!(temp < 0))
            return true;
            else {
                runnable.run();
                alert.setContentText("Data Entry Error Height: No Negative numbers");
                alert.showAndWait();
                return false;}
        }
        catch (NumberFormatException e)
        {
            runnable.run();
            alert.setContentText("Data Entry Error Height: height as double");
            alert.showAndWait();
            return false;
        }
    }
    private boolean isDoubleWeight(TextField weightField)
    {
        try {
            double temp=Double.parseDouble(weightField.getText());
            if (!(temp < 0))
            return true;
            else {
                runnable.run();
                alert.setContentText("Data Entry Error Weight: No Negative numbers");
                alert.showAndWait();
                return false;}
        }
        catch (NumberFormatException e)
        {
            runnable.run();
            alert.setContentText("Data Entry Error: weight as double");
            alert.showAndWait();
            return false;
        }
    }
}