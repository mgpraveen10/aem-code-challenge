
package com.anf.core.servlets;

import com.anf.core.constants.AppConstants;
import com.anf.core.services.ContentService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

// **** Begin code - M G Praveen **** //

@Component(service = { Servlet.class })
@SlingServletPaths(value = "/bin/saveUserDetails")
public class UserServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
     private static Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Reference
    private transient ContentService contentService;

  
@Override
     protected void doPost(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
   
        PrintWriter out =resp.getWriter();
        BufferedReader bufferedReader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String eachLine;

        while ((eachLine = bufferedReader.readLine()) != null) 
        {
            stringBuilder.append(eachLine);
        }
        try {
            JSONObject json = new JSONObject(stringBuilder.toString());
            if (contentService.validateUserAge((int) json.get("age"))) {
                out.print(new JSONObject(
                        "{'successMessage':'" + contentService.commitUserDetails(json) + "'}"));
            } else {
                out.print(new JSONObject(AppConstants.VALIDATE_AGE_ERROR));
            }

        } catch (Exception e) {
            LOGGER.error(AppConstants.SERVLET_ERROR, e.getMessage());
        }

    }

            }

// *** END CODE ***//
