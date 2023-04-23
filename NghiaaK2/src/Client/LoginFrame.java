package Client;

import Model.User;
import Server.DBService;
import View.LoadingForm;
import View.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class LoginFrame extends JFrame{
    private JTextField tFEmail;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel loginPanel;
    private String serviceName =  "rmi://localhost:56789/test";
    private DBService service;

    public LoginFrame(){
        setContentPane(loginPanel);
        setVisible(true);
        setSize(650,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            service = (DBService) Naming.lookup(serviceName);

        }catch (Exception e){
            e.printStackTrace();
        }
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tFEmail.getText();
                String password = pfPassword.getText();
                new LoadingForm(() -> {
                                   if(username.isBlank()|| password.isBlank()){
                   JOptionPane.showMessageDialog(null,"Username or password not be null" );
                   return;
              }else {
                   User user = new User(username, password);
                   try{
                        User user1 = service.login(user);
                       if (user1 != null){
                            JOptionPane.showMessageDialog(null,"Login successfully !");
                           System.out.println(user1.getRole());
                            if (user1.getRole().equals("Admin")) {
                               // qua UI ADMIN
                           } else if (user1.getRole().equals("Guest")) {
                               // qua UI Guest
                           } else if (user1.getRole().equals("Staff")) {
                               // qua UI Staff
                           }
                            MainFrameC mainFrameC = new MainFrameC();
////                            mainFrame.setVisible(true);
                            dispose();

                       }else {
                            JOptionPane.showMessageDialog(null,"Login failed!");

                       }
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                   }
                }

                    dispose();


                });
//                if(username.isBlank()|| password.isBlank()){
//                    JOptionPane.showMessageDialog(null,"Username or password not be null" );
//                    return;
//
//                }else {
//                    User user = new User(username, password);
//                    try{
//                        User user1 = service.login(user);
//                        if (user1 != null){
//                            JOptionPane.showMessageDialog(null,"Login successfully !");
//                            System.out.println(user1.getRole());
//                            if (user1.getRole().equals("Admin")) {
//                                // qua UI ADMIN
//                            } else if (user1.getRole().equals("Guest")) {
//                                // qua UI Guest
//                            } else if (user1.getRole().equals("Staff")) {
//                                // qua UI Staff
//                            }
//                            MainFrameC mainFrameC = new MainFrameC();
////                            mainFrame.setVisible(true);
//                            dispose();
//
//                        }else {
//                            JOptionPane.showMessageDialog(null,"Login failed!");
//
//                        }
//                    }catch (Exception e1){
//                        JOptionPane.showMessageDialog(null, e1.getMessage());
//                    }
//                }

            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBar mn = new MenuBar();
                mn.setVisible(true);
//                SignUpFrame signUpFrame = new SignUpFrame();
//                signUpFrame.setVisible(true);
                dispose();


            }
        });
    }

    public static void main(String[] args) {
//        LoginFrame loginFrame = new LoginFrame();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }
}
