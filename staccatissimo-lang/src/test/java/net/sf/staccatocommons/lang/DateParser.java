package net.sf.staccatocommons.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.staccatocommons.defs.Thunk;

import org.junit.Test;

public class DateParser {

  @Test
  public void testName() throws Exception {
    final String date = "2010-02-10";
    new Fallible<Date>(new Thunk<Date>() {
      public Date value() {
        try {
          return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
          throw SoftException.soften(e);
        }
      }
    }).value();
  }
}
