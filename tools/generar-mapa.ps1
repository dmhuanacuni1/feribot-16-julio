# Genera el plano referencial offline de la Feria 16 de Julio.
# La imagen se usa como GroundOverlay de osmdroid: no requiere conexión ni tiles.
# Uso: pwsh -File tools/generar-mapa.ps1

Add-Type -AssemblyName System.Drawing

$raiz = Split-Path -Parent $PSScriptRoot
$salida = Join-Path $raiz "app\src\main\res\drawable-nodpi\mapa_feria.png"
New-Item -ItemType Directory -Force -Path (Split-Path $salida) | Out-Null

$W = 1600
$H = 1400
$latMax = -16.5000
$latMin = -16.5100
$lonMin = -68.1710
$lonMax = -68.1580

function X([double]$lon) { return [int](($lon - $lonMin) / ($lonMax - $lonMin) * $W) }
function Y([double]$lat) { return [int](($latMax - $lat) / ($latMax - $latMin) * $H) }

$bmp = New-Object System.Drawing.Bitmap($W, $H)
$g = [System.Drawing.Graphics]::FromImage($bmp)
$g.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::AntiAlias
$g.TextRenderingHint = [System.Drawing.Text.TextRenderingHint]::AntiAliasGridFit

$papel = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(243, 241, 231))
$g.FillRectangle($papel, 0, 0, $W, $H)

$manzana = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(228, 224, 207))
$bordeManzana = New-Object System.Drawing.Pen([System.Drawing.Color]::FromArgb(206, 200, 180), 2)
$calle = New-Object System.Drawing.Pen([System.Drawing.Color]::FromArgb(255, 255, 255), 26)
$calleBorde = New-Object System.Drawing.Pen([System.Drawing.Color]::FromArgb(214, 209, 189), 30)
$avenida = New-Object System.Drawing.Pen([System.Drawing.Color]::FromArgb(255, 253, 245), 54)
$avenidaBorde = New-Object System.Drawing.Pen([System.Drawing.Color]::FromArgb(205, 199, 176), 60)

$calles = @(
    @{ lat = -16.5018; nombre = "Calle 1" },
    @{ lat = -16.5026; nombre = "Calle 2" },
    @{ lat = -16.5038; nombre = "Calle 3" },
    @{ lat = -16.5046; nombre = "Calle 4" },
    @{ lat = -16.5053; nombre = "Calle 5" },
    @{ lat = -16.5061; nombre = "Calle 6" },
    @{ lat = -16.5069; nombre = "Calle 7" }
)

# Manzanas de fondo entre calles.
for ($i = 0; $i -lt $calles.Count - 1; $i++) {
    $y1 = (Y $calles[$i].lat) + 22
    $y2 = (Y $calles[$i + 1].lat) - 22
    for ($x = 40; $x -lt $W - 40; $x += 160) {
        $g.FillRectangle($manzana, $x, $y1, 140, [Math]::Max(10, $y2 - $y1))
        $g.DrawRectangle($bordeManzana, $x, $y1, 140, [Math]::Max(10, $y2 - $y1))
    }
}

# Avenida 16 de Julio (diagonal).
$x1 = X $lonMin; $y1 = Y(-16.5020)
$x2 = X $lonMax; $y2 = Y(-16.5082)
$g.DrawLine($avenidaBorde, $x1, $y1, $x2, $y2)
$g.DrawLine($avenida, $x1, $y1, $x2, $y2)

# Avenida Juan Pablo II (vertical al este).
$xv = X(-68.1597)
$g.DrawLine($avenidaBorde, $xv, 0, $xv, $H)
$g.DrawLine($avenida, $xv, 0, $xv, $H)

# Calles horizontales.
foreach ($c in $calles) {
    $yc = Y $c.lat
    $g.DrawLine($calleBorde, 0, $yc, $W, $yc)
    $g.DrawLine($calle, 0, $yc, $W, $yc)
}

function Etiqueta($texto, $x, $y, $fuente, $color, $ancho) {
    $b = New-Object System.Drawing.SolidBrush($color)
    $formato = New-Object System.Drawing.StringFormat
    $formato.Alignment = [System.Drawing.StringAlignment]::Center
    $rect = New-Object System.Drawing.RectangleF([float]($x - $ancho / 2), [float]($y - 14), [float]$ancho, [float]28)
    $g.DrawString($texto, $fuente, $b, $rect, $formato)
    $b.Dispose(); $formato.Dispose()
}

$fNombreCalle = New-Object System.Drawing.Font("Segoe UI", 15, [System.Drawing.FontStyle]::Bold)
$colorCalle = [System.Drawing.Color]::FromArgb(120, 116, 100)
foreach ($c in $calles) {
    Etiqueta $c.nombre 90 ((Y $c.lat) - 34) $fNombreCalle $colorCalle 150
}

# Sectores con relleno translúcido.
$sectores = @(
    @{ nombre = "ELECTRÓNICA"; lat = -16.5022; lon = -68.1676; color = [System.Drawing.Color]::FromArgb(60, 21, 101, 192) },
    @{ nombre = "ROPA"; lat = -16.5042; lon = -68.1644; color = [System.Drawing.Color]::FromArgb(60, 216, 67, 21) },
    @{ nombre = "CALZADO"; lat = -16.5053; lon = -68.1626; color = [System.Drawing.Color]::FromArgb(60, 94, 53, 177) },
    @{ nombre = "HERRAMIENTAS"; lat = -16.5065; lon = -68.1607; color = [System.Drawing.Color]::FromArgb(60, 46, 125, 50) },
    @{ nombre = "AUTOPARTES"; lat = -16.5075; lon = -68.1601; color = [System.Drawing.Color]::FromArgb(60, 245, 124, 0) }
)

$fSector = New-Object System.Drawing.Font("Segoe UI", 20, [System.Drawing.FontStyle]::Bold)
foreach ($s in $sectores) {
    $cx = X $s.lon
    $cy = Y $s.lat
    $b = New-Object System.Drawing.SolidBrush($s.color)
    $p = New-Object System.Drawing.Pen($s.color, 3)
    $rect = New-Object System.Drawing.Rectangle([int]($cx - 130), [int]($cy - 70), 260, 140)
    $g.FillRectangle($b, $rect)
    $g.DrawRectangle($p, $rect)
    Etiqueta $s.nombre $cx ($cy + 78) $fSector ([System.Drawing.Color]::FromArgb(70, 66, 58)) 260
    $b.Dispose(); $p.Dispose()
}

# Recuadro de título.
$panel = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(235, 255, 255, 255))
$g.FillRectangle($panel, 24, 24, 620, 96)
$pBorde = New-Object System.Drawing.Pen([System.Drawing.Color]::FromArgb(21, 101, 192), 3)
$g.DrawRectangle($pBorde, 24, 24, 620, 96)

$fTitulo = New-Object System.Drawing.Font("Segoe UI", 26, [System.Drawing.FontStyle]::Bold)
$fSubtitulo = New-Object System.Drawing.Font("Segoe UI", 14, [System.Drawing.FontStyle]::Regular)
$azul = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(21, 101, 192))
$gris = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(107, 114, 128))
$g.DrawString("Feria 16 de Julio", $fTitulo, $azul, 44, 36)
$g.DrawString("El Alto · Plano referencial offline · datos de muestra", $fSubtitulo, $gris, 46, 82)

# Rosa de los vientos.
$cxN = 1480
$cyN = 90
$norte = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(21, 101, 192))
$puntos = @(
    (New-Object System.Drawing.Point -ArgumentList $cxN, ($cyN - 46)),
    (New-Object System.Drawing.Point -ArgumentList ($cxN - 16), ($cyN + 16)),
    (New-Object System.Drawing.Point -ArgumentList $cxN, ($cyN + 4)),
    (New-Object System.Drawing.Point -ArgumentList ($cxN + 16), ($cyN + 16))
)
$g.FillPolygon($norte, [System.Drawing.Point[]]$puntos)
$fN = New-Object System.Drawing.Font("Segoe UI", 16, [System.Drawing.FontStyle]::Bold)
Etiqueta "N" $cxN ($cyN + 46) $fN ([System.Drawing.Color]::FromArgb(21, 101, 192)) 60

# Pie con la fuente de datos.
$fPie = New-Object System.Drawing.Font("Segoe UI", 13, [System.Drawing.FontStyle]::Italic)
$g.DrawString("Coordenadas de ejemplo. No usar para orientación real fuera de la feria.", $fPie, $gris, 24, ($H - 44))

$bmp.Save($salida, [System.Drawing.Imaging.ImageFormat]::Png)
$g.Dispose()
$bmp.Dispose()

Write-Host "Mapa generado: $salida"
