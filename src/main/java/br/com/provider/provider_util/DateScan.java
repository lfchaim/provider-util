package br.com.provider.provider_util;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateScan {

    public static void main(String args[]) {

    	DateScan run = new DateScan();
        run.dateInputScan();
    }

    public void dateInputScan() {

        //Instantiating variables
        String lvarStrDateOfTransaction = null;
        DateFormat formatter = null;
        Date lvarObjDateOfTransaction = null;

        //Use one of the following date formats.
        //lvarStrDateOfTransaction = "29/07/2013";
        //lvarStrDateOfTransaction = "29-07-2013";
        //lvarStrDateOfTransaction = "20130729";
        //lvarStrDateOfTransaction = "2013-07-29";
        //lvarStrDateOfTransaction = "29/07/2013";
        lvarStrDateOfTransaction = "2013/02/30";

        //You can also add your own regex (Regular Expression)
        if (lvarStrDateOfTransaction.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
        } else if (lvarStrDateOfTransaction.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
            formatter = new SimpleDateFormat("dd-MM-yyyy");
        } else if (lvarStrDateOfTransaction.matches("([0-9]{4})([0-9]{2})([0-9]{2})")) {
            formatter = new SimpleDateFormat("yyyyMMdd");
        } else if (lvarStrDateOfTransaction.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        } else if (lvarStrDateOfTransaction.matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")) {
            formatter = new SimpleDateFormat("yyyy/MM/dd");
        }

        try {
            lvarObjDateOfTransaction = formatter.parse(lvarStrDateOfTransaction);
            JOptionPane.showMessageDialog(null, "Date: " + lvarObjDateOfTransaction);

        } catch (Exception ex) {    //Catch the Exception in case the format is not found.
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}