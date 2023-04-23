package Server;

import Model.Food;
import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface DBService extends Remote {
    public User login(User user ) throws RemoteException;

    boolean signUp(User user) throws RemoteException;

    Vector<Food> getAllProducts() throws RemoteException;

    boolean addProduct(Food food) throws RemoteException;

    boolean deleteProduct(Food food) throws RemoteException;

    boolean editProduct(Food food) throws RemoteException;

    Vector<User> getUsers() throws RemoteException;
}
