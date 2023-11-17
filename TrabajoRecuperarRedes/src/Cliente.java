import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Cliente {
    private String nombre;
    private String ip;
    private int puerto;

    private Socket socket;


    public Cliente(String nombre, String ip, int puerto ) {
        this.nombre = nombre;
        this.ip = ip;
        this.puerto = puerto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;




}
