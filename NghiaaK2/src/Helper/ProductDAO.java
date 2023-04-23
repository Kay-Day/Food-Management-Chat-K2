package Helper;

import Model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ProductDAO {
    private Connection connection;
    public ProductDAO(){
        connection = DBConnection.getConnection();

    }
    public Vector<Food> getAllProducts(){
        try{
            Vector<Food> res = new Vector<Food>();
            String sql = "SELECT * FROM producttbl";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println();
                res.add(new Food(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4)));

            }
            return res;

        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public boolean addProduct(Food food){
        try{
            String sql = "INSERT INTO producttbl (id, name, price, description) VALUE(?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, food.getId());
            statement.setString(2, food.getName());
            statement.setDouble(3, food.getPrice());
            statement.setString(4, food.getDescription());
            statement.execute();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean editProduct(Food food) {
        try {
            String sql = "UPDATE producttbl set name = ?, price = ?, description = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, food.getName());
            statement.setDouble(2, food.getPrice());
            statement.setString(3, food.getDescription());
            statement.setString(4, food.getId());
            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteProduct(Food food){
        try{
            String sql = "DELETE FROM producttbl WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, food.getId());
            statement.execute();
            return true;

        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
