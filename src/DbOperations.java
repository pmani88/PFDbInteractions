import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DbOperations {

	public static ArrayList<Map<String, String>> getEntitiesByType(int pmap_id) {
		Connection conn = null;
		ArrayList<Map<String, String>> entityList = new ArrayList<Map<String, String>>();
		try {
			PF_DBConn dbConnObj = new PF_DBConn();
			conn = dbConnObj.getDBConn();

			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM entities WHERE problem_map_id = ?");
			stmt.setInt(1, pmap_id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Map<String, String> entity = new HashMap<String, String>();
				entity.put("id", rs.getString("id"));
				entity.put("name", rs.getString("name"));
				entity.put("type", rs.getString("type"));
				entity.put("subtype", rs.getString("subtype"));
				entityList.add(entity);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Entities Fetched!!");
		return entityList;
	}

	/*
	 * Change the parameter ArrayList to match 
	 * 0 => { entity_id: 1001, type_mismatch?: 1, suggested_type: function }
	 */
	@SuppressWarnings("unused")
	private static void batchUpdateRecordsIntoEntitiesTable(
			ArrayList<Map<String, String>> entityList) throws SQLException {

		Connection conn = null;
		PreparedStatement preparedStatement = null;

		// TODO: Change the SQL query to update correct columns
		String updateTableSQL = "UPDATE entities SET score=? WHERE id=?";

		try {
			PF_DBConn dbConnObj = new PF_DBConn();
			conn = dbConnObj.getDBConn();
			preparedStatement = conn.prepareStatement(updateTableSQL);

			conn.setAutoCommit(false);
			for (Map<String, String> entity : entityList) {
				// TODO: Modify the following statements according to the query
				// string
				preparedStatement.setInt(1, 2);
				preparedStatement.setInt(2, Integer.parseInt(entity.get("id")));
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			conn.commit();
			System.out.println("Bulk Update Completed!!");

		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

}
