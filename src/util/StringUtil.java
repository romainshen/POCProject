package util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(converInt2String(10, 3));
		Date curDate = new Date();
		System.out.println(curDate);
		//curDate.
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		System.out.println(cal.getTime()); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(cal.getTime());
		System.out.println(strDate);
		
	}
	public static String converInt2String(int intNumber,int digit){
		DecimalFormat df = new DecimalFormat("000");
		return df.format(intNumber);
		
		
		
	}

}
