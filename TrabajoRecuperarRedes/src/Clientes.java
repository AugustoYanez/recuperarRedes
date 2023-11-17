import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Clientes {

    private ArrayList<Cliente> clientes;

    private Socket socket;

    private PrintWriter out; //escribir datos en el flujo de salida del socket.
    private BufferedReader in; // leer datos del flujo de entrada del socket.
    private BufferedReader stdIn;

    public Clientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void cargarClientesDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split(":");
                String nombre = partes[0];
                String ip = partes[1];
                int puerto = Integer.parseInt(partes[2]);
                Cliente cliente = new Cliente(nombre, ip, puerto);
                clientes.add(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int PosicionDiferencia(Cliente e, Cliente r) {
        int posicionE = 9;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIp().equals(e.getIp())) {
                posicionE = i;
            }
        }
        int posicionR = 9;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIp().equals(r.getIp())) {
                posicionR = i;
            }
        }
        int diferencia = 0;
        if ( posicionR > posicionE  ){
            diferencia = posicionR - posicionE;
            return diferencia;
        }
        if ( posicionE > posicionR ){
            diferencia = posicionE - posicionR;
            return diferencia;
        }
        return diferencia;
    }

    public void start(String host,String nombre) { // funcion start, recibe host y puerto
        try {
            Cliente clientereceptor = null;
            for ( Cliente c : clientes){
                if ( c.getNombre().equals(nombre)){
                    clientereceptor = new Cliente(c.getNombre(),c.getIp(),c.getPuerto());
                }
            }
            int diferencia = this.PosicionDiferencia(clientes.get(0),clientereceptor);

            socket = new Socket(host, clientes.get(diferencia).getPuerto() ); // crear socket
            System.out.println("Conectado al servidor: " + socket);
            out = new PrintWriter(socket.getOutputStream(),  true); // parametros, el primero escribe datos de salida y el segundo modo de limpieza
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // el parametro leera los datos de flujo de entrada
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> leerRespuestasServer()).start(); // asociamos el hilo a el metodo de leer respuestas

            String entradasUser; // se usa para almacenar los mensajes ingresados por el usuario
            while ((entradasUser = stdIn.readLine()) != null) { // mientras  siga ingresando mensajes
                System.out.println("Mensaje enviado: " + entradasUser);
                out.println(entradasUser); // imprime mensajes
                if (entradasUser.equalsIgnoreCase("chau")) { // si ingresa chau finaliza
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
            System.out.println("Desconectado del servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leerRespuestasServer() { // Este método se encargará de leer las respuestas del servidor y mostrarlas en la consola del cliente.
        try {
            String respuestServer; // para almacenar la respuesta del servidor
            while ((respuestServer = in.readLine()) != null) { // mientras que el flujo de entrada de datos no sea nulo
                System.out.println("Respuesta del cliente: " + respuestServer); // se escribe la respuesta
            }
        } catch (IOException e) { // busqueda e impresion de errores
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String host = "localhost"; // crear host
        ArrayList<Cliente> clientes = new ArrayList<>();
        Clientes s = new Clientes(clientes);
        s.cargarClientesDesdeArchivo("/home/eberzeta1/Escritorio/TrabajoRecuperarRedes/src/archivoConfiguracion");
        Cliente cliente = s.getClientes().get(0);
        s.start(host,cliente.getNombre());
    }


}

