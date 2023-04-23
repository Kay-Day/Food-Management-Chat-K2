package Client;

import Model.Food;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditFrame extends JFrame {
    private JPanel EditPanel;
    private JTextField idTF;
    private JTextField nameTF;
    private JTextField PrTF;
    private JTextField DesTF;
    private JButton editBtn;
    private JButton cancelBtn;

    public EditFrame(MainFrameC mainFrameC,Food food){
        setContentPane(EditPanel);
        setVisible(true);
        setSize(650,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        idTF.setEnabled(false);
        idTF.setText(food.getId());
        nameTF.setText(food.getName());
        PrTF.setText(food.getPrice().toString());
        DesTF.setText(food.getDescription());
        editBtn.addActionListener(new ActionListener() {
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
                        if (mainFrameC.editProduct(food)) {
                            JOptionPane.showMessageDialog(null, "Edit successfully!");
                            mainFrameC.getFoods();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Edit failed!");
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
        for (int i=0; i<num.length(); ++i) {
            if ((
                    num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

    }

}
