package pl.nask.crs.app.utils;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

public class JsonResponse {
    public static String OK() throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(Collections.singletonMap("status", "ok"));
        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("application/json");
        response.getWriter().write(jsonString);
        return null;
    }
}
