package com.anf.core.services.Impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.anf.core.services.impl.NewsFeedServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * NewsFeedModelTest class
 * 
 * @author M G Praveen
 *
 */
@ExtendWith(AemContextExtension.class)
class NewsFeedImplTest {

    private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);
    private static final String TEST_VAR_PATH = "/var/commerce/products/anf-code-challenge/newsData";
    NewsFeedServiceImpl unitTest = new NewsFeedServiceImpl();

    @Test
    void testGetNewsFeed() {
        aemContext.create().resource(TEST_VAR_PATH);
        aemContext.create().resource("/var/commerce/products/anf-code-challenge/newsData/news_0",
                "jcr:title", "News Data", "author", "Carolina Fox");
        aemContext.create().resource("/var/commerce/products/anf-code-challenge/newsData/news_1",
                "jcr:title", "News Data");
        aemContext.create().resource("/var/commerce/products/anf-code-challenge/newsData/news_2",
                "jcr:title", "News Data");
        List<Map<String, String>> result = unitTest.getNewsData();
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals("News Data", result.get(0).get("jcr:title")),
                () -> assertEquals("Carolina Fox", result.get(0).get("author")));

    }

    @Test
    void testThrows() {

        List<Map<String, String>> result = unitTest.getNewsData();
        assertEquals(1, result.size());
        assertTrue(result.get(0).containsKey("nfError"));
    }
}

// *** END CODE ***//