package net.simihost.deli.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashed on May,2018
 */
public class MyServletRequestWrapper extends HttpServletRequestWrapper {
    private final HashMap<String, String[]> params;

    public MyServletRequestWrapper(HttpServletRequest request, HashMap<String, String[]> params) {
        super(request);
        this.params = params;
    }

    @Override
    public String getParameter(String name) {
        if (this.params.containsKey(name)) {
            return this.params.get(name)[0];
        }
        return "";
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }
}