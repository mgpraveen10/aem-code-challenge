package com.anf.core.listeners;

import java.util.HashMap;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.anf.core.constants.AppConstants;

// **** Begin code - M G Praveen **** //

@Component(service = EventListener.class, immediate = true)
public class PageCreationListener implements EventListener {

    private static final Logger log = LoggerFactory.getLogger(PageCreationListener.class);
    @Reference
    private ResourceResolverFactory resolverFactory;
    
     ResourceResolver resolver;
     Node pageContentNode;
     Session session;

    @Activate
    protected void activate(ComponentContext componentContext) {

        try {

            Map<String, Object> parameter = new HashMap<>();
            parameter.put(ResourceResolverFactory.SUBSERVICE, AppConstants.SUB_SERVICE);
            resolver = resolverFactory.getServiceResourceResolver(parameter);
            session = resolver.adaptTo(Session.class);
            session.getWorkspace().getObservationManager().addEventListener(this,
                    Event.NODE_ADDED,
                    AppConstants.PAGE_CREATION_PATH, true, null, null, false);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
        }
    }

    @Deactivate
    protected void deactivate() {

        if (session != null) {
            session.logout();
        }
    }

    @Override
    public void onEvent(EventIterator events) {
             String path;

        try {

            while (events.hasNext()) {
                path = events.nextEvent().getPath();
                session.getNode(path);
                if (path.endsWith(AppConstants.JCR_PATH_CHECK)) {
                    pageContentNode = session.getNode(path.substring(0, path.lastIndexOf("/")));
                    pageContentNode.setProperty(AppConstants.PROPERTY_PAGE_CREATED,
                            AppConstants.PROPERTY_BOOLEAN_VALUE);
                    pageContentNode.getSession().save();
                }
            }
        } catch (Exception e) {

            log.error(AppConstants.PAGECREATION_ERROR, e.getMessage());
        }
    }

}

// *** END CODE ***//