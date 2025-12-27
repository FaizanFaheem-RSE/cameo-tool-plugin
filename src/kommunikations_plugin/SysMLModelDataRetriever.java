package kommunikations_plugin;

import java.util.*; 

public class SysMLModelDataRetriever {

	String relatedSysMLElementParsed; 
	String propertyName;

	String SystemelemntNameExtractedRightPartDone = null;
	String ActivityNameExtractedRightPartDone = null;

	String relationshipdone = null; 

	String propertyvaluedone = null; 

	List<String> ActicityPropertyList = new ArrayList<>();
	List<String> SystemElementPropertyList = new ArrayList<>();
	String ActivityPropertyForQuery = null; 
	String SystemElementPropertyForQuery = null; 


	SysMLModelDataSaver data = new SysMLModelDataSaver();

	List<List<String>> records = data.addModelElementsToListOfLists();

	int count = 0; 
	int listsize = records.size(); 

	public List<String[]> addModelElementsFromListOfLists() {
		List<String[]> results = new ArrayList<>();

		while (listsize > 0 ) {
			for (String value : records.get(count)) {

				// Here I am checking if its system element, Activity/Function and etc and storing it in 
				// variables so that these names I can fill within the sparql query
				if(value.contains(",") && value.contains("Systemelement")) {


					String[] SystemelemntNameExtracted = value.split(",");

					String SystemelemntNameExtractedRightPart = SystemelemntNameExtracted[1];


					SystemelemntNameExtractedRightPartDone = SystemelemntNameExtractedRightPart.substring(0, SystemelemntNameExtractedRightPart.length() - 1);
					SystemelemntNameExtractedRightPartDone = ":" + SystemelemntNameExtractedRightPartDone;
					SystemelemntNameExtractedRightPartDone = SystemelemntNameExtractedRightPartDone.replaceAll("\\s", ""); 

				}
				else if(value.contains(",") && value.contains("Activity")) {


					String[] ActivityNameExtracted = value.split(",");

					String ActivityNameExtractedRightPart = ActivityNameExtracted[1];


					ActivityNameExtractedRightPartDone = ActivityNameExtractedRightPart.substring(0, ActivityNameExtractedRightPart.length() - 1);
					ActivityNameExtractedRightPartDone = ":" + ActivityNameExtractedRightPartDone;
					ActivityNameExtractedRightPartDone = ActivityNameExtractedRightPartDone.replaceAll("\\s", ""); 

				}

				else if (value.contains("relationshipname")){

					String relationship = value;
					relationshipdone =  relationship.replace("relationshipname", "");
					relationshipdone = ":" + relationshipdone;

				}
				else if (value.contains("propertyname")) {

					String propertyvalue = value;
					propertyvaluedone =  propertyvalue.replace("propertyname", "");
					propertyvaluedone = ":" + propertyvaluedone;
					propertyvaluedone = propertyvaluedone.replaceAll("\\s", ""); 

					if(propertyvaluedone.contains(":ActivityProperty:")) {
						propertyvaluedone = propertyvaluedone.replace(":ActivityProperty", "");
						ActicityPropertyList.add(propertyvaluedone);
					}
					else {
						propertyvaluedone = propertyvaluedone.replace(":SystemElementProperty", "");
						SystemElementPropertyList.add(propertyvaluedone);
					}

				}	                	

			} 

			for (String ActicityProperty : ActicityPropertyList) {
				for (String SystemElementProperty: SystemElementPropertyList) {
					ActivityPropertyForQuery = ActicityProperty; 
					SystemElementPropertyForQuery = SystemElementProperty; 
					results.add(new String[]{
							SystemelemntNameExtractedRightPartDone,
							ActivityNameExtractedRightPartDone,
							SystemElementPropertyForQuery,
							ActivityPropertyForQuery,
							relationshipdone
					});
				}
			}

			ActicityPropertyList.clear();
			SystemElementPropertyList.clear();

			// Step by step I am extracting the records and shrinking the size of list  
			count ++; 
			listsize--; 

		}
		return results;
	}	



}