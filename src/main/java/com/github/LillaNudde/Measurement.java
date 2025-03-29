package com.github.LillaNudde;

import java.util.ArrayList;
import java.util.List;
/**
 * Class for creating Measurement objects as well as adding/removing/modifying their and their defects' data.
 *
 * @author LillaNudde
 * @version 1.0 2025/03/31
 *
 */
public class Measurement
{
/**
 * Unique identifier for the measurement.
 */
private int id;

/**
 * The date of the measurement in a String format ("YYYY-MM-DD").
 */
private String date;

/**
 * Length of the measurement in centimeters.
 */
private double length;

/**
 * Width of the measurement in millimeters.
 */
private double width;

/**
 * Thickness of the measurement in millimeters.
 */
private double thickness;

/**
 * List of defects associated with this measurement.
 */
private ArrayList<Defect> defectList = new ArrayList<>();

/**
 * Constructs a new Measurement instance.
 *
 * @param id        Unique identifier for the measurement.
 * @param date      The date of the measurement.
 * @param length    Length of the measurement in centimeters.
 * @param width     Width of the measurement in millimeters.
 * @param thickness Thickness of the measurement in millimeters.
 */
public Measurement(int id, String date, double length, double width, double thickness)
    {
        this.id = id;
        this.date = date;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
    }

/**
 * Returns the unique identifier of the measurement.
 *
 * @return the ID of the measurement.
 */
public int getId()
    {
        return id;
    }

/**
 * Retrieves the date of the measurement.
 *
 * @return the date of the measurement.
 */
public String getDate()
    {
        return date;
    }

/**
 * Sets the date of this measurement.
 *
 * @param date The date to be set.
 */
public void setDate(String date)
    {
        this.date = date;
    }

/**
 * Gets the length (cm) of the measurement.
 *
 * @return the length in centimeters.
 */
public double getLength()
    {
        return length;
    }

/**
 * Sets the length (cm) of this measurement.
 *
 * @param length The length to be set in centimeters.
 */
public void setLength(double length)
    {
        this.length = length;
    }

/**
 * Gets the width (mm) of the measurement.
 *
 * @return the width in millimeters.
 */
public double getWidth()
    {
        return width;
    }

/**
 * Sets the width (mm) of this measurement.
 *
 * @param width The width to be set in millimeters.
 */
public void setWidth(double width)
    {
        this.width = width;
    }

/**
 * Retrieves the thickness (mm) of the measurement.
 *
 * @return the thickness in millimeters.
 */
public double getThickness() {
        return thickness;
    }

/**
 * Sets the thickness (mm) of this measurement.
 *
 * @param thickness The thickness to be set in millimeters.
 */
public void setThickness(double thickness)
    {
        this.thickness = thickness;
    }

/**
 * Retrieves the list of defects associated with the measurement.
 *
 * @return List of defects.
 */
public List<Defect> getDefects()
    {
        return defectList;
    }

/**
 * Adds a new defect to the list of defects.
 *
 * @param X1         The starting position of the defect.
 * @param X2         The ending position of the defect.
 * @param defectType The type of defect.
 */
public void addDefect(double X1, double X2, String defectType)
    {
        defectList.add(new Defect(X1, X2, defectType));
    }

/**
 * Adds multiple defects to the list of defects.
 *
 * @param defects The list of defects to be added.
 */
public void addDefects(ArrayList<Defect> defects)
    {
        defectList.addAll(defects);
    }

/**
 * Removes a defect from the list based on its index.
 *
 * @param index The index of the defect to remove.
 */
public void removeDefect(int index)
    {
        if (index < defectList.size() && index >= 0)
        {
            defectList.remove(index);
        }
    }

/**
 * Gets the total number of defects in this measurement.
 *
 * @return Total defect count.
 */
public int getDefectAmount()
    {
        return defectList.size();
    }

/**
 * Gets the total number of defects of a specific type.
 *
 * @param defectType The type of defect to count.
 * @return Total defect count of the specified type.
 */
public int getDefectAmount(String defectType)
    {
        int defectAmount = 0;
        for (Defect defect : defectList)
        {
            if (defect.getDefectType().equals(defectType))
            {
                defectAmount++;
            }
        }
        return defectAmount;
    }

/**
 * Gets the count of end defects for a specific defect type.
 *
 * @param defectType The type of defect to count as end defects.
 * @return The number of end defects matching the type.
 */
public int getEndDefectAmount(String defectType)
    {
        int defectAmount = 0;
        for (Defect defect : defectList)
        {
            if (defect.isEndDefect(defectType, length))
            {
                defectAmount++;
            }
        }
        return defectAmount;
    }

/**
 * Counts all end defects present in this measurement.
 *
 * @return Total count of end defects.
 */
public int getEndDefectAmount()
    {
        int defectAmount = 0;
        for (Defect defect : defectList)
        {
            if (defect.isEndDefect(length))
            {
                defectAmount++;
            }
        }
        return defectAmount;
    }

/**
 * Calculates the combined length of all end defects of a specific type.
 *
 * @param defectType The type of defect to calculate length.
 * @return Total length of the matching end defects.
 */
public double getEndDefectLength(String defectType)
    {
        double defectLength = 0;
        for (Defect defect : defectList)
        {
            if (defect.isEndDefect(defectType, length))
            {
                defectLength += defect.getDefectLength();
            }
        }
        return defectLength;
    }

/**
 * Calculates the combined length of all end defects.
 *
 * @return Total length of all end defects.
 */
public double getEndDefectLength()
    {
        double defectLength = 0;
        for (Defect defect : defectList)
        {
            if (defect.isEndDefect(length))
            {
                defectLength += defect.getDefectLength();
            }
        }
        return defectLength;
    }

    /**
     * Turns the data of a measurement into 1 readable String for the GUI.
     *
     * @return All data of a measurement (excluding defects) as a String.
     */
    @Override
    public String toString()
    {
        return (id + " | " + date +
                " | (" + length + "cm x " + width + "mm x " + thickness + "mm)" +
                " | Defects: " + getDefectAmount());
    }
}