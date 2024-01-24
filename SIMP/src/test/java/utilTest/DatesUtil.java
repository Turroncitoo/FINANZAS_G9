package utilTest;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.junit.Test;

public class DatesUtil
{
    // @Test
    public void test()
    {
        long time = System.currentTimeMillis();
        DateTime date = new DateTime(time);
        System.out.println(date.toDate());
        System.out.println(date.toString("yyyy-MM-dd"));
    }

    // @Test
    public void formatStringDate()
    {
        SimpleDateFormat sdfRealDate = new SimpleDateFormat("yyMM");
        System.out.println(sdfRealDate.format(new Date()));
    }

    //@Test
    public void duracion()
    {
        Period periodo = new Duration(60000000).toPeriod();
        PeriodFormatter hm = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2)
                .appendMinutes().appendSeparator(":").appendSeconds().toFormatter();
        System.out.println(hm.print(periodo));
    }

    @Test
    public void milisegundosAMinutos()
    {
        System.out.println(TimeUnit.MILLISECONDS.toMinutes(60000005));
    }

}