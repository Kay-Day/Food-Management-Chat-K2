package ChatTCP;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.sound.midi.Receiver;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientChatter extends JFrame {

    private JPanel contentPane;
    private JTextField txtStaff;
    private JTextField txtServerIP;
    private JTextField txtServerPort;

    Socket mngSocket = null;
    String mngIP = null;
    int mngPort = 0;
    String staffName = "";
    BufferedReader bf = null;
    DataOutputStream os = null;
    OutputThread t = null;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientChatter frame = new ClientChatter();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public ClientChatter() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 667, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Staff and server info.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(1, 7, 0, 0));

        JLabel lblNewLabel = new JLabel("Staff:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel);

        txtStaff = new JTextField();
        panel.add(txtStaff);
        txtStaff.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Mng IP:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel_1);

        txtServerIP = new JTextField();
        panel.add(txtServerIP);
        txtServerIP.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Port:");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel_2);

        txtServerPort = new JTextField();
        panel.add(txtServerPort);
        txtServerPort.setColumns(10);

        JFrame thisFame = this;
        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mngIP= txtServerIP.getText();
                mngPort = Integer.parseInt(txtServerPort.getText());
                staffName = txtStaff.getText();
                try {
                    mngSocket = new Socket(mngIP, mngPort);
                    if (mngSocket != null) {
                        ChatPanel p= new ChatPanel(mngSocket, staffName, "Manager");
                        thisFame.getContentPane().add(p);
                        p.getTxtMessages().append("Manager is running");
                        p.updateUI();

                        bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream()));
                        os = new DataOutputStream(mngSocket.getOutputStream());

                        os.writeBytes("Staff: " + staffName);
                        os.write(13);
                        os.write(10);
                        os.flush();
                    }

                } catch (Exception e2) {

                }
            }
        });
        panel.add(btnConnect);
    }

}
