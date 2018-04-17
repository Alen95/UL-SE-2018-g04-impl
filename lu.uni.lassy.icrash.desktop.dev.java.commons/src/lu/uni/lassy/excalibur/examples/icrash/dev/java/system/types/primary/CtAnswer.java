package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class CtAnswer{

	/**ID of the answer*/
	public DtAnswerID id;
	
	/**The answer*/
	public PtString answer;
	
	/**The ID of the question the answer is added to*/
	public DtQuestionID question_id;
	
	/**The counter which tells us the amount of selections*/
	public int count;
	
	/**
	 * Initializes the answer
	 * @param id the ID of the answer
	 * @param answer The answer to be added 
	 * @param question_id The ID of the question the answer should be added to
	 * @return
	 */
	public PtBoolean init(DtAnswerID id, PtString answer, DtQuestionID question_id,int count) {
		
		this.id = id;
		this.answer = answer;
		this.question_id=question_id;
		this.count = count;
		return new PtBoolean(true);
	}
	
}
