package com.ceac.demo1.controllers;

/// Paquete donde vive esta clase.
/// En Spring Boot, los controladores van normalmente en "controllers".
/// Su función es recibir las peticiones HTTP y delegarlas al servicio correspondiente.

import com.ceac.demo1.entities.ProductModel;   /// Importa la entidad ProductModel (la clase que representa los productos)
import com.ceac.demo1.services.ProductService; /// Importa el servicio que contiene la lógica de negocio de productos
import org.springframework.data.domain.Page;   /// Clase que representa una "página" de resultados (paginación)
import org.springframework.http.ResponseEntity; /// Respuestas HTTP controladas (permite devolver códigos 200, 404, etc.)
import org.springframework.web.bind.annotation.*; /// Anotaciones para crear endpoints (GetMapping, PathVariable, etc.)

/// Marca la clase como un controlador REST.
/// Esto significa que cada método devolverá directamente datos (JSON) y no vistas HTML.
@RestController

/// Define la ruta base de este controlador.
/// Todos los endpoints de esta clase comenzarán con /products
@RequestMapping("/products")
public class ProductController {

    /// Dependencia hacia el servicio de productos.
    /// Este servicio se encarga de la lógica de negocio (consultas, filtros, etc.)
    private final ProductService service;

    /// Constructor que recibe el servicio.
    /// Spring inyectará automáticamente la instancia del ProductService.
    public ProductController(ProductService service){
        this.service = service;
    }

    // ==============================================================
    // 🔹 ENDPOINT: GET /products?page=0&size=20
    // ==============================================================
    /// Este método devuelve una lista paginada de productos.
    /// Recibe parámetros "page" (número de página) y "size" (cantidad por página).
    /// Si no se envían, usa los valores por defecto (0 y 20).
    @GetMapping
    public Page<ProductModel> list(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size) {
        /// Llama al servicio para obtener los productos paginados
        /// y devuelve directamente el resultado en formato JSON.
        return service.getProducts(page, size);
    }

    // ==============================================================
    // 🔹 ENDPOINT: GET /products/search?q=elden&page=0&size=10
    // ==============================================================
    /// Este endpoint busca productos por su título (parcialmente).
    /// - "q" es el texto a buscar.
    /// - También admite paginación con "page" y "size".
    @GetMapping("/search")
    public Page<ProductModel> search(@RequestParam String q,
                                     @RequestParam(defaultValue="0") int page,
                                     @RequestParam(defaultValue="20") int size){
        /// Llama al servicio, que internamente hace un "LIKE" ignorando mayúsculas/minúsculas.
        return service.search(q, page, size);
    }

    // ==============================================================
    // 🔹 ENDPOINT: GET /products/platform/{platform}?page=0&size=20
    // ==============================================================
    /// Este endpoint devuelve los productos filtrados por plataforma (por ejemplo, "PC" o "PS5").
    /// - {platform} viene en la URL (PathVariable)
    /// - "page" y "size" controlan la paginación
    @GetMapping("/platform/{platform}")
    public Page<ProductModel> byPlatform(@PathVariable String platform,
                                         @RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        /// Llama al servicio y devuelve los productos filtrados por plataforma
        return service.byPlatform(platform, page, size);
    }

    // ==============================================================
    // 🔹 ENDPOINT: GET /products/{id}
    // ==============================================================
    /// Devuelve un único producto por su ID.
    /// Si el ID no existe, devuelve un código 404 (Not Found).
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        /// Llama al servicio para buscar el producto por ID (devuelve Optional)
        return service.getById(id)
                /// Si lo encuentra, devuelve ResponseEntity.ok(producto)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                /// Si no lo encuentra, devuelve ResponseEntity.notFound()
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
