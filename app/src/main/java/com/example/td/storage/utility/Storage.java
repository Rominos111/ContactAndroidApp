package com.example.td.storage.utility;

import java.util.List;

public interface Storage<T> {
    /**
     * Get un élément
     *
     * @param id Id
     * @return Élément
     */
    T find(int id);

    /**
     * Trouve tous les éléments
     *
     * @return Tous les éléments
     */
    List<T> findAll();

    /**
     * @return Nombre d'éléments
     */
    int size();

    /**
     * Insère un élément
     *
     * @param elem Élément
     * @return Inséré ou non
     */
    boolean insert(T elem);

    /**
     * Update un élément
     *
     * @param id Id
     * @param elem Élément
     * @return Updated ou non
     */
    boolean update(int id, T elem);

    /**
     * Supprime un élément
     *
     * @param id Id
     * @return Supprimé ou non
     */
    boolean delete(int id);
}
