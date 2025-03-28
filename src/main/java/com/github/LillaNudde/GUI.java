package com.github.LillaNudde;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

public class GUI extends Application
{
    ListView<Measurement> measurementList = new ListView<>();
    ListView<Defect> defectList = new ListView<>();

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

    private int getNextID()
    {
        int nextID = 0;
        for (Measurement measurement : measurementList.getItems())
        {
            if (measurement.getID() > nextID)
            {
                nextID = measurement.getID();
            }
        }
        return (nextID + 1);
    }

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

        measurementList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        defectList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as .CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null)
            {
                try
                {
                    CSVSaver.saveAsCSV(file.getAbsolutePath(), measurementList.getItems());
                    Alert saved = new Alert(Alert.AlertType.INFORMATION);
                    saved.setTitle("Export Successful");
                    saved.setHeaderText("Measurement(s) exported successfully.");
                    saved.showAndWait();
                }
                catch (Exception ex)
                {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Export Failed");
                    error.setHeaderText("An unexpected error occurred.");
                    error.setContentText(ex.getMessage());
                    error.showAndWait();
                }
            }

        });


        newMeasurementButton.setOnAction(e ->
        {
            Stage popUp = new Stage();
            popUp.setTitle("New Measurement");

            int newID = getNextID();

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

            Scene popUpScene = new Scene(inputBox, 300, 180);
            popUp.setScene(popUpScene);
            popUp.show();
        });

        deleteMeasurementButton.setOnAction(e ->
        {
           List<Measurement> selectedMeasurements = measurementList.getSelectionModel().getSelectedItems();

           Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
           confirmation.setTitle("Confirm Deletion");
           confirmation.setHeaderText("Are you sure you want to delete the selected measurement(s)?");
           confirmation.setContentText("Deletion CANNOT be undone.");

           confirmation.showAndWait().ifPresent(response ->
           {
               if (response == ButtonType.OK)
               {
                   measurementList.getItems().removeAll(selectedMeasurements);
               }
               else
               {
                   confirmation.close();
               }
           });
        });

        measurementList.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) ->
        {
            defectList.getItems().clear();

            List<Measurement> selectedMeasurements = measurementList.getSelectionModel().getSelectedItems();

            if (selectedMeasurements.isEmpty())
            {
                return;
            }
            else
            {
                defectList.getItems().clear();
                for (Measurement measurement : selectedMeasurements)
                {
                    for (Defect defect : measurement.getDefects())
                    {
                        defectList.getItems().add(defect); // Adds defect as a string representation
                    }
                }
            }
        });

        newDefectButton.setOnAction(e ->
        {
           Stage popUp = new Stage();
           popUp.setTitle("New Defect");

           TextField typeField = new TextField();
           typeField.setPromptText("Defect Type");

           TextField X1Field = new TextField();
           X1Field.setPromptText("X1 (cm)");

           TextField X2Field = new TextField();
           X2Field.setPromptText("X2 (cm)");

           Button saveButton = new Button("Save");

           List<Measurement> selectedMeasurements = measurementList.getSelectionModel().getSelectedItems();
           Measurement selectedMeasurement = selectedMeasurements.get(0);
           if (selectedMeasurements.isEmpty())
           {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("No measurement selected");
               alert.setHeaderText("You must select a measurement first.");
               alert.setContentText(null);
               alert.showAndWait();
               return;
           }
           else if (selectedMeasurements.size() > 1)
           {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Multiple measurements selected");
               alert.setHeaderText("Please select only a single measurement");
               alert.setContentText(null);
               alert.showAndWait();
               return;
           }
           else
           {
               saveButton.setOnAction(saveEvent ->
               {
                   try
                   {
                       String type = typeField.getText();
                       double x1 = Double.parseDouble(X1Field.getText());
                       double x2 = Double.parseDouble(X2Field.getText());

                       Defect newDefect = new Defect(x1, x2, type);
                       selectedMeasurement.addDefect(x1, x2, type);

                       defectList.getItems().clear();
                       defectList.getItems().addAll(selectedMeasurement.getDefects());

                       popUp.close();
                       measurementList.refresh();
                   }
                   catch (NumberFormatException ex)
                   {
                       // Tähki joku errorrrerror varmaa
                   }
               });
           }
           VBox inputBox = new VBox(10, typeField, X1Field, X2Field, saveButton);
           inputBox.setAlignment(Pos.CENTER);
           Scene popUpScene = new Scene(inputBox, 300, 180);
           popUp.setScene(popUpScene);
           popUp.show();
        });

        deleteDefectButton.setOnAction(e ->
        {
            List<Defect> selectedDefects = defectList.getSelectionModel().getSelectedItems();
            Measurement selectedMeasurement = measurementList.getSelectionModel().getSelectedItem();

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Are you sure you want to delete the selected defect(s)?");
            confirmation.setContentText("Deletion CANNOT be undone.");

            confirmation.showAndWait().ifPresent(response ->
            {
                if (response == ButtonType.OK)
                {
                    selectedMeasurement.getDefects().removeAll(selectedDefects);

                    defectList.getItems().clear();
                    defectList.getItems().addAll(selectedMeasurement.getDefects());

                    measurementList.refresh();
                    defectList.refresh();
                }
                else
                {
                    confirmation.close();
                }
            });
        });


        root.setCenter(measurementBox);
        root.setRight(defectBox);
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
