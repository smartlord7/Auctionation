package Startup;

import Helpers.config.AuthHelper;
import Helpers.config.Authorization;
import Helpers.config.ClassHelper;
import Helpers.config.UserSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static Helpers.config.AuthHelper.TOKEN_HEADER_KEY_NAME;
import static Helpers.config.AuthHelper.sessions;

/**
 * Class to implement a custom request interceptor.
 */
public class CustomRequestInterceptor implements HandlerInterceptor {

    private static Iterable<Class<?>> controllers = null;

    public CustomRequestInterceptor() throws ClassNotFoundException {
        controllers = ClassHelper.getClasses("Layers.PresentationLayer.Controllers");
    }

    /**
     * Function that retrieves the headers of a certain request.
     * @param request Request.
     * @return Headers retrieved.
     */
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

    /**
     * Function that checks if an endpoint has authorization.
     * @param endpointURI URI of the endpoint.
     * @param reqMethod Request method.
     * @param roleId ID of the role.
     * @return Return true if it has the authorization.
     */
    private static boolean isAuthorizedEndpoint(String endpointURI, String reqMethod, long roleId) {
        String totalURI;
        RequestMapping reqMap;
        UriTemplate uriTemplate;
        Authorization auth;
        int[] allowedRoles;

        for (Class<?> controller : controllers) {
            if (!controller.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            for (Method endpoint : controller.getDeclaredMethods()) {
                if (endpoint.isAnnotationPresent(RequestMapping.class) && endpoint.isAnnotationPresent(Authorization.class)) {
                    reqMap = endpoint.getAnnotation(RequestMapping.class);
                    totalURI = controller.getAnnotation(RequestMapping.class).value()[0] + reqMap.value()[0];

                    uriTemplate  = new UriTemplate(totalURI);

                    if (!uriTemplate.matches(endpointURI) || !reqMap.method()[0].toString().equals(reqMethod)) {
                        continue;
                    }

                    auth = endpoint.getAnnotation(Authorization.class);

                    if (auth.allowAnonymous()) {
                        return true;
                    }

                    allowedRoles = auth.roles();

                    for (int role : allowedRoles) {
                        if (role == roleId) {
                            return true;
                        }
                    }

                    return false;
                }
            }
        }

        return false;
    }

    /**
     * Function that prehandles a request.
     * @param requestServlet Request servlet.
     * @param responseServlet Response servlet.
     * @param handler Handler to the request.
     * @return True if the request if the request is authorized.
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest requestServlet, @NotNull HttpServletResponse responseServlet, Object handler){

        String requestURI = requestServlet.getRequestURI(), customToken = null,
                reqMethod = requestServlet.getMethod();
        UserSession session;
        Map<String, String> headers;

        boolean authorized;

        headers = getHeadersInfo(requestServlet);
        authorized = headers.containsKey(TOKEN_HEADER_KEY_NAME);

        if (authorized) {
            customToken = headers.get(TOKEN_HEADER_KEY_NAME);
            authorized = AuthHelper.sessions.containsKey(customToken);
        }
        if (authorized) {
            session = sessions.get(customToken);
            authorized = isAuthorizedEndpoint(requestURI, reqMethod, session.getRoleId());
        } else {
            authorized = isAuthorizedEndpoint(requestURI, reqMethod, -1);
        }

        if (!authorized) {
            responseServlet.setStatus(HttpStatus.FORBIDDEN.value());
        }

        return authorized;
    }
}
