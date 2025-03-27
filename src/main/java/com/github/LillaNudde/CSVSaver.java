/*package com.github.LillaNudde;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVSaver
{
    public  static void saveAsCSV(File file, List<Measurement> measurements)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Name;Setting;Species;Thickness (mm);Width (mm);Length (cm);" +
                    "Tag;Sensor;Cracked (%);Correct;Cracks;End cracked length (cm);" +
                    "Middle cracked length (cm);Total cracked length (cm);" +
                    "X1;X2;X1;X2;X1;X2;X1;X2;X1;X2;X1;X2;X1;X2;X1;X2;X1;X2;X1;X2");
            writer.newLine();


            for (Measurement measurement : measurements) {
                writer.write(formattedMeasurement(measurement));
                writer.newLine();
            }
        }
        catch (IOException e)
        {
        }

        private static String formattedMeasurement(Measurement measurement)
    }
    // TÄÄKI KOKO LUOKKA PITÄÄ VIELÄ SÄÄTÄÄ KONDIKSEEN
}
*/