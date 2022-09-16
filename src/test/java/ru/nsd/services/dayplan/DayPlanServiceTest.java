package ru.nsd.services.dayplan;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.nsd.models.DayPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

public class DayPlanServiceTest {

    private final DayPlanDao dayPlanDao = Mockito.mock(DayPlanDao.class);

    private final DayPlanService dayPlanService = new DayPlanService(dayPlanDao);

    @Test
    public void insert() {
        DayPlan dayPlan = Mockito.mock(DayPlan.class);

        {
            Mockito.doNothing().when(dayPlanDao).insert(dayPlan);
        }

        dayPlanService.insert(dayPlan);

        {
            Mockito.verify(dayPlanDao, times(1)).insert(dayPlan);
        }
    }

    @Test
    public void select() {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> expected = new ArrayList<>();
        expected.add(map);
        {
            Mockito.when(dayPlanDao.select(1L)).thenReturn(expected);
        }

        List<Map<String, String>> actual = dayPlanService.select(1L);

        {
            Mockito.verify(dayPlanDao, times(1)).select(1L);
            assertTrue(actual.size() == expected.size() && actual.containsAll(expected) && actual.containsAll(expected));
        }
    }


}
