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

public class FacilitiesQuestionController {
	
	 @FXML
	 private Button coinAnswer;
	 @FXML
	 private Button handAnswer;
	 @FXML
	 private Button nailAnswer;
	 @FXML
	 private Button selfAnswer;
	 @FXML
	 private Button noSalonAnswer;
	 @FXML
	 private Button salonAnswer;
	 @FXML
	 private Button shoppingAnswer;
	 @FXML
	 private Button youtudeAnswer;
	 @FXML
	 private Button yesAnswer;
	 @FXML
	 private Button noAnswer;
	 @FXML
	 private Button nextPage;
	 
	 public String[] page = {"Question29","Question30", "Question31", "Question32", "Question33"};
	 public String answer;
	 static int count = 0;
	 
	 public void coinAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = coinAnswer.getText();
		 coinAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void handAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = handAnswer.getText();
		 handAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void nailAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = nailAnswer.getText();
		 nailAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void selfAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = selfAnswer.getText();
		 selfAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void noSalonAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = noSalonAnswer.getText();
		 noSalonAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void salonAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = salonAnswer.getText();
		 salonAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void shoppingAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = shoppingAnswer.getText();
		 shoppingAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void youtudeAnswerBtn(MouseEvent event) throws Exception
	 {
		 answer = youtudeAnswer.getText();
		 youtudeAnswer.setStyle("-fx-background-color: #D070FB");
	 }
	 
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
	            //����ڰ� ���� �о��� ù �������� ���
	            if(count < 5) {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/"+page[count]+".fxml"));
	                Parent root = loader.load();
	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));
	                stage.show();
	                prevStage.close();
	            } else {
	            	count = 0;
					String nextCategory = Network.lastCheckReq("7"); // lastCheckReq�Լ����� ���� ��з�id(body)�� return
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
	                        case "6": loadPage("Question24");
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
