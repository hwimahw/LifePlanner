import org.apache.commons.io.IOUtils;
import org.junit.Test;
import ru.nsd.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.Noda;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LifePlanTest {
    @Test
    public void buildLifePlan() throws FileNotFoundException {
        LifePlan lifePlan;
        try {
            InputStream inputStream = new FileInputStream("src/test/resources/ru/nsd/lifePlan.xml");
            lifePlan = new LifePlan(inputStream);
        } catch (FileNotFoundException ex) {
            throw ex;
        }
        assertNotNull(lifePlan.getRoot());
        assertEquals("Self-development", lifePlan.getRoot().getName());
    }

    @Test
    public void fillPlanOfLeaves() throws IOException {
        LifePlan lifePlan;
        try {
            InputStream inputStream = new FileInputStream("src/test/resources/ru/nsd/lifePlan.xml");
            lifePlan = new LifePlan(inputStream);
        } catch (FileNotFoundException ex) {
            throw ex;
        }
        Map<String, String> dayPlanMap = new HashMap<>();
        dayPlanMap.put("DATA", "01.01.2021");
        dayPlanMap.put("ALGORITHMS", "plan1");
        dayPlanMap.put("PHYSIQUES", "plan2");
        dayPlanMap.put("OTHER", "plan3");
        DayPlan dayPlan = new DayPlan(dayPlanMap);
        lifePlan.fillPlanOfLeaves(dayPlan);
        List<Noda> leaves = lifePlan.getLeaves();
        Noda leafAlgo = leaves.get(1);
        Noda leafPhys = leaves.get(2);
        Noda leafOther = leaves.get(4);

        assertEquals("plan1", leafAlgo.getPlan());
        assertEquals("plan2", leafPhys.getPlan());
        assertEquals("plan3", leafOther.getPlan());
    }

    @Test
    public void addDayPlan() throws IOException {
        LifePlan lifePlan;
        try {
            InputStream inputStream = new FileInputStream("src/test/resources/ru/nsd/lifePlan.xml");
            lifePlan = new LifePlan(inputStream);
        } catch (FileNotFoundException ex) {
            throw ex;
        }
        Map<String, String> dayPlanMap = new HashMap<>();
        dayPlanMap.put("DATA", "01.01.2021");
        dayPlanMap.put("ALGORITHMS", "plan1");
        dayPlanMap.put("PHYSIQUES", "plan2");
        dayPlanMap.put("OTHER", "plan3");
        DayPlan dayPlan = new DayPlan(dayPlanMap);
        lifePlan.addDayPlan(dayPlan, dayPlanMap.get("DATA"));
        InputStream inputStream;
        String outContentExpected;
        String outContentActual;
        try {
            inputStream = new FileInputStream("src/test/resources/ru/nsd/outTest.txt");
            outContentExpected = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
            inputStream = new FileInputStream("out.txt");
            outContentActual = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            throw ex;
        }
        File file = new File("out.txt");
        file.delete();
        assertEquals(outContentExpected, outContentActual);

    }
}
