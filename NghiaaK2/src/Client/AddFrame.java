package Client;

import Model.Food;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddFrame extends JFrame {

    private JButton addButton;
    private JButton cancelBtn;
    private JTextField idTF;
    private JTextField nameTF;
    private JTextField PrTF;
    private JTextField DesTF;
    private JPanel addPanel;
    private JPanel iconsPanel;

    public AddFrame(MainFrameC mainFrameC) {
        setVisible(true);
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(addPanel);
        setTitle("Add Product");


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTF.getText();
                String name = nameTF.getText();
                String price = PrTF.getText();
                String description = DesTF.getText();

                if (id.isBlank() || name.isBlank() || price.isBlank() || description.isBlank()) {
                    JOptionPane.showMessageDialog(null, "One of these feild not be empty");
                } else {
                    if (doubleValidate(price)) {
                        Double priceDouble = Double.parseDouble(price);
                        Food food = new Food(id, name, priceDouble, description);
                        if (mainFrameC.addProduct(food)) {
                            JOptionPane.showMessageDialog(null, "Add successfully !");
                            mainFrameC.getFoods();
                            dispose();

                        } else {
                            JOptionPane.showMessageDialog(null, "Add failed !");

                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Price must be double");
                    }
                }

            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private boolean doubleValidate(String num) {
        for (int i = 0; i < num.length(); ++i) {
            if ((num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        AddFrame addFrame = new AddFrame(null);
    }
}
