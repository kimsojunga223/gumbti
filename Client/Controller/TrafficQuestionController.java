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

public class TrafficQuestionController {

	@FXML
	private Button train;
	@FXML
	private Button kickBoard;
	@FXML
	private Button bicycle;
	@FXML
	private Button bus;
	@FXML
	private Button car;
	@FXML
	private Button taxi;
	@FXML
	private Button noGo;
	@FXML
	private Button anyWhere;
	@FXML
	private Button airplane;
	@FXML
	private Button seagull;
	@FXML
	private Button ship;
	@FXML
	private Button nextPage;

	public String[] page = {"Question1","Question2", "Question3", "Question4", "Question5"};
	public String answer;
	public String questionId;
	static int count = 0;

	public void trainBtn(MouseEvent event) throws Exception
	{
		answer = train.getText();
		train.setStyle("-fx-background-color: #D070FB");
	}

	public void kickBoardBtn(MouseEvent event) throws Exception
	{
		answer = kickBoard.getText();
		kickBoard.setStyle("-fx-background-color: #D070FB");
	}

	public void bicycleBtn(MouseEvent event) throws Exception
	{
		answer = bicycle.getText();
		bicycle.setStyle("-fx-background-color: #D070FB");
	}

	public void busBtn(MouseEvent event) throws Exception
	{
		answer = bus.getText();
		bus.setStyle("-fx-background-color: #D070FB");
	}

	public void carBtn(MouseEvent event) throws Exception
	{
		answer = car.getText();
		car.setStyle("-fx-background-color: #D070FB");
	}

	public void taxiBtn(MouseEvent event) throws Exception
	{
		answer = taxi.getText();
		taxi.setStyle("-fx-background-color: #D070FB");
	}

	public void noGoBtn(MouseEvent event) throws Exception
	{
		answer = noGo.getText();
		noGo.setStyle("-fx-background-color: #D070FB");
	}

	public void anyWhereBtn(MouseEvent event) throws Exception
	{
		answer = anyWhere.getText();
		anyWhere.setStyle("-fx-background-color: #D070FB");
	}

	public void airplaneBtn(MouseEvent event) throws Exception
	{
		answer = airplane.getText();
		airplane.setStyle("-fx-background-color: #D070FB");
	}

	public void seagullBtn(MouseEvent event) throws Exception
	{
		answer = seagull.getText();
		seagull.setStyle("-fx-background-color: #D070FB");
	}

	public void shipBtn(MouseEvent event) throws Exception
	{
		answer = ship.getText();
		ship.setStyle("-fx-background-color: #D070FB");

	}

	public void loadPage(String page) throws Exception {
		Stage prevStage = (Stage)nextPage.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/"+page+".fxml"));
		Parent root = loader.load();
		Stage newstage = new Stage();
		newstage.setScene(new Scene(root));
		newstage.show();
		prevStage.close();
	}

	public void NextBtn(MouseEvent event) throws Exception
	{
		Stage prevStage = (Stage)nextPage.getScene().getWindow();
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
				String nextCategory = Network.lastCheckReq("1");
				if(nextCategory.equals("99")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/Result.fxml"));
					Parent root = loader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					stage.show();
					prevStage.close();
				}
				else {
					switch (nextCategory) {
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
						case "7": loadPage("Question29");
							break;
						default:
							break;
					}
				}

			}
		}
		else {
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setTitle("Warning Dialog");
			alert1.setHeaderText("Look, a Warning Dialog.");
			alert1.setContentText("분류 선택 실패");
			alert1.showAndWait();
		}
	}
}
