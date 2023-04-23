package Server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        String servicName = "rmi://localhost:56789/test";
        DBServiceImp service;
        try {
            LocateRegistry.createRegistry(56789);
            service = new DBServiceImp();
            Naming.bind(servicName, service);
            System.out.println("Service is running");
        }catch(Exception ex){
            ex.printStackTrace();

        }
    }

}
