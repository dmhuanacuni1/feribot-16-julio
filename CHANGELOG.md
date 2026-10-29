# Changelog

Todos los cambios relevantes de este proyecto se documentan aquí.
Formato basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/)
y versionado [SemVer](https://semver.org/lang/es/).

## [0.2.0] - 2026-10-29

### Agregado
- Proyecto Android en Java 17 con Gradle 9.5 y Android Gradle Plugin 9.3.3.
- Persistencia local con Room: 9 entidades, DAOs y carga automática del dataset.
- Motor FeriBot offline: normalización, intenciones, sinónimos, modismos y
  coincidencia difusa (Levenshtein).
- Pantalla de chat con burbujas, envío por teclado y aviso de voz para el Sprint 2.
- 11 pruebas unitarias del normalizador y del motor.
- Pipeline de integración continua en GitHub Actions (compilar, probar y publicar APK).

## [0.1.0] - 2026-10-29

### Agregado
- Estructura inicial del repositorio y configuración de Git.
- Documentación: visión, alcance, metodología DevOps, arquitectura, modelo de datos,
  backlog y convenciones.
- Diagramas Mermaid de arquitectura, flujo del motor, entidades y sincronización.
- Esquema SQLite (`database/schema.sql`) compatible con Room.
- Dataset semilla con 5 rubros, 25 productos y 8 puestos de la Feria 16 de Julio.
- Script `tools/generar-seed.ps1` para regenerar `database/seed.sql`.
