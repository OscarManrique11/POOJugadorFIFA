import models.Jugador;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static operations.Operation.readOnePlayer;
import static operations.Operation.readPlayers;
import static operations.Operation.savePlayer;

public class Main {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        int opcion;

        do {
            imprimirMenu();
            opcion = leerEntero(leer, "Elige una opción: ");

            switch (opcion) {
                case 1:
                    crearJugador(leer);
                    break;
                case 2:
                    readPlayers();
                    break;
                case 3:
                    buscarJugador(leer);
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción no válida. Elige 1, 2, 3 o 0.");
            }
        } while (opcion != 0);
    }

    private static void imprimirMenu() {
        System.out.println("\n===== Repertorio de jugadores =====");
        System.out.println("1. Guardar jugador");
        System.out.println("2. Mostrar todos los jugadores");
        System.out.println("3. Buscar jugador por ID");
        System.out.println("0. Salir");
    }

    private static void crearJugador(Scanner leer) {
        String nombre = leerNombre(leer);
        LocalDate nacimiento = leerFecha(leer);
        int dorsal = leerEntero(leer, "Dorsal: ");

        Jugador jugador = new Jugador(nombre, nacimiento);
        jugador.dorsal = dorsal;
        jugador.is_alive = true;
        savePlayer(jugador);
    }

    private static void buscarJugador(Scanner leer) {
        String nombre = leerNombre(leer);
        readOnePlayer(nombre);
    }

    private static String leerNombre(Scanner leer) {
        while (true) {
            System.out.print("Nombre del jugador : ");
            String texto = leer.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
            }  else {
                return texto;
            }
        }
    }

    private static int leerEntero(Scanner leer, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = leer.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero.");
            }
        }
    }

    private static LocalDate leerFecha(Scanner leer) {
        while (true) {
            System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
            String texto = leer.nextLine().trim();
            try {
                LocalDate fecha = LocalDate.parse(texto);
                if (fecha.isAfter(LocalDate.now())) {
                    System.out.println("La fecha no puede estar en el futuro.");
                    continue;
                }
                return fecha;
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no válida. Usa el formato AAAA-MM-DD, por ejemplo 1993-01-18.");
            }
        }
    }
}
