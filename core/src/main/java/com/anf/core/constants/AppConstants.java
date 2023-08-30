
package com.anf.core.constants;

public final class AppConstants {
    public static final String SUB_SERVICE="anfUser";
    public static final String NEWSFEED_BASE_PATH = "/var/commerce/products/anf-code-challenge/newsData";
    public static final String NEWSFEED_ERROR="{}:Error while getting resource resolver:{}";
    public static final String PAGE_BASE_PATH="/content/anf-code-challenge/us/en";
    public static final String PAGE_CREATION_PATH="/content/anf-code-challenge/us/en";
    public static final String PATH="path";
    public static final String PROPERTY="property";
    public static final String PROPERTY_UNSTRUCTURED="nt:unstructured";
    public static final String PROPERTY_DATA="jcr:data";
    public static final String PROPERTY_PAGE_CREATED="pageCreated";
    public static final String PROPERTY_BOOLEAN_VALUE="true";
    public static final String PROPERTY_VALUE="anfCodeChallenge";
    public static final String PROPERTY_OPERATION="property.operation";
    public static final String PROPERTY_EXIST="exists";
    public static final String PROPERTY_LIMIT="p.limit";
    public static final String PROPERTY_LIMIT_VALUE="10";
    public static final String PROPERTY_TITLE="jcr:title";
    public static final String NODE_DATASOURCE="datasource";
    public static final String JSON_DATAPATH="jsonDataPath";
    public static final String QUERY="SELECT * FROM [cq:PageContent] WHERE ISDESCENDANTNODE('/content/anf-code-challenge/us/en') AND [anfCodeChallenge] IS NOT NULL ORDER BY [jcr:title] ASC ";
    public static final String JCR_ERROR="jcr error : {}";
    public static final String QUERYBUILDER_ERROR="Query Builder Error:: {} ";
    public static final String PAGECREATION_ERROR="Page Creation Error:: {} ";
    public static final String SERVLET_ERROR="error in servlet: {}";
    public static final String VALIDATE_AGE_ERROR="{'errorMessage':'You are not eligible'}";
    public static final String AGE_ERROR="ages error : {}";
    public static final String JCR_PATH_CHECK="jcr:content/root";
    public static final String AGE="age";
    public static final String MIN_AGE="minAge";
    public static final String MAX_AGE="maxAge";
    public static final String USER_RESPONSE="User data saved successfully";
    public static final String USER_ERROR="Error saving the user data : {} ";
    public static final String RESOURCE_ERROR="Resource not found at path: {}";
   

    




}