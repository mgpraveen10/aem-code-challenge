
package com.anf.core.services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.anf.core.constants.AppConstants;

import com.anf.core.services.NewsFeedService;

// **** Begin code - M G Praveen **** //

@Component(service = NewsFeedService.class, immediate = true)
public class NewsFeedServiceImpl implements NewsFeedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsFeedServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;
    ResourceResolver resourceResolver;
    Resource resource;
    Node resourceNode;
    NodeIterator nodeIterator;
    PropertyIterator propertyIterator;
    Property property;

    @Override
    public List<Map<String, String>> getNewsData() {
        List<Map<String, String>> newsDataList = new LinkedList<>();
        try {
            Map<String, Object> serviceUserMap = new HashMap<>();
            serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, AppConstants.SUB_SERVICE);
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
            resource = resourceResolver.getResource(AppConstants.NEWSFEED_BASE_PATH);
            resourceNode = resource.adaptTo(Node.class);
            nodeIterator = resourceNode.getNodes();
            while (nodeIterator.hasNext()) {
                Node nNode = nodeIterator.nextNode();
                propertyIterator = nNode.getProperties();
                while (propertyIterator.hasNext()) {
                    Map<String, String> newsDataMap = new HashMap<>();
                    property = propertyIterator.nextProperty();
                    newsDataMap.put(property.getName(), property.getValue().getString());
                    newsDataList.add(newsDataMap);
                }
            }
        } catch (Exception e) {
            LOGGER.error(AppConstants.NEWSFEED_ERROR, e.getMessage());
        }

        return newsDataList;

    }

}

// *** END CODE ***//