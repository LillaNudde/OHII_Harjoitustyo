package com.github.LillaNudde;

public class Defect
{
    // Fields
    double X1;
    double X2;
    String DefectType;

    // Getters for the fields
    public double getX1()
    {
        return X1;
    }
    public double getX2()
    {
        return X2;
    }
    public String getDefectType()
    {
        return DefectType;
    }

    // Constructor for defect
    public Defect(double X1, double X2, String DefectType)
    {
        this.X1 = X1;
        this.X2 = X2;
        this.DefectType = DefectType;
    }

    @Override
    public String toString()
    {
        return ("Defect: " + DefectType +
                " | X1: " + X1 + "cm" +
                " | X2: " + X2 + "cm");
    }

    // Get length of defect
    public double getDefectLength()
    {
        return X2 - X1;
    }
}
