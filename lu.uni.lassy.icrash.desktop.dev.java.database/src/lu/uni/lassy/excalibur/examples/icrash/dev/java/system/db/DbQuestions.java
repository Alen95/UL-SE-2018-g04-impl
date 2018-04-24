package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAnswer;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtQuestion;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtSurvey;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAnswerID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtQuestionID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtSurveyID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtSurveyStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DbQuestions extends DbAbstract{

	/**
	 * Insert a question into the database
	 * @param idQuestion The id of the question
	 * @param question The question to be asked
	 * @param idSurvey The id of the survey the question corresponds to
	 */
	static public void insertQuestion(String idQuestion, String question, String idSurvey){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
	
				log.debug("[DATABASE]-Insert Question");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".questions" +
											"(id,question,id_survey)" + 
											"VALUES("+"'"+idQuestion+"','"+question+"','"+idSurvey+"')");
				
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
	 * Gets a survey's ID from the database from the questionid provided.
	 *
	 * @param questionId The ID of the question
	 * @return The ID of the survey that was retrieved from the database
	 */
	static public String getSurveyID(String questionId){
		
		String idSurvey = "";
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".questions WHERE id = '" + questionId +"'";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) 				
					idSurvey = res.getString("id_survey");
								
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
	 * Deletes a question from the database.
	 *
	 * @param idQuestion The ID of the question to be removed
	 */
	static public void deleteQuestion(String idQuestion){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".questions WHERE id = ?";
				String id = idQuestion;

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
	
	/**
	 * Gets a all questions from the database
	 *
	 * @return List of the questions retrieved from the database
	 */
	static public ArrayList<CtQuestion> getAllQuestions(){
		
		ArrayList<CtQuestion> questions = new ArrayList<CtQuestion>();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".questions ";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				while(res.next()) {
					CtQuestion question = new CtQuestion();
					question.init(new DtQuestionID(new PtString(res.getString("id"))), new PtString(res.getString("question")), new DtSurveyID(new PtString(res.getString("id_survey"))));
					questions.add(question);
				}
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			logException(e);
		}
				
		return questions;

	}

}
