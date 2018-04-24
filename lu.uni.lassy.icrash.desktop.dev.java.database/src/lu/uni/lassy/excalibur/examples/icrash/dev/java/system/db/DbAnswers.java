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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtSurvey;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAnswerID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtQuestionID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtSurveyID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtSurveyStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DbAnswers extends DbAbstract{

	/**
	 * Insert an answer into the database
	 * @param idAnswer The ID of the answer to be added
	 * @param answer The answer 
	 * @param idQuestion The ID of the question to be added
	 */
	static public void insertAnswer(String idAnswer, String answer, String idQuestion){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
	
				log.debug("[DATABASE]-Insert Question");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".answers" +
											"(id,answer,count,id_question)" + 
											"VALUES("+"'"+idAnswer+"','"+answer+"','"+0+"','"+idQuestion+"')");
				
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
	 * Gets a all answers from the database whose question id is the one provided.
	 *
	 * @param questionId The ID of the question
	 * @return The ID of the survey that was retrieved from the database
	 */
	static public List<CtAnswer> getAnswers(String questionId){
		
		List<CtAnswer> answers = new ArrayList<CtAnswer>();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".answers WHERE id_question = '" + questionId +"'";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				while(res.next()) {
					CtAnswer answer = new CtAnswer();
					answer.init(new DtAnswerID(new PtString(res.getString("id"))), new PtString(res.getString("answer")), new DtQuestionID(new PtString(res.getString("id_question"))),res.getInt("count"));
					answers.add(answer);
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
				
		return answers;

	}
	
	/**
	 * Gets a all answers from the database
	 *
	 * @return List of the answers retrieved from the database
	 */
	static public ArrayList<CtAnswer> getAllAnswers(){
		
		ArrayList<CtAnswer> answers = new ArrayList<CtAnswer>();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".answers";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				while(res.next()) {
					CtAnswer answer = new CtAnswer();
					answer.init(new DtAnswerID(new PtString(res.getString("id"))), new PtString(res.getString("answer")), new DtQuestionID(new PtString(res.getString("id_question"))),res.getInt("count"));
					answers.add(answer);
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
				
		return answers;

	}

	public static void selectAnswer(String id) {

		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
	
				log.debug("[DATABASE]-Select Answer");
				int val = st.executeUpdate("UPDATE "+ dbName+ ".answers SET count=count+1 WHERE id='"+id+"'");
				
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

}
