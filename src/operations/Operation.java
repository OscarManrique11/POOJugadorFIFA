package operations;

import models.Jugador;

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

        //TODO adicionar ID para los registros de jugadores
}
