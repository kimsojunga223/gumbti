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

public class AnimalQuestionController {
	
	 @FXML
	 private Button yesAnswer;
	 @FXML
	 private Button noAnswer;
	 @FXML
	 private Button dogAnswer;
	 @FXML
	 private Button catAnswer;
	 @FXML
	 private Button petAnswer;
	 @FXML
	 private Button bodyAnswer;
	 @FXML
	 private Button takeOutAnswer;
	 @FXML
	 private Button sorryAnswer;
	 @FXML
	 private Button takeAnswer;
	 @FXML
	 private Button notAnswer;
	 @FXML
	 private Button nextPage;
	 
	 public String[] page = {"Question24","Question25", "Question26", "Question27", "Question28"};
	 public String answer;
	 static int count = 0;
	 
	 public void yesAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = yesAnswer.getText();
		 yesAnswer.setStyle("-fx-background-color: #D070FB");

	 }
	 
	 public void noAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = noAnswer.getText();
		 noAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void dogAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = dogAnswer.getText();
		 dogAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void catAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = catAnswer.getText();
		 catAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void petAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = petAnswer.getText();
		 petAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void bodyAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = bodyAnswer.getText();
		 bodyAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void takeOutAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = takeOutAnswer.getText();
		 takeOutAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void sorryAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = sorryAnswer.getText();
		 sorryAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void takeAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = takeAnswer.getText();
		 takeAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void notAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = notAnswer.getText();
		 notAnswer.setStyle("-fx-background-color: #D070FB");
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
	            //����ڰ� ���� �о��� �������� ���
	            if(count < 5) {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/"+page[count]+".fxml"));
	                Parent root = loader.load();
	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));
	                stage.show();
	                prevStage.close();
	            } else {
					count = 0;
					String nextCategory = Network.lastCheckReq("6"); // lastCheckReq�Լ����� ���� ��з�id(body)�� return
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
	                        case "2": loadPage("Question6");
	                        break;
	                        case "3": loadPage("Question11");
	                        break;
	                        case "4": loadPage("Question16");
	                        break;
	                        case "5": loadPage("Question19");
	                        break;
	                        case "7": loadPage("Question29");
	                        break;
	                        default:
	                        break;
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
