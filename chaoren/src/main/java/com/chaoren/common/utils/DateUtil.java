package com.chaoren.common.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lh on 2018/6/11.
 */

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static SimpleDateFormat sdf;
	/**
	 * 短时间字符串格式
	 */
	public static String SHORT_PATTERN = "yyyy-MM-dd";
	/**
	 * 默认时间字符串格式
	 */
	public static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 天的单位
	 */
	public static final String DAY_UNIT = "d";
	/**
	 * 小时的单位
	 */
	public static final String HOUR_UNIT = "h";
	/**
	 * 分钟的单位
	 */
	public static final String MINUTE_UNIT = "m";
	/**
	 * 周的单位
	 */
	public static final String WEEK_UNIT = "w";
	/**
	 * 月的单位
	 */
	public static final String MONTH_UNIT = "m";
	/**
	 * 季度的单位
	 */
	public static final String QUARTER_UNIT = "q";
	/**
	 * 年份的单位
	 */
	public static final String YEAR_UNIT = "y";


	/**
	 * 以默认模式格式化时间字符串
	 *
	 * @param timeStr 时间字符串
	 * @return 格式化后的时间类
	 */
	public static Date parse(String timeStr) {
		return parse(timeStr, DEFAULT_PATTERN);
	}

	/**
	 * 格式化时间字符串返回Date
	 *
	 * @param timeStr 时间字符串
	 * @param pattern 字符串模式 例如:yyyy-MM-dd
	 * @return 格式化后的时间类
	 */
	public static Date parse(String timeStr, String pattern) {
		Date result = null;
		sdf = new SimpleDateFormat(pattern);
		try {
			result = sdf.parse(timeStr);
		} catch (ParseException e) {
			try {
				result = sdf.parse("0000-00-00");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
		return result;
	}

	/**
	 * 以字符串格式获取当前时间的
	 *
	 * @return 当前时间的字符串
	 */
	public static String getNow() {
		return getNow(DEFAULT_PATTERN);
	}

	/**
	 * 以特定的模式获取当前时间的字符串
	 *
	 * @param pattern 模式
	 * @return 当前时间的字符串
	 */
	public static String getNow(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 以默认模式格式化一个时间类为字符串
	 *
	 * @param date 时间
	 * @return 格式化后的字符串
	 */
	public static String format(Date date) {
		return format(date, DEFAULT_PATTERN);
	}

	/**
	 * 格式化一个时间类为字符串
	 *
	 * @param date    时间
	 * @param pattern 模式
	 * @return 格式化后的字符串
	 */
	public static String format(Date date, String pattern) {
		sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 获取当前年份
	 *
	 * @return
	 */
	public static int getNowYearNum() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取今年的各个单位的最大数
	 *
	 * @param unit
	 * @return
	 */
	public static int getMaxNumInYear(String unit) {
		return getMaxNumInYear(getNowYearNum(), unit);
	}

	/**
	 * 获取某年的各个单位的最大数
	 *
	 * @param unit
	 * @return
	 */
	public static int getMaxNumInYear(Integer year, String unit) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		int num = 0;
		switch (unit) {
			case WEEK_UNIT:
				num = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
				break;
			case MONTH_UNIT:
				num = 12;
				break;
			case QUARTER_UNIT:
				num = 4;
				break;
			default:
				num = 0;
				break;
		}
		return num;
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		between_days++;
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * @param date
	 * @param day
	 * @return Date    返回类型
	 * @throws
	 * @Title: addDays
	 * @Description: 日期增加多少天
	 */
	public static Date addDays(Date date, Integer day) {
		return addTime(date, day, DAY_UNIT);
	}

	/**
	 * 给时间添加指定的时间段,返回计算后的时间
	 *
	 * @param startTime 开始时间
	 * @param time      需要添加的时间
	 * @param unit      单位
	 * @return Date 返回类型
	 */
	public static Date addTime(Date startTime, Integer time,
							   String unit) {
		Calendar calendar = Calendar.getInstance();
		// 设置开始时间
		calendar.setTime(startTime);
		// 默认为分钟
		Integer type = Calendar.MINUTE;
		switch (unit) {
			case DAY_UNIT:
				type = Calendar.DATE;
				break;
			case HOUR_UNIT:
				type = Calendar.HOUR;
				break;
			case MINUTE_UNIT:
				type = Calendar.MINUTE;
				break;
		}

		calendar.add(type, time);

		return calendar.getTime();
	}

	/**
	 * @param time
	 * @return Date    返回去掉时分秒的日期
	 * @throws
	 * @Title: toDay
	 * @Description: 将传入的日期 去掉 时分秒
	 */
	public static Date toDay(Date time) {
		String p = "yyyy-MM-dd";
		String str = format(time, p);
		return parse(str, p);
	}

	/**
	 * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Date>    返回类型
	 * @throws
	 * @Title: getCurrentYearScope
	 * @Description: 得到本年的时间范围
	 */
	public static Map<String, Date> getCurrentYearScope() {
		Map<String, Date> data = new HashMap<String, Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Integer year = calendar.get(Calendar.YEAR);
		String start = year + "-1-1 00:00:00";
		String end = year + "-12-31 23:59:59";
		data.put("startTime", parse(start));
		data.put("endTime", parse(end));
		return data;
	}

	/**
	 * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Date>    返回类型
	 * @throws
	 * @Title: getCurrentQuarterScope
	 * @Description: 得到本季度的时间范围
	 */
	public static Map<String, Date> getCurrentQuarterScope() {
		Map<String, Date> data = new HashMap<String, Date>();
		data.put("startTime", getCurrentQuarterStartTime());
		data.put("endTime", getCurrentQuarterEndTime());
		return data;
	}

	/**
	 * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Date>    返回类型
	 * @throws
	 * @Title: getCurrentMonthScope
	 * @Description: 得到本月的时间范围
	 */
	public static Map<String, Date> getCurrentMonthScope() {
		Map<String, Date> data = new HashMap<String, Date>();
		data.put("startTime", getCurrentMonthStartTime());
		data.put("endTime", getCurrentMonthEndTime());
		return data;
	}

	/**
	 * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Date>    返回类型
	 * @throws
	 * @Title: getCurrentWeekScope
	 * @Description: 得到本周的时间范围
	 */
	public static Map<String, Date> getCurrentWeekScope() {
		Map<String, Date> data = new HashMap<String, Date>();
		data.put("startTime", getTimesWeekmorning());
		data.put("endTime", getTimesWeeknight());
		return data;
	}

	/**
	 * 获取现在是本年的第几周 从1开始
	 *
	 * @return
	 */
	public static int getCurrentWeekNum() {
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		return getTimesWeekmorning(getCurrentWeekNum());
	}

	// 获得本周日24点时间
	public static Date getTimesWeeknight() {
		return getTimesWeeknight(getCurrentWeekNum());
	}

	/**
	 * 获取任意周的开始时间
	 *
	 * @param week
	 * @return
	 */
	public static Date getTimesWeekmorning(int week) {
		return getTimesWeekmorning(getNowYearNum(), week, 0);
	}

	/**
	 * 获取任意周的开始时间
	 *
	 * @param year 年份
	 * @param week 第几周
	 * @param num  增加或者减少的周数
	 * @return
	 */
	public static Date getTimesWeekmorning(int year, int week, int num) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.add(Calendar.DAY_OF_YEAR, num * 7);

		return cal.getTime();
	}

	/**
	 * 获取任意周的结束时间
	 *
	 * @param week
	 * @return
	 */
	public static Date getTimesWeeknight(int week) {
		return getTimesWeeknight(getNowYearNum(), week, 0);
	}

	/**
	 * 获取任意周的结束时间
	 *
	 * @param week
	 * @return
	 */
	public static Date getTimesWeeknight(int year, int week, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(SHORT_PATTERN);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.setTime(getTimesWeekmorning(week));
		cal.add(Calendar.DAY_OF_YEAR, num * 7);
		cal.add(Calendar.DAY_OF_WEEK, 6);
		return parse(sdf.format(cal.getTime()) + " 23:59:59");
	}

	/**
	 * 获取现在是本年的第几月,从0开始
	 *
	 * @return
	 */
	public static int getCurrentMonthNum() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 当前季度的开始时间，即2012-01-1 00:00:00
	 *
	 * @return
	 */
	public static Date getCurrentQuarterStartTime() {
		Date now = null;
		try {
			now = getQuarterStartTime(getCurrentQuarterNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Date now = null;
		try {
			now = getQuarterEndTime(getCurrentQuarterNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获取当前季度数
	 *
	 * @return
	 */
	public static int getCurrentQuarterNumber() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		int currentQuarter = 0;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				currentQuarter = 1;
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				currentQuarter = 2;
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				currentQuarter = 3;
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				currentQuarter = 4;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentQuarter;
	}

	/**
	 * 获取季度的开始时间
	 *
	 * @param quarter 季度数
	 * @return
	 */
	public static Date getQuarterStartTime(int quarter) {
		return getQuarterStartTime(getNowYearNum(), quarter, 0);
	}

	/**
	 * 获取季度的开始时间
	 *
	 * @param quarter 季度数
	 * @return
	 */
	public static Date getQuarterStartTime(int year, int quarter, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(SHORT_PATTERN);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		Date now = null;
		try {
			switch (quarter) {
				case 1:
					c.set(Calendar.MONTH, 0);
					break;
				case 2:
					c.set(Calendar.MONTH, 3);
					break;
				case 3:
					c.set(Calendar.MONTH, 6);
					break;
				case 4:
					c.set(Calendar.MONTH, 9);
					break;
				default:
					c.set(Calendar.MONTH, 0);
					break;
			}
			c.set(Calendar.DATE, 1);
			c.add(Calendar.MONTH, num * 3);
			now = parse(sdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获取季度的结束时间
	 *
	 * @param quarter 季度数
	 * @return
	 */
	public static Date getQuarterEndTime(int quarter) {
		return getQuarterEndTime(getNowYearNum(), quarter, 0);
	}

	/**
	 * 获取季度的结束时间
	 *
	 * @param quarter 季度数
	 * @return
	 */
	public static Date getQuarterEndTime(int year, int quarter, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(SHORT_PATTERN);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		Date now = null;

		try {
			switch (quarter) {
				case 1:
					c.set(Calendar.MONTH, 2);
					c.set(Calendar.DATE, 31);
					break;
				case 2:
					c.set(Calendar.MONTH, 5);
					c.set(Calendar.DATE, 30);
					break;
				case 3:
					c.set(Calendar.MONTH, 8);
					c.set(Calendar.DATE, 30);
					break;
				case 4:
					c.set(Calendar.MONTH, 11);
					c.set(Calendar.DATE, 31);
					break;
				default:
					c.set(Calendar.MONTH, 2);
					c.set(Calendar.DATE, 31);
					break;
			}
			c.add(Calendar.MONTH, num * 3);
			now = parse(sdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本月的开始时间，即2012-01-01 00:00:00
	 *
	 * @return
	 */
	public static Date getCurrentMonthStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(SHORT_PATTERN);
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.DATE, 1);
			now = parse(sdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前月的结束时间，即2012-01-31 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentMonthEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			now = getMonthEndTime(c.get(Calendar.MONTH) + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得上月的开始时间，即2012-01-01 00:00:00
	 *
	 * @return
	 */
	public static Date getPreviousMonthStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(SHORT_PATTERN);
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.add(Calendar.MONTH, -1);
			c.set(Calendar.DATE, 1);
			now = parse(sdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当上月的结束时间，即2012-01-31 23:59:59
	 *
	 * @return
	 */
	public static Date getPreviousMonthEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			now = getMonthEndTime(c.get(Calendar.MONTH));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获取月份的开始时间
	 *
	 * @param month 月份
	 * @return
	 */
	public static Date getMonthStartTime(int month) {
		return getMonthStartTime(getNowYearNum(), month, 0);
	}

	/**
	 * 获取月份的开始时间
	 *
	 * @param month 月份
	 * @return
	 */
	public static Date getMonthStartTime(int year, int month, int num) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		Date now = null;
		try {
			c.set(Calendar.MONTH, month - 1);
			c.set(Calendar.DATE, 1);
			c.add(Calendar.MONTH, num);
			now = parse(format(c.getTime(), SHORT_PATTERN) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获取月份的结束时间
	 *
	 * @param month 月份
	 * @return
	 */
	public static Date getMonthEndTime(int month) {
		return getMonthEndTime(getNowYearNum(), month, 0);
	}

	/**
	 * 获取月份的结束时间
	 *
	 * @param month 月份
	 * @return
	 */
	public static Date getMonthEndTime(int year, int month, int num) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		Date now = null;
		try {
			c.set(Calendar.MONTH, month - 1);
			c.set(Calendar.DATE, 1);
			c.add(Calendar.MONTH, 1);
			c.add(Calendar.DAY_OF_YEAR, -1);
			c.add(Calendar.MONTH, num);
			now = parse(format(c.getTime(), SHORT_PATTERN) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获取当天的开始时间
	 *
	 * @return
	 */
	public static Date getCurrentDayStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			now = parse(format(c.getTime(), SHORT_PATTERN) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获取当天的结束时间
	 *
	 * @return
	 */
	public static Date getCurrentDayEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			now = parse(format(c.getTime(), SHORT_PATTERN) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 计算两个时间段之间的天数
	 *
	 * @param date   时间1
	 * @param date2  时间2
	 * @param hasSTA 是否包括周六
	 * @param hasSUN 是否包括周日
	 * @return int 天数
	 */
	public static int getDayNumber(Date date, Date date2, boolean hasSTA, boolean hasSUN) {
		int num = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBefore(date, date2));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(getAfter(date, date2));

		for (; cal.compareTo(cal2) <= 0; cal.add(Calendar.DAY_OF_MONTH, 1)) {
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.MONDAY:
				case Calendar.TUESDAY:
				case Calendar.WEDNESDAY:
				case Calendar.THURSDAY:
				case Calendar.FRIDAY:
					num++;
					break;
				case Calendar.SATURDAY:
					if (hasSTA) {
						num++;
					}
					break;
				case Calendar.SUNDAY:
					if (hasSUN) {
						num++;
					}
					break;
				default:
					break;
			}
		}
		return num;
	}

	/**
	 * 获取两个时间段之间的每天的时间集合
	 *
	 * @param date   时间1
	 * @param date2  时间2
	 * @param hasSTA 是否包括周六
	 * @param hasSUN 是否包括周日
	 * @return List date集合
	 */
	public static List<Date> getDayList(Date date, Date date2, boolean hasSTA, boolean hasSUN) {
		List<Date> dateList = Lists.newArrayList();

		Calendar cal = Calendar.getInstance();
		cal.setTime(getBefore(date, date2));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(getAfter(date, date2));

		for (; cal.compareTo(cal2) <= 0; cal.add(Calendar.DAY_OF_MONTH, 1)) {
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.MONDAY:
				case Calendar.TUESDAY:
				case Calendar.WEDNESDAY:
				case Calendar.THURSDAY:
				case Calendar.FRIDAY:
					dateList.add(cal.getTime());
					break;
				case Calendar.SATURDAY:
					if (hasSTA) {
						dateList.add(cal.getTime());
					}
					break;
				case Calendar.SUNDAY:
					if (hasSUN) {
						dateList.add(cal.getTime());
					}
					break;
				default:
					break;
			}
		}
		return dateList;
	}

	/**
	 * 比较两个时间,取靠前的时间
	 *
	 * @param date
	 * @param date2
	 * @return
	 */
	public static Date getBefore(Date date, Date date2) {
		if (date.before(date2)) {
			return date;
		} else {
			return date2;
		}
	}

	/**
	 * 比较两个时间,取靠后的时间
	 *
	 * @param date
	 * @param date2
	 * @return
	 */
	public static Date getAfter(Date date, Date date2) {
		if (date.after(date2)) {
			return date;
		} else {
			return date2;
		}
	}

	/**
	 * 获取日期的星期数
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取日期的星期数的名称
	 *
	 * @param date     日期
	 * @param language 语言 中文用 zh,英文用en
	 * @return
	 */
	public static String getDayOfWeekName(Date date, String language) {
		String name = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				name = language.equals("zh") ? "星期一" : "MON";
				break;
			case Calendar.TUESDAY:
				name = language.equals("zh") ? "星期二" : "TUE";
				break;
			case Calendar.WEDNESDAY:
				name = language.equals("zh") ? "星期三" : "WED";
				break;
			case Calendar.THURSDAY:
				name = language.equals("zh") ? "星期四" : "THU";
				break;
			case Calendar.FRIDAY:
				name = language.equals("zh") ? "星期五" : "FRI";
				break;
			case Calendar.SATURDAY:
				name = language.equals("zh") ? "星期六" : "STA";
				break;
			case Calendar.SUNDAY:
				name = language.equals("zh") ? "星期日" : "SUN";
				break;
			default:
				break;
		}
		return name;
	}

	public static String timeStampToDate(Long timeStamp) {
		return format(new Date(Long.parseLong(String.valueOf(timeStamp))), DEFAULT_PATTERN);
	}

	public static int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age;
		} catch (Exception e) {//兼容性更强,异常后返回数据
			return 0;
		}
	}
//	public static void main(String[] args) throws ParseException {
//	}
}
