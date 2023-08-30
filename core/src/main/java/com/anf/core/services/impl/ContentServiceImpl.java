package com.anf.core.services.impl;

import com.anf.core.constants.AppConstants;
import com.anf.core.services.ContentService;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.crx.JcrConstants;
import java.util.HashMap;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.resource.Resource;


@Component(immediate = true, service = ContentService.class)
public class ContentServiceImpl implements ContentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);
    private static final String STORING_PATH = "/var/anf-code-challenge";
    private static final String USER_FIRST_NAME_KEY = "firstName";
    private static final String USER_LAST_NAME_KEY = "lastName";
    private static final String USER_AGE_KEY = "age";
    private static final String USER_COUNTRY_KEY = "country";
    private static final String PROP_NAME_F_NAME = "firstName";
    private static final String PROP_NAME_L_NAME = "lastName";
    private static final String PROP_NAME_AGE = "age";
    private static final String PROP_NAME_COUNTRY = "country";
    private static final String AGE_RESOURCE_PATH = "/etc/age";

   
   ResourceResolver getResource;
   @Reference
   ResourceResolverFactory resourceResolverFactory;
   Session session;
    @Override
    public String commitUserDetails(JSONObject userdetails) {
        String response = null;
        try{
        Map<String, Object> serviceUserMap = new HashMap<>();
            serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, AppConstants.SUB_SERVICE);
            getResource = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
            

            session = getResource.adaptTo(Session.class);
            Node node = JcrUtil.createPath(STORING_PATH,
             JcrConstants.NT_UNSTRUCTURED, session);
            node.setProperty(PROP_NAME_F_NAME, userdetails.get(USER_FIRST_NAME_KEY).toString());
            node.setProperty(PROP_NAME_L_NAME, userdetails.get(USER_LAST_NAME_KEY).toString());
            node.setProperty(PROP_NAME_AGE, userdetails.get(USER_AGE_KEY).toString());
            node.setProperty(PROP_NAME_COUNTRY, userdetails.get(USER_COUNTRY_KEY).toString());
            session.save();
            response = AppConstants.USER_RESPONSE;
        } catch (Exception e) {
            LOGGER.error(AppConstants.USER_ERROR, e.getMessage());
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
            if (getResource != null && getResource.isLive()) {
                getResource.close();
            }
        }

        return response;
    }

    @Override
    public boolean validateUserAge(int age) {
        
                
        try {
          
            Map<String, Object> serviceUserMap = new HashMap<>();
            serviceUserMap.put(ResourceResolverFactory.SUBSERVICE,AppConstants.SUB_SERVICE);
            ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
            Resource resource = resourceResolver.getResource(AGE_RESOURCE_PATH);
            if (resource == null) {
                LOGGER.info(AGE_RESOURCE_PATH);
                return false;
            }
            Node node = resource.adaptTo(Node.class);
            int minAge =Integer.parseInt(node.getProperty(AppConstants.MIN_AGE).getString());
            int maxAge =Integer.parseInt(node.getProperty(AppConstants.MAX_AGE).getString());  
            if (age >= minAge && age <= maxAge) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.info(AppConstants.AGE_ERROR, e.getMessage());
            return false;
        }
        
    }

}

   
