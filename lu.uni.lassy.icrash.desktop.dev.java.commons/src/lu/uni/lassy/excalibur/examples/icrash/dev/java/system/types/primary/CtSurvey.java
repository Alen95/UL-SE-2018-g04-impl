package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class CtSurvey implements Serializable{

	/**ID of the survey*/
	public DtSurveyID id;
	
	/**Status of the survey*/
	public EtSurveyStatus status;
	
	/**Name of the survey*/
	public PtString name;
	
	/**
	 * Initializes the survey
	 * @param id the ID of the survey
	 * @param status the status of the survey
	 * @param name the name of the survey
	 * @return the success of the initialization of the survey
	 */
	public PtBoolean init(DtSurveyID id, PtString name, EtSurveyStatus status) {
			
		this.id = id;
		this.status = status;
		this.name=name;
		return new PtBoolean(true);
	}
}
