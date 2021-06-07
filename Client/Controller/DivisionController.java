package Client.Controller;

import Network.Network;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;


public class DivisionController {

   @FXML
   private Button traffic;
   @FXML
   private Button health;
   @FXML
   private Button food;
   @FXML
   private Button culture;
   @FXML
   private Button education;
   @FXML
   private Button animal;
   @FXML
   private Button facilities;
   @FXML
   private Button next;
   
   public String[] page = new String[3];
   public String[] divisionIds = new String[3];
   public int count = 0;
   public boolean toomuch = false;

   public void TrafficBtn(MouseEvent event) throws Exception
   {
      try {
         page[count] = "Question1";
         divisionIds[count++] = "1";
         traffic.setStyle("-fx-background-color: #D070FB");
      } catch(Exception e) {
         toomuch = true;
      }
   }

   public void HealthBtn(MouseEvent event) throws Exception     
   {
     try {
        page[count] = "Question6";
        divisionIds[count++] = "2";
        health.setStyle("-fx-background-color: #D070FB");
     } catch(Exception e) {
        toomuch = true;
     }


   }

   public void FoodBtn(MouseEvent event) throws Exception       
   {
      try {
         page[count] = "Question11";
         divisionIds[count++] = "3";
         food.setStyle("-fx-background-color: #D070FB");
      } catch(Exception e) {
         toomuch = true;
      }
   }

   public void CultureBtn(MouseEvent event) throws Exception        
   {
      try {
         page[count] = "Question16";
         divisionIds[count++] = "4";
         culture.setStyle("-fx-background-color: #D070FB");
      } catch(Exception e) {
         toomuch = true;
      }
   }

   public void EducationBtn(MouseEvent event) throws Exception    
   {
      try {
         page[count] = "Question19";
         divisionIds[count++] = "5";
         education.setStyle("-fx-background-color: #D070FB");
      } catch(Exception e) {
         toomuch = true;
      }
   }

   public void AnimalBtn(MouseEvent event) throws Exception        
   {
      try {
         page[count] = "Question24";
         divisionIds[count++] = "6";
         animal.setStyle("-fx-background-color: #D070FB");
      } catch(Exception e) {
         toomuch = true;
      }

   }

   public void FacilitiesBtn(MouseEvent event) throws Exception       
   {
      try {
         page[count] = "Question29";
         divisionIds[count++] = "7";
         facilities.setStyle("-fx-background-color: #D070FB");
      } catch(Exception e) {
         toomuch = true;
      }
   }

   public void NextBtn(MouseEvent event) throws Exception // 다음페이지 누를 시
   {
      int DivisionResult;
      if(toomuch) {
         DivisionResult = 99;
      } else {
         //네트워크로 분류 id를 배열로 보낸다.
         DivisionResult= Network.divisionChoiceReq(divisionIds);
      }

      if(DivisionResult == 1) {
         //사용자가 누른 분야의 첫 질문지를 출력
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/"+page[0]+".fxml"));
         Parent root = loader.load();
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.show();
      }
      else {
         //분류 경고메시지
         Alert alert1 = new Alert(AlertType.CONFIRMATION);
         alert1.setTitle("Warning Dialog");
         alert1.setHeaderText("Look, a Warning Dialog.");
         alert1.setContentText("분류 선택 실패");
         alert1.showAndWait();
         divisionIds = new String[3];
         page = new String[3];
         count=0;
         toomuch = false;
         traffic.setStyle("-fx-background-color: #7509cd;");
         health.setStyle("-fx-background-color: #7509cd;");
         food.setStyle("-fx-background-color: #7509cd;");
         culture.setStyle("-fx-background-color: #7509cd;");
         education.setStyle("-fx-background-color: #7509cd;");
         animal.setStyle("-fx-background-color: #7509cd;");
         facilities.setStyle("-fx-background-color: #7509cd;");
      }
   }
}
