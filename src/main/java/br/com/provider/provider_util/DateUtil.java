package br.com.provider.provider_util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date parse( String val, String format ){
		Date ret = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			ret = sdf.parse(val);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
	
	public static String format( Date val, String format ){
		String ret = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			ret = sdf.format(val);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
}
