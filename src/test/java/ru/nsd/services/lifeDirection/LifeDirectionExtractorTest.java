package ru.nsd.services.lifeDirection;

import org.junit.Test;
import org.mockito.Mockito;
import ru.nsd.models.LifeDirection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class LifeDirectionExtractorTest {

    private final LifeDirectionExtractor lifeDirectionExtractor = new LifeDirectionExtractor();

    @Test
    public void extractData() throws Exception {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        List<LifeDirection> lifeDirectionsExpected = new ArrayList<>();
        lifeDirectionsExpected.add(new LifeDirection(1, 1, 1, "1", 1, 1));
        lifeDirectionsExpected.add(new LifeDirection(2, 2, 2, "2", 2, 2));

        {
            Mockito.when(resultSet.getLong(1)).thenReturn(1l).thenReturn(2l);
            Mockito.when(resultSet.getLong(2)).thenReturn(1l).thenReturn(2l);
            Mockito.when(resultSet.getInt(3)).thenReturn(1).thenReturn(2);
            Mockito.when(resultSet.getString(4)).thenReturn("1").thenReturn("2");
            Mockito.when(resultSet.getInt(5)).thenReturn(1).thenReturn(2);
            Mockito.when(resultSet.getInt(6)).thenReturn(1).thenReturn(2);
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        }

        List<LifeDirection> lifeDirectionsActual = lifeDirectionExtractor.extractData(resultSet);

        {
            assertTrue(lifeDirectionsActual.size() == lifeDirectionsExpected.size()
                    && lifeDirectionsActual.containsAll(lifeDirectionsExpected) && lifeDirectionsActual.containsAll(lifeDirectionsExpected));
        }
    }
}
