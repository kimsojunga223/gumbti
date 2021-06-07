package Client.Controller;

import Network.Network;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HealthQuestionController {
	
	 @FXML
	 private Button mountain;
	 @FXML
	 private Button trip;
	 @FXML
	 private Button park;
	 @FXML
	 private Button home;
	 @FXML
	 private Button yes;
	 @FXML
	 private Button no;
	 @FXML
	 private Button exercise;
	 @FXML
	 private Button health;
	 @FXML
	 private Button nutrients;
	 @FXML
	 private Button patient;
	 @FXML
	 private Button hospital;
	 @FXML
	 private Button pharmacy; 
	 @FXML
	 private Button noMoney;
	 @FXML
	 private Button gym; 
	 @FXML
	 private Button nextPage;
	 
	 public String[] page = {"Question6","Question7", "Question8", "Question9", "Question10"};
	 public String answer;
	 static int count = 0;
	 
	 public void mountainBtn(MouseEvent event) throws Exception
	 {
		 answer = mountain.getText();
		 mountain.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void tripBtn(MouseEvent event) throws Exception
	 {
		 answer = trip.getText();
		 trip.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void parkBtn(MouseEvent event) throws Exception
	 {
		 answer = park.getText();
		 park.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void homeBtn(MouseEvent event) throws Exception
	 {
		 answer = home.getText();
		 home.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void yesBtn(MouseEvent event) throws Exception
	 {
		 answer = yes.getText();
		 yes.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void noBtn(MouseEvent event) throws Exception
	 {
		 answer = no.getText();
		 no.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void exerciseBtn(MouseEvent event) throws Exception
	 {
		 answer = exercise.getText();
		 exercise.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void healthBtn(MouseEvent event) throws Exception
	 {
		 answer = health.getText();
		 health.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void nutrientsBtn(MouseEvent event) throws Exception
	 {
		 answer = nutrients.getText();
		 nutrients.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void patientBtn(MouseEvent event) throws Exception
	 {
		 answer = patient.getText();
		 patient.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void hospitalBtn(MouseEvent event) throws Exception
	 {
		 answer = hospital.getText();
		 hospital.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void pharmacyBtn(MouseEvent event) throws Exception
	 {
		 answer = pharmacy.getText();
		 pharmacy.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void noMoneyBtn(MouseEvent event) throws Exception
	 {
		 answer = noMoney.getText();
		 noMoney.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void gymBtn(MouseEvent event) throws Exception
	 {
		 answer = gym.getText();
		 gym.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void loadPage(String page) throws Exception {
		 Stage prevStage = (Stage)nextPage.getScene().getWindow();
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/"+page+".fxml"));
         Parent root = loader.load();
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.show();
         prevStage.close();
	 }
	 
	 public void NextBtn(MouseEvent event) throws Exception // ���������� ���� ��
	    {
		 Stage prevStage = (Stage)nextPage.getScene().getWindow();
	        //��Ʈ��ũ�� �з� id�� �迭�� ������.
	        int answerResult = Network.answerChoiceReq(answer);
	        if(answerResult == 1) {
	            count++;
	            if(count < 5) {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/"+page[count]+".fxml"));
	                Parent root = loader.load();
	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));
	                stage.show();
	                prevStage.close();
	            } else {
					count = 0;
					String nextCategory = Network.lastCheckReq("2"); // lastCheckReq�Լ����� ���� ��з�id(body)�� return
					if(nextCategory.equals("99")) {
						// 결과
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/Result.fxml"));
						Parent root = loader.load();
						Stage stage = new Stage();
						stage.setScene(new Scene(root));
						stage.show();
						prevStage.close();
	                }
	                else {
	                    switch (nextCategory) {
                        	case "1": loadPage("Question1");
                        	break;
	                        case "3": loadPage("Question11");
	                        break;
	                        case "4": loadPage("Question16");
	                        break;
	                        case "5": loadPage("Question19");
	                        break;
	                        case "6": loadPage("Question24");
	                        break;
	                        case "7": loadPage("Question29");
	                        break;
	                        default:
	                    }
	                }

	            }
	        }
	        else {
	            //�з� ���޽���
	            Alert alert1 = new Alert(AlertType.CONFIRMATION);
	            alert1.setTitle("Warning Dialog");
	            alert1.setHeaderText("Look, a Warning Dialog.");
	            alert1.setContentText("답변 선택 실패");
	            alert1.showAndWait();
	        }
	    }
}
