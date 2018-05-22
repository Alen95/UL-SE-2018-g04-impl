package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtSurveyID extends DtString{

	/**
	 * Instantiates a new datatype survey id.
	 *
	 * @param s The string to use to create the survey ID
	 */
	public DtSurveyID(PtString s) {
		super(s);
	}
	
	public boolean is(DtSurveyID adtSurveyID) {
		String id = adtSurveyID.value.getValue();
		try {
			int i = Integer.parseInt(id);
		}catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
