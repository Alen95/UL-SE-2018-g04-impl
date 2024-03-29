package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtAnswerID extends DtString {
	
	/**
	 * Instantiates a new datatype answer id.
	 *
	 * @param s The string to use to create the answer ID
	 */
	public DtAnswerID(PtString s) {
		super(s);
	}

	public boolean is(DtAnswerID aDtAnswerID) {
		String id = aDtAnswerID.value.getValue();
		try {
			int i = Integer.parseInt(id);
		}catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
