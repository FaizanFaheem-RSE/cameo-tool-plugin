package kommunikations_plugin;

import java.util.*; 
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.Activity;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Classifier;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Relationship;

public class SysMLModelDataSaver {

	String RelationshipNames; 
	String relatedSysMLElementParsed; 
	String propertyName; 
	String relationshipNameString = "relationshipname"; 
	String PropertyNameString = "propertyname"; 
	List<List<String>> records = new ArrayList<>();
	List<String> record1;

	String fucntionID = SelectedFucntionElementId.getSelectedFunctionElementID();



	public List<List<String>> addModelElementsToListOfLists() {

		// Retrieving the selected SysML function ID
		Element functionElement = (Element) Application.getInstance().getProject().getElementByID(fucntionID);


		if (functionElement != null) {
			/* 
			 * Iterating all the relationships associated with the selected function/Activity. 
			 * The loop retrieves and processes each relationship that is related to the functionElement (i.e., the selected SysML Function/Activity element) 
			 */
			for (Relationship relationship : functionElement.get_relationshipOfRelatedElement()) {

				// Getting the names of the relationships
				RelationshipNames = relationship.getHumanName(); 

				// Always creating a new record for each complete parsing 
				record1 = new ArrayList<>(); 


				// Insert the relationship with an additional string to facilitate retrieval later in SysMLModelRetriever.java class 
				record1.add(relationshipNameString + RelationshipNames);

				// Getting the SysML elements on the both sides of relation including the Function/Activity and Related SysML element
				Collection<?> relatedElements = relationship.getRelatedElement();

				// Iterating all the SysML elements relevant to the relationships of the selected function/activity including the function/activity itself 
				for (Object relatedElementObject : relatedElements) {

					Element relatedElement = (Element) relatedElementObject;

					// Getting the names of the all the SysML elements including the Function/Activity and Related SysML element
					String relatedSysMLElement =  relatedElement.getHumanName(); 
					/* 
					 * Splitting the string into two parts. 
					 * The first part contains the default SysML element label before the first space. 
					 * The second part contains the name of the SysML element (including the Activity/Function) after the first space.
					 * The SysMLModelDataRetriever.java class use the second part to retrieve the actual name of the SysML element or Activity/Function.  
					 */
					relatedSysMLElementParsed = Arrays.toString(relatedSysMLElement.split(" ", 2));

					// Inserting the Activity/Function and its relevant SysML elements names in the record list 
					record1.add(relatedSysMLElementParsed); 

					// Iterate over the properties of the SysML element or Activity/Function
					for (Property property : ((Classifier) relatedElement).getAttribute()) {
						// Checking here if the extracted element is property not a part 
						if (!property.isComposite()) {
							// Retrieve the property name
							propertyName = property.getName();

							if (relatedElement instanceof Activity) {
								/* 
								 * If the related element is an Activity, tag the property with "Activity:".
								 * This label supports in reteriving the property later in the SysMLModelDataRetriever.java class
								 */
						 
								record1.add("ActivityProperty:" + PropertyNameString + propertyName);
							} else {
								// Otherwise, tag it with "SysML:"
								record1.add("SystemElementProperty:" + PropertyNameString + propertyName);
							}

						}

					}

				}
				// Add the record1 list to the parent list 'records' to store a complete record.
				records.add(record1); 
			}

		}
		return records;
	}
}