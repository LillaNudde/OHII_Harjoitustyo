package com.github.LillaNudde;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader
{
    public static List<Measurement> readCSV(String filepath, String separator)
    {
        final String ID_COL = "Name";
        final String DATE_COL = "Date";
        final String Length = "Length (cm)";
        final String Width = "Width (mm)";
        final String Thickness = "Thickness (mm)";

        final String X1_COL = "X1";
        final String X2_COL = "X2";
        final String DefectType_COL = "DefectType";

        List<Measurement> measurementList = new ArrayList<Measurement>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
        {
            String line = "";
            boolean headerLine = true;

            int nameColIdx = -1;
            int dateColIdx = -1;
            int lengthColIdx = -1;
            int widthColIdx = -1;
            int thicknessColIdx = -1;
            int x1ColIdx = -1;
            int x2ColIdx = -1;
            int defectTypeColIdx = -1;

            while ((line = br.readLine()) != null)
            {
                String[] values = line.split(separator);

                if (headerLine)
                {
                    int colIdx = 0;
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
                        else if (column.equals(Length))
                        {
                            lengthColIdx = colIdx;
                        }
                        else if (column.equals(Width))
                        {
                            widthColIdx = colIdx;
                        }
                        else if (column.equals(Thickness))
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
                        else if (defectTypeColIdx < 0 && column.equals(DefectType_COL))
                        {
                            defectTypeColIdx = colIdx;
                        }

                        colIdx++;
                    }
                }
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
                            // No defects if string is empty
                            if (column == null || column.length() == 0)
                            {
                                break;
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
                                else
                                {
                                    defectType = column;

                                    x1found = false;
                                    x2found = false;

                                    Defect defect = new Defect(x1, x2, defectType);

                                    defects.add(defect);
                                }
                            }
                        }

                        colIdx++;
                    }

                    Measurement currentMeasurement = new Measurement(currentId, currentDate, currentLength, currentWidth, currentThickness);
                    currentMeasurement.addDefects(defects);

                    measurementList.add(currentMeasurement);
                }

                headerLine = false;
            }
        }
        catch (IOException e)
        {

        }

        return measurementList;
    }
}
