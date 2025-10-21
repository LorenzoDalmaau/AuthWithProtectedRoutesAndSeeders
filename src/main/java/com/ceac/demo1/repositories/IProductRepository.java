package com.ceac.demo1.repositories;

/// Paquete donde se guardan las interfaces que acceden directamente a la base de datos.
/// En Spring Boot, estas interfaces suelen extender de JpaRepository para obtener toda la funcionalidad CRUD.

import com.ceac.demo1.entities.ProductModel;   /// Importa la entidad ProductModel (representa la tabla products)
import org.springframework.data.domain.Page;   /// Representa una "página" de resultados (paginación)
import org.springframework.data.domain.Pageable; /// Permite definir el número de página y tamaño de página en consultas
import org.springframework.data.jpa.repository.JpaRepository; /// Proporciona métodos CRUD (findAll, save, delete, etc.)

/// Interfaz que gestiona las operaciones de base de datos para los productos.
///
/// Extiende de JpaRepository, lo que le da automáticamente todos los métodos básicos:
/// - findAll()   → obtener todos los registros
/// - findById()  → buscar por ID
/// - save()      → guardar un registro
/// - deleteById() → eliminar por ID
///
/// Además, aquí definimos métodos personalizados de búsqueda (derivados de nombres de métodos).
public interface IProductRepository extends JpaRepository<ProductModel, Long> {

    /// Busca productos cuyo título contenga un texto (parcialmente),
    /// sin distinguir mayúsculas/minúsculas.
    ///
    /// Ejemplo: findByTitleContainingIgnoreCase("elden", pageable)
    /// → devolverá todos los productos con "elden" en el título.
    ///
    /// Pageable permite definir la paginación (page, size, sort).
    Page<ProductModel> findByTitleContainingIgnoreCase(String q, Pageable pageable);

    /// Busca productos por plataforma (PC, PS5, etc.),
    /// ignorando mayúsculas/minúsculas.
    ///
    /// Ejemplo: findByPlatformIgnoreCase("pc", pageable)
    /// → devolverá todos los productos cuya plataforma sea "PC".
    Page<ProductModel> findByPlatformIgnoreCase(String platform, Pageable pageable);
}
