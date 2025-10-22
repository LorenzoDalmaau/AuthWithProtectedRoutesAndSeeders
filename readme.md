# 🧩 AuthWithProtectedRoutesAndSeeders

Este proyecto es una **implementación básica y educativa** que muestra cómo estructurar una aplicación backend profesional utilizando **Spring Boot**, aplicando una arquitectura en capas y conceptos fundamentales de autenticación, protección de rutas y gestión de datos con Seeders.

---

## 🏗️ Estructura del Proyecto 

Hola que tal

El proyecto sigue una **arquitectura por capas**, separando claramente la lógica en:

- **Controllers** → Gestionan las peticiones HTTP y las respuestas al frontend.
- **Services** → Contienen la lógica de negocio de la aplicación.
- **Repositories** → Se encargan de la comunicación con la base de datos.
- **Entities** → Representan las tablas y modelos de datos.

A lo largo del proyecto se utilizan etiquetas clave de Spring como:
- `@Entity`
- `@Repository`
- `@RequestMapping`
- `@PathVariable`
- `@Autowired`
- `@Service`

Esta estructura permite mantener un código **limpio, escalable y fácil de mantener**, siguiendo buenas prácticas del desarrollo backend con Spring Boot.

---

## 🔐 Autenticación y Rutas Protegidas

El sistema de autenticación se basa en **tokens**, generados al iniciar sesión y almacenados en un **map en memoria** (solo para fines demostrativos).

> ⚠️ En un entorno real, estos tokens deberían almacenarse en una **base de datos dedicada exclusivamente a la gestión de tokens**, separada de la base de datos de usuarios.  
> Esto mejora significativamente la seguridad ante posibles brechas.

El middleware **`AuthFilter`** intercepta todas las peticiones y:
- **Permite** el acceso solo a las rutas públicas (`/auth/**`).
- **Bloquea** el acceso a las rutas protegidas si el usuario no posee un token válido.

Las pruebas se realizan mediante **Postman**, mostrando cómo enviar peticiones autenticadas y acceder a rutas protegidas mediante el header `Authorization`.

---

## 🧠 Conceptos Trabajados

- Uso de **Controllers**, **Services**, **Repositories** y **Entities** correctamente.
- Implementación de **autenticación basada en tokens**.
- **Gestión de usuarios** (registro, login, validación).
- Protección de rutas mediante un **filtro personalizado (AuthFilter)**.
- Respuestas estructuradas hacia el frontend.
- Pruebas de endpoints con **Postman**.
- Implementación de **paginación y búsqueda** para entidades.
- Introducción al uso de **Seeders** para poblar la base de datos.

---

## 🪄 Seeders

Los **Seeders** son clases o componentes que permiten **poblar la base de datos automáticamente** con datos de ejemplo al iniciar la aplicación.  
Son especialmente útiles en entornos de desarrollo o pruebas, para disponer de datos iniciales sin necesidad de insertarlos manualmente.

### 📘 Cómo funcionan
1. Se crean objetos de ejemplo (por ejemplo, productos o usuarios).
2. Estos objetos se guardan en la base de datos al iniciar la aplicación.
3. El Seeder se ejecuta **una sola vez** (normalmente bajo el método `run()` de `CommandLineRunner` o `ApplicationRunner`).

### 🧰 Ventajas
- Facilitan el desarrollo al no depender de datos manuales.
- Permiten probar rápidamente funcionalidades como listados, búsquedas o paginación.
- Simulan un entorno real desde el arranque del proyecto.

---

## 🛍️ Gestión de Productos

El proyecto incluye un módulo completo para la gestión de productos:
- **Listar productos con paginación.**
- **Filtrar o buscar productos** (funcionalidad preparada desde el backend).
- **Obtener producto por ID.**
- **Poblar la base de datos** con productos iniciales mediante Seeders.

---

## 🚀 Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **MySQL Server**
- **MySQL Workbench**
- **Postman (para pruebas de endpoints)**

---

## 🧩 Objetivo del Proyecto

Este proyecto tiene un **enfoque educativo**, diseñado para:
- Comprender cómo estructurar un backend con buenas prácticas.
- Aprender a manejar autenticación, seguridad y rutas protegidas.
- Practicar el uso de Seeders, paginación y filtros.
- Entender cómo responder correctamente al frontend.

---

## 💬 Autor

**Desarrollado por [Lorenzo Dalmau](https://github.com/LorenzoDalmaau)**  
📍 Proyecto educativo centrado en aprender buenas prácticas de arquitectura y seguridad backend con Spring Boot.
