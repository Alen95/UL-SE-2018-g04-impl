package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSurveys extends DbAbstract{

	/**
	 * Insert a survey into the database
	 * @param idSurvey id of the survey
	 * @param surveyName name of the survey
	 * @param status status of the survey
	 */
	static public void insertSurvey(String idSurvey, String surveyName, String status){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
	
				log.debug("[DATABASE]-Insert Survey");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".surveys" +
											"(id,name,status)" + 
											"VALUES("+"'"+idSurvey+"','"+surveyName+"','"+status+"')");
				
				log.debug(val + " row affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}

	
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Gets a survey's ID from the database from the name provided.
	 *
	 * @param name The name of the survey to retrieve the ID of
	 * @return The ID of the survey that was retrieved from the database
	 */
	static public String getSurveyID(String name){
		
		String idSurvey = "";
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".surveys WHERE name = '" + name +"'";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) 				
					idSurvey = res.getString("id");
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			logException(e);
		}
				
		return idSurvey;

	}
	
	/**
	 * Deletes a Survey from the database.
	 *
	 * @param idSurvey The ID of the survey to delete
	 */
	static public void deleteSurvey(String idSurvey){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".surveys WHERE id = ?";
				String id = idSurvey;

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows+" row deleted");				
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}


			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
}
