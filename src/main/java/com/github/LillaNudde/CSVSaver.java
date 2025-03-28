package com.github.LillaNudde;

import java.io.*;
import java.util.List;
import java.io.PrintWriter;

public class CSVSaver
{
    public  static void saveAsCSV(String filePath, List<Measurement> measurements)
    {
        try (PrintWriter writer = new PrintWriter(filePath))
        {
            writer.write("ID;Date;Length (cm);Width (mm);Thickness (mm)");

            int maxDefects = measurements.stream().mapToInt(m -> m.getDefects().size()).max().orElse(0);

            for (int i = 1; i <= maxDefects; i++)
            {
                writer.write(";Defect Type;X1;X2");
            }

            writer.write("\n");

            for (Measurement measurement : measurements)
            {
                StringBuilder row = new StringBuilder();

                row.append(measurement.getID()).append(";")
                        .append(measurement.getDate()).append(";")
                        .append(String.format("%.2f", measurement.getLength())).append(";")
                        .append(String.format("%.2f", measurement.getWidth())).append(";")
                        .append(String.format("%.2f", measurement.getThickness()));

                List<Defect> defects = measurement.getDefects();

                for (Defect defect : defects)
                {
                    row.append(";").append(defect.getDefectType())
                            .append(";").append(String.format("%.2f", defect.getX1()))
                            .append(";").append(String.format("%.2f", defect.getX2()));
                }

                int empty = (maxDefects - defects.size()) * 3;

                for (int i = 0; i < empty; i++)
                {
                    row.append(";");
                }

                writer.write(row.toString());
                writer.write("\n");
            }
        }
        catch (IOException e)
        {

        }
    }
}
