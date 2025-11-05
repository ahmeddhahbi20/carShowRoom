/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carshowroom;
import tools.ConnectDB;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import tools.Switch;

/**
 *
 * @author dhahb
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button loginButton;
    @FXML
    private Button SignUpButton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button close;
    @FXML
    public void login(ActionEvent event ) throws SQLException, ClassNotFoundException{
        Connection conn=ConnectDB.getCon();
        try{
            String sql="select * from login.admin where username=? and passwordd=?";
            PreparedStatement statement =conn.prepareStatement(sql);
            statement.setString(1,username.getText());
            statement.setString(2,password.getText());
            ResultSet result =statement.executeQuery();
            if(result.next()){
                Getdata.username=username.getText();
                JOptionPane.showMessageDialog(null,"successfully Login","Successfully",JOptionPane.INFORMATION_MESSAGE);
                loginButton.getScene().getWindow().hide();
                Switch.changeToScene(getClass(),event,"Dashboard.fxml" );
            }
            else{
                JOptionPane.showMessageDialog(null, "wrong user name/password","Error", JOptionPane.ERROR_MESSAGE);
            }
    }
        catch(Exception e){e.printStackTrace();}
        
    }
    
    @FXML
    public void goSignUp(ActionEvent event) throws Exception{
        Switch.changeToScene(getClass(),event,"SignUp.fxml");
    }
    
    @FXML
    public void exit(){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
