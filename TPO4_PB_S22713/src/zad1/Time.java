/**
 *
 *  @author Perliński Bartłomiej S22713
 *
 */

package zad1;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time {

    public static String passed(String from, String to){

        String result = "";

        try {
            if (from.matches(".*T.*") || to.matches(".*T.*")){
                LocalDateTime fromTime = LocalDateTime.parse(from);
                LocalDateTime toTime = LocalDateTime.parse(to);
                LocalDateTime tempDatetime = LocalDateTime.from(fromTime);
                long days = tempDatetime.until(toTime, ChronoUnit.DAYS);
                tempDatetime = tempDatetime.plusDays(days);
                DecimalFormat formatter = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
                long hours = tempDatetime.until(toTime, ChronoUnit.HOURS);
                tempDatetime = tempDatetime.plusHours(hours);
                long minutes = tempDatetime.until(toTime, ChronoUnit.MINUTES);
                tempDatetime = tempDatetime.plusMinutes(minutes);
                Period period = Period.between(fromTime.toLocalDate(), toTime.toLocalDate());
                return "Od "
                        + fromTime.getDayOfMonth()
                        + " "
                        + fromTime.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + " "
                        + fromTime.getYear()
                        + " ("
                        + fromTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + ") godz. "
                        + fromTime.getHour() + ":" + (fromTime.getMinute() == 0 ? "00" : fromTime.getMinute())
                        + " do "
                        + toTime.getDayOfMonth()
                        + " "
                        + toTime.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + " "
                        + toTime.getYear()
                        + " ("
                        + toTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + ") godz. " + toTime.getHour() + ":" + (toTime.getMinute() == 0 ? "00" : toTime.getMinute())
                        + "\n - mija: "
                        + days
                        + (days != 1 ? " dni, tygodni " : " dzień, tygodni ")
                        + formatter.format(((double) days)/7)
                        + "\n - godzin: "
                        + Duration.between(fromTime, toTime).toHours()
                        + ", minut: "
                        + Duration.between(fromTime, toTime).toMinutes()
                        + "\n - kalendarzowo: "
                        + (period.getYears() >= 1 ? (period.getYears() + (period.getYears() == 1 ? " rok" : (period.getYears() < 5 ? " lata" : " lat"))) : "")
                        + (period.getMonths() >= 1 ? (", " + period.getMonths() + (period.getMonths() == 1 ? " miesiąc, " : (period.getMonths() < 5 ? " miesiące, " : " miesięcy, "))) : "")
                        + ((period.getDays() < toTime.getMonth().maxLength() && period.getDays() > 0)? (period.getDays() + (period.getDays() == 1 ? " dzień " : " dni ")) : "")
                        ;
            } else {
                LocalDate fromTime = LocalDate.parse(from);
                LocalDate toTime = LocalDate.parse(to);
                LocalDate tempDate = LocalDate.from(fromTime);
                long days = tempDate.until(toTime, ChronoUnit.DAYS);
                tempDate = tempDate.plusDays(days);
                DecimalFormat formatter = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
                Period period = Period.between(fromTime, toTime);

                return "Od "
                        + fromTime.getDayOfMonth()
                        + " "
                        + fromTime.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + " "
                        + fromTime.getYear()
                        + " ("
                        + fromTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + ") do "
                        + toTime.getDayOfMonth()
                        + " "
                        + toTime.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + " "
                        + toTime.getYear()
                        + " ("
                        + toTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))
                        + ")\n - mija: "
                        + days
                        + (days != 1 ? " dni, tygodni " : " dzień, tygodni ")
                        + formatter.format(((double) days)/7)
                        + "\n - kalendarzowo: "
                        + (period.getYears() >= 1 ? (period.getYears() + (period.getYears() == 1 ? " rok" : (period.getYears() < 5 ? " lata" : " lat"))) : "")
                        + (period.getMonths() >= 1 ? (", " + period.getMonths() + (period.getMonths() == 1 ? " miesiąc, " : (period.getMonths() < 5 ? " miesiące, " : " miesięcy, "))) : "")
                        + ((period.getDays() < toTime.getMonth().maxLength() && period.getDays() > 0) ? (period.getDays() + (period.getDays() == 1 ? " dzień " : " dni ")) : "")
                        ;
            }
        } catch (DateTimeException e){
            System.out.print("*** " + e);
        }
        return result;
    }
}
