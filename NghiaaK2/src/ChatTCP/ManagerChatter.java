package ChatTCP;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

public class ManagerChatter extends JFrame implements Runnable {

    private JPanel contentPane;
    private JTextField textServerPort;

    ServerSocket srvSocket = null;
    BufferedReader bf = null;
    Thread t;
    private JTabbedPane tabbedPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ManagerChatter frame = new ManagerChatter();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ManagerChatter() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(1, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("Manager Port:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(lblNewLabel);

        textServerPort = new JTextField();
        textServerPort.setText("12340");
        panel.add(textServerPort);
        textServerPort.setColumns(10);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        this.setSize(600,300);
        int serverPort = Integer.parseInt(textServerPort.getText());
        try {
            srvSocket = new ServerSocket(serverPort);
        } catch (Exception e) {

        }
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket aStaffSocket = srvSocket.accept();
                if (aStaffSocket != null) {
                    bf = new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
                    String S = bf.readLine();
                    int pos = S.indexOf(":");
                    String staffName = S.substring(pos + 1);
                    ChatPanel p = new ChatPanel(aStaffSocket, "Manager", staffName);
                    tabbedPane.add(staffName, p);
                    p.updateUI();
                }
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
    }

}
