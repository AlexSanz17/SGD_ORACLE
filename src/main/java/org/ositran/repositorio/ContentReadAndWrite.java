/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.repositorio;

import java.io.InputStream;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author Himizu
 */
public class ContentReadAndWrite extends SamplesBase
{
    /** Content strings used in the sample */
    private static final String INITIAL_CONTENT = "This is some new content that I am adding to the repository";
    private static final String UPDATED_CONTENT = "This is the updated content";

    /** The type of the association we are creating to the new content */
    private static final String ASSOC_CONTAINS = "{http://www.alfresco.org/model/content/1.0}contains";
    private static Logger log=Logger.getLogger(ContentReadAndWrite.class);
    /**
     * Main function
     */  
    public static void main(String[] args) throws Exception
    {
        // Start the session
        AuthenticationUtils.startSession(USERNAME, PASSWORD);

        try
        {
            // Make sure smaple data has been created
            createSampleData();

            // Get the content service
            ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();

            // Create new content in the respository
            Reference newContentReference = createNewContent(contentService, "sampleThreeFileOne.txt", INITIAL_CONTENT);

            // Read the newly added content from the respository
            Content[] readResult = contentService.read(
                                                new Predicate(new Reference[]{newContentReference}, STORE, null),
                                                Constants.PROP_CONTENT);
            Content content = readResult[0];

            // Get the content from the download servlet using the URL and display it
            log.debug("The newly added content is:");
            log.debug(ContentUtils.getContentAsString(content));

            // Update the content with something new
            contentService.write(newContentReference, Constants.PROP_CONTENT, UPDATED_CONTENT.getBytes(), null);

            // Now output the updated content
            Content[] readResult2 = contentService.read(
                                                new Predicate(new Reference[]{newContentReference}, STORE, null),
                                                Constants.PROP_CONTENT);
            Content content2 = readResult2[0];
            log.debug("The updated content is:");
            log.debug(ContentUtils.getContentAsString(content2));

            // Upload binary content into the repository
            Reference reference = Query1.executeSearch();
            ParentReference parentReference = new ParentReference(reference.getStore(), reference.getUuid(), null, ASSOC_CONTAINS, "{" + Constants.NAMESPACE_CONTENT_MODEL + "}test.jpg");

            // Create the content
            NamedValue[] properties = new NamedValue[]{Utils.createNamedValue(Constants.PROP_NAME, "test.jpg")};
            CMLCreate create = new CMLCreate("1", parentReference, null, null, null, Constants.TYPE_CONTENT, properties);
            CML cml = new CML();
            cml.setCreate(new CMLCreate[]{create});
            UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);

            // Get the created node and create the format
            Reference newContentNode = result[0].getDestination();
            ContentFormat format = new ContentFormat("image/jpeg", "UTF-8");

            // Open the file and convert to byte array
            InputStream viewStream = newContentNode.getClass().getClassLoader().getResourceAsStream("org/alfresco/webservice/test/resources/test.jpg");
            byte[] bytes = ContentUtils.convertToByteArray(viewStream);

            // Write the content
            WebServiceFactory.getContentService().write(newContentNode, Constants.PROP_CONTENT, bytes, format);

        }
        finally
        {
            // End the session
            AuthenticationUtils.endSession();
        }
    }

    /**
     * Helper method to create new content.
     *
     * @param contentService    the content web service
     * @param content           the content itself
     * @return                  a reference to the created content node
     * @throws Exception
     */
    public static Reference createNewContent(ContentServiceSoapBindingStub contentService, String name, String contentString)
        throws Exception
    {
        // Update name
        name = System.currentTimeMillis() + "_" + name;

        // Create a parent reference, this contains information about the association we are createing to the new content and the
        // parent of the new content (the space retrived from the search)
        ParentReference parentReference = new ParentReference(STORE, null, "/app:company_home/cm:sample_folder", ASSOC_CONTAINS,
                "{" + Constants.NAMESPACE_CONTENT_MODEL + "}" + name);

        // Define the content format for the content we are adding
        ContentFormat contentFormat = new ContentFormat("text/plain", "UTF-8");

        NamedValue[] properties = new NamedValue[]{Utils.createNamedValue(Constants.PROP_NAME, name)};
        CMLCreate create = new CMLCreate("1", parentReference, null, null, null, Constants.TYPE_CONTENT, properties);
        CML cml = new CML();
        cml.setCreate(new CMLCreate[]{create});
        UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);

        Reference newContentNode = result[0].getDestination();
        Content content = contentService.write(newContentNode, Constants.PROP_CONTENT, contentString.getBytes(), contentFormat);

        // Get a reference to the newly created content
        return content.getNode();
    }
}
