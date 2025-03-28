package com.github.LillaNudde;

public class Defect
{
    // Fields
    private double x1;
    private double x2;
    private String defectType;

    // Getters for the fields
    public double getX1()
    {
        return x1;
    }
    public double getX2()
    {
        return x2;
    }
    public String getDefectType()
    {
        return defectType;
    }

    // Constructor for defect
    public Defect(double X1, double X2, String DefectType)
    {
        this.x1 = X1;
        this.x2 = X2;
        this.defectType = DefectType;
    }

    @Override
    public String toString()
    {
        return ("Defect: " + defectType +
                " | X1: " + x1 + "cm" +
                " | X2: " + x2 + "cm");
    }

    // Get length of defect
    public double getDefectLength()
    {
        return x2 - x1;
    }


}
