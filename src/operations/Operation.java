package operations;

import models.Jugador;
import models.Equipo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Operation {
    private static int nextPlayerId() {
        int nextId = 1;

        try (BufferedReader buffer = new BufferedReader(new FileReader("players.csv"))) {
            String fila;

            while ((fila = buffer.readLine()) != null) {
                String[] datos = fila.split(",");

                if (datos.length >= 5) {
                    try {
                        nextId = Math.max(nextId, Integer.parseInt(datos[0].trim()) + 1);
                    } catch (NumberFormatException ignored) {
                        // Ignora filas que no tengan un ID numérico.
                    }
                } else if (datos.length == 4) {
                    // Fila del formato antiguo: nombre,dorsal,edad,estado.
                    nextId++;
                }
            }
        } catch (java.io.FileNotFoundException e) {
            return 1;
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de jugadores: " + e.getMessage());
        }

        return nextId;
    }
    public static void savePlayer(Jugador jugadorToSave)
{
    try{
        FileWriter escribir = new FileWriter("players.csv", true);

        jugadorToSave.setId(nextPlayerId());

        //escribir.write(jugadorToSave.playerToCSV());
        escribir.write(jugadorToSave.playerToCSV());
        escribir.write("\n");
        escribir.close();
        System.out.println("Player Saved");

    }
    catch (IOException e)
    {
        System.out.println(e.getMessage());
    }
}
    public static void readPlayers()
    {
        try {
            FileReader lector = new FileReader("players.csv");
            BufferedReader buffer = new BufferedReader(lector);
            String playerReader;

            while ((playerReader= buffer.readLine())!=null)
            {
                System.out.println(playerReader);
            }


            buffer.close();
            System.out.println("All players have been read");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void readOnePlayer(String name) {
        try {
            FileReader lector = new FileReader("players.csv");
            BufferedReader buffer = new BufferedReader(lector);
            String playerReader;

            while ((playerReader = buffer.readLine()) != null) {

                String[] data_readed = playerReader.split(",");
                Jugador temp_player = new Jugador();
                temp_player.name = data_readed[0];
                temp_player.dorsal = Integer.parseInt(data_readed[1]);
                temp_player.is_alive = Boolean.parseBoolean(data_readed[3]);

                if (temp_player.name.equals(name)) {

                    System.out.println(playerReader);
                    //playerReader = null;
                }

            }


            buffer.close();
            System.out.println("All players have been read");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        public static void readPlayerById(int id) {
        boolean encontrado = false;

        try (BufferedReader buffer = new BufferedReader(new FileReader("players.csv"))) {
            String fila;

            while ((fila = buffer.readLine()) != null) {
                String[] datos = fila.split(",");

                if (datos.length >= 5) {
                    try {
                        if (Integer.parseInt(datos[0].trim()) == id) {
                            System.out.println(fila);
                            encontrado = true;
                            break;
                        }
                    } catch (NumberFormatException ignored) {
                        // Omite filas sin un ID numérico.
                    }
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró un jugador con ese ID.");
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer players.csv: " + e.getMessage());
        }
    }
    public static void createTeam(String teamName) {
        String nombre = teamName.trim();

        if (nombre.isEmpty() || nombre.contains(",")) {
            System.out.println("Escribe un nombre de equipo válido, sin comas.");
            return;
        }

        try {
            for (String equipo : readLines("teams.csv")) {
                if (equipo.equalsIgnoreCase(nombre)) {
                    System.out.println("Ese equipo ya existe.");
                    return;
                }
            }

            try (FileWriter writer = new FileWriter("teams.csv", true)) {
                writer.write(nombre + System.lineSeparator());
            }
            System.out.println("Equipo creado: " + nombre);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el equipo: " + e.getMessage());
        }
    }

    public static void addPlayerToTeam(String teamName, int playerId) {
        try {
            String nombreEquipo = null;

            for (String equipo : readLines("teams.csv")) {
                if (equipo.equalsIgnoreCase(teamName.trim())) {
                    nombreEquipo = equipo;
                    break;
                }
            }

            if (nombreEquipo == null) {
                System.out.println("No existe ese equipo. Créalo primero.");
                return;
            }

            if (findPlayerRowById(playerId) == null) {
                System.out.println("No existe un jugador con ese ID.");
                return;
            }

            for (String fila : readLines("team_players.csv")) {
                String[] datos = fila.split(",", -1);
                if (datos.length == 2 && parseCsvId(datos[0]) == playerId) {
                    System.out.println("Ese jugador ya está asignado al equipo " + datos[1] + ".");
                    return;
                }
            }

            try (FileWriter writer = new FileWriter("team_players.csv", true)) {
                writer.write(playerId + "," + nombreEquipo + System.lineSeparator());
            }
            System.out.println("Jugador agregado al equipo " + nombreEquipo + ".");
        } catch (IOException e) {
            System.out.println("No se pudo agregar el jugador: " + e.getMessage());
        }
    }

    public static void readTeams() {
        try {
            List<String> equipos = readLines("teams.csv");

            if (equipos.isEmpty()) {
                System.out.println("Todavía no hay equipos.");
                return;
            }

            List<String> asignaciones = readLines("team_players.csv");

            for (String nombreEquipo : equipos) {
                Equipo equipo = new Equipo(nombreEquipo);

                for (String fila : asignaciones) {
                    String[] datos = fila.split(",", -1);
                    if (datos.length != 2 || !datos[1].equalsIgnoreCase(nombreEquipo)) {
                        continue;
                    }

                    int id = parseCsvId(datos[0]);
                    String filaJugador = findPlayerRowById(id);
                    if (filaJugador == null) {
                        continue;
                    }

                    String[] jugadorDatos = filaJugador.split(",", -1);
                    if (jugadorDatos.length < 5) {
                        continue;
                    }

                    Jugador jugador = new Jugador();
                    jugador.setId(id);
                    jugador.name = jugadorDatos[1];
                    jugador.dorsal = Integer.parseInt(jugadorDatos[2]);
                    equipo.agregarJugador(jugador);
                }

                System.out.println("\nEquipo: " + equipo.getTeamName());
                if (equipo.getPlantilla().isEmpty()) {
                    System.out.println("  (sin jugadores)");
                } else {
                    for (Jugador jugador : equipo.getPlantilla()) {
                        System.out.println("  ID " + jugador.getId() + " | " +
                                jugador.name + " | dorsal " + jugador.dorsal);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("No se pudieron leer los equipos: " + e.getMessage());
        }
    }

    private static String findPlayerRowById(int id) throws IOException {
        for (String fila : readLines("players.csv")) {
            String[] datos = fila.split(",", -1);
            if (datos.length >= 5 && parseCsvId(datos[0]) == id) {
                return fila;
            }
        }
        return null;
    }

    private static int parseCsvId(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static List<String> readLines(String fileName) throws IOException {
        List<String> filas = new ArrayList<>();
        File archivo = new File(fileName);

        if (!archivo.exists()) {
            return filas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String fila;
            while ((fila = reader.readLine()) != null) {
                if (!fila.trim().isEmpty()) {
                    filas.add(fila);
                }
            }
        }

        return filas;
    }

        //TODO adicionar ID para los registros de jugadores
}
