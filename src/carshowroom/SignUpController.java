/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carshowroom;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import tools.ConnectDB;
import tools.Switch;
import tools.Switchadv;

/**
 * FXML Controller class
 *
 * @author dhahb
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField Vpassword;
    @FXML
    private Button SignUpButton;
    @FXML
    private Button close;
    @FXML
    private Button goback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void goBack(ActionEvent event) throws Exception{
    Stage currstage=(Stage) SignUpButton.getScene().getWindow();
    currstage.hide();
    Switch.changeToScene(getClass(),event,"FXMLDocument.fxml" );
}
    @FXML
    private void SignUp(ActionEvent event) throws SQLException, ClassNotFoundException, Exception {
        Connection con=ConnectDB.getCon();
        
        if (password.getText().equals(Vpassword.getText())){
         
             String checkSql = "SELECT * FROM login.admin WHERE username = ?";
        PreparedStatement checkStatement = con.prepareStatement(checkSql);
        checkStatement.setString(1, userName.getText());
        ResultSet resultSet = checkStatement.executeQuery();

        if (resultSet.next()) {
        // User already exists
        JOptionPane.showMessageDialog(null, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        Stage currstage=(Stage) SignUpButton.getScene().getWindow();
        currstage.hide();
        // Switch to the login form
         Switch.changeToScene(getClass(),event,"FXMLDocument.fxml" );
        }
        else {
        try{
            String sql="insert into login.admin values (?,?);";
            PreparedStatement statement =con.prepareStatement(sql);
            statement.setString(1,userName.getText());
            statement.setString(2,password.getText());
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                JOptionPane.showMessageDialog(null,"successfully SignedUP","Welcome",JOptionPane.INFORMATION_MESSAGE);
                SignUpButton.getScene().getWindow().hide();
                Switch.changeToScene(getClass(),event,"FXMLDocument.fxml" );
            }
            else{
                JOptionPane.showMessageDialog(null, "wrong user name/password","my message", JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(Exception e){e.printStackTrace();
        JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }}}
        else{
            JOptionPane.showMessageDialog(null,"Password INVALID ","ERROR PWD",JOptionPane.ERROR_MESSAGE);
        }
        
        
    }

    @FXML
    private void fermer(ActionEvent event) {
        System.exit(0);
    }
    
}
