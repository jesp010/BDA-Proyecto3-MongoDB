package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class Regex {

    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    //Password expression. Password must be between 4 and 8 digits long and include at least one numeric digit.
    private static final String PASSWORD_REGEX = "^(?=.*\\d).{4,8}$";
    private static final String USERNAME_REGEX = "^[a-z0-9_-]{3,16}$";

    private static final String DATE_YEAR_REGEX = "^[0-9]{4}$";
    private static final String DATE_MONTH_REGEX = "^[0-9]{2}$";
    private static final String DATE_DAY_REGEX = "^[0-9]{2}$";

    public static boolean matchEmail(String email) {
        Pattern p = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(email);
        return matcher.find();
    }

    public static boolean matchUsername(String username) {
        Pattern p = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = p.matcher(username);
        return matcher.find();
    }

    public static boolean matchPassword(String password) {
        Pattern p = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = p.matcher(password);
        return matcher.find();
    }
    
    public static boolean matchDate(String year, String month, String day) {
        Pattern pYear = Pattern.compile(DATE_YEAR_REGEX);
        Pattern pMonth = Pattern.compile(DATE_MONTH_REGEX);
        Pattern pDay = Pattern.compile(DATE_DAY_REGEX);
        
        Matcher mYear = pYear.matcher(year);
        Matcher mMonth = pMonth.matcher(month);
        Matcher mDay = pDay.matcher(day);
        
        boolean yearMatch = mYear.find(), monthMatch = mMonth.find(), dayMatch = mDay.find();
        
        if(yearMatch && monthMatch && dayMatch) return true;
        return false;
    }
}
