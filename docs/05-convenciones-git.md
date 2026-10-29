# 05 · Convenciones de Git y GitHub

## 1. Ramas

| Rama | Propósito | Protección |
|---|---|---|
| `main` | Versiones estables y releases | Push directo bloqueado |
| `develop` | Integración del sprint | Push directo bloqueado |
| `feature/HU-XX-descripcion` | Una historia de usuario | Libre |
| `bugfix/descripcion` | Corrección puntual | Libre |
| `hotfix/descripcion` | Corrección urgente desde `main` | Libre |
| `docs/descripcion` | Solo documentación | Libre |

Ejemplos:

```bash
git checkout -b feature/HU-04-chat-offline
git checkout -b bugfix/precio-formato-bob
git checkout -b docs/manual-usuario
```

## 2. Commits — Conventional Commits

Formato:

```
<tipo>(<alcance opcional>): <descripción en imperativo>

[cuerpo opcional]

[footer opcional: Refs #12]
```

| Tipo | Uso |
|---|---|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `docs` | Documentación |
| `style` | Formato sin cambio de lógica |
| `refactor` | Reestructuración sin cambio funcional |
| `test` | Pruebas |
| `chore` | Configuración, dependencias, build |
| `perf` | Mejora de rendimiento |
| `ci` | Pipeline de integración continua |

Ejemplos reales del proyecto:

```
feat(chat): agregar motor de intenciones offline
fix(mapa): corregir marcador fuera de rango
docs(arquitectura): documentar sincronización híbrida
test(motor): cubrir sinónimos bolivianos
chore(gradle): actualizar AGP a 8.7
```

Reglas:

- Descripción en minúsculas, sin punto final, máximo 72 caracteres.
- Un commit = un cambio lógico.
- No mezclar refactor con funcionalidad.
- Referenciar el issue en el footer cuando aplique: `Refs #14`.

## 3. Pull Requests

Plantilla disponible en `.github/PULL_REQUEST_TEMPLATE.md`. Todo PR debe:

1. Tener título con el mismo formato de commit.
2. Describir qué cambia y cómo probarlo.
3. Referenciar la historia (`Closes #12`).
4. Pasar CI y revisión.
5. Actualizar documentación si corresponde.

## 4. Etiquetas de issues

`feature` · `bug` · `docs` · `chore` · `priority:alta` · `priority:media` · `priority:baja` · `blocked`

## 5. Releases y versionado

Se usa **SemVer** (`MAYOR.MENOR.PARCHE`).

| Cambio | Versión |
|---|---|
| Nuevo módulo o funcionalidad grande | `1.0.0 → 1.1.0` |
| Correcciones compatibles | `1.1.0 → 1.1.1` |
| Cambio incompatible de API o BD | `1.1.1 → 2.0.0` |

Flujo de release:

```bash
git checkout main
git merge --no-ff develop
git tag -a v1.0.0 -m "Release 1.0.0 - Chat offline"
git push origin main --tags
```

El release incluye: APK firmado, changelog, capturas y notas de la versión.

## 6. Flujo diario recomendado

```bash
git checkout develop
git pull origin develop
git checkout -b feature/HU-05-sinonimos
# ... trabajar ...
git add .
git commit -m "feat(motor): agregar sinónimos bolivianos"
git push -u origin feature/HU-05-sinonimos
# abrir Pull Request en GitHub
```

## 7. Higiene del repositorio

- No subir `build/`, `.idea/`, `local.properties`, APKs ni claves.
- No subir bases `.db` generadas.
- Los binarios pesados (tiles de mapas) se publican como *release asset*, no en Git.
- Revisar `git status` antes de cada commit.
