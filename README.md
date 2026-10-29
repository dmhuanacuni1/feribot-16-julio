# FeriBot 16 de Julio 🤖

Asistente móvil de **cotización y geolocalización** para la Feria 16 de Julio (El Alto, Bolivia).
El usuario pregunta por un producto (texto o voz), FeriBot responde con precio estimado en
Bolivianos, ubicación del puesto y un consejo de compra, incluso **sin conexión a internet**.

> Proyecto académico — Dispositivos II · Metodología DevOps · Java + Android Studio.

---

## 🎯 Alcance del sistema

| Componente | Descripción | Tecnología |
|---|---|---|
| 📱 App móvil | Chat de texto/voz, GPS con mapas offline, cámara, históricos | Java 17, Android Studio, Room, CameraX, osmdroid |
| 🌐 Panel web | Registro de productos, precios, puestos y horarios | Spring Boot 3 + Thymeleaf + Bootstrap |
| 🔌 API REST | Sincronización y administración del catálogo | Spring Boot 3, PostgreSQL, JWT |
| 🗃️ Datos | Catálogo por rubros, precios BOB y referencias geográficas | SQLite (móvil) · PostgreSQL (nube) |

**Arquitectura híbrida (offline-first):** la app responde desde SQLite local y sincroniza
con la nube mediante una cola de cambios cuando hay internet.

---

## 🗂️ Estructura del repositorio

```
AGENTE IA/
├── app/                    # Aplicación Android (Java)          [Sprint 2]
├── backend/                # API REST + panel web (Spring Boot) [Sprint 3]
├── database/
│   ├── schema.sql          # Esquema SQLite (referencia Room)
│   ├── seed.sql            # Datos iniciales (generado)
│   └── dataset/
│       └── productos.json  # Dataset semilla editable
├── docs/                   # Visión, DevOps, arquitectura, modelo de datos, backlog
├── tools/
│   ├── generar-seed.ps1    # Dataset JSON → seed.sql
│   ├── generar-mapa.ps1    # Plano offline de la feria → PNG
│   └── validar-seed.py     # Verifica la consistencia del dataset
├── .github/                # Workflows de CI y plantillas
└── README.md
```

---

## 📚 Documentación

| Documento | Contenido |
|---|---|
| [00 · Visión y alcance](docs/00-vision-alcance.md) | Problema, objetivos, alcance, KPIs y riesgos |
| [01 · Metodología DevOps](docs/01-metodologia-devops.md) | Scrum + DevOps, ramas, CI/CD y Definition of Done |
| [02 · Arquitectura](docs/02-arquitectura.md) | Componentes, flujo offline-first, diagramas y ADRs |
| [03 · Modelo de datos](docs/03-modelo-datos.md) | Diagrama ER, diccionario de datos y sincronización |
| [04 · Backlog](docs/04-backlog.md) | Épicas, historias de usuario y plan de sprints |
| [05 · Convenciones Git](docs/05-convenciones-git.md) | Ramas, commits, PRs y releases |

---

## 🚀 Puesta en marcha

```bash
git clone https://github.com/dmhuanacuni1/feribot-16-julio.git
cd feribot-16-julio

# Compilar el APK de depuración
./gradlew assembleDebug

# Ejecutar las pruebas unitarias del motor
./gradlew testDebugUnitTest
```

El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

Requisitos: JDK 17+ (Android Studio incluye uno), Android Studio Ladybug o superior,
Android SDK 36. Si Gradle no encuentra el SDK, crea `local.properties` con
`sdk.dir=<ruta del SDK>`.

> **Si el proyecto vive dentro de OneDrive** y la compilación falla con
> `AccessDeniedException` o `Unable to delete directory` en `app/build`, cierra
> Android Studio, ejecuta `rmdir /s /q app\build` y vuelve a compilar. También puedes
> excluir `app/build` y `.gradle` de la sincronización de OneDrive.

---

## ✨ Funcionalidades por sprint

| Sprint | Funcionalidad | Estado |
|---|---|---|
| 1 | Chat offline con motor de reglas, sinónimos y precios en BOB | ✅ |
| 2 | Mapa offline con el puesto marcado, GPS y distancia | ✅ |
| 2 | Dictado de consultas por voz | ✅ |
| 2 | Foto del producto guardada con la cotización | ✅ |
| 3 | Panel web + API REST + sincronización | ⏳ |

---

## 🗺️ Roadmap

- [x] **Sprint 0** — Repositorio, documentación, diagramas, esquema y dataset
- [x] **Sprint 1** — App Android en Java, Room y chat offline con motor de reglas
- [x] **Sprint 2** — Mapa offline, GPS, dictado por voz y cámara
- [ ] **Sprint 3** — API REST + panel web + sincronización
- [ ] **Sprint 4** — Pruebas, hardening y entrega final

---

## ⚠️ Nota sobre los datos

El dataset incluido es **de ejemplo**. Los precios, coordenadas y puestos son referenciales
y deben validarse en campo con comerciantes reales antes de la entrega final.

---

## 👥 Equipo y roles

| Rol | Responsable |
|---|---|
| Product Owner | _Por definir_ |
| Scrum Master | _Por definir_ |
| Desarrollo móvil | _Por definir_ |
| Desarrollo backend / web | _Por definir_ |
| QA | _Por definir_ |
