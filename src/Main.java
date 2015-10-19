import java.util.ArrayList;
import java.util.Map;


public class Main {
	public static void main(String[] args) {
		ArrayList<Map<String, String>> entityList = DbOperations.getEntitiesByType(210);
		
		// try {
		// batchUpdateRecordsIntoEntitiesTable(entityList);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		
		for (Map<String, String> entity : entityList) {
			System.out.print("EntityId: " + entity.get("id"));
			System.out.print(", Name: " + entity.get("name"));
			System.out.print(", Type: " + entity.get("type"));
			System.out.println(", SubType: " + entity.get("subtype"));
		}
	}
}
