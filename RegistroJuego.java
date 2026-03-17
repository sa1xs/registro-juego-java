import java.io.*;
public class RegistroJuego {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream out = System.out;
    static int[] partidasJugadas;
    static int[][] puntaje;
    static String[] nombre;
    static final int PUNTAJE_REFERENCIA = 1000;
    
    public static void main(String args[]) throws IOException {

        int opcion = 0;
        registroCantidadJugadores();

        do {
            imprimirMenu();
            opcion = solicitarOpcion();

            switch (opcion) {
                case 1:
                    registroJugadores();
                    break;
                case 2:
                    registroPartidas();
                    break;
                case 3:
                    registroPuntajes();
                    break;
                case 4:
                    resultados();
                    break;
                case 5:
                    salida();
                    break;
                default:
                    out.print("\n========================================");
                    out.print("\nDigite un numero que esté en el menú\n");
                    out.print("========================================");
                    break;
            }
        } while (opcion != 5 );
    }

    static void registroCantidadJugadores() throws IOException {
        out.print("========================================\n");
        out.print("Registrar número de jugadores ");
        out.print("\n========================================");
        out.print("\nCantidad: ");
        int cantJugadores = Integer.parseInt(in.readLine());

        while (cantJugadores <= 0) {
            out.print("========================================\n");
            out.print("Digite un número mayor al 0.");
            out.print("\n========================================\n");
            out.print("Intente de nuevo: ");
            cantJugadores = Integer.parseInt(in.readLine());
        }

        nombre = new String[cantJugadores];
        partidasJugadas = new int[cantJugadores];
        puntaje = new int[cantJugadores][];
    }

    static void imprimirMenu() {
        out.println("\n============ Menú ============");
        out.println("1. Nombre del jugador");
        out.println("2. Numero de partidas");
        out.println("3. Puntajes");
        out.println("4. Resultados");
        out.println("5. Salir");
        out.println("==============================\n");
    }

    static int solicitarOpcion() throws IOException{
        out.print("Seleccione la opción que desea (1-5): ");
        int seleccion = Integer.parseInt(in.readLine());
        return seleccion;
    }

    static void registroJugadores() throws IOException {
        for (int i = 0; i < nombre.length; i++) {
            out.print("Digite el nombre del jugador " + (i + 1) + ": ");
            nombre[i] = in.readLine().trim();
        }
    }

    static void registroPartidas() throws IOException {
        for (int i = 0; i < nombre.length; i++) {
            if (nombre[i] == null) {
                out.print("========================================\n");
                out.println("Faltan datos para el jugador " + (i + 1) + ".");
                out.print("========================================\n");
                continue;
            }else{
                out.print("Digite el número de partidas jugadas por " + nombre[i] + ": ");
                partidasJugadas[i] = Integer.parseInt(in.readLine());
                while (partidasJugadas[i] <= 0) {
                    out.print("Digite de nuevo el número de partidas jugadas: ");
                    partidasJugadas[i] = Integer.parseInt(in.readLine());
                }
            }       
            puntaje[i] = new int[partidasJugadas[i]];
        }
    }

    static void registroPuntajes() throws IOException {
        for (int i = 0; i < nombre.length; i++) {
            if (puntaje[i] == null || nombre[i] == null) {
                out.print("========================================\n");
                out.println("Faltan datos para el jugador " + (i + 1) + ".");
                out.print("========================================\n");
                continue;
            }
            for (int j = 0; j < puntaje[i].length; j++) {
                out.print("Digite el puntaje " + (j + 1) + " de " + nombre[i] + ": ");
                puntaje[i][j] = Integer.parseInt(in.readLine());
                while (puntaje[i][j] <= 0) {
                    out.print("Inválido. Debe ser > 0. Intente de nuevo: ");
                    puntaje[i][j] = Integer.parseInt(in.readLine());
                }
            }
        }
    }

    static void resultados() {

        int maxPuntajeGlobal = 0;
        String jugadorMaxPuntaje = "";
        double mejorPromedioGlobal = 0;
        String jugadorMejorPromedio = "";

        for (int i = 0; i < nombre.length; i++) {
            if (nombre[i] == null || puntaje[i] == null || puntaje[i].length == 0 || partidasJugadas[i] <= 0) {
                out.print("========================================\n");
                out.println("Faltan datos para el jugador " + (i + 1) + ".");
                out.print("========================================\n");
                continue;
            }

            boolean faltanPuntajes = false;
            for (int j = 0; j < puntaje[i].length; j++) {
                if (puntaje[i][j] <= 0) {
                    faltanPuntajes = true;
                    break;
                }
            }
            if (faltanPuntajes) {
                out.print("========================================\n");
                out.println("Faltan puntajes para el jugador " + nombre[i] + ".");
                out.print("========================================\n");
                continue;
            }

            int max = puntaje[i][0];
            int min = puntaje[i][0];
            int suma = puntaje[i][0];
            int contadorReferencia = 0;

            for (int j = 1; j < puntaje[i].length; j++) {
                if (puntaje[i][j] > max) max = puntaje[i][j];
                if (puntaje[i][j] < min) min = puntaje[i][j];
                suma += puntaje[i][j];
            }

            for (int j = 0; j < puntaje[i].length; j++) {
                if (puntaje[i][j] > PUNTAJE_REFERENCIA) {
                contadorReferencia++;
                }
            }

            double promedio = (double) suma / puntaje[i].length;

            if (max > maxPuntajeGlobal) {
                maxPuntajeGlobal = max;
                jugadorMaxPuntaje = nombre[i];
            }
            
            if (promedio > mejorPromedioGlobal) {
                mejorPromedioGlobal = promedio;
                jugadorMejorPromedio = nombre[i];
            }

            out.println("====================================="); 
            out.println("Jugador: " + nombre[i]);
            out.println("  Puntaje máximo: " + max);
            out.println("  Puntaje mínimo: " + min);
            out.println("  Promedio: " + promedio);
            out.println("  Partidas con puntaje > " + PUNTAJE_REFERENCIA + ": " + contadorReferencia);
            out.print("  Puntajes: [");
            for (int j = 0; j < puntaje[i].length; j++) {
                out.print(puntaje[i][j]);
                if (j < puntaje[i].length - 1) 
                    out.print(", ");
            }
            out.println("]");
            out.println("=====================================");
        }
        out.println("\n============== RESULTADOS GLOBALES ================");
        out.println("Jugador con el puntaje más alto: " + jugadorMaxPuntaje + " (" + maxPuntajeGlobal + ")");
        out.println("Jugador con el mejor promedio: " + jugadorMejorPromedio + " (" + mejorPromedioGlobal + ")");
        out.println("===================================================");
    }

    static void salida() {
        out.print("========================================\n");
        out.println("Gracias por usar el sistema. ¡Hasta pronto!");
        out.print("========================================\n");
    }
}