package com.github.LillaNudde;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

public class GUI extends Application
{
    ListView<Defect> defectList = new ListView<>();
    ListView<Measurement> measurementList = new ListView<>();

    Button importButton = new Button("Import...");
    Button exportButton = new Button("Export...");
    VBox leftButtonBox = new VBox(importButton, exportButton);

    FileChooser csvChooser = new FileChooser();

    Button newMeasurementButton = new Button("New Measurement...");
    Button deleteMeasurementButton = new Button("Delete Measurement...");
    VBox measurementButtons = new VBox(newMeasurementButton, deleteMeasurementButton);

    Button newDefectButton = new Button("New Defect...");
    Button deleteDefectButton = new Button("Delete Defect...");
    VBox defectButtons = new VBox(newDefectButton, deleteDefectButton);

    VBox measurementBox = new VBox(measurementButtons, measurementList);
    VBox defectBox = new VBox(defectButtons, defectList);

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("GUI");
        BorderPane root = new BorderPane();
        root.setLeft(leftButtonBox);
        leftButtonBox.setAlignment(Pos.CENTER);

        // Button action to open the file chooser
        importButton.setOnAction(e ->
        {
            File file = csvChooser.showOpenDialog(primaryStage);
            if (file != null)
            {
                System.out.println("File selected: " + file.getAbsolutePath());
                List<Measurement> measurements = CSVReader.readCSV(file.getAbsolutePath(), ";");
                measurementList.getItems().clear();
                measurementList.getItems().addAll(measurements);
            } else
            {
                System.out.println("No file selected.");
            }
        });

        exportButton.setOnAction(e ->
        {
            // RAKENNA TÄÄ ROSKA
        });

        newMeasurementButton.setOnAction(e ->
        {
            Stage popUp = new Stage();
            popUp.setTitle("New Measurement");

            int newID = measurementList.getItems().size() + 1;

            TextField dateField = new TextField();
            dateField.setPromptText("Date (YYYY-MM-DD):");

            TextField lengthField = new TextField();
            lengthField.setPromptText("Length (cm):");

            TextField widthField = new TextField();
            widthField.setPromptText("Width (mm):");

            TextField thicknessField = new TextField();
            thicknessField.setPromptText("Thickness (mm):");

            Button saveButton = new Button("Save");
            saveButton.setOnAction(saveEvent ->
            {
                try
                {
                    String date = dateField.getText();
                    double length = Double.parseDouble(lengthField.getText());
                    double width = Double.parseDouble(widthField.getText());
                    double thickness = Double.parseDouble(thicknessField.getText());

                    Measurement newMeasurement = new Measurement(newID, date, length, width, thickness);

                    measurementList.getItems().add(newMeasurement);

                    popUp.close();
                }
                catch (NumberFormatException ex)
                {
                   // Tyyliin joku erroriviestitähän
                }
            });

            VBox inputBox = new VBox(10, dateField, lengthField, widthField, thicknessField, saveButton);
            inputBox.setAlignment(Pos.CENTER);

            Scene popUpScene = new Scene(inputBox, 250, 180);
            popUp.setScene(popUpScene);
            popUp.show();
        });

        root.setCenter(measurementBox);
        root.setRight(defectBox);
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
