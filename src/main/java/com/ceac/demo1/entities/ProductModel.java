package com.ceac.demo1.entities;

/// Paquete donde se define la entidad.
/// Las "entities" representan tablas de la base de datos (cada objeto = fila en la tabla).

import jakarta.persistence.*;      /// Importa las anotaciones JPA necesarias para mapear la entidad.
import java.math.BigDecimal;       /// Tipo para manejar precios con precisión decimal (mejor que double).
import java.time.LocalDate;        /// Tipo de dato para fechas (sin hora).

/// Marca esta clase como una entidad JPA.
/// Es decir, se guardará como una tabla en la base de datos.
@Entity

/// Define el nombre de la tabla y crea índices para acelerar búsquedas por título y plataforma.
@Table(name = "products", indexes = {
        @Index(name = "idx_products_title", columnList = "title"),      // índice por título
        @Index(name = "idx_products_platform", columnList = "platform") // índice por plataforma
})
public class ProductModel {

    /// Campo que actúa como clave primaria.
    /// @Id → marca este campo como el identificador único de cada registro.
    /// @GeneratedValue → indica que el valor se genera automáticamente (auto-incremental en MySQL).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Título del producto (nombre del videojuego).
    /// @Column(nullable = false) → este campo no puede ser nulo en la BD.
    @Column(nullable = false)
    private String title;

    /// Plataforma del juego (por ejemplo "PC", "PS5", etc.).
    /// También obligatoria (no puede ser nula).
    @Column(nullable = false)
    private String platform;

    /// Precio del producto con precisión decimal.
    /// precision=10 y scale=2 → máximo 10 dígitos, de los cuales 2 son decimales (ej: 99999999.99)
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    /// Descuento del producto (en porcentaje).
    /// Puede ser nulo o 0 si no hay descuento.
    @Column
    private Integer discount;

    /// URL de la imagen o portada del juego.
    /// length=512 → límite de 512 caracteres.
    @Column(length = 512)
    private String imageUrl;

    /// Descripción corta del producto.
    /// length=1024 → permite textos algo largos (1 KB aprox).
    @Column(length = 1024)
    private String description;

    /// Fecha de lanzamiento del producto.
    /// Usamos LocalDate porque solo nos interesa la fecha (sin hora).
    @Column
    private LocalDate releaseDate;

    /// Nombre del editor o desarrollador del juego.
    @Column
    private String publisher;

    /// Stock disponible (cantidad en inventario, opcional).
    @Column
    private Integer stock;

    /// Etiquetas o categorías separadas por comas (por ejemplo: "rpg,openworld").
    /// length=256 → tamaño suficiente para varias etiquetas.
    @Column(length = 256)
    private String tags;

    // ==============================================================
    // 🔹 GETTERS y SETTERS
    // ==============================================================
    /// Los Getters y Setters son necesarios para que JPA pueda leer y modificar los valores
    /// de los campos de esta clase cuando los mapea a la base de datos.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
