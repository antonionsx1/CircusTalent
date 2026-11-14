# CircusTalent

Aplicación web Java basada en **Spring MVC** y **Spring Security** (configuración XML) que implementa un flujo de autenticación con formulario de login personalizado, páginas públicas y páginas protegidas por rol.

> Estado actual: prueba de concepto funcional. Sirve como base para construir la plataforma CircusTalent.

---

## Tabla de contenido

1. [Tecnologías](#tecnologías)
2. [Estructura del proyecto](#estructura-del-proyecto)
3. [Requisitos](#requisitos)
4. [Compilación](#compilación)
5. [Ejecución](#ejecución)
6. [Rutas disponibles](#rutas-disponibles)
7. [Seguridad](#seguridad)
8. [Configuración](#configuración)
9. [Desarrollo en Eclipse](#desarrollo-en-eclipse)
10. [Próximos pasos](#próximos-pasos)

---

## Tecnologías

| Componente        | Versión          |
|-------------------|------------------|
| Java              | 8 (1.8) o superior |
| Spring Web MVC    | 4.3.30.RELEASE   |
| Spring Security   | 4.2.20.RELEASE   |
| Servlet API       | 3.1              |
| JSP + JSTL        | 1.2              |
| Maven             | 3.x              |
| Servidor          | Apache Tomcat 8.5 (o cualquier contenedor Servlet 3.1) |

---

## Estructura del proyecto

```
CircusTalent/
├── pom.xml                         # Definición Maven (empaquetado WAR)
├── src/
│   └── com/mx/beeva/controller/
│       └── MainController.java     # Controlador con todas las rutas
└── WebContent/
    ├── index.jsp                   # Portada pública
    ├── META-INF/
    │   └── context.xml             # Context path de Tomcat: /CircusTalent
    └── WEB-INF/
        ├── web.xml                 # DispatcherServlet + filtro de Spring Security
        ├── circustalent-servlet.xml# Escaneo de componentes y resolución de vistas
        ├── spring-security.xml     # Reglas de acceso, login, logout y usuarios
        └── jsp/
            ├── login.jsp           # Formulario de inicio de sesión
            ├── hello.jsp           # Página pública
            └── admin.jsp           # Página protegida (requiere ROLE_USER)
```

---

## Requisitos

- JDK 8 o superior
- Maven 3.x
- Apache Tomcat 8.5+ (para despliegue)

---

## Compilación

```bash
mvn clean package
```

Genera el archivo `target/CircusTalent.war`.

---

## Ejecución

### Opción A: Tomcat

1. Copiar `target/CircusTalent.war` a la carpeta `webapps/` de Tomcat.
2. Iniciar Tomcat.
3. Abrir `http://localhost:8080/CircusTalent/`.

### Opción B: Jetty (rápido, sin instalar servidor)

```bash
mvn org.eclipse.jetty:jetty-maven-plugin:9.4.53.v20231009:run-war -Djetty.http.port=8080
```

Abrir `http://localhost:8080/`.

---

## Rutas disponibles

| Ruta                          | Método | Acceso     | Descripción |
|-------------------------------|--------|------------|-------------|
| `/`                           | GET    | Público    | Portada con enlaces a la página pública y la privada |
| `/hello`, `/hello.html`       | GET    | Público    | Página pública con título y mensaje |
| `/welcome`                    | GET    | Público    | Destino por defecto tras iniciar sesión |
| `/login`                      | GET    | Público    | Formulario de login (`?error` y `?logout` muestran mensajes) |
| `/j_spring_security_check`    | POST   | Público    | Procesa el login (gestionado por Spring Security) |
| `/admin`, `/admin.html`       | GET    | `ROLE_USER`| Página protegida; sin sesión redirige a `/login` |
| `/j_spring_security_logout`   | POST   | Autenticado| Cierra la sesión y redirige a `/login?logout` |

---

## Seguridad

Definida en `WebContent/WEB-INF/spring-security.xml`:

- **Control de acceso:** las rutas `/admin**` requieren el rol `ROLE_USER`.
- **Login por formulario:** página propia en `/login`, parámetros `username` y `password`.
- **Protección CSRF activada:** todos los formularios POST (login y logout) incluyen el token `_csrf`.
- **Usuario de demostración:**

  | Usuario | Contraseña | Rol        |
  |---------|------------|------------|
  | `mario` | `123456`   | `ROLE_USER`|

> ⚠️ **Solo para desarrollo.** El usuario está definido en memoria y su contraseña en texto plano. Antes de cualquier entorno productivo se debe migrar a un repositorio de usuarios persistente (base de datos/LDAP) con contraseñas cifradas (BCrypt).

---

## Configuración

| Archivo | Propósito |
|---------|-----------|
| `web.xml` | Registra el `DispatcherServlet` (`circustalent`) en `/`, el `ContextLoaderListener` y el filtro `springSecurityFilterChain` en `/*`. |
| `circustalent-servlet.xml` | Escanea `com.mx.beeva.*` y resuelve vistas en `/WEB-INF/jsp/{vista}.jsp`. |
| `spring-security.xml` | Reglas de autorización, login, logout, CSRF y proveedor de usuarios. |
| `context.xml` | Context path `/CircusTalent` al desplegar en Tomcat. |

---

## Desarrollo en Eclipse

El proyecto incluye la configuración de Eclipse (`.project`, `.classpath`, `.settings`).

1. *File → Import → Existing Maven Projects* y seleccionar la carpeta del repositorio.
2. Asociar un servidor Apache Tomcat 8.5.
3. *Run As → Run on Server*.

---

## Próximos pasos

- Persistir usuarios y roles en base de datos con contraseñas BCrypt.
- Añadir pruebas unitarias e integración (Spring Test / MockMvc).
- Externalizar configuración por entorno.
- Desarrollar los módulos de negocio de CircusTalent.
