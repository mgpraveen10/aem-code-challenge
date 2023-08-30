package com.anf.core.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.anf.core.services.NewsFeedService;

// **** Begin code - M G Praveen **** //

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class News {

    private static final Logger LOGGER = LoggerFactory.getLogger(News.class);

    @OSGiService
    NewsFeedService newsFeedService;

    public List<Map<String, String>> getMessage() {
        List<Map<String, String>> newsFeeds = new LinkedList<>();
        try {
            newsFeeds = newsFeedService.getNewsData();

        } catch (Exception e) {
            Map<String, String> newsMap = new HashMap<>();
            newsMap.put("error", e.getMessage());
            newsFeeds = new LinkedList<>();
            newsFeeds.add(newsMap);
            LOGGER.info("NewsFeed error : {}", e.getMessage());
            return newsFeeds;
        }
        return newsFeeds;
    }
}

// *** END CODE ***//