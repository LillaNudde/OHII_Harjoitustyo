package com.github.LillaNudde;
/**
 * Class for creating Defect objects as well as adding/removing/modifying their data.
 *
 * @author LillaNudde
 * @version 1.1
 *
 */
public class Defect
{
    private double x1;
    private double x2;
    private String defectType;

/**
 * Constructs a new Defect with the provided coordinates and defect type.
 *
 * @param x1 the starting position of the defect.
 * @param x2 the ending position of the defect.
 * @param defectType the type of defect.
 */
public Defect(double x1, double x2, String defectType)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.defectType = defectType;
    }

/**
 * Gets the starting position of the defect.
 *
 * @return the x1 coordinate of the defect.
 */
public double getX1()
    {
        return x1;
    }

/**
 * Sets the starting position of the defect.
 *
 * @param x1 the new starting position of the defect.
 */
public void setX1(double x1)
    {
        this.x1 = x1;
    }

/**
 * Gets the ending position of the defect.
 *
 * @return the x2 coordinate of the defect.
 */
public double getX2()
    {
        return x2;
    }

/**
 * Sets the ending position of the defect.
 *
 * @param x2 the new ending position of the defect.
 */
public void setX2(double x2)
    {
        this.x2 = x2;
    }

/**
 * Gets the type of the defect.
 *
 * @return the type of defect as a String.
 */
public String getDefectType()
    {
        return defectType;
    }

/**
 * Sets the type of the defect.
 *
 * @param defectType the new defect type to be set.
 */
public void setDefectType(String defectType)
    {
        this.defectType = defectType;
    }

/**
 * Calculates the length of the defect based on the coordinates x1 and x2.
 *
 * @return the length of the defect.
 */
public double getDefectLength()
    {
        return x2 - x1;
    }

/**
 * Determines if the defect occurs at the start or the end of the measurement range.
 *
 * @param measurementLength the total length of the measurement.
 * @return true if the defect is at the start (x1 equals 0) or at the end
 *         (x2 equals the measurement length), otherwise false.
 */
public boolean isEndDefect(double measurementLength)
    {
        return x1 == 0 || x2 == measurementLength;
    }

/**
 * Determines if the defect is of a specified type and occurs at the start
 * or end of the measurement range.
 *
 * @param defectType the type of defect to match.
 * @param measurementLength the total length of the measurement.
 * @return true if the defect matches the type and is located at the start
 *         or end of the measurement range, otherwise false.
 */
public boolean isEndDefect(String defectType, double measurementLength)
    {
        return this.defectType.equals(defectType) && isEndDefect(measurementLength);
    }

/**
 * Turn the data of a defect into 1 readable String for the GUI.
 *
 * @return All data of a defect as a String.
 */
@Override
public String toString()
    {
        return ("Defect: " + defectType +
                " | X1: " + x1 + "cm" +
                " | X2: " + x2 + "cm");
    }
}