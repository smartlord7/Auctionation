package Startup;

import Helpers.config.AuthHelper;
import Layers.PresentationLayer.Controllers.AuctionController;
import Layers.PresentationLayer.Controllers.BidController;
import Layers.PresentationLayer.Controllers.NotificationController;
import Layers.PresentationLayer.Controllers.UserController;
import com.jayway.jsonpath.Configuration;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static Helpers.config.AuthHelper.TOKEN_HEADER_KEY_NAME;

public class CustomRequestInterceptor implements HandlerInterceptor {

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest requestServlet, @NotNull HttpServletResponse responseServlet, Object handler) throws Exception {

        String requestURI = requestServlet.getRequestURI(), customToken;

        if (requestURI.endsWith("login") || requestURI.endsWith("login/")) {
            return true;
        }

        Map<String, String> headers = getHeadersInfo(requestServlet);

        if (!headers.containsKey(TOKEN_HEADER_KEY_NAME)) {
            responseServlet.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        customToken = headers.get(TOKEN_HEADER_KEY_NAME);

        if (!AuthHelper.sessions.containsKey(customToken)) {
            responseServlet.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        return true;
    }
}
