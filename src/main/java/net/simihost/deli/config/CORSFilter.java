package net.simihost.deli.config;

/**
 * Created by Rashed on Apr,2018
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class CORSFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
        if (httpRequest.getServletPath().equals("/oauth/token")) {
			InputStream is = req.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			byte[] json = buffer.toByteArray();

			HashMap<String, String> result = new ObjectMapper().readValue(json, HashMap.class);
			HashMap<String, String[]> r = new HashMap<>();
			for (String key : result.keySet()) {
				String[] val = new String[1];
				val[0] = result.get(key);
				r.put(key, val);
			}

			String[] val = new String[1];
			val[0] = ((HttpServletRequest) req).getMethod();
			r.put("_method", val);

			HttpServletRequest s = new MyServletRequestWrapper(((HttpServletRequest) req), r);
			chain.doFilter(s, res);
		} else {

			HttpServletResponse response = (HttpServletResponse) res;
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");

			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}