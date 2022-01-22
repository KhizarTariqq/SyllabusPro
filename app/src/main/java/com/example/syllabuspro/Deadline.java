package com.example.syllabuspro;

import android.util.Log;

public class Deadline {
    private Deadline.Alternative Alternative;
    private int year;
    private int month;
    private int day;

    public enum Alternative
    {
        TBA,
        Ongoing,
        NA
    }

    public Deadline(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Deadline(Deadline.Alternative alternative)
    {
        this.Alternative = alternative;
    }

    public Deadline(String deadline)
    {
        int year = Integer.parseInt(deadline.substring(0, 4));
        int month = Integer.parseInt(deadline.substring(5, 7));
        int day = Integer.parseInt(deadline.substring(8, 10));
    }

    public String toString()
    {
        if (this.Alternative != null)
        {
            return this.Alternative.toString();
        }

        else
        {
            return Integer.toString(this.year) + "-" + Integer.toString(this.month) + "-" + Integer.toString(this.day);
        }
    }

    public int getYear()
    {
        return this.year;
    }

    public int getMonth()
    {
        return this.month;
    }

    public int getDay()
    {
        return this.day;
    }

    public void setAlternative(Deadline.Alternative alternative)
    {
        this.Alternative = alternative;
    }
}
