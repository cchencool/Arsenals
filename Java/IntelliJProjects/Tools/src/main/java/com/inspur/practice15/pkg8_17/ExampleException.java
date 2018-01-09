package com.inspur.practice15.pkg8_17;

public class ExampleException extends Exception {
    private int idnumber;

    public ExampleException(String message, int id) {
        super(message);
        this.idnumber = id;
    }

    public int getId() {
        return idnumber;
    }

//	public String getMessage()
//	{
//		return "This is ExampleException";
//	}
}
