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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.admin;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.AdminController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.SystemStateController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAnswer;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtQuestion;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtSurvey;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAnswerID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtQuestionID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtSurveyID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtSurveyStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Server;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator.CreateICrashCoordGUI;
import javafx.scene.layout.GridPane;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
/*
 * This is the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
/**
 * The Class ICrashGUIController, which deals with handling the GUI and it's functions for the Administrator.
 */
public class ICrashAdminGUIController extends AbstractAuthGUIController {
	
	/*
	* This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/
	
    /** The pane containing the logon controls. */
	@FXML
    private Pane pnAdminLogon;

    /** The textfield that allows input of a username for logon. */
    @FXML
    private TextField txtfldAdminUserName;

    /** The passwordfield that allows input of a password for logon. */
    @FXML
    private PasswordField psswrdfldAdminPassword;

    /** The button that initiates the login function. */
    @FXML
    private Button bttnAdminLogin;

    /** The borderpane that contains the normal controls the user will use. */
    @FXML
    private BorderPane brdpnAdmin;

    /** The anchorpane that will have the add or delete coordinator controls added/removed from it */
    @FXML
    private AnchorPane anchrpnCoordinatorDetails;
    
    /** The anchorpane that will have the add, delete or edit survey controls added/removed from it */
    @FXML
    private AnchorPane anchrpnSurveyDetails;

    /** The button that shows the controls for adding a coordinator */
    @FXML
    private Button bttnBottomAdminCoordinatorAddACoordinator;

    /** The button that shows the controls for deleting a coordinator */
    @FXML
    private Button bttnBottomAdminCoordinatorDeleteACoordinator;

    /** The tableview of the recieved messages from the system */
    @FXML
    private TableView<Message> tblvwAdminMessages;

    /** The button that allows a user to logoff */
    @FXML
    private Button bttnAdminLogoff;
    
    /** The button that shows the controls for adding a survey**/
    @FXML
    private Button bttnBottomAdminCreateSurvey;

    /** The button that shows the controls for editing a survey*/
    @FXML
    private Button bttnBottomAdminEditSurvey;
    
    /**The button that shows the controls for adding a new question*/
    @FXML
    private Button bttnBottomAdminAddQuestion;
    
    /**The button that shows the controls for adding a new answer*/
    @FXML
    private Button bttnBottomAdminAddAnswer;
    
    /**The button that shows the survey results**/
    @FXML
    private Button bttnBottomAdminShowSurveyResult;
    /**
     * The button event that will show the controls for adding a coordinator
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminCoordinatorAddACoordinator_OnClick(ActionEvent event) {
    	showCoordinatorScreen(TypeOfEdit.Add);
    }
    
    /**
     * The button event that will show the survey results
     * @param event The event type thrown, we do not need this, but it must be specified
     * @throws NotBoundException 
     * @throws RemoteException 
     */
    @FXML
    void bttnBottomAdminShowSurveyResult_OnClick(ActionEvent event) throws RemoteException, NotBoundException {
    		showSurveyResult();
    }
    /**
     * The button event that will show the controls for deleting a coordinator
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminCoordinatorDeleteACoordinator_OnClick(ActionEvent event) {
    	showCoordinatorScreen(TypeOfEdit.Delete);
    }

    /**
     * The button event that will initiate the logging on of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomLoginPaneLogin_OnClick(ActionEvent event) {
    	logon();
    }

    /**
     * The button event that will initiate the logging off of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnTopLogoff_OnClick(ActionEvent event) {
    	logoff();
    }
    
    /**
     * The button event that will show the controls for adding a new survey
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     * @throws NotBoundException 
     * @throws RemoteException 
     * @throws IncorrectFormatException 
     * @throws ServerNotBoundException 
     * @throws ServerOfflineException 
     */
    @FXML
    void bttnBottomAdminCreateSurvey_OnClick(ActionEvent event) {
    	showSurveyScreen(TypeOfEdit.Add);
   
    }
    
    /**
     * The button event that will show the controls for adding a new survey
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     * @throws NotBoundException 
     * @throws RemoteException 
     * @throws IncorrectFormatException 
     * @throws ServerNotBoundException 
     * @throws ServerOfflineException 
     */
    @FXML
    void bttnBottomAdminEditSurvey_OnClick(ActionEvent event) {
    		showSurveyScreen(TypeOfEdit.Edit);
    }
    
    /** 
     * The button event that will show the controls for adding a new question
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminAddQuestion_OnClick(ActionEvent event) {
    		showQuestionScreen(TypeOfEdit.Add);
    }

    @FXML
    void bttnBottomAdminAddAnswer_OnClick(ActionEvent event) {
    		showAnswerScreen(TypeOfEdit.Add);
    }
    /*
     * These are other classes accessed by this controller
     */
    /** The user controller, for this GUI it's the admin controller and allows access to admin functions like adding a coordinator. */
	private AdminController userController;
	
	/** Used to get the actor coordinator that was created by the admin, for creating the new window with. */
	private SystemStateController systemstateController;
	
	/*
	 * Other things created for this controller
	 */
	/**
	 * The enumeration dictating the type of edit an admin is doing to a coordinator.
	 */
	private enum TypeOfEdit{
		
		/** Adding a coordinator,survey,question. */
		Add,
		
		/** Deleting a coordinator,survey,question. */
		Delete,
		
		/** Edit a survey*/
		Edit
		
		
	}
	
	/**
	 * The list of open windows in the system.
	 * We open a new window when a coordinator is created, so we also should close the window if the coordinator is deleted 
	 */
	private ArrayList<CreateICrashCoordGUI> listOfOpenWindows = new ArrayList<CreateICrashCoordGUI>();
	/*
	 * Methods used within the GUI
	 */

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logonShowPanes(boolean)
	 */
	protected void logonShowPanes(boolean loggedOn) {
		pnAdminLogon.setVisible(!loggedOn);
		brdpnAdmin.setVisible(loggedOn);
		bttnAdminLogoff.setDisable(!loggedOn);
		bttnAdminLogin.setDefaultButton(!loggedOn);
		if (!loggedOn){
			txtfldAdminUserName.setText("");
			psswrdfldAdminPassword.setText("");
			txtfldAdminUserName.requestFocus();
			for (int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
				anchrpnCoordinatorDetails.getChildren().remove(i);
		}
		
	}	
	
	/**
	 * Server has gone down.
	 */
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.AbstractGUIController#serverHasGoneDown()
	 */
	protected void serverHasGoneDown(){
		logoff();
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables#setUpTables()
	 */
	public void setUpTables(){
		setUpMessageTables(tblvwAdminMessages);
	}

	private void showSurveyResult() throws RemoteException, NotBoundException {
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		
		GridPane grdpn = new GridPane();		
		TableView table = new TableView();
	    TableColumn<CtSurvey,String> surveyNameCol = new TableColumn<CtSurvey,String>("Name");
	    TableColumn<CtSurvey,String> surveyIdCol = new TableColumn<CtSurvey,String>("ID");
	    TableColumn<CtSurvey,String> surveyStatusCol = new TableColumn<CtSurvey,String>("Status");
	    surveyNameCol.setCellValueFactory(new Callback<CellDataFeatures<CtSurvey, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtSurvey, String> survey) {
				return new ReadOnlyObjectWrapper<String>(survey.getValue().name.getValue());
			}
		});
	    surveyIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtSurvey, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtSurvey, String> survey) {
				return new ReadOnlyObjectWrapper<String>(survey.getValue().id.value.getValue());
			}
		});
	    surveyStatusCol.setCellValueFactory(new Callback<CellDataFeatures<CtSurvey, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtSurvey, String> survey) {
				return new ReadOnlyObjectWrapper<String>(survey.getValue().status.toString());
			}
		});
	    table.getColumns().addAll(surveyIdCol,surveyNameCol,surveyStatusCol);
	    setColumnsSameWidth(table);
	    Button select = new Button();
	    select.setText("Select");
	    grdpn.add(table, 1, 1);
	    grdpn.add(select, 1, 2);
	    updateSurveysTable(table);   
		
	    anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		select.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CtSurvey survey = (CtSurvey)getObjectFromTableView(table);
				showSelectedSurveyResults(survey);
			}
		});
		
	}
	
	private void showSelectedSurveyResults(CtSurvey survey) {
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		Label surveyName = new Label();
		surveyName.setText(survey.name.getValue());
		GridPane grdpn = new GridPane();
		grdpn.add(surveyName, 1, 1);
		Server server = Server.getInstance();
		
		TableView table = new TableView();
	    TableColumn<CtQuestion,String> QuestionNameCol = new TableColumn<CtQuestion,String>("question");
	    TableColumn<CtQuestion,String> QuestionIdCol = new TableColumn<CtQuestion,String>("id");
	    TableColumn<CtQuestion,String> surveyIdCol = new TableColumn<CtQuestion,String>("id_survey");
	    QuestionNameCol.setCellValueFactory(new Callback<CellDataFeatures<CtQuestion, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtQuestion, String> question) {
				return new ReadOnlyObjectWrapper<String>(question.getValue().question.getValue());
			}
		});
	    surveyIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtQuestion, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtQuestion, String> question) {
				return new ReadOnlyObjectWrapper<String>(question.getValue().survey_id.value.getValue());
			}
		});
	    QuestionIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtQuestion, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtQuestion, String> question) {
				return new ReadOnlyObjectWrapper<String>(question.getValue().id.toString());
			}
		});
	    table.getColumns().addAll(QuestionIdCol,QuestionNameCol,surveyIdCol);
	    setColumnsSameWidth(table);
	    ArrayList<CtQuestion> questions = null;
		try {
			questions = server.sys().getQuestionsWithSurveyID(survey.id.value.getValue());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		table.setItems(FXCollections.observableArrayList( questions));
		grdpn.add(table, 1, 2);
		Button select = new Button();
	    select.setText("Select");
	    grdpn.add(select, 1, 3);
		anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		select.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CtQuestion question = (CtQuestion)getObjectFromTableView(table);
				showSelectedQuestionResults(question);
			}
		});
	}
	
	public void showSelectedQuestionResults(CtQuestion question) {
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		Label questionName = new Label();
		questionName.setText(question.question.getValue());
		GridPane grdpn = new GridPane();
		grdpn.add(questionName, 1, 1);
		Server server = Server.getInstance();
		
		TableView table = new TableView();
	    TableColumn<CtAnswer,String> answerNameCol = new TableColumn<CtAnswer,String>("answer");
	    TableColumn<CtAnswer,String> answerIdCol = new TableColumn<CtAnswer,String>("id");
	    TableColumn<CtAnswer,String> questionIdCol = new TableColumn<CtAnswer,String>("id_question");
	    TableColumn<CtAnswer,String> count = new TableColumn<CtAnswer,String>("count");

	    answerNameCol.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(answer.getValue().answer.getValue());
			}
		});
	    answerIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(answer.getValue().id.value.getValue());
			}
		});
	    questionIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(answer.getValue().question_id.toString());
			}
		});
	    count.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(String.valueOf(answer.getValue().count));
			}
		});
	    table.getColumns().addAll(answerIdCol,answerNameCol,questionIdCol,count);
	    setColumnsSameWidth(table);
	    ArrayList<CtAnswer> answers = null;
		try {
			answers = server.sys().getAnswersWithQuestionID(question.id.value.getValue());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		table.setItems(FXCollections.observableArrayList(answers));
		grdpn.add(table, 1, 2);
		anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		
	}
	
	/**
	 * Shows the answer screen.
	 *
	 * @param type The type of edit to be done, this could be add
	 */
	private void showAnswerScreen(TypeOfEdit type){
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		TextField txtfldAnswerID = new TextField();
		TextField txtfldAnswer = new TextField();
		TextField txtfldQuestionID = new TextField();
		txtfldQuestionID.setPromptText("Question ID");
		txtfldAnswerID.setPromptText("Answer ID");
		txtfldAnswer.setPromptText("Answer");
		Button bttntypOK = null;
		GridPane grdpn = new GridPane();
		grdpn.add(txtfldAnswerID, 1, 1);
		
		TableView table = new TableView();
	    TableColumn<CtAnswer,String> answerNameCol = new TableColumn<CtAnswer,String>("answer");
	    TableColumn<CtAnswer,String> answerIdCol = new TableColumn<CtAnswer,String>("id");
	    TableColumn<CtAnswer,String> questionIdCol = new TableColumn<CtAnswer,String>("id_question");
	    TableColumn<CtAnswer,String> count = new TableColumn<CtAnswer,String>("count");

	    answerNameCol.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(answer.getValue().answer.getValue());
			}
		});
	    answerIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(answer.getValue().id.value.getValue());
			}
		});
	    questionIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(answer.getValue().question_id.toString());
			}
		});
	    count.setCellValueFactory(new Callback<CellDataFeatures<CtAnswer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAnswer, String> answer) {
				return new ReadOnlyObjectWrapper<String>(String.valueOf(answer.getValue().count));
			}
		});
	    table.getColumns().addAll(answerIdCol,answerNameCol,questionIdCol,count);
		setColumnsSameWidth(table);
	    try {
			updateAnswers(table);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
	    grdpn.add(table, 1, 5);
		switch(type){
		case Add:
			bttntypOK = new Button("Add");;
			grdpn.add(txtfldQuestionID, 1, 2);
			grdpn.add(txtfldAnswer, 1, 3);
			grdpn.add(bttntypOK, 1, 4);
			break;
		/*case Delete:
			bttntypOK = new Button("Delete");
			grdpn.add(bttntypOK, 1, 2);
			break;	*/	
		}
		bttntypOK.setDefaultButton(true);
		bttntypOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkIfAllDialogHasBeenFilledIn(grdpn))
					showWarningNoDataEntered();
				else{
					try {
						DtAnswerID answerID = new DtAnswerID(new PtString(txtfldAnswerID.getText()));
						switch(type){
						case Add:
							if (userController.oeAddAnswer(txtfldAnswerID.getText(), txtfldAnswer.getText(), txtfldQuestionID.getText()).getValue()){
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
								try {
									updateAnswers(table);
								} catch (RemoteException e) {
									e.printStackTrace();
								} catch (NotBoundException e) {
									e.printStackTrace();
								}
							}
							else
								showErrorMessage("Unable to add Answer", "An error occured when adding the Answer");
							break;
						/*case Delete:
							if (userController.oeDeleteCoordinator(txtfldUserID.getText()).getValue()){
								for(CreateICrashCoordGUI window : listOfOpenWindows){
									if (window.getDtCoordinatorID().value.getValue().equals(coordID.value.getValue()))
										window.closeWindow();
								}
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to delete coordinator", "An error occured when deleting the coordinator");
							break;*/
						}
					} catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
						showExceptionErrorMessage(e);
					}					
				}
			}
		});
		anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		txtfldAnswerID.requestFocus();
	}
	
	public void updateAnswersTable(TableView table) {
		Server server = Server.getInstance();
		try {
			ArrayList<CtAnswer> answers = server.sys().getAllAnswers();
			table.setItems(FXCollections.observableArrayList( answers));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updateQuestionsTable(TableView table) {
		Server server = Server.getInstance();
		try {
			ArrayList<CtQuestion> questions = server.sys().getAllQuestions();
			table.setItems(FXCollections.observableArrayList( questions));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updateSurveysTable(TableView table) {
		Server server = Server.getInstance();
		try {
			ArrayList<CtSurvey> surveys = server.sys().getAllSurveys();
			table.setItems(FXCollections.observableArrayList(surveys));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Shows the modify Question screen.
	 *
	 * @param type The type of edit to be done, this could be add or delete 
	 */
	private void showQuestionScreen(TypeOfEdit type){
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		TextField txtfldQuestionID = new TextField();
		TextField txtfldQuestion = new TextField();
		TextField txtfldSurveyID = new TextField();
		txtfldQuestionID.setPromptText("Question ID");
		txtfldSurveyID.setPromptText("Survey ID");
		txtfldQuestion.setPromptText("Question");
		Button bttntypOK = null;
		GridPane grdpn = new GridPane();
		grdpn.add(txtfldQuestionID, 1, 1);
		TableView table = new TableView();
	    TableColumn<CtQuestion,String> QuestionNameCol = new TableColumn<CtQuestion,String>("question");
	    TableColumn<CtQuestion,String> QuestionIdCol = new TableColumn<CtQuestion,String>("id");
	    TableColumn<CtQuestion,String> surveyIdCol = new TableColumn<CtQuestion,String>("id_survey");
	    QuestionNameCol.setCellValueFactory(new Callback<CellDataFeatures<CtQuestion, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtQuestion, String> question) {
				return new ReadOnlyObjectWrapper<String>(question.getValue().question.getValue());
			}
		});
	    surveyIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtQuestion, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtQuestion, String> question) {
				return new ReadOnlyObjectWrapper<String>(question.getValue().survey_id.value.getValue());
			}
		});
	    QuestionIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtQuestion, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtQuestion, String> question) {
				return new ReadOnlyObjectWrapper<String>(question.getValue().id.toString());
			}
		});
	    table.getColumns().addAll(QuestionIdCol,QuestionNameCol,surveyIdCol);
	    setColumnsSameWidth(table);
	    try {
			updateQuestions(table);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
	    grdpn.add(table, 1, 5);
		switch(type){
		case Add:
			bttntypOK = new Button("Add");;
			grdpn.add(txtfldSurveyID, 1, 2);
			grdpn.add(txtfldQuestion, 1, 3);
			grdpn.add(bttntypOK, 1, 4);
			break;
		/*case Delete:
			bttntypOK = new Button("Delete");
			grdpn.add(bttntypOK, 1, 2);
			break;	*/	
		}
		bttntypOK.setDefaultButton(true);
		bttntypOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkIfAllDialogHasBeenFilledIn(grdpn))
					showWarningNoDataEntered();
				else{
					try {
						DtQuestionID questID = new DtQuestionID(new PtString(txtfldQuestionID.getText()));
						switch(type){
						case Add:
							if (userController.oeAddQuestion(txtfldQuestionID.getText(), txtfldQuestion.getText(), txtfldSurveyID.getText()).getValue()){
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
								try {
									updateQuestions(table);
								} catch (RemoteException e) {
									e.printStackTrace();
								} catch (NotBoundException e) {
									e.printStackTrace();
								}
							}
							else
								showErrorMessage("Unable to add Question", "An error occured when adding the Question");
							break;
						/*case Delete:
							if (userController.oeDeleteCoordinator(txtfldUserID.getText()).getValue()){
								for(CreateICrashCoordGUI window : listOfOpenWindows){
									if (window.getDtCoordinatorID().value.getValue().equals(coordID.value.getValue()))
										window.closeWindow();
								}
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to delete coordinator", "An error occured when deleting the coordinator");
							break;*/
						}
					} catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
						showExceptionErrorMessage(e);
					}					
				}
			}
		});
		anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		txtfldQuestionID.requestFocus();
	}
	
	/**
	 * Shows the modify survey screen.
	 *
	 * @param type The type of edit to be done, this could be add or delete or edit
	 */
	private void showSurveyScreen(TypeOfEdit type) {
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		TextField txtfldSurveyID = new TextField();
		TextField txtfldSurveyName = new TextField();
		txtfldSurveyID.setPromptText("Survey ID");
		Button bttntypOK = null;
		GridPane grdpn = new GridPane();
		grdpn.add(txtfldSurveyID, 1, 1);
		switch(type){
		case Add:
			bttntypOK = new Button("Add");
			txtfldSurveyName.setPromptText("Survey name");
			grdpn.add(txtfldSurveyName, 1, 2);
			grdpn.add(bttntypOK, 1, 4);
			break;
		/*case Delete:
			bttntypOK = new Button("Delete");
			grdpn.add(bttntypOK, 1, 2);
			break;*/	
			
		case Edit:
		    TableView table = new TableView();
		    TableColumn<CtSurvey,String> surveyNameCol = new TableColumn<CtSurvey,String>("Name");
		    TableColumn<CtSurvey,String> surveyIdCol = new TableColumn<CtSurvey,String>("ID");
		    TableColumn<CtSurvey,String> surveyStatusCol = new TableColumn<CtSurvey,String>("Status");
		    surveyNameCol.setCellValueFactory(new Callback<CellDataFeatures<CtSurvey, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(CellDataFeatures<CtSurvey, String> survey) {
					return new ReadOnlyObjectWrapper<String>(survey.getValue().name.getValue());
				}
			});
		    surveyIdCol.setCellValueFactory(new Callback<CellDataFeatures<CtSurvey, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(CellDataFeatures<CtSurvey, String> survey) {
					return new ReadOnlyObjectWrapper<String>(survey.getValue().id.value.getValue());
				}
			});
		    surveyStatusCol.setCellValueFactory(new Callback<CellDataFeatures<CtSurvey, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(CellDataFeatures<CtSurvey, String> survey) {
					return new ReadOnlyObjectWrapper<String>(survey.getValue().status.toString());
				}
			});
		    table.getColumns().addAll(surveyIdCol,surveyNameCol,surveyStatusCol);
		    setColumnsSameWidth(table);
		    updateSurveysTable(table);
		    grdpn.add(table, 1, 5);
		    bttntypOK = new Button("Edit");
			txtfldSurveyName.setPromptText("Survey status");
			grdpn.add(txtfldSurveyName, 1, 2);
			grdpn.add(bttntypOK, 1, 4);
			break;
		}
		bttntypOK.setDefaultButton(true);
		bttntypOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkIfAllDialogHasBeenFilledIn(grdpn))
					showWarningNoDataEntered();
				else{
					try {
						DtSurveyID surveyID = new DtSurveyID(new PtString(txtfldSurveyID.getText()));
						switch(type){
						case Add:
							if (!userController.oeCreateSurvey(txtfldSurveyID.getText(),txtfldSurveyName.getText(), "open").getValue()){
								showErrorMessage("Unable to add Survey", "An error occured when adding the Survey");
							}
							anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							break;
							
						case Edit:
							if (!userController.oeEditSurvey(txtfldSurveyID.getText(), txtfldSurveyName.getText().toLowerCase()).getValue()){
								showErrorMessage("Unable to edit Survey", "An error occured when editing the Survey");
							}
							anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							break;
						/*case Delete:
							if (userController.oeDeleteCoordinator(txtfldUserID.getText()).getValue()){
								for(CreateICrashCoordGUI window : listOfOpenWindows){
									if (window.getDtCoordinatorID().value.getValue().equals(coordID.value.getValue()))
										window.closeWindow();
								}
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to delete coordinator", "An error occured when deleting the coordinator");
							break;*/
						}
					} catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
						showExceptionErrorMessage(e);
					}					
				}
			}
		});
		anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		txtfldSurveyID.requestFocus();
	}

	/**
	 * Shows the modify coordinator screen.
	 *
	 * @param type The type of edit to be done, this could be add or delete 
	 */
	private void showCoordinatorScreen(TypeOfEdit type){
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		TextField txtfldUserID = new TextField();
		TextField txtfldUserName = new TextField();
		PasswordField psswrdfldPassword = new PasswordField();
		txtfldUserID.setPromptText("User ID");
		Button bttntypOK = null;
		GridPane grdpn = new GridPane();
		grdpn.add(txtfldUserID, 1, 1);
		switch(type){
		case Add:
			bttntypOK = new Button("Create");
			txtfldUserName.setPromptText("User name");
			psswrdfldPassword.setPromptText("Password");
			grdpn.add(txtfldUserName, 1, 2);
			grdpn.add(psswrdfldPassword, 1, 3);
			grdpn.add(bttntypOK, 1, 4);
			break;
		case Delete:
			bttntypOK = new Button("Delete");
			grdpn.add(bttntypOK, 1, 2);
			break;		
		}
		bttntypOK.setDefaultButton(true);
		bttntypOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkIfAllDialogHasBeenFilledIn(grdpn))
					showWarningNoDataEntered();
				else{
					try {
						DtCoordinatorID coordID = new DtCoordinatorID(new PtString(txtfldUserID.getText()));
						switch(type){
						case Add:
							if (userController.oeAddCoordinator(txtfldUserID.getText(), txtfldUserName.getText(), psswrdfldPassword.getText()).getValue()){
								listOfOpenWindows.add(new CreateICrashCoordGUI(coordID, systemstateController.getActCoordinator(txtfldUserName.getText())));
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to add coordinator", "An error occured when adding the coordinator");
							break;
						case Delete:
							if (userController.oeDeleteCoordinator(txtfldUserID.getText()).getValue()){
								for(CreateICrashCoordGUI window : listOfOpenWindows){
									if (window.getDtCoordinatorID().value.getValue().equals(coordID.value.getValue()))
										window.closeWindow();
								}
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to delete coordinator", "An error occured when deleting the coordinator");
							break;
						}
					} catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
						showExceptionErrorMessage(e);
					}					
				}
			}
		});
		anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		txtfldUserID.requestFocus();
	}
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logon()
	 */
	@Override
	public void logon() {
		if(txtfldAdminUserName.getText().length() > 0 && psswrdfldAdminPassword.getText().length() > 0){
			try {
				if (userController.oeLogin(txtfldAdminUserName.getText(), psswrdfldAdminPassword.getText()).getValue())
					logonShowPanes(true);
			}
			catch (ServerOfflineException | ServerNotBoundException e) {
				showExceptionErrorMessage(e);
			}	
    	}
    	else
    		showWarningNoDataEntered();
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logoff()
	 */
	@Override
	public void logoff() {
		try {
			if (userController.oeLogout().getValue()){
				logonShowPanes(false);
			}
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		try {
			userController.removeAllListeners();
			systemstateController.closeServerConnection();
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		systemstateController = new SystemStateController();
		logonShowPanes(false);
		setUpTables();
		
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		try {
			if (actor instanceof ActAdministrator)
				try{
					userController = new AdminController((ActAdministrator)actor);
					try{
						userController.getAuthImpl().listOfMessages.addListener(new ListChangeListener<Message>() {
							@Override
							public void onChanged(ListChangeListener.Change<? extends Message> c) {
								addMessageToTableView(tblvwAdminMessages, c.getList());
							}
						});
					} catch (Exception e){
						showExceptionErrorMessage(e);
					}
				}catch (RemoteException e){
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerOfflineException();
				} catch (NotBoundException e) {
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerNotBoundException();
				}
			else
				throw new IncorrectActorException(actor, ActAdministrator.class);
		} catch (ServerOfflineException | ServerNotBoundException | IncorrectActorException e) {
			showExceptionErrorMessage(e);
			return new PtBoolean(false);
		}
		return new PtBoolean(false);
	}	
}
