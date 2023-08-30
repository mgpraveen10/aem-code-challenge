package com.anf.core.servlets;

import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.Rendition;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.anf.core.constants.AppConstants;
import com.day.crx.JcrConstants;
import com.google.common.io.CharStreams;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author M G PRAVEEN
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Json Data in dynamic Dropdown",
        "sling.servlet.paths=" + "/bin/jsonDataDropdown", "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class JsonDataDropDownServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataDropDownServlet.class);

    transient ResourceResolver resourceResolver;
    transient Resource pathResource;
    transient ValueMap valueMap;
    transient List<Resource> resourceList;
    transient InputStream inputStream;
    transient Node jsonAsset;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        String eachLine = "";
        String jsonDataPath;
        resourceResolver = request.getResourceResolver();
        pathResource = request.getResource();
        resourceList = new ArrayList<>();
        try {
            /* Getting AEM Tags Path given on datasource Node */
            jsonDataPath = Objects.requireNonNull(pathResource.getChild(AppConstants.NODE_DATASOURCE)).getValueMap()
                    .get(AppConstants.JSON_DATAPATH, String.class);
            assert jsonDataPath != null;
            Resource jsonResource = request.getResourceResolver().getResource(jsonDataPath);
            assert jsonResource != null;
            // Getting Asset from jsonResource
            jsonAsset = jsonResource.adaptTo(Node.class);
            assert jsonAsset != null;
            // Converting Asset into input stream for JSON Object
            inputStream = jsonAsset.getProperty(AppConstants.PROPERTY_DATA).getBinary().getStream();
            StringBuilder stringBuilder = new StringBuilder();
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            while ((eachLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(eachLine);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            Iterator<String> jsonKeys = jsonObject.keys();
            // Iterating JSON Objects over key
            while (jsonKeys.hasNext()) {
                String jsonKey = jsonKeys.next();
                String jsonValue = jsonObject.getString(jsonKey);
                valueMap = new ValueMapDecorator(new HashMap<>());
                valueMap.put("value", jsonKey);
                valueMap.put("text", jsonValue);
                resourceList.add(
                        new ValueMapResource(resourceResolver, new ResourceMetadata(),
                                AppConstants.PROPERTY_UNSTRUCTURED, valueMap));
            }

            /* Create a DataSource that is used to populate the drop-down control */
            DataSource dataSource = new SimpleDataSource(resourceList.iterator());
            request.setAttribute(DataSource.class.getName(), dataSource);

        } catch (JSONException | IOException | RepositoryException e) {
            LOGGER.error("Error in Json Data Exporting : {}", e.getMessage());
        }
    }
}

// *** END CODE ***//