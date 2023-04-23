package ChatTCP;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatPanel extends JPanel {
    private JTextField txtMessage;

    Socket socket = null;
    BufferedReader bf = null;
    DataOutputStream os = null;
    OutputThread t = null;
    String sender;
    String receiver;
    JTextArea txtMessages;

    public ChatPanel(Socket s, String sender, String reciever) {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(panel, BorderLayout.SOUTH);
        panel.setLayout(new GridLayout(1, 2, 0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);

        txtMessage = new JTextField();
        scrollPane.setViewportView(txtMessage);
        txtMessage.setColumns(10);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtMessage.getText().trim().length() == 0) return;
                try {
                    os.writeBytes(txtMessage.getText());
                    os.write(13);
                    os.write(10);
                    os.flush();
                    txtMessages.append("\n" + sender + ": " + txtMessage.getText());
                    txtMessage.setText("");
                } catch (Exception e2) {
                    // TODO: handle exception
                }
            }
        });
        panel.add(btnSend);

        JScrollPane scrollPane_1 = new JScrollPane();
        add(scrollPane_1, BorderLayout.CENTER);

        txtMessages = new JTextArea();
        scrollPane_1.setViewportView(txtMessages);

        socket = s;
        this.sender= sender;
        this.receiver= reciever;
        try {
            bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new DataOutputStream(socket.getOutputStream());
            t = new OutputThread(s, txtMessages, sender, reciever);
            t.start();
        }
        catch (Exception e) {

        }
    }
    public JTextArea getTxtMessages() {
        return this.txtMessages;


    }

}
