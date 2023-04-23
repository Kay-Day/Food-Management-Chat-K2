package View;

import Client.OnLoading;

import javax.swing.*;
import java.util.List;

public class LoadingForm extends JFrame {

    public JProgressBar proBar;
    public JPanel Bar;
    public JLabel Pers;

    public LoadingForm(OnLoading onLoading) {
        setContentPane(Bar);
        setSize(510, 450);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(10);
                        publish(i);
                    }
                } catch (Exception e) {

                }
                return null;
            }
            @Override
            protected void process(List<Integer> chunks) {
                super.process(chunks);
                proBar.setValue(chunks.get(0));
                Pers.setText(Integer.toString(chunks.get(0)) + "%");
            }

            @Override
            protected void done() {
                super.done();
                dispose();
                onLoading.onClick();
            }
        };
        worker.execute();

    }



}
