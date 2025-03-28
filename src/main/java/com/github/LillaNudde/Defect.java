package com.github.LillaNudde;

public class Defect
{
    private double x1;
    private double x2;
    private String defectType;

    public Defect(double x1, double x2, String defectType)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.defectType = defectType;
    }

    public double getX1()
    {
        return x1;
    }

    public void setX1(double x1)
    {
        this.x1 = x1;
    }

    public double getX2()
    {
        return x2;
    }

    public void setX2(double x2)
    {
        this.x2 = x2;
    }

    public String getDefectType()
    {
        return defectType;
    }

    public void setDefectType(String defectType)
    {
        this.defectType = defectType;
    }

    public double getDefectLength()
    {
        return x2 - x1;
    }

    public boolean isEndDefect(double measurementLength)
    {
        return x1 == 0 || x2 == measurementLength;
    }

    public boolean isEndDefect(String defectType, double measurementLength)
    {
        return this.defectType.equals(defectType) && isEndDefect(measurementLength);
    }

    @Override
    public String toString()
    {
        return ("Defect: " + defectType +
                " | X1: " + x1 + "cm" +
                " | X2: " + x2 + "cm");
    }
}