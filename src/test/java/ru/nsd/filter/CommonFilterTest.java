package ru.nsd.filter;


import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.times;

public class CommonFilterTest {

    private final CommonFilter commonFilter = new CommonFilter();

    @Test
    public void doFilterRegisterPageRequest() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String requestURI = "/registerPage";

        {
            Mockito.when(request.getSession()).thenReturn(session);
            Mockito.when(request.getRequestURI()).thenReturn(requestURI);
            Mockito.when(session.getAttribute("login")).thenReturn("login");
            Mockito.when(session.getAttribute("password")).thenReturn("password");
            Mockito.doNothing().when(response).sendRedirect("/lifePlanInput");
        }

        commonFilter.doFilter(request, response, filterChain);

        {
            Mockito.verify(session, times(1)).getAttribute("login");
            Mockito.verify(session, times(1)).getAttribute("password");
            Mockito.verify(response, times(1)).sendRedirect("/lifePlanInput");
        }
    }

    @Test
    public void doFilterRegisterRequest() {
        try {
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            FilterChain filterChain = Mockito.mock(FilterChain.class);
            HttpSession session = Mockito.mock(HttpSession.class);
            String requestURI = "/register";

            {
                Mockito.when(request.getSession()).thenReturn(session);
                Mockito.when(request.getRequestURI()).thenReturn(requestURI);
                Mockito.when(session.getAttribute("login")).thenReturn("login");
                Mockito.when(session.getAttribute("password")).thenReturn("password");
                Mockito.doNothing().when(response).sendRedirect("/lifePlanInput");
            }

            commonFilter.doFilter(request, response, filterChain);

            {
                Mockito.verify(session, times(1)).getAttribute("login");
                Mockito.verify(session, times(1)).getAttribute("password");
                Mockito.verify(response, times(1)).sendRedirect("/lifePlanInput");
            }

        } catch (Exception ex) {

        }
    }

    @Test
    public void doFilterLogInPageRequest() {
        try {
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            FilterChain filterChain = Mockito.mock(FilterChain.class);
            HttpSession session = Mockito.mock(HttpSession.class);
            String requestURI = "/logInPage";

            {
                Mockito.when(request.getSession()).thenReturn(session);
                Mockito.when(request.getRequestURI()).thenReturn(requestURI);
                Mockito.when(session.getAttribute("login")).thenReturn("login");
                Mockito.when(session.getAttribute("password")).thenReturn("password");
                Mockito.doNothing().when(response).sendRedirect("/lifePlanInput");
            }

            commonFilter.doFilter(request, response, filterChain);

            {
                Mockito.verify(session, times(1)).getAttribute("login");
                Mockito.verify(session, times(1)).getAttribute("password");
                Mockito.verify(response, times(1)).sendRedirect("/lifePlanInput");
            }

        } catch (Exception ex) {

        }
    }

    @Test
    public void doFilterLogInRequest() {
        try {
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            FilterChain filterChain = Mockito.mock(FilterChain.class);
            HttpSession session = Mockito.mock(HttpSession.class);
            String requestURI = "/logIn";

            {
                Mockito.when(request.getSession()).thenReturn(session);
                Mockito.when(request.getRequestURI()).thenReturn(requestURI);
                Mockito.when(session.getAttribute("login")).thenReturn("login");
                Mockito.when(session.getAttribute("password")).thenReturn("password");
                Mockito.doNothing().when(response).sendRedirect("/lifePlanInput");
            }

            commonFilter.doFilter(request, response, filterChain);

            {
                Mockito.verify(session, times(1)).getAttribute("login");
                Mockito.verify(session, times(1)).getAttribute("password");
                Mockito.verify(response, times(1)).sendRedirect("/lifePlanInput");
            }

        } catch (Exception ex) {

        }
    }

    @Test
    public void doFilterChainDoFilter() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        String requestURI = "/logInPage";

        {
            Mockito.when(request.getSession()).thenReturn(null);
            Mockito.when(request.getRequestURI()).thenReturn(requestURI);
        }

        commonFilter.doFilter(request, response, filterChain);

        {
            Mockito.verify(filterChain, times(1)).doFilter(request, response);
        }
    }

    @Test
    public void doFilterChainDoFilterWithOtherRequest() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String requestURI = "/other";

        {
            Mockito.when(request.getSession()).thenReturn(session);
            Mockito.when(request.getRequestURI()).thenReturn(requestURI);
            Mockito.when(session.getAttribute("login")).thenReturn("login");
            Mockito.when(session.getAttribute("password")).thenReturn("password");
        }

        commonFilter.doFilter(request, response, filterChain);

        {
            Mockito.verify(filterChain, times(1)).doFilter(request, response);
            Mockito.verify(session, times(1)).getAttribute("login");
            Mockito.verify(session, times(1)).getAttribute("password");
        }
    }

    @Test
    public void doFilterSendRedirectWithOtherRequest() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        String requestURI = "/other";

        {
            Mockito.when(request.getSession()).thenReturn(null);
            Mockito.when(request.getRequestURI()).thenReturn(requestURI);
        }

        commonFilter.doFilter(request, response, filterChain);

        {
            Mockito.verify(filterChain, times(0)).doFilter(request, response);
            Mockito.verify(response, times(1)).sendRedirect("/logInPage");
        }
    }
}
