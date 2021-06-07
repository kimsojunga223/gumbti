package Client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Network.Network;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

// 결과 컨트롤러
public class ResultController implements Initializable {

    private ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private Label resultDistrict;	// 결과 법정동명
    @FXML
    private Label firstDivision;	// 첫번째 분류
    @FXML
    private Label secondDivision;	// 두번째 분류
    @FXML
    private Label thirdDivision;	// 세번째 분류
    @FXML
    private ListView<String> firstList;	// 첫번째 분류 법정동명 순위
    @FXML
    private ListView<String> secondList;	// 두번째 분류 법정동명 순위
    @FXML
    private ListView<String> thirdList;	// 세번째 분류 법정동명 순위
    @FXML
    private Button detailPage;	// 자세히보기 버튼


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws Exception // 데이터 불러오기
    {
        //선택된 분류 저장
        String[] selectedDivision = Network.selectedDivisionReadReq();
        for (int i = 0; i < selectedDivision.length; i++)
            System.out.println(selectedDivision[i]);


        // 분류들 가져오기
        firstDivision.setText(selectedDivision[0]);
        secondDivision.setText(selectedDivision[1]);
        thirdDivision.setText(selectedDivision[2]);

        //분류 순위 가져오기
        list.removeAll(list); // 리스트 비우기
        list.addAll(Network.divisionListReadReq(0)); // 첫번째 분류 순위list 요청
        firstList.getItems().addAll(list); // 첫번째 분류 법정동명 목록에 추가
        list.removeAll(list); //첫번째 리스트 비우기
        list.addAll(Network.divisionListReadReq(1)); // 두번째 분류 순위list 요청
        secondList.getItems().addAll(list); // 두번째 분류 법정동명 목록에 추가
        list.removeAll(list); //두번째 리스트 비우기
        list.addAll(Network.divisionListReadReq(2)); // 세번째 분류 순위list 요청
        thirdList.getItems().addAll(list); // 세번째 분류 법정동명 목록에 추가

        //최종 결과동 가져오기
        String LastResult = Network.lastResultReadReq();
        // 결과 법정동명 가져오기
        resultDistrict.setText(LastResult);
    }

    // '자세히 보기' 클릭 시 DetailResult.fxml 로드
    public void datailPageClick(MouseEvent evnet) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DetailResult.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}