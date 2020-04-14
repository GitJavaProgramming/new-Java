package org.pp.commons.jodatime;

import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Map;

public class TestJodaTime {

    private static final String DATE_FORMAT = "E yyyy/dd/MM HH:mm:ss.SSS";

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.FEBRUARY, 3);
        System.out.println(calendar.getTime());

        DateTime dateTime = new DateTime(2020, 1, 1, 0, 0);
        System.out.println(dateTime.dayOfYear().get()); // day of year
        DateTime now = DateTime.now();
        System.out.println(now); // now

        // 某一个瞬间加上 90 天并输出结果
        System.out.println(now.plusDays(90).toString(DATE_FORMAT));
        System.out.println(dateTime.plusDays(45)
//                .plusMonths(1)
                .dayOfWeek()
                .withMaximumValue()
                .toString(DATE_FORMAT));

        // 日期间隔
        Period p = new Period(dateTime, now, PeriodType.days());
        Map<String, String> map = BeanUtils.describe(p);
        map.forEach((k, v) -> System.out.println(k + ":" + v));

//        Interval interval = new Interval();
    }
}
