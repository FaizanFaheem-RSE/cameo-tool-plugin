package kommunikations_plugin;

import java.util.*; 
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.ui.ScalableImageIcon;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader; 
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class SPARQLQueryManager extends DefaultDiagramAction {
	public SPARQLQueryManager(String id, String name) {
		super(id, name, null, null);

		URL url = getClass().getResource("/pick.png");
		setSmallIcon(new ScalableImageIcon(url));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		SysMLModelDataRetriever parsedInformation = new SysMLModelDataRetriever();
		List<String[]> parsedNamesList = parsedInformation.addModelElementsFromListOfLists();



		for (int j = 0; j < parsedNamesList.size(); j++) {


			String[] paresedName = parsedNamesList.get(j);

			String SystemelemntNameExtractedRightPartDone = paresedName[0];
			String ActivityNameExtractedRightPartDone = paresedName[1];
			String SystemElementPropertyForQuery = paresedName[2];
			String ActivityPropertyForQuery = paresedName[3];
			String relationshipdone = paresedName[4];

			Application.getInstance().getGUILog().showMessage(SystemelemntNameExtractedRightPartDone + "," + ActivityNameExtractedRightPartDone + "," + relationshipdone + "," + ActivityPropertyForQuery + "," + SystemElementPropertyForQuery);

			try {
				// Set the endpoint URL and SPARQL query
				String endpointUrl = "http://MB-KONSTR194:7200/repositories/1";
				String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
						+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\r\n"
						+ "PREFIX : <http://pse.com/>\r\n"
						+ "\r\n"
						+ "SELECT DISTINCT ?commonRelation ?commonRelationName ?commonList ?Errors\r\n"
						+ "WHERE {\r\n"
						+ "  "+ SystemelemntNameExtractedRightPartDone +" :has_relation ?commonRelation .\r\n"  // System element name
						+ "\r\n"
						+ "  "+ ActivityNameExtractedRightPartDone +" :has_relation ?commonRelation .\r\n" // Function name
						+ "\r\n"
						+ "  "+ SystemElementPropertyForQuery +" :has_relation ?commonRelation .\r\n" // System element property
						+ "  "+  ActivityPropertyForQuery +" :has_relation ?commonRelation .\r\n"    // Function property
						+ "\r\n"
						+ "  ?commonRelation :relationship_name "+ relationshipdone +" ;\r\n"
						+ "                  :could_lead_to ?commonList .\r\n"
						+ "\r\n"
						+ "  "+ relationshipdone +" :could_lead_to ?commonList .\r\n"
						+ "\r\n"
						+ "  ?commonList :that_is ?Errors .\r\n"
						+ "}";
				String requestUrl = endpointUrl + "?query=" + java.net.URLEncoder.encode(queryString, "UTF-8");

				// Creating an HTTP connection and setting the request
				URL url = new URL(requestUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/sparql-results+json");

				// Reading the response from server 
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				StringBuilder responseBuilder = new StringBuilder();
				while ((line = in.readLine()) != null) {
					responseBuilder.append(line);
				}
				in.close();


				// Response from the server
				String response = responseBuilder.toString();

				Application.getInstance().getGUILog().showMessage("Errors: " + response);

				// Used Jackson library to parse JSON in order to extract the potential failures  
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(response);

				JsonNode resultsNode = jsonNode.get("results");
				JsonNode bindingsNode = resultsNode.get("bindings");


				if (bindingsNode.size() > 0) {
					for (int i = 0; i <= bindingsNode.size(); i++){
						JsonNode firstBinding = bindingsNode.get(i);
						String potenitalErrorValue = firstBinding.get("Errors").get("value").asText();


						String newUrl = potenitalErrorValue;
						String searchString = "/"; 


						int lastIndex = newUrl.lastIndexOf(searchString);
						String result = newUrl.substring(lastIndex + searchString.length());

						Application.getInstance().getGUILog().showMessage("Potential Failures: " + result);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}               	
		}
	}  

}

