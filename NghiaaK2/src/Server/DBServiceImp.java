package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import Helper.ProductDAO;
import Helper.UserDAO;
import Model.Food;
import Model.User;

public class DBServiceImp extends UnicastRemoteObject implements DBService {

    private UserDAO userDAO;
    private ProductDAO productDAO;

    protected DBServiceImp() throws RemoteException {
        super();
        userDAO = new UserDAO();
        productDAO = new ProductDAO();
    }

    private static final long serialVersionUID = 1L;

    @Override
    public User login(User user) throws RemoteException {
        return userDAO.login(user);
    }

    @Override
    public boolean signUp(User user) throws RemoteException {
        return userDAO.signUp(user);
    }

    @Override
    public Vector<Food> getAllProducts() throws RemoteException {
        return productDAO.getAllProducts();
    }

    @Override
    public boolean addProduct(Food food) throws RemoteException {
        return productDAO.addProduct(food);
    }

    @Override
    public boolean deleteProduct(Food food) throws RemoteException {
        return productDAO.deleteProduct(food);
    }

    @Override
    public boolean editProduct(Food food) throws RemoteException {
        return productDAO.editProduct(food);
    }

    @Override
    public Vector<User> getUsers() throws RemoteException {
        return userDAO.getUsers();
    }

}


