package com.ceac.demo1.config;

/// Paquete donde se encuentra esta clase.
/// "config" suele contener clases de configuración general del proyecto.

import com.ceac.demo1.entities.ProductModel;   /// Importa la entidad ProductModel (representa la tabla products)
import com.ceac.demo1.repositories.IProductRepository;  /// Importa el repositorio para interactuar con la base de datos
import com.fasterxml.jackson.core.type.TypeReference;   /// Clase que ayuda a leer listas genéricas de JSON
import com.fasterxml.jackson.databind.ObjectMapper;     /// Conversor entre JSON y objetos Java (de la librería Jackson)
import org.springframework.boot.CommandLineRunner;      /// Permite ejecutar código al iniciar la aplicación
import org.springframework.context.annotation.Bean;      /// Marca un método como "bean" (instancia gestionada por Spring)
import org.springframework.context.annotation.Configuration; /// Indica que esta clase contiene configuración para Spring

import java.io.InputStream;  /// Sirve para leer archivos dentro del proyecto (como el JSON)
import java.util.List;       /// Lista genérica de Java

@Configuration  /// Indica a Spring que esta clase define Beans de configuración
public class ProductSeeder {

    /// Repositorio para guardar y consultar productos en la base de datos
    private final IProductRepository repo;

    /// Mapper de Jackson que convierte JSON <-> Objetos Java
    private final ObjectMapper mapper;

    /// Constructor con inyección de dependencias
    /// Spring pasa automáticamente el repositorio y el ObjectMapper
    public ProductSeeder(IProductRepository repo, ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;  /// Usamos el ObjectMapper de Spring (ya configurado)
    }

    /// Definimos un Bean de tipo CommandLineRunner
    /// Este método se ejecuta automáticamente una vez cuando la aplicación arranca
    @Bean
    CommandLineRunner seedProducts() {
        /// Devuelve una función (lambda) que contiene el código a ejecutar al inicio
        return args -> {

            /// Si ya hay productos en la base de datos, no hacemos nada
            /// Así evitamos duplicar los datos cada vez que la app arranca
            if (repo.count() > 0) return;

            /// Abrimos el archivo products.json que está en la carpeta resources
            /// El try-with-resources cierra automáticamente el archivo al terminar
            try (InputStream is = getClass().getResourceAsStream("/products.json")) {

                /// Convertimos el contenido del JSON en una lista de objetos ProductModel
                /// TypeReference permite a Jackson entender que debe leer una lista genérica (List<ProductModel>)
                List<ProductModel> items = mapper.readValue(
                        is, new TypeReference<List<ProductModel>>() {}
                );

                /// Guardamos todos los productos en la base de datos de una sola vez
                repo.saveAll(items);

                /// Mostramos por consola cuántos productos se insertaron
                System.out.println("Seed de products cargado: " + items.size());

            } catch (Exception e) {
                /// Si ocurre un error (archivo no encontrado, formato incorrecto, etc.)
                /// mostramos un mensaje de error por consola
                System.err.println("No se pudo cargar products.json: " + e.getMessage());
            }
        };
    }
}
