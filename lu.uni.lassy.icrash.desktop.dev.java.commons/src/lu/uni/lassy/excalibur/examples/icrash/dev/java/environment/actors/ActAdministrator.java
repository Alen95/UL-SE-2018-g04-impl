/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAnswerID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtQuestionID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtSurveyID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtSurveyStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Interface ActAdministrator that allows RMI access to administrator functions.
 */
public interface ActAdministrator extends ActAuthenticated {

	/**
	 * Add a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when creating the coordinator
	 * @param aDtLogin The username to use when creating the coordinator
	 * @param aDtPassword The password to use when creating the coordinator
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID,
			DtLogin aDtLogin, DtPassword aDtPassword) throws RemoteException,
			NotBoundException;

	/**
	 * Add a survey to the system using the provided parameters
	 * @param aDtSurveyID id of the survey
	 * @param name name of the survey
	 * @param status status of the survey
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeCreateSurvey(DtSurveyID aDtSurveyID,
			PtString name, EtSurveyStatus status) throws RemoteException,
			NotBoundException;
	
	/**
	 * Edit a survey's status
	 * @param aDtSurveyID the ID of the survey to be edited
	 * @param status the new status to be set
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeEditSurvey(DtSurveyID aDtSurveyID,
			EtSurveyStatus status) throws RemoteException,
			NotBoundException;
	
	/**
	 * Delete a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when looking for the coordinator to delete
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID)
			throws RemoteException, NotBoundException;
	
	/**
	 * A message sent to the listening actor saying the coordinator was created .
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorAdded() throws RemoteException;

	/**
	 * A message sent to the listening actor saying the survey was created
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieSurveyCreated() throws RemoteException;

	/**
	 * A message sent to the listening actor saying the coordinator was deleted.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorDeleted() throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the coordinator was updated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorUpdated() throws RemoteException;

	/**
	 * A message sent to the listening actor saying the survey was edited
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieSurveyEdited() throws RemoteException;

	/**
	 * Add a question to the system using the passed parameters
	 * @param aDtQuestionID The ID of the question to be added
	 * @param qQuestion The question to be added
	 * @param aDtSurveyID The ID of the survey the question should be added to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeAddQuestion(DtQuestionID aDtQuestionID, PtString qQuestion, DtSurveyID aDtSurveyID) throws RemoteException, NotBoundException;

	/**
	 * A message sent to the listening actor saying the survey was added
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieQuestionAdded() throws RemoteException;

	/**
	 * Add an answer to the system using the passed parameters
	 * @param aDtAnswerID The ID of the answer to be added
	 * @param aAnswer The answer to be added
	 * @param aDtQuestionID The Id of the question the answer should be added to
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeAddAnswer(DtAnswerID aDtAnswerID, PtString aAnswer, DtQuestionID aDtQuestionID) throws RemoteException, NotBoundException;

	/**
	 * A message sent to the listening actor saying the answer was added
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieAnswerAdded() throws RemoteException;	
}
