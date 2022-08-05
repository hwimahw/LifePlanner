package ru.nsd.controlers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import ru.nsd.LifePlan;
import ru.nsd.Noda;
import ru.nsd.models.DayPlan;
import ru.nsd.models.LifeDirection;
import ru.nsd.models.User;
import ru.nsd.services.LifePlanCycleService;
import ru.nsd.services.dayplan.DayPlanService;
import ru.nsd.services.lifeDirection.LifeDirectionService;
import ru.nsd.services.user.UserService;
import ru.nsd.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;
import static ru.nsd.utils.Utils.buildDate;

@Controller
public class LifePlanController {

    private final LifePlanCycleService lifePlanCycleService;
    private final LifeDirectionService lifeDirectionService;
    private final UserService userService;
    private final DayPlanService dayPlanService;

    public LifePlanController(LifePlanCycleService lifePlanCycleService, LifeDirectionService lifeDirectionService, UserService userService, DayPlanService dayPlanService) {
        this.lifePlanCycleService = lifePlanCycleService;
        this.lifeDirectionService = lifeDirectionService;
        this.userService = userService;
        this.dayPlanService = dayPlanService;
    }

    @PostMapping("logIn")
    public String logIn(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (!hasText(login) || !hasText(password)) {
            request.setAttribute("error", "Such user doesn't exist");
            return "logInPage";

        }
        User user = userService.get(new User(login, password));
        if (isNull(user)) {
            request.setAttribute("error", "Such user doesn't exist");
            return "logInPage";
        } else {
            request.getSession().setAttribute("login", user.getLogin());
            request.getSession().setAttribute("password", user.getPassword());
            request.getSession().setAttribute("userId", user.getId());
            List<LifeDirection> lifeDirections = lifeDirectionService.get(user.getId());
            LifePlan lifePlan = lifePlanCycleService.prepareLifeDirectionsToLifePlan(lifeDirections);
            request.getSession().setAttribute("lifePlan", lifePlan);
            request.getSession().setAttribute("leaves", lifePlan.getLeaves());
            return "redirect:/lifePlanInput";
        }

    }

    @GetMapping("logInPage")
    public String logInPage() {
        return "logInPage";
    }

    @PostMapping("register")
    public String register(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null && password != null) {
            if (userService.get(new User(login, password)) != null) {
                request.setAttribute("error", "User already exists");
                return "registerPage";
            } else {
                userService.add(new User(login, password));
                request.setAttribute("success", "Registration was successful");
                return "logInPage";
            }
        }
        return "registerPage";
    }

    @GetMapping("registerPage")
    public String registerPage() {
        return "registerPage";
    }

    @GetMapping("lifePlanInput")
    public String lifePlanInput() {
        return "lifePlanInput";
    }

    @GetMapping("exit")
    public String exit(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/logInPage";
    }

    @PostMapping("uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getSession().getAttribute("userId");
            InputStream fileContent = file.getInputStream();
            LifePlan lifePlan = lifePlanCycleService.createLifePlan(fileContent);
            List<LifeDirection> lifeDirections = lifePlanCycleService.prepareLifePlanToLifeDirections(lifePlan, userId);
            lifeDirectionService.delete(userId);
            lifeDirectionService.add(lifeDirections);
            model.addAttribute("leaves", lifePlan.getLeaves());
            model.addAttribute("lifePlan", lifePlan);
            return "lifePlanInput";
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    @PostMapping("setDayPlan")
    public String setDayPlan(HttpServletRequest request) {
        return setLeafPlan(request);
    }

    @PostMapping("save")
    public String save(HttpServletRequest request) {
        DayPlan dayPlan = (DayPlan) request.getAttribute("dayPlan");
        dayPlanService.insert(dayPlan);
        List<String> leaves = (List<String>) request.getSession().getAttribute("leaves");
        request.setAttribute("leaves", leaves);
        return "lifePlanInput";
    }

    @GetMapping("get")
    public void get(HttpServletRequest request, HttpServletResponse response) {
        createFile(request, response);
        downloadFile(response);
    }

    protected String setLeafPlan(HttpServletRequest request) {
        List<Noda> leaves = (List) request.getSession().getAttribute("leaves");
        DayPlan dayPlan = buildDayPlan(request, leaves);
        request.setAttribute("dayPlan", dayPlan);
//        request.setAttribute(
//                View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return "forward:/save";
    }

    private DayPlan buildDayPlan(HttpServletRequest request, List<Noda> leaves) {
        Map<String, String> dayPlanMap = new HashMap<>();
        Long userId = (Long) request.getSession().getAttribute("userId");
        LocalDate date = Utils.buildDate(request.getParameter("date"));
        for (Noda leave : leaves) {
            String subject = leave.getName();
            String plan = request.getParameter(subject);
            if (hasText(plan)) {
                dayPlanMap.put(subject, plan);
            }
        }
        return new DayPlan(userId, date, dayPlanMap);
    }

    private void createFile(HttpServletRequest request, HttpServletResponse response) {
        LifePlan lifePlan = (LifePlan) request.getSession().getAttribute("lifePlan");
        Long userId = (Long) request.getSession().getAttribute("userId");
//        if (lifePlan == null) {
//            File file = new File("out.txt");
//            downloadFile(response);
//            return;
//        }
        List<Map<String, String>> dayPlans = dayPlanService.select(userId);
        for (Map<String, String> dayPlan : dayPlans) {
            lifePlan.fillNonVisitNodes();
            DayPlan dayPlanModel = new DayPlan(buildDate(dayPlan.get("DATE")), dayPlan);
            lifePlan.fillPlanOfLeaves(dayPlanModel);
            lifePlan.fillVisitNodesForPrinting();
            lifePlan.printDayPlanToFile(dayPlanModel);
        }
        downloadFile(response);
    }

    private void downloadFile(HttpServletResponse response) {
        String filePath = "out.txt";
        File downloadFile = new File(filePath);

        try (FileInputStream inStream = new FileInputStream(downloadFile);
             OutputStream outStream = response.getOutputStream()) {

//            String mimeType = getServletContext().getMimeType(filePath);
//            if (mimeType == null) {
//                mimeType = "application/octet-stream";
//            }
//            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            byte[] byteArray = new byte[4096];
            int bytesRead;
            while ((bytesRead = inStream.read(byteArray)) != -1) {
                outStream.write(byteArray, 0, bytesRead);
            }
        } catch (IOException ex) {
            downloadFile.delete();
            throw new RuntimeException();
        }
        downloadFile.delete();
    }

}
