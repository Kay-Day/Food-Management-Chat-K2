package Client;

import Model.Food;
import Server.DBService;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Vector;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private Vector header = new Vector();
    private Vector data = null;
    private String serviceName = "rmi://localhost:56789/test";
    private DBService service;
    private JButton deleteButton;
    private JButton newButton;
    private JButton editButton;
    private JButton btnNewButton;
    private JButton btnNewButton_1;


    public MainFrame() {
        try {
            service = (DBService) Naming.lookup(serviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(true);
        setTitle("Product management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 450, 300);
        setSize(700,450);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        deleteButton = new JButton("Delete");
//        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        newButton = new JButton("New");
//        newButton.addActionListener(this);
        panel.add(newButton);

        editButton = new JButton("Edit");
//        editButton.addActionListener(this);
        panel.add(editButton);

        btnNewButton = new JButton("Statistics");
        panel.add(btnNewButton);

        btnNewButton_1 = new JButton("Chat");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel.add(btnNewButton_1);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        data = new Vector();
        table = new JTable();
        scrollPane.setViewportView(table);
        getProducts();
        header.add("Id");
        header.add("Name");
        header.add("Price");
        header.add("Desciption");
        DefaultTableModel dModel = (DefaultTableModel) table.getModel();
        dModel.setDataVector(data, header);
    }
    public boolean deleteProduct(Food food){
        try{
            return service.deleteProduct(food);

        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public void getProducts(){
        try{
            Vector<Food> foods = service.getAllProducts();
            data.clear();
            for (Food food : foods){
                Vector row = new Vector();
                row.add(food.getId());
                row.add(food.getName());
                row.add(food.getPrice());
                row.add(food.getDescription());
                data.add(row);
            }
            table.updateUI();
        }catch (RemoteException e1){
            e1.printStackTrace();
        }
    }
    public boolean editProduct(Food food){
        try{
            return service.editProduct(food);

        }catch (Exception ex){
            ex.printStackTrace();
            return false;

        }


    }
    public boolean addProduct(Food food) {
        try {
            return service.addProduct(food);

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }




}
