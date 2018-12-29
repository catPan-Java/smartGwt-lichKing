package lichKing.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 *
 * @author catPan
 */
public class MyDateFormat {
	
	
	public static Date getMonthFirstDay(){
		return new Date(Integer.parseInt(getYearStr())-1900,Integer.parseInt(getMonthOfYearStr())-1,1);
	}
    
    public static String getYearStr() {
        return DateTimeFormat.getFormat("yyyy").format(new Date());
    }

    /**
     * 获取给定日期的月份
     * @param date 日期字符串
     * @return 月份字符串
     */
    @SuppressWarnings("deprecation")
	public static String getMonthOfYearStr(String date) {
        return DateTimeFormat.getFormat("M").format(new Date(date));
    }

	public static String getMonthOfYearStr() {
        return DateTimeFormat.getFormat("M").format(new Date());
    }
    /**
     * 获取给定日期是礼拜几
     * @param date 日期字符串
     * @return 礼拜几符串
     */
    @SuppressWarnings("deprecation")
	public static String getDayOfWeekStr(String date) {
        return DateTimeFormat.getFormat("E").format(new Date(date));
    }

    /**
     * 获取给定日期是礼拜几
     * @param date 日期字符串
     * @return 礼拜几整型
     */
    @SuppressWarnings("deprecation")
	public static int getDayOfWeek(String date) {
        return new Date(date).getDay();
    }

    /**
     * 获取日期时间的字符串，给定时间和格式化的字符串
     * @param dateOrTime 时间
     * @param format 格式化的字符串
     * @return 日期时间
     */
    @SuppressWarnings("deprecation")
	public static String dateAndTimeFormat(String dateOrTime, String format) {
        return DateTimeFormat.getFormat(format).format(new Date(dateOrTime));
    }

    /**
     * 获取日期时间的字符串，给定时间和格式化的字符串
     * @param date
     * @param fromat "yyyy-MM-dd","yyyy-MM-dd HH:mm","yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String dateAndTimeFormat(Date date, String fromat) {
        DateTimeFormat dateFormatter = DateTimeFormat.getFormat(fromat);
        return dateFormatter.format(date);
    }
//    
//    public static Date strToDate(String dateTimeStr, DateTimeFormat format) {
//        return format.parse(dateTimeStr);
//    }
    /**
     * 将时间类型转成指定的字符串格式
     * @param date date
     * @param type 0:"yyyy-MM-dd",1:"HH:mm",2:"yyyy-MM-dd HH:mm:ss",3:"yyyyMMddHHmmss",4:"yyyyMMdd",other :"yyyy-MM-dd HH:mm"
     * @return 日期时间
     */
    public static String dateAndTimeFormat(Date date, int type) {
        String DT = "";
        DateTimeFormat dateFormatter;
        if (type == 0) {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd");
        } else if (type == 1) {
            dateFormatter = DateTimeFormat.getFormat("HH:mm");
        } else if (type == 2) {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        } else if (type == 3) {
            dateFormatter = DateTimeFormat.getFormat("yyyyMMddHHmmss");
        } else if (type == 4) {
            dateFormatter = DateTimeFormat.getFormat("yyyyMMdd");
        } else if (type == 5) {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM");
        } else {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
        }
        if(date!=null){
        	DT = dateFormatter.format(date);
        }
        return DT;
    }

    /**
     * 获取日期时间Date，给定时间字符串和格式化的快捷类型int
     * @param dateTimeStr 时间字符串
     * @param type 1:"yyyy-MM-dd",2:"yyyy-MM-dd HH:mm",3:"yyyy-MM-dd HH:mm:ss",other :"yyyy-MM-dd"
     * @return 
     */
    public static Date strToDate(String dateTimeStr, int type) {
        DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd");
        if (type == 1) {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd");
        } else if (type == 2) {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
        } else if (type == 3) {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        } else if (type == 8) {
            dateFormatter = DateTimeFormat.getFormat("yyyy-MM");
        }
        return dateFormatter.parse(dateTimeStr);
    }

    /**
     * 获取日期时间，给定日期时间和格式化的字符串
     * @param date
     * @param fromat "yyyy-MM-dd","yyyy-MM-dd HH:mm","yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static Date dateFormat(Date date, String fromat){
        DateTimeFormat dateFormatter = DateTimeFormat.getFormat(fromat);
        return dateFormatter.parse(dateFormatter.format(date));
    }
}
