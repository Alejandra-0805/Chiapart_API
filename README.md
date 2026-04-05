# Chiapart — API REST

> Documentación técnica del backend para el sistema de catálogo y gestión de productos regionales **Chiapart**.  
> Incluye autenticación de usuarios, catálogos de filtros fijos y un CRUD completo de productos con subida de imágenes a la nube.

---

## Tabla de Contenidos

- [Información General](#información-general)
- [Autenticación](#autenticación)
- [Módulo de Autenticación](#1-módulo-de-autenticación)
- [Módulo de Catálogo](#2-módulo-de-catálogo)
- [Módulo de Productos](#3-módulo-de-productos)

---

## Información General

| Parámetro        | Valor                                                              |
|------------------|--------------------------------------------------------------------|
| URL Producción   | `http://TU_IP_PUBLICA/api/v1`                                      |
| URL Desarrollo   | `http://localhost:8080/api/v1`                                     |
| Puerto local     | `8080`                                                             |
| Formato de datos | `application/json` (salvo endpoints con imagen: `multipart/form-data`) |

---

## Autenticación

Las rutas de gestión de productos requieren autenticación mediante **JWT**. Incluye el token en cada petición protegida usando la siguiente cabecera HTTP:

```
Authorization: Bearer <tu_token_jwt>
```

---

## 1. Módulo de Autenticación

**Base:** `/users`

---

### Registrar Usuario

```
POST /users/register
```

**Auth requerida:** No

**Body — `application/json`**
```json
{
  "email": "usuario@correo.com",
  "passwordRaw": "password123",
  "username": "Nombre Completo"
}
```

---

### Iniciar Sesión

```
POST /users/login
```

**Auth requerida:** No

**Body — `application/json`**
```json
{
  "email": "usuario@correo.com",
  "passwordRaw": "password123"
}
```

**Respuesta exitosa — `200 OK`**

Devuelve un objeto con los campos `token` y `username`.

---

## 2. Módulo de Catálogo

**Base:** `/catalog`

> Todos los endpoints de este módulo son **públicos** y no requieren token.  
> Se utilizan para llenar los menús desplegables de la aplicación.

---

### Obtener Categorías

```
GET /catalog/categories
```

**Auth requerida:** No

---

### Obtener Regiones

```
GET /catalog/regions
```

**Auth requerida:** No

---

## 3. Módulo de Productos

**Base:** `/products`

---

### Ver Catálogo General

```
GET /products
```

**Auth requerida:** No

Admite parámetros de consulta opcionales para búsqueda y filtrado:

| Parámetro   | Tipo    | Descripción                           |
|-------------|---------|---------------------------------------|
| `search`    | String  | Busca por coincidencia en el nombre   |
| `category`  | Integer | Filtra por ID de categoría            |
| `region`    | Integer | Filtra por ID de región               |

**Ejemplo:**
```
GET /products?search=muñeca&region=2
```

---

### Ver Mis Productos

```
GET /products/me
```

**Auth requerida:** Sí

Devuelve únicamente los productos creados por el usuario autenticado.

---

### Crear Producto

```
POST /products
```

**Auth requerida:** Sí

> **Importante:** Este endpoint usa `multipart/form-data`, no `application/json`.

**Body — `multipart/form-data`**

| Campo         | Tipo           | Descripción                          |
|---------------|----------------|--------------------------------------|
| `nombre`      | Text           | Nombre del producto                  |
| `precio`      | Text / Decimal | Precio del producto                  |
| `descripcion` | Text           | Descripción (máximo 200 caracteres)  |
| `categoriaId` | Text / Integer | ID de la categoría                   |
| `regionId`    | Text / Integer | ID de la región                      |
| `imagen`      | File           | Archivo de imagen del producto       |

---

### Actualizar Producto

```
PUT /products/{id}
```

**Auth requerida:** Sí

> **Importante:** Este endpoint usa `multipart/form-data`, no `application/json`.

**Body — `multipart/form-data`**

Los campos son los mismos que en la creación. El campo `imagen` es **opcional**: si no se envía un archivo nuevo, se conserva la imagen existente.

| Campo         | Tipo           | Requerido | Descripción                         |
|---------------|----------------|-----------|-------------------------------------|
| `nombre`      | Text           | Sí        | Nombre del producto                 |
| `precio`      | Text / Decimal | Sí        | Precio del producto                 |
| `descripcion` | Text           | Sí        | Descripción (máximo 200 caracteres) |
| `categoriaId` | Text / Integer | Sí        | ID de la categoría                  |
| `regionId`    | Text / Integer | Sí        | ID de la región                     |
| `imagen`      | File           | No        | Nueva imagen (reemplaza la actual)  |

---

### Eliminar Producto

```
DELETE /products/{id}
```

**Auth requerida:** Sí

| Parámetro | Tipo    | Descripción                      |
|-----------|---------|----------------------------------|
| `id`      | Integer | ID del producto a eliminar       |

> **Nota:** Solo es posible eliminar un producto si el usuario autenticado es su creador original.

---

<div align="center">

**Chiapart** · Backend · v1.0

</div>