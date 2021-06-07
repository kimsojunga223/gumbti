package Client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Network.Network;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DetailResultController implements Initializable{

    @FXML
    private Label resultDistrict;	// 결과 법정동명
    @FXML
    private Label districtDetail; //결과동의 상세내용
    @FXML
    private Label busDetail; //결과동의 상세내용
    @FXML
    private ImageView districtImg; //이미지
    @FXML
    private Button againBtn;	//다시하기 버튼
    @FXML
    private Button exitBtn;	//종료하기 버튼


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
        //최종 결과동 가져오기
        String [] finalResult = Network.detailReadReq();
        //이미지 가져오기
        /*
        districtInfo[0] = dto.get(0).getDistrict_name();
		districtInfo[1] = dto.get(0).getPhoto();
		districtInfo[2] = dto.get(0).getExplain();
		districtInfo[3] = dto.get(0).getBusExplain();
        * */
        resultDistrict.setText(finalResult[0]);
        Image img = new Image(finalResult[1]);
        districtImg.setImage(img);

        String[] detailSplit = finalResult[2].split("/");
        StringBuilder detailStr = new StringBuilder();
        for (int i = 0; i < detailSplit.length; i++) {
            detailStr.append(detailSplit[i]);
            detailStr.append("\n");  //this is the new line you need
        }

        String[] busSplit = finalResult[3].split("/");
        StringBuilder busStr = new StringBuilder();
        for (int i = 0; i < busSplit.length; i++) {
            busStr.append(busSplit[i]);
            busStr.append("\n");  //this is the new line you need
        }
        districtDetail.setText(detailStr.toString());
        busDetail.setText(busStr.toString());
    }

    // '다시하기' 클릭 시 StartPage.fxml 로드
    public void againBtnClick(MouseEvent event) throws Exception {
        try {
            Network.again();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DivisionSelect.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // '종료하기' 클릭 시 시스템 종료
    public void exitBtnClick(MouseEvent event) throws IOException {
        try {
            Network.exit(Network.getLocalPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.exit();
    }
}