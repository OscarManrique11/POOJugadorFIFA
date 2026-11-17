import models.Jugador;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import static operations.Operation.addPlayerToTeam;
import static operations.Operation.createTeam;
import static operations.Operation.readTeams;

import static operations.Operation.readPlayerById;
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
                    readPlayerById(leerEnteroPositivo(leer, "ID del jugador: "));
                    break;
                case 4:
                    createTeam(leerNombre(leer, "Nombre del equipo"));
                    break;
                case 5: {
                    String equipo = leerNombre(leer, "Nombre del equipo: ");
                    int idJugador = leerEnteroPositivo(leer, "ID del jugador: ");
                    addPlayerToTeam(equipo, idJugador);
                    break;
                }
                case 6:
                    readTeams();
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
        System.out.println("1. Crear jugador");
        System.out.println("2. Mostrar todos los jugadores");
        System.out.println("3. Buscar jugador por ID");
        System.out.println("4. Crear equipo");
        System.out.println("5. Agregar jugador a un equipo");
        System.out.println("6. Mostrar equipos y sus jugadores");
        System.out.println("0. Salir");
    }

    private static void crearJugador(Scanner leer) {
        String nombre = leerNombre(leer, "Nombre del jugador");
        LocalDate nacimiento = leerFecha(leer);
        int dorsal = leerEnteroPositivo(leer, "Dorsal: ");

        Jugador jugador = new Jugador(nombre, nacimiento);
        jugador.dorsal = dorsal;
        jugador.is_alive = true;
        savePlayer(jugador);
    }

    private static String leerNombre(Scanner leer, String tipo) {
        while (true) {
            System.out.print(tipo + ": ");
            String texto = leer.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                continue;
            }

            if (texto.contains(",")) {
                System.out.println("No uses comas porque separan los campos del CSV.");
                continue;
            }

            return texto;
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

    private static int leerEnteroPositivo(Scanner leer, String mensaje) {
        while (true) {
            int valor = leerEntero(leer, mensaje);
            if (valor > 0) {
                return valor;
            }
            System.out.println("El valor debe ser mayor que cero.");
        }
    }

    private static LocalDate leerFecha(Scanner leer) {
        while (true) {
            int anio = leerEntero(leer, "Año de nacimiento: ");
            int mes = leerEntero(leer, "Mes de nacimiento: ");
            int dia = leerEntero(leer, "Día de nacimiento: ");
            try {
                LocalDate fecha = LocalDate.of(anio, mes, dia);
                if (fecha.isAfter(LocalDate.now())) {
                    System.out.println("La fecha no puede estar en el futuro. Ingresa la fecha otra vez.");
                    continue;
                }
                return fecha;
            } catch (DateTimeException e) {
                System.out.println("La fecha no es válida. Ingresa año, mes y día otra vez.");
            }
        }
    }
}

