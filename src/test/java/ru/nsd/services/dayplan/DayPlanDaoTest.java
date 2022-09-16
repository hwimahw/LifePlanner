package ru.nsd.services.dayplan;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.nsd.models.DayPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

public class DayPlanDaoTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);

    private final DayPlanDao dayPlanDao = new DayPlanDao(jdbcTemplate);

    @Test
    public void insert() {
        DayPlan dayPlan = Mockito.mock(DayPlan.class);
        Map<String, String> dayPlanMap = Mockito.mock(HashMap.class);
        Set<Map.Entry<String, String>> entrySet = Mockito.mock(HashSet.class);
        Iterator iterator = Mockito.mock(Iterator.class);
        Map.Entry entry1 = Mockito.mock(Map.Entry.class);
        Map.Entry entry2 = Mockito.mock(Map.Entry.class);

        {
            Mockito.when(dayPlan.getUserId()).thenReturn(1L).thenReturn(2L);
            Mockito.when(dayPlan.getDate()).thenReturn(LocalDate.now());
            Mockito.when(dayPlan.getDayPlan()).thenReturn(dayPlanMap);
            Mockito.when(dayPlanMap.entrySet()).thenReturn(entrySet);
            Mockito.when(entrySet.iterator()).thenReturn(iterator);
            Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
            Mockito.when(iterator.next()).thenReturn(entry1).thenReturn(entry2);
            Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyLong(),
                    Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1);
        }

        dayPlanDao.insert(dayPlan);

        {
            Mockito.verify(jdbcTemplate, times(2)).update(anyString(),
                    Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any());
        }
    }

    @Test
    public void select() {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> expected = new ArrayList<>();
        expected.add(map);

        {
            Mockito.when(jdbcTemplate.query(anyString(), Mockito.<ResultSetExtractor>any(), Mockito.anyLong()))
                    .thenReturn(expected);
        }

        List<Map<String, String>> actual = dayPlanDao.select(1L);

        {
            assertTrue(actual.size() == expected.size()
                    && actual.containsAll(expected) && actual.containsAll(expected));
            Mockito.verify(jdbcTemplate, times(1)).query(anyString(), Mockito.<ResultSetExtractor>any(), Mockito.anyLong());
        }
    }
}
