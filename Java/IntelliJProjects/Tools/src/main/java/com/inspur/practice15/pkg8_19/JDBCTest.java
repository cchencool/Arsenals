package com.inspur.practice15.pkg8_19;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;

public class JDBCTest {
    public static Connection conn;
    public static String Driver = "com.mysql.jdbc.Driver";
    public static String Url = "jdbc:mysql://127.0.0.1:3306/test";
    public static String User = "root";
    public static String Pass = "";
    public static ResultSet rs = null;

    public static void close() {
        try {
            rs.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Close ():" + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        //Properties connProp = new Properties();

        try {
            Class.forName(Driver);
            conn = DriverManager.getConnection(Url, User, Pass);
            DatabaseMetaData dbmd = conn.getMetaData();

            List<Student> studentsList = new ArrayList<Student>();

//			Statement stmt = conn.createStatement();
//			rs = stmt.executeQuery("select * from students_table");
//
//			while (rs.next())
//			{
//				Student stu = new Student();
//				stu.setStudentId(rs.getInt(1));
//				stu.setStudentName(rs.getString(2));
//				stu.setStudentGender(rs.getString(3));
//				stu.setStudentClass(rs.getString(4));
//				stu.setJavaTeacherID(rs.getInt(5));
//				studentsList.add(stu);
//			}

            PreparedStatement pstmt = conn.prepareStatement("select * from students_table where student_name = ?");
            String id = "Kang";
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Student stu = new Student();
                stu.setStudentId(rs.getInt(1));
                stu.setStudentName(rs.getString(2));
                stu.setStudentGender(rs.getString(3));
                stu.setStudentClass(rs.getString(4));
                stu.setJavaTeacherID(rs.getInt(5));
                studentsList.add(stu);
            }

            List<String> tableNames = new ArrayList<String>();
            rs = dbmd.getTables(null, null, "%", null);
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }

            System.out.println(studentsList);//.get(0).getStudentName());
            System.out.println(tableNames);

        } catch (Exception e) {
            System.out.println("got a exception! " + e);
        } finally {
            close();
        }

    }

}
