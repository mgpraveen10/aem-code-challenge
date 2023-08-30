package com.anf.core.servlets;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import javax.servlet.Servlet;
import com.day.cq.search.QueryBuilder;
import com.anf.core.constants.AppConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;
import javax.jcr.Session;

// **** Begin code - M G Praveen **** //

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Json Data in dynamic Dropdown",
        "sling.servlet.paths=" + "/bin/fetchquerybuilder", "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class FetchQueryBuilder extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchQueryBuilder.class);

    @Reference
    private transient QueryBuilder queryBuilder;
    private transient Query query;
    private transient SearchResult result;
    private transient Resource resource;
    private transient String pageTitle;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        // Specify the query parameters
        Map<String, String> queryParameter = new LinkedHashMap<>();
        queryParameter.put(AppConstants.PATH, AppConstants.PAGE_BASE_PATH);
        queryParameter.put(AppConstants.PROPERTY, AppConstants.PROPERTY_VALUE);
        queryParameter.put(AppConstants.PROPERTY_OPERATION, AppConstants.PROPERTY_EXIST);
        queryParameter.put(AppConstants.PROPERTY_LIMIT, AppConstants.PROPERTY_LIMIT_VALUE);

        // Build the query
        query = queryBuilder.createQuery(PredicateGroup.create(queryParameter),
                request.getResourceResolver().adaptTo(Session.class));
        result = query.getResult();

        try {

            // Process the search results and write the page titles
            for (Hit hit : result.getHits()) {
                resource = hit.getResource();
                pageTitle = resource.getValueMap().get(AppConstants.PROPERTY_TITLE, String.class);
                response.getWriter().println(pageTitle);
            }

        } catch (Exception e) {
            LOGGER.info(AppConstants.QUERYBUILDER_ERROR, e.getMessage());

        }
    }

}
// *** END CODE ***//