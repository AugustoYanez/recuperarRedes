import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfiguracionCliente {

    private Map<String, Map<String, Object>> clientes;
    private Map<String, List<String>> topologia;

    public ConfiguracionCliente() {
        this.clientes = new HashMap<>();
        this.topologia = new HashMap<>();
    }

    public void cargarClientesDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split(":");
                String nombre = partes[0];
                String ip = partes[1];
                int puerto = Integer.parseInt(partes[2]);

                if (!clientes.containsKey(nombre)) {
                    Map<String, Object> infoCliente = new HashMap<>();
                    infoCliente.put("IP", ip);
                    infoCliente.put("Puerto", puerto);
                    clientes.put(nombre, infoCliente);
                } else {
                    Map<String, Object> infoCliente = clientes.get(nombre);
                    infoCliente.put("IP", ip);
                    infoCliente.put("Puerto", puerto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarTopologiaDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split("<->");

                if (partes.length > 1) {
                    for (int i = 0; i < partes.length - 1; i++) {
                        String clienteActual = partes[i];
                        String clienteConectado = partes[i + 1];

                        // Almacena la topología en el mapa
                        if (!topologia.containsKey(clienteActual)) {
                            topologia.put(clienteActual, new ArrayList<>());
                        }
                        topologia.get(clienteActual).add(clienteConectado);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Map<String, Object>> getClientes() {
        return clientes;
    }

    public Map<String, List<String>> getTopologia() {
        return topologia;
    }

    public static void main(String[] args) {
        ConfiguracionCliente configuracion = new ConfiguracionCliente();
        configuracion.cargarClientesDesdeArchivo("C:\\Users\\augus\\IdeaProjects\\RedesTrabajoExtra\\src\\Configuracion");
        configuracion.cargarTopologiaDesdeArchivo("C:\\Users\\augus\\IdeaProjects\\RedesTrabajoExtra\\src\\Conexion");

        // Obtener la información de los clientes
        Map<String, Map<String, Object>> clientes = configuracion.getClientes();

        // Imprimir la información de los clientes
        for (Map.Entry<String, Map<String, Object>> entry : clientes.entrySet()) {
            System.out.println("Nombre: " + entry.getKey());
            System.out.println("IP: " + entry.getValue().get("IP"));
            System.out.println("Puerto: " + entry.getValue().get("Puerto"));
            System.out.println("------");
        }

        // Obtener la información de la topología
        Map<String, List<String>> topologia = configuracion.getTopologia();

        // Imprimir la información de la topología
        for (Map.Entry<String, List<String>> entry : topologia.entrySet()) {
            System.out.println("Cliente: " + entry.getKey());
            System.out.println("Conectado con: " + entry.getValue());
            System.out.println("------");
        }
    }
}
