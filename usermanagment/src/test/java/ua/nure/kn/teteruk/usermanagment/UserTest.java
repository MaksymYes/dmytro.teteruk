package ua.nure.kn.teteruk.usermanagment;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class UserTest {

    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Petrov";

    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void should_returnFullName() {
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        String expectedResult = new StringBuilder(LAST_NAME).append(", ").append(FIRST_NAME).toString();

        assertEquals(expectedResult, user.getFullName());
    }

    @Test
    public void should_returnAge_whenBirthDateIsLessThenCurrentDate() throws ParseException {
        Date someBirthDate = new SimpleDateFormat("d-MM-yyyy").parse("10-01-1988");
        int expectedAge = Period.between(someBirthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()).getYears();
        user.setDateOfBirth(someBirthDate);

        assertEquals(expectedAge, user.getAge());
    }

    @Test
    public void should_returnAge_whenBirthDateIsBiggerThenCurrentDate() throws ParseException {
        Date someBirthDate = new SimpleDateFormat("d-MM-yyyy").parse("31-12-1988");
        int expectedAge = Period.between(someBirthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()).getYears();
        user.setDateOfBirth(someBirthDate);

        assertEquals(expectedAge, user.getAge());
    }

    @Test
    public void should_returnAge_whenBirthDateIsEqualsToCurrentDate() {
        int expectedAge = 0;
        user.setDateOfBirth(new Date());

        assertEquals(expectedAge, user.getAge());
    }
}