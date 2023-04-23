package Client;

import ChatTCP.ClientChatter;
import ChatTCP.ManagerChatter;
import Helper.DBConnection;
import Model.Food;
import Model.User;
import Server.DBService;
import View.LoadingForm;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.rmi.Naming;
import java.util.Vector;

public class MainFrameC extends JFrame {
    private JPanel rootPanel;
    private JTable showTable;
    private String serviceName = "rmi://localhost:56789/test";
    private JButton themBtn;
    private JButton sửaButton;
    private JButton xóaButton;
    private JButton chatBtn;
    private JButton ChartBtn;
    private JButton showBtn;
    private JScrollPane scrollPane1;
    private JButton cancelButton;
    private DBService service;
    private Vector data = null;
    private Vector header = new Vector();

    public MainFrameC() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
        setSize(650, 650);
        setVisible(true);
        try {
            service = (DBService) Naming.lookup(serviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }


        createTable();

        themBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new LoadingForm(new OnLoading() {
//                    @Override
//                    public void onClick() {
//                        AddFrame addFrame = new AddFrame(MainFrameC.this);
//                        addFrame.setVisible(true);
//                    }
//                });
                new LoadingForm(() -> {
//                    ManagerChatter managerChatter = new ManagerChatter();
//                    managerChatter.setVisible(true);
//
//                    ClientChatter clientChatter = new ClientChatter();
//                    clientChatter.setVisible(true);
                    AddFrame addFrame = new AddFrame(MainFrameC.this);
                    addFrame.setVisible(true);


                });

//                AddFrame addFrame = new AddFrame(MainFrameC.this);
//                addFrame.setVisible(true);

            }
        });
        sửaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = showTable.getSelectedRow();
                if (selectedRow < 0) return;
                Vector vector = (Vector) data.get(selectedRow);
                Food food = new Food(vector.get(0).toString(), vector.get(1).toString(), Double.parseDouble(vector.get(2).toString()), vector.get(3).toString());
                EditFrame editFrame = new EditFrame(MainFrameC.this, food);
                editFrame.setVisible(true);

            }
        });
        xóaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choose = JOptionPane.showConfirmDialog(null, "Do you want to delete?", "Delete confirm", JOptionPane.YES_NO_OPTION);
                if (choose == JOptionPane.NO_OPTION) return;

                int selectedRow = showTable.getSelectedRow();
                if (selectedRow < 0) return;
                Vector vector = (Vector) data.get(selectedRow);
                Food food = new Food(vector.get(0).toString(), vector.get(1).toString(), Double.parseDouble(vector.get(2).toString()), vector.get(3).toString());
                if (deleteFood(food)) {
                    JOptionPane.showMessageDialog(null, "Delete successfully");
                    getFoods();
                } else {
                    JOptionPane.showMessageDialog(null, "Delete failed");
                }
            }
        });
        chatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadingForm(() -> {
                    ManagerChatter managerChatter = new ManagerChatter();
                    managerChatter.setVisible(true);

                    ClientChatter clientChatter = new ClientChatter();
                    clientChatter.setVisible(true);

                });
            }
        });
        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadingForm(() -> {
                    try {
                        File file = new File("./foods.xml");
                        Desktop desktop = Desktop.getDesktop();
                        if (file.exists())         //checks file exists or not
                            desktop.open(file);              //opens the specified file
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                });
//                try {
//                    File file = new File("./foods.xml");
//                    Desktop desktop = Desktop.getDesktop();
//                    if (file.exists())         //checks file exists or not
//                        desktop.open(file);              //opens the specified file
//                } catch (Exception e2) {
//                    e2.printStackTrace();
//                }


            }
        });
        ChartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadingForm(() -> {
                    try {
                        String query = "SELECT name ,price FROM customer.producttbl";
                        JDBCCategoryDataset dataset = new JDBCCategoryDataset(DBConnection.getConnection(), query);
                        JFreeChart chart = ChartFactory.createBarChart3D("Ng Nghia", "Name", "Price", dataset, PlotOrientation.VERTICAL, true, true, true);
                        BarRenderer renderer = null;
                        CategoryPlot plot = null;
                        renderer = new BarRenderer();
                        ChartFrame frame = new ChartFrame("Chart Teacher Nghia", chart);
                        frame.setVisible(true);
                        frame.setSize(800, 600);
                        frame.setLocationRelativeTo(null);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }


                });
//                try {
//                    String query = "SELECT name ,price FROM customer.producttbl";
//                    JDBCCategoryDataset dataset = new JDBCCategoryDataset(DBConnection.getConnection(), query);
//                    JFreeChart chart = ChartFactory.createBarChart3D("Ng Nghia", "Name", "Price", dataset, PlotOrientation.VERTICAL, true, true, true);
//                    BarRenderer renderer = null;
//                    CategoryPlot plot = null;
//                    renderer = new BarRenderer();
//                    ChartFrame frame = new ChartFrame("Chart Teacher Nghia", chart);
//                    frame.setVisible(true);
//                    frame.setSize(800, 600);
//                    frame.setLocationRelativeTo(null);
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(null, ex);
//                }


            }
        });
    }

    private void writeUsers(Vector<User> users) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("users");
            for (int i = 0; i < users.toArray().length; ++i) {
                Vector row = (Vector) data.get(i);
                Element user = doc.createElement("user");
                rootElement.appendChild(user);
                Element name = doc.createElement("username");
                name.appendChild(doc.createTextNode((String) row.get(0)));
                name.appendChild(name);
                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode((String) row.get(1)));
                user.appendChild(password);
            }
            doc.appendChild(rootElement);
            FileOutputStream output =
                    new FileOutputStream("./password.xml");
            writeXml(doc, output);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void writeXml(Document doc,
                          OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

    private void writeXML() {
        try {
            Vector<User> users = service.getUsers();
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("data");
            Element rootElement = doc.createElement("foods");
//            for (int i = 0; i < data.toArray().length; ++i) {
//                Vector row = (Vector) data.get(i);
//                Element food = doc.createElement("food");
//                rootElement.appendChild(food);
//                Attr attr = doc.createAttribute("id");
//                attr.setValue((String) row.get(0));
//                food.setAttributeNode(attr);
//                Element name = doc.createElement("name");
//                name.appendChild(doc.createTextNode((String) row.get(1)));
//                food.appendChild(name);
//
//                Element price = doc.createElement("price");
//                price.appendChild(doc.createTextNode((String) row.get(2)));
//                food.appendChild(price);
//
//                Element des = doc.createElement("description");
//                des.appendChild(doc.createTextNode((String) row.get(3)));
//                food.appendChild(des);
//            }
//            root.appendChild(rootElement);
/////////////////////////////////////////////////
            Element userRoot = doc.createElement("users");

            for (int i = 0; i < users.toArray().length; ++i) {
                User u = users.get(i);
                Element user = doc.createElement("user");
                userRoot.appendChild(user);
                Element name = doc.createElement("username");
                name.appendChild(doc.createTextNode(u.getUsername()));
                user.appendChild(name);
                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode(u.getPassword()));
                user.appendChild(password);
            }

            root.appendChild(userRoot);
            doc.appendChild(root);
            FileOutputStream output =
                    new FileOutputStream("./foods.xml");
            writeXml(doc, output);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void createTable() {
        data = new Vector();
        header.add("Id");
        header.add("Name");
        header.add("Price");
        header.add("Description");
        getFoods();
        showTable.setModel(new DefaultTableModel(
                data,
                header
        ));

    }

    public void getFoods() {
        try {
            Vector<Food> foods = service.getAllProducts();
            data.clear();
            for (int i = 0; i < foods.size(); ++i) {
                Vector food = new Vector();
                food.add(foods.get(i).getId());
                food.add(foods.get(i).getName());
                food.add(foods.get(i).getPrice().toString());
                food.add(foods.get(i).getDescription());
                data.add(food);
            }
            writeXML();
            showTable.updateUI();
        } catch (Exception e) {
            e.printStackTrace();
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

    public boolean editProduct(Food food) {
        try {
            return service.editProduct(food);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean deleteFood(Food food) {
        try {
            return service.deleteProduct(food);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        MainFrameC mainFrameC = new MainFrameC();
        mainFrameC.setLocationRelativeTo(null);

    }
}
