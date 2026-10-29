"""
Valida el esquema y el seed de FeriBot en una base SQLite en memoria.

Uso:
    python tools/validar-seed.py
"""

from pathlib import Path
import sqlite3
import sys

raiz = Path(__file__).resolve().parent.parent
schema = (raiz / "database" / "schema.sql").read_text(encoding="utf-8")
seed = (raiz / "database" / "seed.sql").read_text(encoding="utf-8")

con = sqlite3.connect(":memory:")
con.executescript(schema)
con.executescript(seed)

errores = []

def contar(tabla: str) -> int:
    return con.execute(f"SELECT COUNT(*) FROM {tabla}").fetchone()[0]

esperado = {
    "rubros": 5,
    "productos": 25,
    "puestos": 8,
    "producto_puesto": 29,
    "comerciantes": 5,
    "sinonimos": 101,
}

for tabla, cantidad in esperado.items():
    real = contar(tabla)
    if real != cantidad:
        errores.append(f"{tabla}: esperado {cantidad}, obtenido {real}")

# Integridad referencial
faltantes = con.execute("PRAGMA foreign_key_check").fetchall()
if faltantes:
    errores.append(f"foreign_key_check devolvió {len(faltantes)} filas")

# Consulta de ejemplo: "¿a cómo está la lap?"
fila = con.execute(
    """
    SELECT p.nombre, p.precio_min, p.precio_max, u.codigo, u.calle
    FROM productos p
    JOIN producto_puesto pp ON pp.producto_id = p.id
    JOIN puestos u ON u.id = pp.puesto_id
    JOIN sinonimos s ON s.producto_id = p.id
    WHERE s.termino = 'lap'
    ORDER BY pp.precio_min
    LIMIT 1
    """
).fetchone()

if not fila:
    errores.append("la consulta de ejemplo 'lap' no devolvió resultados")
else:
    print(f"Ejemplo: {fila[0]} | Bs {fila[1]:.0f} - {fila[2]:.0f} | {fila[3]} ({fila[4]})")

# Productos sin sinónimos
sin_sinonimos = con.execute(
    """
    SELECT COUNT(*) FROM productos p
    WHERE NOT EXISTS (SELECT 1 FROM sinonimos s WHERE s.producto_id = p.id)
    """
).fetchone()[0]
if sin_sinonimos:
    errores.append(f"{sin_sinonimos} productos sin sinónimos")

# Puestos sin coordenadas
sin_coords = con.execute(
    "SELECT COUNT(*) FROM puestos WHERE latitud IS NULL OR longitud IS NULL"
).fetchone()[0]
if sin_coords:
    errores.append(f"{sin_coords} puestos sin coordenadas")

if errores:
    print("FALLÓ la validación:")
    for e in errores:
        print(f"  - {e}")
    sys.exit(1)

print(
    f"OK: {contar('rubros')} rubros, {contar('productos')} productos, "
    f"{contar('puestos')} puestos, {contar('producto_puesto')} ofertas, "
    f"{contar('sinonimos')} sinónimos."
)
