package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DataService extends Remote {

//    int[] requestData(
//            String studentCode,
//            String qCode
//    ) throws RemoteException;

    String requestData(String studentCode, String qCode) throws RemoteException;

//    void submitData(
//            String studentCode,
//            String qCode,
//            List<Integer> data
//    ) throws RemoteException;
//
    void submitData(
            String studentCode,
            String qCode,
            String data
    ) throws RemoteException;
}