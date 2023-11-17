import java.util.ArrayList;

public class main {
    public static void main(String[] args) {

        ArrayList<Cliente> clientes = new ArrayList<>();

        Clientes s = new Clientes(clientes);

        s.cargarClientesDesdeArchivo("/home/eberzeta1/Escritorio/TrabajoRecuperarRedes/src/archivoConfiguracion");

for ( Cliente x : s.getClientes()){
    System.out.println(x.getNombre() + " " + x.getIp() + " " + x.getPuerto());
}
        // juan jose carlos
        // 0 1 2
        System.out.println(s.PosicionDiferencia(clientes.get(2),clientes.get(0)));

    }
}