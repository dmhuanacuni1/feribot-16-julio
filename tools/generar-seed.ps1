# ============================================================
# generar-seed.ps1
# Convierte database/dataset/productos.json en database/seed.sql
#
# Uso:
#   powershell -ExecutionPolicy Bypass -File tools/generar-seed.ps1
# ============================================================

$ErrorActionPreference = 'Stop'

$raiz     = Split-Path -Parent $PSScriptRoot
$jsonPath = Join-Path $raiz 'database/dataset/productos.json'
$outPath  = Join-Path $raiz 'database/seed.sql'

if (-not (Test-Path $jsonPath)) {
    throw "No se encontró el dataset: $jsonPath"
}

$data = Get-Content -Raw -Encoding UTF8 $jsonPath | ConvertFrom-Json
$inv  = [System.Globalization.CultureInfo]::InvariantCulture

function Txt([object]$valor) {
    if ($null -eq $valor) { return 'NULL' }
    return "'" + ([string]$valor).Replace("'", "''") + "'"
}

function Num([object]$valor) {
    if ($null -eq $valor) { return 'NULL' }
    return ([double]$valor).ToString($inv)
}

$lineas = New-Object System.Collections.Generic.List[string]

$lineas.Add('-- ============================================================')
$lineas.Add('-- ARCHIVO GENERADO AUTOMÁTICAMENTE — NO EDITAR A MANO')
$lineas.Add("-- Origen: database/dataset/productos.json (versión $($data.version))")
$lineas.Add('-- Generador: tools/generar-seed.ps1')
$lineas.Add('-- ============================================================')
$lineas.Add('')
$lineas.Add('PRAGMA foreign_keys = ON;')
$lineas.Add('BEGIN TRANSACTION;')
$lineas.Add('')
$lineas.Add('DELETE FROM producto_puesto;')
$lineas.Add('DELETE FROM sinonimos;')
$lineas.Add('DELETE FROM comerciantes;')
$lineas.Add('DELETE FROM productos;')
$lineas.Add('DELETE FROM puestos;')
$lineas.Add('DELETE FROM rubros;')
$lineas.Add('')

# ---------------- Metadatos ----------------
$lineas.Add('-- Metadatos')
$lineas.Add("INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('dataset_version', $(Txt $data.version));")
$lineas.Add("INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('feria_nombre', $(Txt $data.feria.nombre));")
$lineas.Add("INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('feria_ciudad', $(Txt $data.feria.ciudad));")
$lineas.Add("INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('generado_en', $(Txt $data.fecha));")
$lineas.Add('')

# ---------------- Rubros ----------------
$lineas.Add('-- Rubros')
foreach ($rubro in $data.rubros) {
    $lineas.Add("INSERT INTO rubros (id, nombre, descripcion) VALUES ($($rubro.id), $(Txt $rubro.nombre), $(Txt $rubro.descripcion));")
}
$lineas.Add('')

# ---------------- Puestos ----------------
$lineas.Add('-- Puestos')
foreach ($p in $data.puestos) {
    $lineas.Add("INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES ($($p.id), $(Txt $p.codigo), $(Txt $p.sector), $(Txt $p.calle), $(Txt $p.referencia), $(Num $p.latitud), $(Num $p.longitud), $(Txt $p.dias_atencion), $(Txt $p.horario), 1);")
}
$lineas.Add('')

# ---------------- Comerciantes ----------------
$mapaPuestos = @{}
foreach ($p in $data.puestos) { $mapaPuestos[$p.codigo] = $p.id }

$lineas.Add('-- Comerciantes')
foreach ($c in $data.comerciantes) {
    $puestoId = $mapaPuestos[$c.puesto]
    if ($null -eq $puestoId) { throw "Puesto desconocido en comerciante: $($c.puesto)" }
    $lineas.Add("INSERT INTO comerciantes (id, nombre, telefono, puesto_id) VALUES ($($c.id), $(Txt $c.nombre), $(Txt $c.telefono), $puestoId);")
}
$lineas.Add('')

# ---------------- Productos ----------------
$lineas.Add('-- Productos')
foreach ($rubro in $data.rubros) {
    foreach ($prod in $rubro.productos) {
        $lineas.Add("INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES ($($prod.id), $($rubro.id), $(Txt $prod.nombre), $(Txt $prod.estado), $(Num $prod.precio_min), $(Num $prod.precio_max), 'BOB', $(Txt $prod.descripcion), $(Txt $prod.consejo), 1);")
    }
}
$lineas.Add('')

# ---------------- Oferta por puesto ----------------
$lineas.Add('-- Oferta por puesto')
$totalOfertas = 0
foreach ($rubro in $data.rubros) {
    foreach ($prod in $rubro.productos) {
        foreach ($of in $prod.puestos) {
            $puestoId = $mapaPuestos[$of.codigo]
            if ($null -eq $puestoId) { throw "Puesto desconocido en producto $($prod.nombre): $($of.codigo)" }
            $lineas.Add("INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES ($($prod.id), $puestoId, $(Num $of.precio_min), $(Num $of.precio_max), 1);")
            $totalOfertas++
        }
    }
}
$lineas.Add('')

# ---------------- Sinónimos de producto ----------------
$lineas.Add('-- Sinónimos de producto')
$idSinonimo = 1
foreach ($rubro in $data.rubros) {
    foreach ($prod in $rubro.productos) {
        foreach ($sin in $prod.sinonimos) {
            $lineas.Add("INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES ($idSinonimo, $(Txt $sin), 'producto', $($prod.id), NULL);")
            $idSinonimo++
        }
    }
}
$lineas.Add('')

# ---------------- Modismos e intenciones ----------------
$lineas.Add('-- Modismos e intenciones')
foreach ($m in $data.modismos) {
    $lineas.Add("INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES ($idSinonimo, $(Txt $m.termino), $(Txt $m.tipo), NULL, $(Txt $m.destino));")
    $idSinonimo++
}
$lineas.Add('')

$lineas.Add('COMMIT;')
$lineas.Add('')
$lineas.Add("-- Resumen: $($data.rubros.Count) rubros, $(($data.rubros | ForEach-Object { $_.productos.Count } | Measure-Object -Sum).Sum) productos, $($data.puestos.Count) puestos, $totalOfertas ofertas, $($idSinonimo - 1) sinónimos.")

$contenido = ($lineas -join "`r`n")
[System.IO.File]::WriteAllText($outPath, $contenido, (New-Object System.Text.UTF8Encoding($false)))

Write-Host "OK: generado $outPath"
Write-Host "Rubros: $($data.rubros.Count) | Productos: $(($data.rubros | ForEach-Object { $_.productos.Count } | Measure-Object -Sum).Sum) | Puestos: $($data.puestos.Count) | Ofertas: $totalOfertas | Sinónimos: $($idSinonimo - 1)"
