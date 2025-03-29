package com.github.LillaNudde;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Class for "reading" a specific type of .CSV file and turning the contents into a list to be handled by the GUI
 * @author LillaNudde
 * @version 1.0
 */
public class CSVReader
{
    /**
     * Reads and parses a .CSV file into a {@code Measurement} list
     *
     * @param filepath  the file path to the .CSV file
     * @param separator the separator used in a .CSV file
     * @return a {@code List} of {@code Measurement} objects parsed from the CSV
     * @throws IllegalArgumentException if the .CSV file is "unreadable" by the program
     */
    public static List<Measurement> readCSV(String filepath, String separator)
    {
        // Constants for header column (measurement)
        final String ID_COL = "Name";
        final String DATE_COL = "Date";
        final String LENGTH_COL = "Length (cm)";
        final String WIDTH_COL = "Width (mm)";
        final String THICKNESS_COL = "Thickness (mm)";
        // Constants for header column (defects)
        final String X1_COL = "X1";
        final String X2_COL = "X2";
        final String DEFECTTYPE_COL = "Defect Type";

        // List to store parsed measurements
        List<Measurement> measurementList = new ArrayList<Measurement>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
        {
            String line = "";
            boolean headerLine = true;

            // Column indexes for the .CSV file
            int nameColIdx = -1;
            int dateColIdx = -1;
            int lengthColIdx = -1;
            int widthColIdx = -1;
            int thicknessColIdx = -1;
            int x1ColIdx = -1;
            int x2ColIdx = -1;
            int defectTypeColIdx = -1;

            // Read each line in the .CSV file
            while ((line = br.readLine()) != null)
            {
                String[] values = line.split(separator);

                // Determine column indexes from first line (header)
                if (headerLine)
                {
                    int colIdx = 0;
                    // Assign indexes for columns by constant
                    for (String column : values)
                    {
                        if (column.equals(ID_COL))
                        {
                            nameColIdx = colIdx;
                        }
                        else if (column.equals(DATE_COL))
                        {
                            dateColIdx = colIdx;
                        }
                        else if (column.equals(LENGTH_COL))
                        {
                            lengthColIdx = colIdx;
                        }
                        else if (column.equals(WIDTH_COL))
                        {
                            widthColIdx = colIdx;
                        }
                        else if (column.equals(THICKNESS_COL))
                        {
                            thicknessColIdx = colIdx;
                        }
                        else if (x1ColIdx < 0 && column.equals(X1_COL))
                        {
                            x1ColIdx = colIdx;
                        }
                        else if (x2ColIdx < 0 && column.equals(X2_COL))
                        {
                            x2ColIdx = colIdx;
                        }
                        else if (defectTypeColIdx < 0 && column.equals(DEFECTTYPE_COL))
                        {
                            defectTypeColIdx = colIdx;
                        }

                        colIdx++;
                    }
                }
                // Parse values for measurement entries
                else
                {
                    int currentId = -1;
                    String currentDate = "";
                    double currentLength = -1;
                    double currentWidth = -1;
                    double currentThickness = -1;

                    ArrayList<Defect> defects = new ArrayList<Defect>();

                    int colIdx = 0;
                    boolean x1found = false;
                    boolean x2found = false;

                    double x1 = 0;
                    double x2 = 0;
                    String defectType = "";

                    // Assign values for fields based on index
                    for (String column : values)
                    {
                        if (colIdx == nameColIdx)
                        {
                            currentId = Integer.parseInt(column);
                        }
                        else if (colIdx == dateColIdx)
                        {
                            currentDate = column;
                        }
                        else if (colIdx == lengthColIdx)
                        {
                            currentLength = Double.parseDouble(column);
                        }
                        else if (colIdx == widthColIdx)
                        {
                            currentWidth = Double.parseDouble(column);
                        }
                        else if (colIdx == thicknessColIdx)
                        {
                            currentThickness = Double.parseDouble(column);
                        }
                        else if (colIdx >= x1ColIdx)
                        {
                            if (column == null || column.isEmpty())
                            {
                                break; // No more defect data for this measurement
                            }
                            else
                            {
                                if (!x1found)
                                {
                                    x1 = Double.parseDouble(column);
                                    x1found = true;
                                }
                                else if (!x2found)
                                {
                                    x2 = Double.parseDouble(column);
                                    x2found = true;
                                }

                                // If header doesn't contain defect type, hasDefectType = False
                                boolean hasDefectType = (defectTypeColIdx != -1);

                                if (x1found && x2found)
                                {
                                    // If defect type in header: set defect type from column
                                    if (hasDefectType && colIdx + 1 < values.length)
                                    {
                                        defectType = values[++colIdx];
                                    }
                                    // If no defect type in header: defect type is not available
                                    else
                                    {
                                        defectType = "N/A";
                                    }

                                    // Create and add defect
                                    defects.add(new Defect(x1, x2, defectType));

                                    // Reset for next defect
                                    x1found = false;
                                    x2found = false;
                                }
                            }
                        }


                        colIdx++;
                    }
                    // Create measurement object with data parsed from .CSV file
                    Measurement currentMeasurement = new Measurement(currentId, currentDate, currentLength, currentWidth, currentThickness);
                    currentMeasurement.addDefects(defects);

                    // Add measurement to list
                    measurementList.add(currentMeasurement);
                }

                // No more header after first line
                headerLine = false;
            }
        }
        catch (IOException e)
        {
            // If error: Give alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An unexpected error has occurred.");
            alert.showAndWait();
        }

        // Return list with measurements parsed from .CSV file
        return measurementList;
    }
}
