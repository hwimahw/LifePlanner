package ru.nsd.services.dayplan;

import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class DayPlansResultSetExtractorTest {

    DayPlansResultSetExtractor dayPlansResultSetExtractor = new DayPlansResultSetExtractor();

    @Test
    public void doFilterRegisterPageRequest() throws Exception {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        List<Map<String, String>> dayPlansExpected = new ArrayList<>();
        Map<String, String> dayPlan = new HashMap<>();
        dayPlan.put("DATE", "2022-08-22");
        dayPlan.put("Programming", "plan5");
        dayPlan.put("Algorithms", "plan4");
        dayPlansExpected.add(dayPlan);

        {
            Mockito.when(resultSet.getString(1)).thenReturn("2022-08-22");
            Mockito.when(resultSet.getString(2)).thenReturn("Programming")
                    .thenReturn("Algorithms").thenReturn("Programming").thenReturn("Algorithms").thenReturn("Programming");
            Mockito.when(resultSet.getString(3)).thenReturn("plan1").thenReturn("plan2").thenReturn("plan3")
                    .thenReturn("plan4").thenReturn("plan5");
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        }

        List<Map<String, String>> dayPlansActual = dayPlansResultSetExtractor.extractData(resultSet);

        {
            assertTrue(dayPlansActual.size() == dayPlansExpected.size()
                    && dayPlansActual.containsAll(dayPlansExpected) && dayPlansActual.containsAll(dayPlansExpected));
        }
    }
}
