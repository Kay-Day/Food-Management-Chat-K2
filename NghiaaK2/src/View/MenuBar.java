package View;

import ChatTCP.ClientChatter;
import ChatTCP.ManagerChatter;
import Client.LoginFrame;
import Client.SignUpFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JFrame{
    private JButton LoginBtn;
    private JButton SignupBtn;
    private JPanel Noooo;
    private JPanel MenuBarr;

    public MenuBar() {
        setContentPane(MenuBarr);
        setSize(900,750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadingForm(() -> {
                    LoginFrame lg = new LoginFrame();
                    lg.setVisible(true);
                    dispose();


                });

            }
        });
        SignupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadingForm(() -> {
                    SignUpFrame sg = new SignUpFrame();
                    sg.setVisible(true);
                    dispose();


                });

            }
        });
    }

    public static void main(String[] args) {
        MenuBar mn = new MenuBar();

    }
}
