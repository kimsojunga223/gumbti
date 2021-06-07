package Client.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FirstScreenController{
	
	@FXML
	private Button startBtn;
	@FXML
	private AnchorPane first;
	
	public void StartBtn(MouseEvent event) throws Exception // 시작하기 버튼
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/DivisionSelect.fxml"));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
}

