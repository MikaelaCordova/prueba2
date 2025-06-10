package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.CategoriaDTO;
import com.example.espacio_compartido.model.Categoria;

import java.util.List;

public interface ICategoriaService {

    List<CategoriaDTO> obtenerTodasLasCategorias();

    CategoriaDTO obtenerCategoriaPorId(Long id);

    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO);

    CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO);

    CategoriaDTO eliminarCategoria(Long id);

    Categoria obtenerCategoriaConBloqueo(Long id);

    void eliminarCategoriaFisicamente(Long id);
}