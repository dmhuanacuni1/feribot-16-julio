# 00 · Visión y alcance

## 1. Problema

La Feria 16 de Julio de El Alto es uno de los mercados populares más grandes de Bolivia.
Un visitante enfrenta tres dificultades concretas:

1. **No conoce los precios de referencia** y queda expuesto al regateo desinformado.
2. **No sabe dónde está el puesto** que busca dentro de más de 2 km de feria.
3. **No tiene señal de internet confiable** en la zona, por lo que las apps tradicionales fallan.

## 2. Solución propuesta

**FeriBot** es un asistente móvil que:

- Interpreta consultas en lenguaje natural y modismos bolivianos ("lap", "a cómo está", "chamarra").
- Devuelve una ficha estructurada: producto, rango de precio en BOB, ubicación y consejo.
- Funciona **offline** con una base SQLite y un motor de reglas local.
- Guía al usuario con GPS y mapas descargados hasta el puesto.
- Permite capturar fotos de productos y guardar cotizaciones.
- Se alimenta de un panel web administrado por comerciantes y administradores.

## 3. Objetivos

### Objetivo general
Desarrollar un sistema móvil híbrido de cotización y geolocalización para la Feria 16 de Julio,
con respuesta local sin internet y sincronización en la nube.

### Objetivos específicos
1. Modelar un catálogo por rubros con precios de referencia en BOB y ubicación de puestos.
2. Implementar un motor de intenciones con reglas y sinónimos que responda en < 1 s sin conexión.
3. Integrar mapas offline y navegación GPS al puesto.
4. Construir un panel web para que comerciantes actualicen precios y disponibilidad.
5. Aplicar un flujo DevOps con control de versiones, integración continua y releases.

## 4. Alcance

### Incluido (MVP)
- Chat de texto con respuestas estructuradas.
- Dictado por voz (reconocimiento del sistema Android).
- Catálogo de 5 rubros: Electrónica, Autopartes, Ropa, Calzado y Herramientas.
- Búsqueda por nombre, sinónimo y modismo.
- Ficha con precio, ubicación, acción sugerida y consejo.
- Mapa offline con marcador del puesto y ruta aproximada.
- Captura de foto y guardado local de la cotización.
- Panel web de administración (CRUD de productos y puestos).
- Sincronización bidireccional básica con resolución *last-write-wins*.

### Excluido (versiones futuras)
- Pagos en línea o reservas.
- Chat con modelo de lenguaje en la nube en tiempo real.
- Reputación de comerciantes y reseñas.
- Múltiples ferias simultáneas.
- iOS.

## 5. Stakeholders

| Stakeholder | Interés |
|---|---|
| Visitantes de la feria | Precio justo y ubicación rápida |
| Comerciantes | Publicar y actualizar su oferta |
| Administradores | Mantener catálogo y calidad de datos |
| Docente / jurado | Cumplimiento técnico y metodológico |
| Equipo de desarrollo | Entregar en plazo con calidad |

## 6. Métricas de éxito (KPIs)

| KPI | Meta |
|---|---|
| Tiempo de respuesta local del chat | ≤ 1 s (p95) |
| Consultas resueltas sin internet | ≥ 95 % |
| Cobertura de sinónimos del catálogo | ≥ 80 % de productos |
| Precisión de ubicación del puesto | ≤ 15 m |
| Cobertura de pruebas unitarias del motor | ≥ 70 % |
| Errores críticos en release | 0 |

## 7. Riesgos

| Riesgo | Impacto | Mitigación |
|---|---|---|
| Datos de precios desactualizados | Alto | Fecha de actualización visible + sincronización semanal |
| Puestos cambian de lugar | Medio | Campo `referencia` textual + validación en campo |
| Mapas offline pesados | Medio | Recortar tiles al área de la feria (zoom 14–18) |
| Sin señal en la feria | Alto | Arquitectura offline-first desde el diseño |
| Falta de tiempo del equipo | Alto | Priorización MoSCoW y sprints de 1 semana |
| Regateo distorsiona el precio | Bajo | Mostrar rango, no precio único |

## 8. Supuestos

- El usuario tiene un Android 8.0 (API 26) o superior.
- Existe al menos un dispositivo de prueba con GPS.
- Los comerciantes colaboran con la carga inicial de datos.
- El repositorio se aloja en GitHub y el equipo tiene acceso.
