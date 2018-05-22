package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtQuestionID extends DtString{
	
	/**
	 * Instantiates a new datatype question id.
	 *
	 * @param s The string to use to create the question ID
	 */
	public DtQuestionID(PtString s) {
		super(s);
	}

	public boolean is(DtQuestionID aDtQuestionID) {
		String id = aDtQuestionID.value.getValue();
		try {
			int i = Integer.parseInt(id);
		}catch(NumberFormatException nfe) {
			return false;
		}
		return true;
		
	}
}	
