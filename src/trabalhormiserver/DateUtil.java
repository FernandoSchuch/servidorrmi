package trabalhormiserver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Projeto....: TrabalhoRMIServer Criado em..: 03/10/2017, 21:42:36 Arquivo....:
 * DateUtil.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class DateUtil {

    private static final String FORMATO_DATA_HORA = "dd/MM/yyyy";

    public static String gerDataAtualFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        String data1 = "06/10/2017";
        String data2 = "07/10/2017";
        String data3 = "09/10/2017";

//        Date date1 = parse(data1);
//        Date date2 = parse(data2);
//        Date date3 = parse(data3);
//        
//        System.out.println(date1.toString());
//        System.out.println(date2.toString());
//        System.out.println(date3.toString());
        // -1 Menor
        //  0 Igual
        //  1 Maior
        System.out.println(isBetween(data3, data2, data3));
    }

    public static boolean isBetween(String date, String start, String end) {
        return isBetween(parse(date), parse(start), parse(end));
    }

    public static boolean isBetween(Date date, Date start, Date end) {
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    public static Date parse(String str) {
        DateFormat df = new SimpleDateFormat(FORMATO_DATA_HORA);
        df.setLenient(false);
        try {
            return df.parse(str);
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }
}
