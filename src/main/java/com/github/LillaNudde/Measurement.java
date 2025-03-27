package com.github.LillaNudde;

import java.util.ArrayList;
import java.util.List;

public class Measurement
{
    // Fields
    private int ID;
    private String date;
    private double length;
    private double width;
    private double thickness;

    // toString
    @Override
    public String toString()
    {
        return (ID + " | " + date +
                " | (" + length + "cm x " + width + "mm x " + thickness + "mm)" +
                " | Defects: " + getDefectAmount());
    }

    // List for defects
    private ArrayList<Defect> DefectList = new ArrayList<Defect>();


    // Getters for fields
    public int getID()
    {
        return ID;
    }
    public String getDate()
    {
        return date;
    }
    public double getLength()
    {
        return length;
    }
    public double getWidth()
    {
        return width;
    }
    public double getThickness()
    {
        return thickness;
    }

    // Setters for fields (excluding ID)
    public void setDate()
    {
        this.date = date;
    }
    public void setLength()
    {
        this.length = length;
    }
    public void setWidth()
    {
        this.width = width;
    }
    public void setThickness()
    {
        this.thickness = thickness;
    }

    // Constructor
    public Measurement(int ID, String date, double length, double width, double thickness)
    {
        this.ID = ID;
        this.date = date;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
    }

    // Method to add defect to list
    public void addDefect(double X1, double X2, String defectType)
    {
        DefectList.add(new Defect(X1, X2, defectType));
    }

    public List<Defect> getDefects()
    {
        return DefectList;
    }

    // Get total amount of defects of a certain type
    public int GetDefectAmount(String defectType)
    {
        int defectAmount = 0;
        for (Defect defect : DefectList)
        {
            if (defect.getDefectType().equals(defectType))
            {
                defectAmount++;
            }
        }
        return defectAmount;
    }

    // Get total amount of all defects
    public int getDefectAmount()
    {
        return DefectList.size();
    }

    // Get certain defect from list by index
    public Defect getDefect(int index)
    {
        if (index < DefectList.size())
        {
            return DefectList.get(index);
        }
        else
        {
            return null;
        }
    }

    // Get total length of all defects of a certain type
    public double getTotalDefectLength(String defectType)
    {
        double totalDefectLength = 0;
        for (Defect defect : DefectList)
        {
            if (defect.getDefectType().equals(defectType))
            {
                totalDefectLength += defect.getDefectLength();
            }
        }
        return totalDefectLength;
    }

    // Get total length of all defects
    public double getTotalDefectLength()
    {
        double totalDefectLength = 0;
        for (Defect defect : DefectList)
        {
            totalDefectLength += defect.getDefectLength();
        }
        return totalDefectLength;
    }

    // Get total amount of end defects from certain type of defect
    public int GetEndDefectAmount(String defectType)
    {
        int defectAmount = 0;
        for (Defect defect : DefectList)
        {
            if (defect.getDefectType().equals(defectType) && ( defect.getX1() == 0 || defect.getX2() == getLength() ))
            {
                defectAmount++;
            }
        }
        return defectAmount;
    }

    // Get total amount of all end defects
    public int GetEndDefectAmount()
    {
        int defectAmount = 0;
        for (Defect defect : DefectList) {
            if (defect.getX1() == 0 || defect.getX2() == getLength())
            {
                defectAmount++;
            }
        }
        return defectAmount;
    }

    // Get total length of end defects by certain defect type
    public double GetEndDefectLength(String defectType)
    {
        double defectLength = 0;
        for (Defect defect : DefectList)
        {
            if (defect.getDefectType().equals(defectType) && ( defect.getX1() == 0 || defect.getX2() == getLength() ))
            {
                defectLength += defect.getDefectLength();
            }
        }
        return defectLength;
    }

    // Get total length of all end defects
    public double GetEndDefectLength()
    {
        double defectLength = 0;
        for (Defect defect : DefectList)
        {
            if (defect.getX1() == 0 || defect.getX2() == getLength() )
            {
                defectLength += defect.getDefectLength();
            }
        }
        return defectLength;
    }

    public void addDefects(ArrayList<Defect> defects)
    {
        DefectList.addAll(defects);
    }


    // Remove defect from list by index
    public void removeDefect(int index)
    {
        if (index < DefectList.size() && index >= 0)
        {
            DefectList.remove(index);
        }
    }
}