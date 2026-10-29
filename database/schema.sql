-- ============================================================
-- FeriBot 16 de Julio — Esquema SQLite (referencia para Room)
-- Versión: 1
-- Moneda: BOB
-- ============================================================

PRAGMA foreign_keys = ON;

-- ------------------------------------------------------------
-- Metadatos del dataset (permite versionar y migrar la carga)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS metadata (
    clave  TEXT PRIMARY KEY,
    valor  TEXT NOT NULL
);

-- ------------------------------------------------------------
-- Rubros
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS rubros (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre      TEXT    NOT NULL UNIQUE,
    descripcion TEXT
);

-- ------------------------------------------------------------
-- Puestos de la feria
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS puestos (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo         TEXT    NOT NULL UNIQUE,
    sector         TEXT    NOT NULL,
    calle          TEXT,
    referencia     TEXT,
    latitud        REAL,
    longitud       REAL,
    dias_atencion  TEXT    NOT NULL DEFAULT 'jueves,domingo',
    horario        TEXT    DEFAULT '08:00-17:00',
    activo         INTEGER NOT NULL DEFAULT 1 CHECK (activo IN (0, 1)),
    actualizado_en TEXT    NOT NULL DEFAULT (datetime('now'))
);

-- ------------------------------------------------------------
-- Comerciantes
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS comerciantes (
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre    TEXT    NOT NULL,
    telefono  TEXT,
    puesto_id INTEGER,
    FOREIGN KEY (puesto_id) REFERENCES puestos(id) ON DELETE SET NULL
);

-- ------------------------------------------------------------
-- Productos
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS productos (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    rubro_id       INTEGER NOT NULL,
    nombre         TEXT    NOT NULL,
    estado         TEXT    NOT NULL DEFAULT 'nuevo'
                           CHECK (estado IN ('nuevo', 'usado', 'reacondicionado')),
    precio_min     REAL    NOT NULL CHECK (precio_min >= 0),
    precio_max     REAL    NOT NULL CHECK (precio_max >= precio_min),
    moneda         TEXT    NOT NULL DEFAULT 'BOB',
    descripcion    TEXT,
    consejo        TEXT,
    activo         INTEGER NOT NULL DEFAULT 1 CHECK (activo IN (0, 1)),
    actualizado_en TEXT    NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (rubro_id) REFERENCES rubros(id) ON DELETE RESTRICT
);

-- ------------------------------------------------------------
-- Oferta por puesto (el precio puede variar entre caseros)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS producto_puesto (
    producto_id INTEGER NOT NULL,
    puesto_id   INTEGER NOT NULL,
    precio_min  REAL    NOT NULL CHECK (precio_min >= 0),
    precio_max  REAL    NOT NULL CHECK (precio_max >= precio_min),
    disponible  INTEGER NOT NULL DEFAULT 1 CHECK (disponible IN (0, 1)),
    PRIMARY KEY (producto_id, puesto_id),
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE,
    FOREIGN KEY (puesto_id)   REFERENCES puestos(id)   ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- Sinónimos y modismos bolivianos
--   tipo = 'producto'  -> producto_id apunta al producto
--   tipo = 'intencion' -> destino = consultar_precio, ubicacion, saludo...
--   tipo = 'modismo'   -> destino = término canónico
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS sinonimos (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    termino     TEXT    NOT NULL UNIQUE,
    tipo        TEXT    NOT NULL CHECK (tipo IN ('producto', 'intencion', 'modismo')),
    producto_id INTEGER,
    destino     TEXT,
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- Historial de consultas (telemetría local)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS historial_consultas (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    texto       TEXT    NOT NULL,
    intencion   TEXT,
    producto_id INTEGER,
    respuesta   TEXT,
    origen      TEXT    NOT NULL DEFAULT 'local' CHECK (origen IN ('local', 'nube')),
    creado_en   TEXT    NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE SET NULL
);

-- ------------------------------------------------------------
-- Fotos tomadas en el puesto y asociadas a una cotización
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS fotos_cotizacion (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    producto_id INTEGER,
    producto    TEXT    NOT NULL,
    precio      TEXT,
    ruta        TEXT    NOT NULL,
    creado_en   TEXT    NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE SET NULL
);

-- ------------------------------------------------------------
-- Cola de sincronización (offline-first)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS cola_sincronizacion (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    entidad    TEXT    NOT NULL,
    entidad_id INTEGER NOT NULL,
    operacion  TEXT    NOT NULL CHECK (operacion IN ('INSERT', 'UPDATE', 'DELETE')),
    payload    TEXT,
    estado     TEXT    NOT NULL DEFAULT 'pendiente'
                       CHECK (estado IN ('pendiente', 'enviado', 'error')),
    intentos   INTEGER NOT NULL DEFAULT 0,
    creado_en  TEXT    NOT NULL DEFAULT (datetime('now')),
    enviado_en TEXT
);

-- ------------------------------------------------------------
-- Índices
-- ------------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_productos_rubro ON productos(rubro_id);
CREATE INDEX IF NOT EXISTS idx_productos_nombre ON productos(nombre);
CREATE INDEX IF NOT EXISTS idx_puestos_sector ON puestos(sector);
CREATE INDEX IF NOT EXISTS idx_pp_puesto ON producto_puesto(puesto_id);
CREATE INDEX IF NOT EXISTS idx_sinonimos_producto ON sinonimos(producto_id);
CREATE INDEX IF NOT EXISTS idx_historial_fecha ON historial_consultas(creado_en);
CREATE INDEX IF NOT EXISTS idx_cola_estado ON cola_sincronizacion(estado);
CREATE INDEX IF NOT EXISTS idx_fotos_fecha ON fotos_cotizacion(creado_en);
