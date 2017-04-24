package io.temperley;

import org.wikidata.wdtk.datamodel.interfaces.*;
import org.wikidata.wdtk.datamodel.json.jackson.JacksonItemDocument;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.util.Iterator;

/**
 * Created by will on 29/01/17.
 */
public class WikiDataAccess {

    private final WikibaseDataFetcher wbdf = WikibaseDataFetcher.getWikidataDataFetcher();

    public static void main(String[] args) throws MediaWikiApiErrorException {

        WikiDataAccess wikiDataAccess = new WikiDataAccess("Q223589");
    }

    public WikiDataAccess(String entityId) throws MediaWikiApiErrorException {

        EntityDocument q42 = wbdf.getEntityDocument(entityId);

        if (q42 != null) {
            JacksonItemDocument j42 = (JacksonItemDocument) q42;
            Iterator<Statement> allStatements = j42.getAllStatements();

            allStatements.forEachRemaining(f -> {

                Snak mainSnak = f.getClaim().getMainSnak();

                PropertyIdValue propertyId = mainSnak.getPropertyId();

                Value value = mainSnak.getValue();

//                System.out.println("propertyId = " + propertyId.getId());
//                System.out.println("value = " + value);

                if (propertyId.getId().equals("P17")) {
                    if (value instanceof EntityIdValue) {

                        EntityIdValue entityIdValue = (EntityIdValue) value;
                        String id = entityIdValue.getId();

                        try {
                            ItemDocument country = getCountry(id);

                            MonolingualTextValue en = country.getLabels().get("en");
                            System.out.println("en = " + en.getText());

                        } catch (MediaWikiApiErrorException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    }

    public ItemDocument getCountry(String id) throws MediaWikiApiErrorException {
        ItemDocument entityDocument = (ItemDocument) wbdf.getEntityDocument(id);
        return entityDocument;
    }

}
