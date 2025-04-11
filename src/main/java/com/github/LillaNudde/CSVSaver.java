package com.github.LillaNudde;

import javafx.scene.control.Alert;
import java.io.*;
import java.util.List;
import java.io.PrintWriter;

/**
 * Class for saving list of {@code Measurement} objects into a .CSV file.
 * .CSV file includes measurement data (id, date, length (cm), width (mm), thickness (mm))
 * along with their defects and their data (defect type, x1, x2).
 *
 * @author LillaNudde
 * @version 1.1
 */
public class CSVSaver
{

    /**
     * Saves a list of {@code Measurement} objects to a CSV file.
     * The data is written in a structured format where each measurement's details
     * are followed by its associated defects, if any.
     *
     * @param filePath The file path where the CSV should be saved.
     * @param measurements The list of {@code Measurement} objects to be saved.
     */
    public static void saveAsCSV(String filePath, List<Measurement> measurements)
    {
        try (PrintWriter writer = new PrintWriter(filePath))
        {
            // Header for .CSV file
            writer.write("ID;Date;Length (cm);Width (mm);Thickness (mm)");

            // A counter that assigns itself the value of highest amount of defects for any single given measurement
            // Used to create multiple columns of Defect Type, X1, X2 without brute-forcing a maximum amount
            // Dynamic is the word I think?
            int maxDefects = measurements.stream().mapToInt(m -> m.getDefects().size()).max().orElse(0);

            // Header columns for defect data
            for (int i = 1; i <= maxDefects; i++)
            {
                writer.write(";Defect Type;X1;X2");
            }

            writer.write("\n");

            // Write each measurement into .CSV file
            for (Measurement measurement : measurements)
            {
                StringBuilder row = new StringBuilder();

                // Write measurement data into columns separated by ";"
                row.append(measurement.getId()).append(";")
                        .append(measurement.getDate()).append(";")
                        .append(String.format("%.2f", measurement.getLength())).append(";")
                        .append(String.format("%.2f", measurement.getWidth())).append(";")
                        .append(String.format("%.2f", measurement.getThickness()));

                // Get defects of a measurement
                List<Defect> defects = measurement.getDefects();

                // Write defect data into columns separated by ";"
                for (Defect defect : defects)
                {
                    row.append(";").append(defect.getDefectType())
                            .append(";").append(String.format("%.2f", defect.getX1()))
                            .append(";").append(String.format("%.2f", defect.getX2()));
                }

                // If a measurement has fewer defects than the one that has the most, some empty columns are created instead
                int empty = (maxDefects - defects.size()) * 3;
                for (int i = 0; i < empty; i++)
                {
                    row.append(";");
                }

                // Write the constructed row to .CSV file
                writer.write(row.toString());
                writer.write("\n");
            }
        }
        catch (IOException e)
        {
            // If error: Show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An unexpected error has occurred.");
            alert.showAndWait();
        }
    }
}
