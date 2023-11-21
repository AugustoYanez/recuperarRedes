import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public void start(String nombre, String ip, int puerto, List<String> clientesConectados) {
        try {
            socket = new Socket(ip, puerto);
            System.out.println("Conectado al servidor en " + ip + ":" + puerto);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            // Inicia un hilo para leer respuestas del servidor
            new Thread(() -> leerRespuestasServer()).start();

            // Solicita al usuario el destinatario
            System.out.print("Ingrese el nombre del destinatario: ");
            String destinatario = stdIn.readLine();

            // Verifica que el destinatario esté en la lista de clientes conectados
            if (clientesConectados.contains(destinatario)) {
                // Envía el nombre del destinatario al servidor
                out.println(destinatario);

                // Resto del código para enviar mensajes...
            } else {
                System.out.println("El destinatario no está disponible.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leerRespuestasServer() {
        try {
            String respuestaServer;
            while ((respuestaServer = in.readLine()) != null) {
                System.out.println("Respuesta del servidor: " + respuestaServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Obtén la información del cliente desde la configuración
        String nombre = "JUAN";
        String ip = "172.16.0.10";
        int puerto = 30000;

        // Simula la lista de clientes conectados (puedes obtenerla de la topología)
        List<String> clientesConectados = List.of("JOSE", "CARLOS");

        Cliente cliente = new Cliente();
        cliente.start(nombre, ip, puerto, clientesConectados);
    }
}
