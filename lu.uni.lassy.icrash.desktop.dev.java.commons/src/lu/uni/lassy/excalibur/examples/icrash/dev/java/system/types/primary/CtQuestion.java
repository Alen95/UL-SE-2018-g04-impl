package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class CtQuestion implements Serializable{

	/**ID of the question*/
	public DtQuestionID id;
	
	/**The question to ask*/
	public PtString question;
	
	/**ID of the survey*/
	public DtSurveyID survey_id;
	
	/**
	 * Initializes the question
	 * @param id ID of the question
	 * @param question The question to be asked
	 * @param survey_id The id of the survey the question is added to
	 * @return The success of the initialization
	 */
	public PtBoolean init(DtQuestionID id, PtString question, DtSurveyID survey_id) {
			
		this.id = id;
		this.question = question;
		this.survey_id=survey_id;
		return new PtBoolean(true);
	}
}
