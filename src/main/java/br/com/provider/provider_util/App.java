package br.com.provider.provider_util;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String date = "2020/02/22";
        String reg = "^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$";
        //reg = "([0-9]{4})\\([0-9]{2})\\([0-9]{2})";
        DateChecker dc = new DateChecker(reg);
        System.out.println(dc.check(date));
    }
}
