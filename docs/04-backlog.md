# 04 · Product Backlog

Priorización **MoSCoW**: Must (imprescindible), Should (importante), Could (deseable),
Won't (fuera del alcance del MVP). Estimación en puntos de historia (1, 2, 3, 5, 8).

## 1. Épicas

| ID | Épica | Prioridad |
|---|---|---|
| E1 | Fundación del proyecto y DevOps | Must |
| E2 | Chat de cotización offline | Must |
| E3 | Geolocalización y mapas offline | Must |
| E4 | Voz y cámara | Should |
| E5 | Panel web y API REST | Must |
| E6 | Sincronización híbrida | Should |
| E7 | Calidad, pruebas y entrega | Must |

## 2. Historias de usuario

### E1 · Fundación

| ID | Historia | Pts | Prioridad |
|---|---|---|---|
| HU-01 | Como equipo, quiero un repositorio con documentación y convenciones para trabajar ordenados. | 3 | Must |
| HU-02 | Como dev, quiero el proyecto Android compilando en Java para empezar a programar. | 3 | Must |
| HU-03 | Como dev, quiero CI que compile y ejecute pruebas en cada PR. | 5 | Should |

**Criterios de aceptación HU-02**
- El proyecto abre en Android Studio sin errores de sincronización.
- `./gradlew assembleDebug` genera un APK.
- `minSdk 26`, `targetSdk 36`, Java 17.

### E2 · Chat offline

| ID | Historia | Pts | Prioridad |
|---|---|---|---|
| HU-04 | Como visitante, quiero escribir "¿a cómo está la lap?" y recibir precio, ubicación y consejo. | 8 | Must |
| HU-05 | Como visitante, quiero que la app entienda modismos bolivianos. | 5 | Must |
| HU-06 | Como visitante, quiero ver el rango de precio en BOB con formato `Bs 2.800 – 3.600`. | 2 | Must |
| HU-07 | Como visitante, quiero consultar categorías aunque no escriba el nombre exacto. | 3 | Must |
| HU-08 | Como visitante, quiero un historial de mis consultas y cotizaciones. | 3 | Should |

**Criterios de aceptación HU-04**
- La respuesta contiene las 5 secciones: producto, precio, ubicación, acción y consejo.
- Se genera en menos de 1 s sin conexión.
- Si no hay coincidencia, responde con el rubro más cercano y avisa que es estimado.

### E3 · Mapas

| ID | Historia | Pts | Prioridad |
|---|---|---|---|
| HU-09 | Como visitante, quiero tocar "Ver en Mapa" y ver el puesto marcado. | 5 | Must |
| HU-10 | Como visitante, quiero usar el mapa sin internet. | 8 | Must |
| HU-11 | Como visitante, quiero ver mi posición actual y la distancia al puesto. | 3 | Should |

### E4 · Voz y cámara

| ID | Historia | Pts | Prioridad |
|---|---|---|---|
| HU-12 | Como visitante, quiero dictar mi consulta con el micrófono. | 5 | Should |
| HU-13 | Como visitante, quiero escuchar la respuesta (TTS). | 3 | Could |
| HU-14 | Como visitante, quiero tomar una foto del producto y guardarla con la cotización. | 5 | Should |

### E5 · Panel web y API

| ID | Historia | Pts | Prioridad |
|---|---|---|---|
| HU-15 | Como administrador, quiero iniciar sesión con usuario y contraseña. | 3 | Must |
| HU-16 | Como comerciante, quiero registrar mi puesto con ubicación en el mapa. | 5 | Must |
| HU-17 | Como comerciante, quiero crear y editar productos con rango de precios y días de atención. | 5 | Must |
| HU-18 | Como administrador, quiero listar y desactivar productos obsoletos. | 3 | Must |

### E6 · Sincronización

| ID | Historia | Pts | Prioridad |
|---|---|---|---|
| HU-19 | Como sistema, quiero subir cambios locales cuando haya internet. | 8 | Should |
| HU-20 | Como usuario, quiero recibir catálogo actualizado sin reinstalar la app. | 5 | Should |

### E7 · Calidad

| ID | Historia | Pts | Prioridad |
|---|---|---|---|
| HU-21 | Como equipo, quiero pruebas unitarias del motor de intenciones. | 5 | Must |
| HU-22 | Como equipo, quiero una demo guiada con datos reales validados. | 3 | Must |
| HU-23 | Como jurado, quiero documentación técnica y manual de usuario. | 3 | Must |

## 3. Plan de sprints

| Sprint | Objetivo | Historias | Entregable |
|---|---|---|---|
| 0 | Base documental y de datos | HU-01 | Repo, docs, ER, dataset |
| 1 | Chat offline funcional | HU-02, HU-04, HU-05, HU-06, HU-21 | APK con chat y Room |
| 2 | Ubicación y captura | HU-09, HU-10, HU-12, HU-14 | Mapa offline, voz, cámara |
| 3 | Administración y nube | HU-15, HU-16, HU-17, HU-18, HU-19 | API + panel web |
| 4 | Cierre | HU-20, HU-22, HU-23 | Release v1.0.0 + manual |

## 4. Definition of Ready

Una historia entra a un sprint si:

- Tiene criterios de aceptación claros y verificables.
- Está estimada y cabe en el sprint.
- No depende de una historia no terminada.
- Tiene diseño o referencia visual si afecta la UI.

## 5. Estado de avance

| Sprint | Estado | Historias completadas |
|---|---|---|
| 0 | ✅ Completado | HU-01 |
| 1 | ✅ Completado | HU-02, HU-04, HU-05, HU-06, HU-21 |
| 2 | ⏳ Pendiente | HU-09 a HU-14 |
| 3 | ⏳ Pendiente | HU-15 a HU-19 |
| 4 | ⏳ Pendiente | HU-20, HU-22, HU-23 |

