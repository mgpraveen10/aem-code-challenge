package com.anf.core.servlets;

import javax.jcr.Session;
import javax.jcr.Node;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.Value;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.constants.AppConstants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import javax.jcr.NodeIterator;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;

// **** Begin code - M G Praveen **** //

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/fetchJcrQuery",
        "sling.servlet.methods=GET"
})
public class FetchJcrQuery extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchJcrQuery.class);
    @Reference
    private transient ResourceResolverFactory resourceResolverFactory;
    private transient Session session;
    private String queryStatement;
    private transient QueryManager queryManager;
    private transient Query query;
    private transient QueryResult result;
    private transient NodeIterator nodeIterator;
    private transient Node pageNode;
    private transient Value pageTitleValue;
    String pageTitle;


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        ResourceResolver resourceResolver =null;
        try {
            resourceResolver = request.getResourceResolver();
            session = resourceResolver.adaptTo(Session.class);
            // Create the SQL2 query
            queryStatement = AppConstants.QUERY;
            // Execute the query
            queryManager = session.getWorkspace().getQueryManager();
            query = queryManager.createQuery(queryStatement, Query.JCR_SQL2);
            query.setLimit(10);
            query.setOffset(0);
            result = query.execute();
            // Process the query result
            nodeIterator = result.getNodes();

            while (nodeIterator.hasNext()) {
                 pageNode = nodeIterator.nextNode();
                 pageTitleValue = pageNode.getProperty(AppConstants.PROPERTY_TITLE).getValue();
                 pageTitle = pageTitleValue.getString();
                response.getWriter().println(pageTitle);
            }
        } catch (Exception e) {
            LOGGER.info(AppConstants.JCR_ERROR, e.getMessage());
        } finally {
            if (resourceResolver != null && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
    }
}
// *** END CODE ***//