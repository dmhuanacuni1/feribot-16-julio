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
├── app/                    # Aplicación Android (Java)          [Sprint 1]
├── backend/                # API REST + panel web (Spring Boot) [Sprint 3]
├── database/
│   ├── schema.sql          # Esquema SQLite (referencia Room)
│   ├── seed.sql            # Datos iniciales (generado)
│   └── dataset/
│       └── productos.json  # Dataset semilla editable
├── docs/                   # Visión, DevOps, arquitectura, modelo de datos, backlog
├── tools/
│   └── generar-seed.ps1    # Dataset JSON → seed.sql
├── .github/                # Plantillas de issues y pull requests
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

## 🚀 Puesta en marcha (cuando exista el módulo `app/`)

```bash
git clone <URL_DEL_REPO>
cd "AGENTE IA"
# Abrir la carpeta en Android Studio y ejecutar el módulo app
./gradlew assembleDebug
```

Requisitos: JDK 17+, Android Studio Ladybug o superior, Android SDK 36.

---

## 🗺️ Roadmap

- [x] **Sprint 0** — Repositorio, documentación, diagramas, esquema y dataset
- [ ] **Sprint 1** — Esqueleto Android + chat offline con reglas y Room
- [ ] **Sprint 2** — Geolocalización, mapas offline, cámara y voz
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
