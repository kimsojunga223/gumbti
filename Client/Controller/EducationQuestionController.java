package Client.Controller;

import Network.Network;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

//���� �о� ��Ʈ�ѷ�
public class EducationQuestionController {

    @FXML
    private Button libraryAnswer;
    @FXML
    private Button bookstoreAnswer;
    @FXML
    private Button nothingAnswer;

    @FXML
    private Button languageAnswer;
    @FXML
    private Button certificateAnswer;

    @FXML
    private Button artAndSportAnswer;
    @FXML
    private Button educationAnswer;


    @FXML
    private Button writingAnswer;
    @FXML
    private Button memorizingAnswer;
    @FXML
    private Button testingAnswer;

    @FXML
    private Button readingroomAnswer;
    @FXML
    private Button cafeAnswer;
    @FXML
    private Button houseAnswer;
	 
    @FXML
	private Button nextPage;

    public String[] page = {"Question19","Question20", "Question21", "Question22", "Question23"};
    public String answer;
    static int count = 0;

    public void libraryAnswerBtn(MouseEvent event) throws Exception // 19
    {
        answer = libraryAnswer.getText();
        libraryAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void bookstoreAnswerBtn(MouseEvent event) throws Exception // 19
    {
        answer = bookstoreAnswer.getText();
        bookstoreAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void nothingAnswerBtn(MouseEvent event) throws Exception // 19
    {
        answer = nothingAnswer.getText();
        nothingAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void languageAnswerBtn(MouseEvent event) throws Exception // 20
    {
        answer = languageAnswer.getText();
        languageAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void certificateAnswerBtn(MouseEvent event) throws Exception // 20
    {
        answer = certificateAnswer.getText();
        certificateAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void artAndSportAnswerBtn(MouseEvent event) throws Exception // 21
    {
        answer = artAndSportAnswer.getText();
        artAndSportAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void educationAnswerBtn(MouseEvent event) throws Exception // 21
    {
        answer = educationAnswer.getText();
        educationAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void writingAnswerBtn(MouseEvent event) throws Exception // 22
    {
        answer = writingAnswer.getText();
        writingAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void memorizingAnswerBtn(MouseEvent event) throws Exception // 22
    {
        answer = memorizingAnswer.getText();
        memorizingAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void testingAnswerBtn(MouseEvent event) throws Exception // 22
    {
        answer = testingAnswer.getText();
        testingAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void readingroomAnswerBtn(MouseEvent event) throws Exception // 22
    {
        answer = readingroomAnswer.getText();
        readingroomAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void cafeAnswerBtn(MouseEvent event) throws Exception // 22
    {
        answer = cafeAnswer.getText();
        cafeAnswer.setStyle("-fx-background-color: #D070FB");
    }

    public void houseAnswerBtn(MouseEvent event) throws Exception // 22
    {
        answer = houseAnswer.getText();
        houseAnswer.setStyle("-fx-background-color: #D070FB");
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
                String nextCategory = Network.lastCheckReq("5"); // lastCheckReq�Լ����� ���� ��з�id(body)�� return
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
                        case "1": loadPage("Question1");  break;
                        case "2": loadPage("Question6");  break;
                        case "3": loadPage("Question11"); break;
                        case "4": loadPage("Question16"); break;
                        case "6": loadPage("Question24"); break;
                        case "7": loadPage("Question29"); break;
                        default: break;
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
