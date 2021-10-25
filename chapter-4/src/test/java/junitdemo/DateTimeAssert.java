package junitdemo;

import org.apache.commons.lang3.NotImplementedException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeAssert {
    public static void assertEquals(LocalDateTime expectedDate, LocalDateTime actualDate, DateTimeDeltaType deltaType, int count) throws Exception {
        if (((expectedDate == null) && (actualDate == null))) {
            return;
        }
        else if ((expectedDate == null)) {
            throw new NullPointerException("The expected date was null");
        }
        else if ((actualDate == null)) {
            throw new NullPointerException("The actual date was null");
        }

        Duration expectedDelta = DateTimeAssert.getTimeSpanDeltaByType(deltaType, count);

        double totalSecondsDifference = Math.abs((actualDate.until(expectedDate, ChronoUnit.SECONDS)));

        if ((totalSecondsDifference > expectedDelta.getSeconds())) {
            var exceptionMessage =String.format("Expected Date: {0}, Actual Date: {1} \nExpected Delta: {2}, Actual Delta in seconds- {3} (Delta Type: " +
                    "{4})", expectedDate, actualDate, expectedDelta, totalSecondsDifference, deltaType);
            throw new Exception(exceptionMessage);
        }

    }

    private static Duration getTimeSpanDeltaByType(DateTimeDeltaType type, int count) {
        Duration result;
        switch (type) {
            case DAYS:
                result = Duration.ofDays(count);
                break;
            case MINUTES:
                result = Duration.ofMinutes(count);
                break;
            default:
                throw new NotImplementedException("The delta type is not implemented.");
        }
        return result;
    }
}
