package cl.duoc.vivero.repository;

import cl.duoc.vivero.model.Planta;

import java.util.List;

public interface PlantaDao<T extends Planta> {

    List<T> leer(); //devuelve lista ya sea de plantas exteriores o interiores

    void guardar(List<T> plantas); //recibe una lista de plantas para guardarlas
}
