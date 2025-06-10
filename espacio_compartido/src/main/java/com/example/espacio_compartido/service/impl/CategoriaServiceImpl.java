package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.CategoriaDTO;
import com.example.espacio_compartido.model.Categoria;
import com.example.espacio_compartido.repository.CategoriaRepository;
import com.example.espacio_compartido.service.ICategoriaService;
import com.example.espacio_compartido.validation.CategoriaValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaValidator categoriaValidator;

    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaValidator categoriaValidator) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaValidator = categoriaValidator;
    }

    @Override
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return convertToDTO(categoria);
    }

    @Override
    @Transactional
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        categoriaValidator.validacionCompletaCategoria(categoriaDTO);

        Categoria categoria = convertToEntity(categoriaDTO);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        return convertToDTO(categoriaGuardada);
    }

    @Override
    @Transactional
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        categoriaValidator.validacionCompletaCategoria(categoriaDTO);

        categoriaExistente.setNombre(categoriaDTO.getNombre());
        categoriaExistente.setDescripcion(categoriaDTO.getDescripcion());
        categoriaExistente.setImgCategoria(categoriaDTO.getImgCategoria()); // <-- Añadido

        Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
        return convertToDTO(categoriaActualizada);
    }

    @Override
    @Transactional
    public CategoriaDTO eliminarCategoria(Long id) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        categoriaRepository.delete(categoriaExistente);
        return convertToDTO(categoriaExistente);
    }

    @Override
    @Transactional(readOnly = false)
    public Categoria obtenerCategoriaConBloqueo(Long id) {
        Categoria categoria = categoriaRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        try {
            Thread.sleep(15000); // Simula espera para mostrar bloqueo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return categoria;
    }

    @Override
    @Transactional
    public void eliminarCategoriaFisicamente(Long id) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        categoriaRepository.delete(categoriaExistente);
    }

    private CategoriaDTO convertToDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .idCategoria(categoria.getIdCategoria())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .imgCategoria(categoria.getImgCategoria()) // <-- Añadido
                .build();
    }

    private Categoria convertToEntity(CategoriaDTO categoriaDTO) {
        return Categoria.builder()
                .idCategoria(categoriaDTO.getIdCategoria())
                .nombre(categoriaDTO.getNombre())
                .descripcion(categoriaDTO.getDescripcion())
                .imgCategoria(categoriaDTO.getImgCategoria()) // <-- Añadido
                .build();
    }
}