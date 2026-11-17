package models;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private String teamName;
    private final List<Jugador> plantilla = new ArrayList<>();

    public Equipo(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Jugador> getPlantilla() {
        return plantilla;
    }

    public boolean agregarJugador(Jugador jugador) {
        for (Jugador actual : plantilla) {
            if (actual.getId() == jugador.getId()) {
                return false;
            }
        }
        plantilla.add(jugador);
        return true;
    }
}