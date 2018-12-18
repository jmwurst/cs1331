package edu.gatech.cs1331.e3prep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class GtStudentComparator implements Comparator<GtStudent> {
    public int compare(GtStudent i, GtStudent j) {
        if (i.getGpa() > j.getGpa()) {
            return -1;
        }
        else if (i.getGpa() < j.getGpa()) {
            return 1;
        }
        return 0;
    }
}

public class GtStudent implements Comparable<GtStudent> {
    private String name;
    private String major;
    private double gpa;
    private int year;

    public GtStudent(String name, String major, double gpa, int year) {
        this.name = name;
        this.major = major;
        this.gpa = gpa;
        this.year = year;
    }

    public int compareTo(GtStudent o) {
        if (this.gpa > o.gpa) {
            return -1;
        }
        else if (!this.name.equals(o.name)) {
            return this.name.compareTo(o.name);
        }
        else if (!this.major.equals(o.major)) {
            return this.major.compareTo(o.major);
        }
        return 0;
    }

    public String toString() {
        return name + ", " + major + ", " + gpa + ", " + year + "|||";
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public double getGpa() {
        return gpa;
    }

    public int getYear() {
        return year;
    }

    public static void main(String[] args) {
        List<GtStudent> studentList = new ArrayList<>();
        studentList.add(new GtStudent("Bill", "Being a massive gay", 6.9, 1992));
        studentList.add(new GtStudent("Josh", "Comnizzle Scizzle", 4.2, 2017));
        studentList.add(new GtStudent("Josh2.0", "dying honors_tm", 4.3, 2017));
        studentList.add(new GtStudent("Jane", "Inta", 3.8, 1987));
        studentList.add(new GtStudent("NotOldMan", "econbitch", 6.9, 1987));


        Collections.sort(studentList);
        System.out.println("compareTo in GtStudent:");
        System.out.println(studentList);

        Collections.sort(studentList, new Comparator<GtStudent>(){
            public int compare(GtStudent i, GtStudent j) {
                return i.name.compareTo(j.name);
            }
        });
        System.out.println("Comparator inner class (name):");
        System.out.println(studentList);

        Collections.sort(studentList, new GtStudentComparator());
        System.out.println("GtStudentComparator (gpa):");
        System.out.println(studentList);

        Collections.sort(studentList, (GtStudent i, GtStudent j) -> i.major.toLowerCase().compareTo(j.major.toLowerCase()));
        System.out.println("Lambda (major):");
        System.out.println(studentList);

        Collections.sort(studentList, Comparator.comparing(GtStudent::getYear).thenComparing(GtStudent::getGpa));
        System.out.println("Super advanced lambda donut steel (same as compareTo):");
        System.out.println(studentList);
    }
}
