/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carshowroom;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.ConnectDB;
import tools.Switch;
import tools.Switchadv;

/**
 * FXML Controller class
 *
 * @author dhahb
 */
public class DashboardController implements Initializable {

    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private Label home_usernamelabel;
    @FXML
    private Button home_homebtn;
    @FXML
    private Button logoutbtn;
    @FXML
    private Button home_avcbtn;
    @FXML
    private Button home_rentbtn;
    @FXML
    private AnchorPane home_availablecars;
    @FXML
    private AnchorPane home_totalincome;
    @FXML
    private AnchorPane home_totalcustomers;
    @FXML
    private BarChart<?, ?> home_incomechart;
    @FXML
    private LineChart<?, ?> home_customedata;
    @FXML
    private AnchorPane avc_form;
    @FXML
    private TextField availablecar_carid;
    @FXML
    private TextField avc_Brand;
    @FXML
    private TextField avc_Model;
    @FXML
    private ComboBox<?> avc_Status;
    @FXML
    private ImageView avc_imageview;
    @FXML
    private Button avc_imporbtn;
    @FXML
    private Button avc_insertbtn;
    @FXML
    private Button avc_updatebtn;
    @FXML
    private Button avc_deletebtn;
    @FXML
    private Button avc_clearbtn;
    @FXML
    private TextField avc_price;
    @FXML
    private TableView<Cardata> avc_tableview;
    @FXML
    private TableColumn<Cardata, Integer> avc_col_carid;
    @FXML
    private TableColumn<Cardata, String> avc_col_brand;
    @FXML
    private TableColumn<Cardata, String> avc_col_model;
    @FXML
    private TableColumn<Cardata, Double> avc_col_price;
    @FXML
    private TableColumn<Cardata, String> avc_col_status;
    @FXML
    private TextField avc_search;
    @FXML
    private AnchorPane rent_form;
    @FXML
    private ComboBox<?> carID;
    @FXML
    private ComboBox<?> Brand;
    @FXML
    private ComboBox<?> model;
    @FXML
    private ComboBox<?> Gender;
    @FXML
    private TextField FirstName;
    @FXML
    private TextField LastName;
    @FXML
    private DatePicker date_rented;
    @FXML
    private DatePicker date_returned;
    @FXML
    private Button rentButton;
    @FXML
    private Label totalLabel;
    @FXML
    private TextField amountField;
    @FXML
    private Label BalanceLabel;
    @FXML
    private TableView<Cardata> rent_table_view;
    @FXML
    private TableColumn<Cardata, String> col_carId;
    @FXML
    private TableColumn<Cardata, String> col_Brand;
    @FXML
    private TableColumn<Cardata, String> col_Model;
    @FXML
    private TableColumn<Cardata, String> col_Price;
    @FXML
    private TableColumn<Cardata,String> col_Status;
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane home_form;

    /**
     * Initializes the controller class.
     */
    //    DATABASE TOOLS
    private Connection conn;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    ////
    private Image image;
    @FXML
    private Label avc_count;
    private String[] listStatus = {"Available", "Not Available"};
    private String[] lgender = {"MALE","FEMALE"};
    @FXML
    private TextField CIN;
    @FXML
    private Label home_total;
    @FXML
    private Button Addcustomer;
    @FXML
    private Label home_tot_customers;
    
public void homeTotalIncome() throws SQLException, ClassNotFoundException{
        String sql = "SELECT SUM(total) FROM fiche";
        
        double sumIncome = 0;
        
        conn = ConnectDB.getCon();
        
        try{
            prepare = conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                sumIncome = result.getDouble("SUM(total)");
            }
            home_total.setText("$" + String.valueOf(sumIncome));
        }catch(Exception e){e.printStackTrace();}
    }
 public void homeAvailableCars2() throws SQLException, ClassNotFoundException{
        
        String sql = "SELECT COUNT(car_id) FROM car WHERE status = 'Available'";
        
        conn = ConnectDB.getCon();
        int countAC = 0;
        try{
            prepare = conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countAC = result.getInt("COUNT(car_id)");
            }
            
            avc_count.setText(String.valueOf(countAC));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
 public void homeTotalCustomers() throws SQLException, ClassNotFoundException{
        
        String sql = "SELECT COUNT(customer_id) FROM customers";
        
        int countTC = 0;
        
        conn = ConnectDB.getCon();
        
        try{
            prepare = conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countTC = result.getInt("COUNT(customer_id)");
            }
            home_tot_customers.setText(String.valueOf(countTC));
        }catch(Exception e){e.printStackTrace();}
        
    }
public void homeIncomeChart() throws SQLException, ClassNotFoundException{
        
        home_incomechart.getData().clear();
        
        String sql = "SELECT date_rented, SUM(total) FROM fiche GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 6";
        
        conn = ConnectDB.getCon();
        
        try{
            XYChart.Series chart = new XYChart.Series();
            
            prepare = conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            home_incomechart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
    }

 public void homeCustomerChart() throws SQLException, ClassNotFoundException{
        home_customedata.getData().clear();
        
        String sql = "SELECT date_rented, COUNT(customer_id) FROM fiche GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 4";
        
        conn = ConnectDB.getCon();
        
        try{
            XYChart.Series chart = new XYChart.Series();
            
            prepare = conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            home_customedata.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    
    @FXML
    public void addCustomer() throws SQLException, ClassNotFoundException{
    String sql="insert into customers(CIN,nom,prenom,gender) values(?,?,?,?);";
    conn = ConnectDB.getCon();
    try{
        Alert alert;
        if(FirstName.getText().isEmpty()
                    || LastName.getText().isEmpty()
                    || Gender.getSelectionModel().getSelectedItem() == null
                    ||CIN.getText().isEmpty()||CIN.getText().length()!=8){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Verify and Fill All the fields !");
                alert.showAndWait();
        }
        else{
        if(userExist()==0){
        prepare=conn.prepareStatement(sql);
        prepare.setString(1,CIN.getText());
        prepare.setString(2,LastName.getText());
        prepare.setString(3,FirstName.getText());
        prepare.setString(4, (String) Gender.getSelectionModel().getSelectedItem());
        int nbrows=prepare.executeUpdate();
        if (nbrows>0){
            alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added");
            alert.showAndWait();
        }}
        else{
             alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("User Exists already");
            alert.showAndWait();
            CIN.setText("");
            FirstName.setText("");
            LastName.setText("");
            Gender.getSelectionModel().clearSelection();
        }
    }}
    catch(Exception e)
    {e.printStackTrace();}
}
public int userExist() throws SQLException, ClassNotFoundException{
    String sql="select customer_id from customers where CIN='"+CIN.getText()+"';";
    conn =ConnectDB.getCon();
    try{
        prepare=conn.prepareStatement(sql);
        result=prepare.executeQuery();
        if(result.next()){
            return (result.getInt("customer_id"));
        }
        else{
            return (0);
        }
        
    }catch(Exception e){
        e.printStackTrace();
    }
        return 0;
   
}

public void clearRent(){
    CIN.setText("");
    FirstName.setText("");
    LastName.setText("");
    Gender.getSelectionModel().clearSelection();
    carID.getSelectionModel().clearSelection();
    Brand.getSelectionModel().clearSelection();
    model.getSelectionModel().clearSelection();
    amountField.setText("");
    date_rented.setValue(null);
    date_returned.setValue(null);
    total=0;
    BalanceLabel.setText("");
    totalLabel.setText("");
    amount = 0;
    balance = 0;
}
    @FXML
public void rentPay() throws SQLException, ClassNotFoundException{
    String sql = "INSERT INTO fiche (customer_id, nom, prenom, gender, car_id, total, date_rented, date_return) VALUES (?, ?, ?, ?, ?, ?, ?, ?); ";
    String sqlCheck = "SELECT COUNT(*) FROM fiche WHERE customer_id = ? AND date_rented = ? ;";
    int idcustomer ;
    conn = ConnectDB.getCon();
    try{
        Alert alert;
            
            if(FirstName.getText().isEmpty()
                    || LastName.getText().isEmpty()
                    || Gender.getSelectionModel().getSelectedItem() == null
                    || carID.getSelectionModel().getSelectedItem() == null
                    || Brand.getSelectionModel().getSelectedItem() == null
                    || model.getSelectionModel().getSelectedItem() == null
                    || total == 0 || amountField.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Fill All the fields !");
                alert.showAndWait();
            }
            else{
        if( userExist()==0){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Add this User!");
                alert.showAndWait();
                CIN.setText("");
                clearRent();
                
                
        }     
            else {
            alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
            idcustomer = userExist();
            prepare=conn.prepareStatement(sql);
            prepare.setInt(1,idcustomer);
            prepare.setString(2,LastName.getText());
            prepare.setString(3,FirstName.getText());
            prepare.setString(4, (String) Gender.getSelectionModel().getSelectedItem()); 
            prepare.setString(5,(String) carID.getSelectionModel().getSelectedItem());
            prepare.setDouble(6,total);
            prepare.setString(7,String.valueOf(date_rented.getValue()));
            prepare.setString(8,String.valueOf(date_returned.getValue()));
            try{
            int nb = prepare.executeUpdate();
            if(nb>0){
            sql="update car set status='Not Available' where car_id="+carID.getSelectionModel().getSelectedItem()+";";
            statement=conn.createStatement();
            statement.executeUpdate(sql);
            alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added");
            alert.showAndWait();
            
            
            showRentcarListData();
            clearRent();
        }
            }catch(SQLException e){}
            }}
        
        }}catch(ClassNotFoundException | SQLException e)
            {};
}





 
private double amount;
private double balance;
    @FXML
    public void rentAmount(){
    Alert alert;
    if (total == 0 || amountField.getText().isEmpty())   {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR MESSAGE");
        alert.setHeaderText(null);
        alert.setContentText("Invalid Entry");
        alert.showAndWait();
        amountField.setText("");
        
    }
    else{
       amount=Double.parseDouble(amountField.getText());
       if (amount>=total){
            balance=amount-total;
            BalanceLabel.setText("$"+String.valueOf(balance));
    }
       else{
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("insufficient amount");
            alert.showAndWait();
            amountField.setText("");
            
       }
    }   
}
private int countdate;
public void rentDate(){
    Alert alert;
    if(carID.getSelectionModel().getSelectedItem()==null || Brand.getSelectionModel().getSelectedItem()==null
       || model.getSelectionModel().getSelectedItem()==null){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR MESSAGE");
        alert.setHeaderText(null);
        alert.setContentText("Something wrong");
        alert.showAndWait();
        date_returned.setValue(null);
        date_rented.setValue(null);
    }
    else{
        if(date_returned.getValue().isAfter(date_rented.getValue())){
            countdate=date_returned.getValue().compareTo(date_rented.getValue());
        }
        else{
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Something wrong");
            alert.showAndWait();
            date_returned.setValue(date_rented.getValue().plusDays(1));
        }
    }
}

private double total;
    @FXML
    public void displayTotal() throws SQLException, ClassNotFoundException{
    rentDate();
    double price=0;
    String sql ="select price from car where car_id='"+carID.getSelectionModel().getSelectedItem()+"';";
    conn=ConnectDB.getCon();
    try{
        prepare=conn.prepareStatement(sql);
        result=prepare.executeQuery();
        while(result.next()){
            price=result.getDouble("price");
        }
        total=(price *countdate);
        totalLabel.setText("$"+String.valueOf(total));
        
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
  
public void rentCarSetGender(){
    List<String> listG=new ArrayList<>();
    for (String S : lgender){
        listG.add(S);
    }
    ObservableList listData=FXCollections.observableArrayList(listG);
    
    Gender.setItems(listData);
}
    @FXML
    public void rentCarBrand() throws SQLException, ClassNotFoundException{
    String sql="select brand from car where car_id= "+carID.getSelectionModel().getSelectedItem()+";";
    conn=ConnectDB.getCon();
    try{
        prepare=conn.prepareStatement(sql);
        result=prepare.executeQuery();
        ObservableList listData=FXCollections.observableArrayList();
        while(result.next()){
            listData.add(result.getString("brand"));
        }
        Brand.setItems(listData);
        rentCarModel();
    }catch(Exception e){e.printStackTrace();}
}

    @FXML
    public void rentCarModel() throws SQLException, ClassNotFoundException{
    String sql="select model from car where brand= '"+Brand.getSelectionModel().getSelectedItem()+"';";
    conn=ConnectDB.getCon();
    try{
        prepare=conn.prepareStatement(sql);
        result=prepare.executeQuery();
        ObservableList listData=FXCollections.observableArrayList();
        while(result.next()){
            listData.add(result.getString("model"));
        }
        model.setItems(listData);
    }catch(Exception e){e.printStackTrace();}
}

    @FXML
    public void rentCarID() throws SQLException, ClassNotFoundException{
    String sql="select car_id from car where status='Available';";
    conn=ConnectDB.getCon();
    try{
        prepare=conn.prepareStatement(sql);
        result=prepare.executeQuery();
        
        ObservableList listData=FXCollections.observableArrayList();
        while (result.next()){
            listData.add(result.getString("car_id"));
            
        }
        carID.setItems(listData);
        rentCarBrand();
         
    }catch(Exception e)
    {
        e.printStackTrace();
    }
    
}
//RentCar Form 
 public ObservableList<Cardata> rentCarList() throws SQLException, ClassNotFoundException{  
        ObservableList<Cardata> listData=FXCollections.observableArrayList();
        String sql="select * from car where status='Available' ;";
        conn=ConnectDB.getCon();
        try{
            prepare=conn.prepareStatement(sql);
            result=prepare.executeQuery();
            Cardata carD;
            while(result.next()){
                carD=new Cardata(result.getInt("car_id"),result.getString("brand"),result.getString("model")
                        ,result.getDouble("price"),result.getString("status"),result.getString("image"),result.getDate("date"));
                listData.add(carD);
            }
        }catch(Exception e)
        {e.printStackTrace();}
       return listData;
     
 }

 private ObservableList<Cardata> rentCarList;
 public void showRentcarListData() throws SQLException, ClassNotFoundException{
     rentCarList =rentCarList();
     col_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
     col_Brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
     col_Model.setCellValueFactory(new PropertyValueFactory<>("model"));
     col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
     col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
     
     rent_table_view.setItems(rentCarList);
     
 }
//AvailableCar Form 
@FXML
public void searchBar(){
    FilteredList<Cardata> filter =new FilteredList<>(availableCarList,e -> true);
    avc_search.textProperty().addListener((Observable,oldValue,newValue)->{
        filter.setPredicate(predicateCardata->{
            if(newValue==null || newValue.isEmpty()){
                return true;
            }
            String searchKey = newValue.toLowerCase();
            
            if (predicateCardata.getCarId().toString().contains(searchKey)){
                return true;
            }
            else if(predicateCardata.getBrand().toLowerCase().contains(searchKey)){
                return true;
            }
            else if(predicateCardata.getModel().toLowerCase().contains(searchKey)){
                return true;
            }
            else if(predicateCardata.getPrice().toString().contains(searchKey)){
                return true;
            }
            else if(predicateCardata.getStatus().toLowerCase().contains(searchKey)){
                return true;
            }
            else return false;
        });
    });
    SortedList<Cardata> sortList = new SortedList<>(filter);
    sortList.comparatorProperty().bind(avc_tableview.comparatorProperty());
    avc_tableview.setItems(sortList);

    }
    

@FXML
public void availableCarDelete() throws SQLException, ClassNotFoundException{
    String sql="delete from car where car_id=?;";
    conn=ConnectDB.getCon();
        try{
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Car ID: " + availablecar_carid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
             if (option.get().equals(ButtonType.OK))
                {
                prepare=conn.prepareStatement(sql);
                prepare.setString(1, availablecar_carid.getText());
                int nbrowsaffected=prepare.executeUpdate();
                if (nbrowsaffected>0){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    
                    availableCarShowListData();
                    availableCarClear();
                
                }
            
                 }
            }
        catch(Exception e){
            e.printStackTrace();
        }
      }
    
    
    @FXML
public void availableCarStatusList() {

        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        avc_Status.setItems(listData);
    }
@FXML
public void availableCarImportImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            Getdata.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 137, 153, false, true);
            avc_imageview.setImage(image);

        }

    }    
@FXML
public void availableCarUpdate() throws SQLException, ClassNotFoundException {

        String uri = Getdata.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE car SET brand = '" + avc_Brand.getText() + "', model = '"
                + avc_Model.getText() + "', status ='"
                + avc_Status.getSelectionModel().getSelectedItem() + "', price = '"
                + avc_price.getText() + "', image = '" + uri
                + "' WHERE car_id = '" + availablecar_carid.getText() + "'";

        conn = ConnectDB.getCon();

        try {
            Alert alert;

            if (availablecar_carid.getText().isEmpty()
                    || avc_Brand.getText().isEmpty()
                    || avc_Model.getText().isEmpty()
                    || avc_Status.getSelectionModel().getSelectedItem() == null
                    || avc_price.getText().isEmpty()
                    || Getdata.path == null || Getdata.path.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Car ID: " + availablecar_carid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = conn.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    availableCarShowListData();
                    availableCarClear();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
@FXML
public void availableCarAdd() throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO car (car_id, brand, model, price, status, image, date) "
                + "VALUES(?,?,?,?,?,?,?)";

        conn = ConnectDB.getCon();

        try {
            Alert alert;

            if (availablecar_carid.getText().isEmpty()
                    || avc_Brand.getText().isEmpty()
                    || avc_Model.getText().isEmpty()
                    || avc_Status.getSelectionModel().getSelectedItem() == null
                    || avc_price.getText().isEmpty()
                    || Getdata.path == null || Getdata.path.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else 
            {
                prepare = conn.prepareStatement(sql);
                prepare.setString(1, availablecar_carid.getText());
                prepare.setString(2, avc_Brand.getText());
                prepare.setString(3, avc_Model.getText());
                prepare.setString(4, avc_price.getText());
                prepare.setString(5, (String) avc_Status.getSelectionModel().getSelectedItem());

                String uri = Getdata.path;
                uri = uri.replace("\\", "\\\\");

                prepare.setString(6, uri);
                //System date 
                Date date = new Date();
                //conversion vers type sql
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(7, String.valueOf(sqlDate));
                //executer la requete
                int rowsAffected = prepare.executeUpdate();
                if(rowsAffected > 0)
                {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();
                availableCarShowListData();
                availableCarClear();
                }
            
                else
                {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Can't Add This Car");
                alert.showAndWait();
                }
        }} 
        catch (Exception e) {
            e.printStackTrace();
        }

}
        @FXML
public void availableCarClear() {
        availablecar_carid.setText("");
        avc_Brand.setText("");
        avc_Model.setText("");
        avc_Status.getSelectionModel().clearSelection();
        avc_price.setText("");

        Getdata.path = "";

        avc_imageview.setImage(null);

    }

    
    @FXML
public void availableCarSelect() {
        Cardata carD = avc_tableview.getSelectionModel().getSelectedItem();
        int num = avc_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        availablecar_carid.setText(String.valueOf(carD.getCarId()));
        avc_Brand.setText(carD.getBrand());
        avc_Model.setText(carD.getModel());
        avc_price.setText(String.valueOf(carD.getPrice()));

        Getdata.path = carD.getImage();

        String uri = "file:" + carD.getImage();

        image = new Image(uri, 116, 153, false, true);
        avc_imageview.setImage(image);

    }
    

    
    
    
public ObservableList<Cardata> availableCarListData() throws SQLException, ClassNotFoundException {
    ObservableList<Cardata> listData = FXCollections.observableArrayList();
    String sql = "SELECT * FROM car";
    conn=ConnectDB.getCon();
    try {
            prepare = conn.prepareStatement(sql);
            result = prepare.executeQuery();

            Cardata carD;
            while (result.next()) {
                carD = new Cardata(result.getInt("car_id"),
                         result.getString("brand"),
                         result.getString("model"),
                         result.getDouble("price"),
                         result.getString("status"),
                         result.getString("image"),
                         result.getDate("date"));

                listData.add(carD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    

private ObservableList<Cardata> availableCarList;
public void availableCarShowListData() throws SQLException, ClassNotFoundException  {
        availableCarList = availableCarListData();
        avc_col_carid.setCellValueFactory(new PropertyValueFactory<>("carId"));
        avc_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        avc_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        avc_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        avc_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        avc_tableview.setItems(availableCarList);
    }
    
//--------------------------------------------------------
public void homeAvailableCars() throws SQLException, ClassNotFoundException{
        
        String sql = "SELECT COUNT(id) FROM car WHERE status = 'Available'";
        
        conn = ConnectDB.getCon();
        int countAC = 0;
        try{
            prepare = conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countAC = result.getInt("COUNT(id)");
            }
            
            avc_count.setText(String.valueOf(countAC));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
public void displayUsername() {
        String user = Getdata.username;
        // TO SET THE FIRST LETTER TO BIG LETTER
        home_usernamelabel.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }
    @FXML
    public void logout() throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                try {
                    // Retrieve the current stage
                    Stage currentStage = (Stage) logoutbtn.getScene().getWindow();
                // Hide the current window
                currentStage.hide();

            // Switch to the login form
            Switchadv.changeToSceneWithoutEvent(getClass(), currentStage, "FXMLDocument.fxml");
            }
            catch (Exception e) {
            e.printStackTrace();
        }
    }}
    @FXML
public void switchForm(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (event.getSource() == home_homebtn) {
            home_form.setVisible(true);
            avc_form.setVisible(false);
            rent_form.setVisible(false);

            home_homebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
            home_avcbtn.setStyle("-fx-background-color:transparent");
            home_rentbtn.setStyle("-fx-background-color:transparent");

            homeTotalIncome();
            homeAvailableCars2();
            homeTotalCustomers();
            homeIncomeChart();
            homeCustomerChart();
            
        } else if (event.getSource() == home_avcbtn) {
            home_form.setVisible(false);
            avc_form.setVisible(true);
            rent_form.setVisible(false);

            home_avcbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
            home_homebtn.setStyle("-fx-background-color:transparent");
            home_rentbtn.setStyle("-fx-background-color:transparent");

            // TO UPDATE YOUR TABLEVIEW ONCE YOU CLICK THE AVAILABLE CAR NAV BUTTON
            availableCarShowListData();
            availableCarStatusList();
            searchBar();

        } else if (event.getSource() == home_rentbtn) {
            home_form.setVisible(false);
            avc_form.setVisible(false);
            rent_form.setVisible(true);

            home_rentbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
            home_homebtn.setStyle("-fx-background-color:transparent");
            home_avcbtn.setStyle("-fx-background-color:transparent");
            
            showRentcarListData();
            
            rentCarID();
            rentCarBrand();
            rentCarModel();
            rentCarSetGender();
            

        }

    }
    @FXML
 public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();
        try{
            availableCarShowListData();
            showRentcarListData();
            rentCarID();
            rentCarBrand();
            rentCarModel();
            rentCarSetGender();
            homeTotalIncome();
            homeAvailableCars2();
            homeTotalCustomers();
            homeIncomeChart();
            homeCustomerChart();
        }
        catch(Exception e){e.printStackTrace();}  
        availableCarStatusList();
        searchBar();
        
}

    
}
