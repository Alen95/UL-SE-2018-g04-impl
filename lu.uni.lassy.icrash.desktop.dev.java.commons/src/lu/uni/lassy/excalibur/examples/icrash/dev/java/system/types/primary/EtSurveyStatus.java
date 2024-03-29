package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public enum EtSurveyStatus{

	//Survey can be edited
	open,
	//Survey cannot be edited anymore
	closed,
	//Survey can be answered by coordinators
	published;

	public boolean is(EtSurveyStatus status) {
		if((status.name() != "open")||(status.name() != "closed")||(status.name() != "published")) {
			return false;
		}else {
			return true;
		}
		
	}

}
