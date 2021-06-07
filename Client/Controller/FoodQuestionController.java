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

public class FoodQuestionController {
	
	 @FXML
	 private Button korean;
	 @FXML
	 private Button westernFood;
	 @FXML
	 private Button Lunch;
	 @FXML
	 private Button japaneseFood;
	 @FXML
	 private Button eat;
	 @FXML
	 private Button make;
	 @FXML
	 private Button out;
	 @FXML
	 private Button noGo;
	 @FXML
	 private Button notBody;
	 @FXML
	 private Button two;
	 @FXML
	 private Button grass;
	 @FXML
	 private Button starve; 
	 @FXML
	 private Button riceCake;
	 @FXML
	 private Button bread; 
	 @FXML
	 private Button nextPage;
	 
	 public String[] page = {"Question11", "Question12", "Question13", "Question14", "Question15"};
	 public String answer;
	 static int count = 0;
	 
	 public void koreanBtn(MouseEvent event) throws Exception
	 {
		 answer = korean.getText();
		 korean.setStyle("-fx-background-color: #D070FB");

	 }
	 
	 public void westernFoodBtn(MouseEvent event) throws Exception
	 {
		 answer = westernFood.getText();
		 westernFood.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void LunchBtn(MouseEvent event) throws Exception
	 {
		 answer = Lunch.getText();
		 Lunch.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void japaneseFoodBtn(MouseEvent event) throws Exception
	 {
		 answer = japaneseFood.getText();
		 japaneseFood.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void eatBtn(MouseEvent event) throws Exception
	 {
		 answer = eat.getText();
		 eat.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void makeBtn(MouseEvent event) throws Exception
	 {
		 answer = make.getText();
		 make.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void outBtn(MouseEvent event) throws Exception
	 {
		 answer = out.getText();
		 out.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void noGoBtn(MouseEvent event) throws Exception
	 {
		 answer = noGo.getText();
		 noGo.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void grassBtn(MouseEvent event) throws Exception
	 {
		 answer = grass.getText();
		 grass.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void starveBtn(MouseEvent event) throws Exception
	 {
		 answer = starve.getText();
		 starve.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void riceCakeBtn(MouseEvent event) throws Exception
	 {
		 answer = riceCake.getText();
		 riceCake.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void breadBtn(MouseEvent event) throws Exception
	 {
		 answer = bread.getText();
		 bread.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void notBodyBtn(MouseEvent event) throws Exception
	 {
		 answer = notBody.getText();
		 notBody.setStyle("-fx-background-color: #D070FB");
	 }
	 
	 public void twoBtn(MouseEvent event) throws Exception
	 {
		 answer = two.getText();
		 two.setStyle("-fx-background-color: #D070FB");
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
					String nextCategory = Network.lastCheckReq("3"); // lastCheckReq�Լ����� ���� ��з�id(body)�� return
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

