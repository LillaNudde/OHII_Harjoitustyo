package com.github.LillaNudde;

import java.util.ArrayList;
import java.util.List;

public class Measurement
{
    private int id;
    private String date;
    private double length;
    private double width;
    private double thickness;

    private ArrayList<Defect> defectList = new ArrayList<>();

    public Measurement(int id, String date, double length, double width, double thickness)
    {
        this.id = id;
        this.date = date;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
    }

    public int getId()
    {
        return id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public double getLength()
    {
        return length;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness)
    {
        this.thickness = thickness;
    }

    public List<Defect> getDefects()
    {
        return defectList;
    }

    public void addDefect(double X1, double X2, String defectType)
    {
        defectList.add(new Defect(X1, X2, defectType));
    }

    public void addDefects(ArrayList<Defect> defects)
    {
        defectList.addAll(defects);
    }

    public void removeDefect(int index)
    {
        if (index < defectList.size() && index >= 0)
        {
            defectList.remove(index);
        }
    }

    public int getDefectAmount()
    {
        return defectList.size();
    }

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

    @Override
    public String toString()
    {
        return (id + " | " + date +
                " | (" + length + "cm x " + width + "mm x " + thickness + "mm)" +
                " | Defects: " + getDefectAmount());
    }
}