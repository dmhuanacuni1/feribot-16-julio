# 01 · Metodología DevOps

El proyecto combina **Scrum** (para la gestión del trabajo) con **prácticas DevOps**
(para la entrega continua). El objetivo es que cada incremento sea pequeño, versionado,
probado y desplegable.

## 1. Marco de trabajo

| Elemento | Definición |
|---|---|
| Sprints | 1 semana (lunes a domingo) |
| Daily | 10 min, avance / impedimento / plan |
| Review | Demo del incremento al final del sprint |
| Retro | 15 min, qué funcionó / qué mejorar |
| Tablero | GitHub Projects (Backlog → Todo → In progress → Review → Done) |
| Historias | Formato: *Como \<rol\>, quiero \<acción\> para \<beneficio\>* |

## 2. Roles

| Rol | Responsabilidad |
|---|---|
| Product Owner | Prioriza el backlog y valida los criterios de aceptación |
| Scrum Master | Facilita ceremonias y elimina impedimentos |
| Dev móvil | App Android en Java, Room, GPS, cámara |
| Dev backend | API REST, panel web, base de datos |
| QA | Pruebas funcionales y de regresión |

En equipos pequeños los roles pueden acumularse, pero **nunca** el PO y el Scrum Master.

## 3. Flujo de trabajo (GitHub Flow adaptado)

```
main        ●────●────────────●────────────●   (releases estables)
             \                \            \
develop       ●──●──●──●────────●──●──●──────●  (integración)
                   \      /
feature/*           ●────●                      (una historia por rama)
```

Reglas:

1. Toda tarea nace de un **Issue** con etiqueta (`feature`, `bug`, `docs`, `chore`).
2. Se crea una rama `feature/HU-XX-descripcion-corta` desde `develop`.
3. Cada commit sigue **Conventional Commits** (ver `05-convenciones-git.md`).
4. Al terminar se abre un **Pull Request** contra `develop`.
5. El PR requiere: CI en verde, 1 revisión y criterios de aceptación cumplidos.
6. `develop` se integra a `main` solo en un release etiquetado (`vX.Y.Z`).

## 4. Integración y entrega continua (CI/CD)

Pipeline planificado en GitHub Actions:

| Etapa | Herramienta | Qué valida |
|---|---|---|
| Build | Gradle (`assembleDebug`) | Que el proyecto compile |
| Lint | Android Lint | Buenas prácticas y errores |
| Test | JUnit + Robolectric | Motor de reglas y repositorios |
| Backend test | Maven + JUnit | API y persistencia |
| Package | Gradle / Maven | Genera APK y JAR |
| Deploy | Render / Railway (backend) | Publica la API |
| Release | GitHub Releases | Adjunta APK firmado y changelog |

Estados obligatorios antes de mezclar:

- [x] Compilación exitosa
- [x] Pruebas unitarias en verde
- [x] Sin errores de lint críticos
- [x] Criterios de aceptación verificados

## 5. Ambientes

| Ambiente | Uso | Datos |
|---|---|---|
| `local` | Desarrollo en emulador/dispositivo | Dataset semilla |
| `staging` | Pruebas integradas | Copia anonimizada |
| `production` | Demo y entrega | Datos reales validados |

Las credenciales se manejan con **GitHub Secrets** y `local.properties` (nunca en el repo).

## 6. Definition of Done (DoD)

Una historia está terminada cuando:

1. El código está en `develop` mediante PR aprobado.
2. Compila sin warnings críticos y pasa lint.
3. Tiene pruebas unitarias de la lógica nueva.
4. Los criterios de aceptación se cumplen y se demuestran en Review.
5. La documentación afectada fue actualizada.
6. No introduce secretos ni archivos generados.
7. El tablero y el issue están actualizados.

## 7. Métricas DevOps

| Métrica | Cómo se mide |
|---|---|
| Frecuencia de despliegue | Releases por sprint |
| Lead time | Tiempo issue → merge |
| Tasa de fallos | Bugs post-release / releases |
| Tiempo de recuperación | Tiempo en corregir un bug crítico |
| Cobertura | Reporte de JaCoCo |

## 8. Herramientas

- **Git + GitHub** — versionado, Issues, Projects, Actions.
- **Android Studio** — desarrollo móvil.
- **Gradle** — build y dependencias.
- **JUnit 4/5 + Mockito + Robolectric** — pruebas.
- **Postman / Insomnia** — pruebas de API.
- **Mermaid** — diagramas versionados como texto.
