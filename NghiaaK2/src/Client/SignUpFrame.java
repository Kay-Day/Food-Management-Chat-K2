package Client;

import Model.User;
import Server.DBService;
import View.MenuBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class SignUpFrame extends JFrame{
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JButton registerbtn;
    private JButton cancelbtn;
    private JPanel SignUpF;
    private String serviceName = "rmi://localhost:56789/test";
    private DBService service;

    public SignUpFrame(){
        setContentPane(SignUpF);
        setSize(700,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try{
            service = (DBService) Naming.lookup(serviceName);

        }catch (Exception e){
            e.printStackTrace();
        }
        registerbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTF.getText();
                String password = passwordTF.getText();
                if(username.isBlank()|| password.isBlank()){
                    JOptionPane.showMessageDialog(null,"Username or password not be null");
                    return;
                }else {
                    User user = new User(username, password);
                    try{
                        if(service.signUp(user)){
                            JOptionPane.showMessageDialog(null,"Sign up successfully!");
                            LoginFrame loginFrame = new LoginFrame();
                            loginFrame.setVisible(true);
                            dispose();
                            return;
                        }else {
                            JOptionPane.showMessageDialog(null, "Sign up failed !");
                        }
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }

            }
        });
        cancelbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBar mn = new MenuBar();
                mn.setVisible(true);
//                LoginFrame loginFrame = new LoginFrame();
//                loginFrame.setVisible(true);
                dispose();

            }
        });
    }

    public static void main(String[] args) {
        SignUpFrame signUpFrame = new SignUpFrame();
    }
}
