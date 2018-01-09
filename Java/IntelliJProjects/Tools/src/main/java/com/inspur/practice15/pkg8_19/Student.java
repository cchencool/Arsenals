package com.inspur.practice15.pkg8_19;

//import java.sql.*;

public class Student {
    private int studentId;
    private String studentName;
    private String studentGender;
    private String studentClass;
    private int javaTeacherID;

//	public void getInfoFromTable(ResultSet rs) throws Exception
//	{
//		try
//		{
//			while (rs.next())
//			{
//				this.studentId = rs.getInt(1);
//				this.studentName = rs.getString(2);
//				this.studentGender = rs.getString(3);
//				this.studentClass = rs.getString(4);
//				this.javaTeacherID = rs.getInt(5);
//				
//			}
//		} 
//		finally 
//		{
//			System.out.println("Get student info");
//		}
//	}

    public String toString() {
        return "Id: " + studentId +
                ", Name: " + studentName +
                ", Gender: " + studentGender +
                ", Class: " + studentClass +
                ", JavaTeacherId: " + javaTeacherID +
                ".\n";
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getJavaTeacherID() {
        return javaTeacherID;
    }

    public void setJavaTeacherID(int javaTeacherID) {
        this.javaTeacherID = javaTeacherID;
    }


}
