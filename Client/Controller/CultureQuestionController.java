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

public class CultureQuestionController {
	
	 @FXML
	 private Button movieAnswer;
	 @FXML
	 private Button bookStoreAnswer;
	 @FXML
	 private Button shoppingAnswer;
	 @FXML
	 private Button singingroomAnswer;
	 @FXML
	 private Button pcAnswer;
	 @FXML
	 private Button billiardsAnswer;
	 @FXML
	 private Button barAnswer;
	 @FXML
	 private Button nextPage;
	 
	 public String[] page = {"Question16","Question17", "Question18"};
	 public String answer;
	 static int count = 0;
	 
	 public void movieAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = movieAnswer.getText();
		 movieAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void bookStoreAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = bookStoreAnswer.getText();
		 bookStoreAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void shoppingAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = shoppingAnswer.getText();
		 shoppingAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void singingroomAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = singingroomAnswer.getText();
		 singingroomAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void pcAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = pcAnswer.getText();
		 pcAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void billiardsAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = billiardsAnswer.getText();
		 billiardsAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void barAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = barAnswer.getText();
		 barAnswer.setStyle("-fx-background-color: #D070FB");
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
	            if(count < 3) {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/"+page[count]+".fxml"));
	                Parent root = loader.load();
	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));
	                stage.show();
	                prevStage.close();
	            } else {
					count = 0;
	                String nextCategory = Network.lastCheckReq("4"); // lastCheckReq�Լ����� ���� ��з�id(body)�� return
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
	                        case "5": loadPage("Question19");
	                        	break;
	                        case "6": loadPage("Question24");
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

