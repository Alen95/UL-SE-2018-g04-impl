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
package lu.uni.lassy.excalibur.examples.icrash.dev.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javafx.collections.ObservableList;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtSurvey;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAnswerID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtQuestionID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtSurveyID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtSurveyStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyAdministratorImpl;


/**
 * The Class AdminController, used to do functions that an admin can only do.
 */
public class AdminController extends AbstractUserController {
	
	/**
	 * Instantiates a new admin controller.
	 *
	 * @param aActAdmin the a act admin
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI properties
	 */
	public AdminController(ActAdministrator aActAdmin) throws RemoteException, NotBoundException{	
		super(new ActProxyAdministratorImpl(aActAdmin));
	}
	/**
	 * If an administrator is logged in, will send an addCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to create, the user specifies the ID, not the system in the creation process
	 * @param login The logon of the user to create. This is the username they will use at point of logon at the client front end
	 * @param password The password of the user to create. This is the password they will use at point of logon at the client front end
	 * @return Returns a PtBoolean true if the user was created, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeAddCoordinator(String coordinatorID, String login, String password) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtCoordinatorID aDtCoordinatorID = new DtCoordinatorID(new PtString(coordinatorID));
			DtLogin aDtLogin = new DtLogin(new PtString(login));
			DtPassword aDtPassword = new DtPassword(new PtString(password));
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtCoordinatorID, aDtCoordinatorID.value.getValue());
			ht.put(aDtLogin, aDtLogin.value.getValue());
			ht.put(aDtPassword, aDtPassword.value.getValue());
			try {
				return actorAdmin.oeAddCoordinator(aDtCoordinatorID, aDtLogin, aDtPassword);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a createSurvey request to the server. If successful, it will return a PtBoolean of true
	 * @param surveyID The survey ID specified by the user not the system in the creation process
	 * @param name name of the survey specified by the user
	 * @param status status of the survey specified by the user
	 * @return Returns a PtBoolean true if the user was created, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public PtBoolean oeCreateSurvey(String surveyID, String name, String status) throws ServerOfflineException, ServerNotBoundException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtSurveyID aDtSurveyID = new DtSurveyID(new PtString(surveyID));
			PtString sName = new PtString(name);
			EtSurveyStatus sStatus = EtSurveyStatus.valueOf(status);
			try {
				return actorAdmin.oeCreateSurvey(aDtSurveyID, sName, sStatus);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a editSurvey request to the server. If successful, it will return a PtBoolean of true
	 * @param surveyID The survey ID specified by the user not the system in the creation process
	 * @param status status of the survey specified by the user
	 * @return Returns a PtBoolean true if the survey was edited, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeEditSurvey(String surveyID,String status) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtSurveyID aDtSurveyID = new DtSurveyID(new PtString(surveyID));
			EtSurveyStatus sStatus = EtSurveyStatus.valueOf(status);
			try {
				return actorAdmin.oeEditSurvey(aDtSurveyID, sStatus);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	

	/**
	 * If an administrator is logged in, will send a deleteCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to delete
	 * @return Returns a PtBoolean true if the user was deleted, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeDeleteCoordinator(String coordinatorID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtCoordinatorID aDtCoordinatorID = new DtCoordinatorID(new PtString(coordinatorID));
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtCoordinatorID, aDtCoordinatorID.value.getValue());
			if (!aDtCoordinatorID.is().getValue())
				return new PtBoolean(false);
			try {
				return actorAdmin.oeDeleteCoordinator(aDtCoordinatorID);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send an addQuestion request to the server. If successful, it will return a PtBoolean of true
	 * @param questionId The id of the question to be added, defined by the user
	 * @param question The question to be added
	 * @param surveyId The ID of the survey the question should be added to
	 * @return Returns a PtBoolean true if the question was created
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeAddQuestion(String questionId, String question, String surveyId) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtQuestionID aDtQuestionID = new DtQuestionID(new PtString(questionId));
			PtString qQuestion = new PtString(question);
			DtSurveyID aDtSurveyID = new DtSurveyID(new PtString(surveyId));
			try {
				return actorAdmin.oeAddQuestion(aDtQuestionID, qQuestion, aDtSurveyID);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send an addAnswer request to the server. If successful, it will return a PtBoolean of true
	 * @param answerId The id of the answer to be added, defined by the user
	 * @param answer The answer to be added
	 * @param questionId The ID of te question the answer should be added to
	 * @return Returns a PtBoolean true if the answer was created
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeAddAnswer(String answerId, String answer, String questionId) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtQuestionID aDtQuestionID = new DtQuestionID(new PtString(questionId));
			PtString aAnswer = new PtString(answer);
			DtAnswerID aDtAnswerID = new DtAnswerID(new PtString(answerId));
			try {
				return actorAdmin.oeAddAnswer(aDtAnswerID, aAnswer, aDtQuestionID);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a getsurveys request to the server. If successful, it will return a list with all the surveys
	 * @return List with all surveys as CtSurvey
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public List<CtSurvey> oeGetSurveys() throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException {
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			try {
				return actorAdmin.oeGetSurveys();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return null;

	}

}
