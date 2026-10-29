-- ============================================================
-- ARCHIVO GENERADO AUTOMÁTICAMENTE — NO EDITAR A MANO
-- Origen: database/dataset/productos.json (versión 1.0.0)
-- Generador: tools/generar-seed.ps1
-- ============================================================

PRAGMA foreign_keys = ON;
BEGIN TRANSACTION;

DELETE FROM producto_puesto;
DELETE FROM sinonimos;
DELETE FROM comerciantes;
DELETE FROM productos;
DELETE FROM puestos;
DELETE FROM rubros;

-- Metadatos
INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('dataset_version', '1.0.0');
INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('feria_nombre', 'Feria 16 de Julio');
INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('feria_ciudad', 'El Alto');
INSERT OR REPLACE INTO metadata (clave, valor) VALUES ('generado_en', '2026-10-29');

-- Rubros
INSERT INTO rubros (id, nombre, descripcion) VALUES (1, 'Electrónica', 'Laptops, celulares, audio, televisores y accesorios');
INSERT INTO rubros (id, nombre, descripcion) VALUES (2, 'Autopartes', 'Repuestos y accesorios para vehículos');
INSERT INTO rubros (id, nombre, descripcion) VALUES (3, 'Ropa', 'Prendas nuevas y usadas, nacionales e importadas');
INSERT INTO rubros (id, nombre, descripcion) VALUES (4, 'Calzado', 'Zapatillas, zapatos formales, sandalias y botines');
INSERT INTO rubros (id, nombre, descripcion) VALUES (5, 'Herramientas', 'Herramientas manuales y eléctricas para construcción y taller');

-- Puestos
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (1, 'ELE-01', 'Electrónica', 'Calle 1', 'Frente a la Plaza 16 de Julio, lado de las galerías', -16.5018, -68.1682, 'jueves,domingo', '08:00-17:00', 1);
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (2, 'ELE-02', 'Electrónica', 'Calle 2', 'Pasillo de laptops, media cuadra de la Av. 16 de Julio', -16.5026, -68.1669, 'jueves,domingo', '08:00-17:00', 1);
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (3, 'AUT-01', 'Autopartes', 'Av. Juan Pablo II', 'Entre calles 6 y 7, lado norte', -16.5075, -68.1601, 'jueves,domingo', '07:30-17:00', 1);
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (4, 'ROP-01', 'Ropa', 'Calle 3', 'Sector chamarras, frente al mercado', -16.5038, -68.1651, 'jueves,domingo', '08:00-17:30', 1);
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (5, 'ROP-02', 'Ropa', 'Calle 4', 'Pasillo de jeans y casacas', -16.5046, -68.1638, 'jueves,domingo', '08:00-17:30', 1);
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (6, 'CAL-01', 'Calzado', 'Calle 4', 'Esquina con Av. 16 de Julio', -16.5053, -68.1626, 'jueves,domingo', '08:00-17:00', 1);
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (7, 'HER-01', 'Herramientas', 'Calle 5', 'Sector ferretería, lado este', -16.5061, -68.1614, 'jueves,domingo', '07:00-17:00', 1);
INSERT INTO puestos (id, codigo, sector, calle, referencia, latitud, longitud, dias_atencion, horario, activo) VALUES (8, 'HER-02', 'Herramientas', 'Calle 6', 'Pasillo de herramientas eléctricas', -16.5069, -68.1601, 'jueves,domingo', '07:00-17:00', 1);

-- Comerciantes
INSERT INTO comerciantes (id, nombre, telefono, puesto_id) VALUES (1, 'Rosa Quispe Mamani', '71234567', 1);
INSERT INTO comerciantes (id, nombre, telefono, puesto_id) VALUES (2, 'Juan Carlos Apaza', '72345678', 3);
INSERT INTO comerciantes (id, nombre, telefono, puesto_id) VALUES (3, 'María Elena Choque', '73456789', 4);
INSERT INTO comerciantes (id, nombre, telefono, puesto_id) VALUES (4, 'Pedro Nina Flores', '74567890', 7);
INSERT INTO comerciantes (id, nombre, telefono, puesto_id) VALUES (5, 'Silvia Condori Ticona', '75678901', 6);

-- Productos
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (1, 1, 'Laptop Lenovo ThinkPad T480', 'usado', 2800, 3600, 'BOB', 'Core i5 de 8va generación, 8 GB RAM, 256 GB SSD', 'Prueba la batería 15 minutos sin cargador, revisa teclado y touchpad, y pide el cargador original.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (2, 1, 'Laptop HP EliteBook 840 G5', 'usado', 2500, 3300, 'BOB', 'Core i5, 8 GB RAM, 256 GB SSD, pantalla 14"', 'Verifica los puertos USB y que la bisagra no tenga juego.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (3, 1, 'Celular Xiaomi Redmi Note 12', 'nuevo', 1200, 1500, 'BOB', '128 GB, 6 GB RAM, cámara de 50 MP', 'Pide el equipo sellado y revisa el IMEI con *#06# antes de pagar.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (4, 1, 'Audífonos Bluetooth JBL Tune 510BT', 'nuevo', 180, 260, 'BOB', 'On-ear inalámbricos, hasta 40 horas de batería', 'Conéctalos por Bluetooth y prueba ambos canales con música.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (5, 1, 'Televisor LG 43" LED', 'nuevo', 1500, 1900, 'BOB', 'Full HD, 2 puertos HDMI, sintonizador digital', 'Pide encenderlo y revisa píxeles muertos con un fondo blanco.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (6, 1, 'Cargador rápido USB-C 65W', 'nuevo', 80, 150, 'BOB', 'Compatible con laptops y celulares, incluye cable', 'Verifica el voltaje en la etiqueta y que no caliente en 5 minutos.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (7, 1, 'Tablet Samsung Galaxy Tab A9', 'nuevo', 900, 1200, 'BOB', '8.7", 64 GB, WiFi, Android', 'Prueba la pantalla táctil en las cuatro esquinas.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (8, 2, 'Llanta Bridgestone 195/65 R15', 'usado', 250, 380, 'BOB', 'Usada con aproximadamente 80 % de vida', 'Revisa el DOT (fecha de fabricación) y que el dibujo tenga al menos 5 mm.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (9, 2, 'Faro delantero Toyota Corolla 2014', 'usado', 300, 450, 'BOB', 'Original, lado derecho', 'Compara el número de parte y revisa que no tenga humedad.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (10, 2, 'Batería Willard 13 placas', 'nuevo', 550, 700, 'BOB', '12 V, libre de mantenimiento', 'Pide la prueba de carga y verifica la fecha de fabricación.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (11, 2, 'Amortiguador delantero Nissan Sentra', 'nuevo', 180, 280, 'BOB', 'Par delantero, gas', 'Presiona el vástago: debe volver firme y sin ruido.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (12, 2, 'Espejo retrovisor Hyundai Accent', 'usado', 120, 200, 'BOB', 'Eléctrico, lado izquierdo', 'Verifica que el conector eléctrico coincida con tu modelo.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (13, 3, 'Chamarra de cuero', 'usado', 80, 200, 'BOB', 'Cuero natural, talles variados', 'Huele el cuero: si huele a plástico es sintético; revisa las costuras.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (14, 3, 'Jeans Levi''s 501', 'usado', 40, 90, 'BOB', 'Corte recto, tiro alto', 'Revisa la etiqueta interior y los remaches; el original no se destiñe fácil.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (15, 3, 'Casaca impermeable', 'nuevo', 120, 220, 'BOB', 'Con capucha, resistente al viento', 'Prueba con unas gotas de agua: debe resbalar, no absorber.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (16, 3, 'Polera de alpaca', 'nuevo', 60, 120, 'BOB', 'Tejido artesanal, talles S a XL', 'Pasa la mano: la alpaca es suave y no pica como la lana de oveja.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (17, 4, 'Zapatillas Nike Air Max', 'usado', 150, 320, 'BOB', 'Importadas, talles 38 a 44', 'Revisa la suela y la etiqueta de talla; las costuras deben estar parejas.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (18, 4, 'Zapatos de cuero formal', 'nuevo', 180, 300, 'BOB', 'Cuero genuino, suela de goma', 'Dobla la suela: debe ser flexible y el cuero no debe agrietarse.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (19, 4, 'Sandalias de cuero', 'nuevo', 60, 120, 'BOB', 'Hechas a mano, talles 35 a 42', 'Verifica que las tiras estén cosidas, no solo pegadas.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (20, 4, 'Botines de seguridad', 'nuevo', 200, 350, 'BOB', 'Puntera de acero, suela antideslizante', 'Pide ver la certificación de la puntera y prueba el peso.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (21, 5, 'Taladro percutor Bosch 650W', 'nuevo', 380, 520, 'BOB', 'Mandril de 13 mm, incluye maletín', 'Pídelo funcionando y revisa que el mandril no tenga juego.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (22, 5, 'Amoladora angular 4½"', 'nuevo', 220, 350, 'BOB', '850 W, disco de corte incluido', 'Prueba en vacío 10 segundos y revisa que el disco gire sin vibración.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (23, 5, 'Juego de llaves mixtas 12 piezas', 'nuevo', 90, 160, 'BOB', 'Acero cromo vanadio, medidas 8 a 19 mm', 'Verifica el cromado y que las medidas estén completas.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (24, 5, 'Multímetro digital', 'nuevo', 60, 140, 'BOB', 'Voltaje, corriente y continuidad', 'Prueba continuidad y revisa que la pantalla no tenga fugas.', 1);
INSERT INTO productos (id, rubro_id, nombre, estado, precio_min, precio_max, moneda, descripcion, consejo, activo) VALUES (25, 5, 'Compresor de aire 50L', 'nuevo', 900, 1400, 'BOB', '2 HP, 8 bar, con manómetro', 'Pide encenderlo y revisa que mantenga presión sin fugas.', 1);

-- Oferta por puesto
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (1, 1, 2800, 3400, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (1, 2, 2900, 3600, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (2, 1, 2500, 3100, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (2, 2, 2600, 3300, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (3, 1, 1200, 1450, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (4, 1, 180, 240, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (4, 2, 190, 260, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (5, 2, 1500, 1850, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (6, 1, 80, 140, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (7, 2, 900, 1180, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (8, 3, 250, 380, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (9, 3, 300, 450, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (10, 3, 550, 700, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (11, 3, 180, 280, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (12, 3, 120, 200, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (13, 4, 80, 200, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (13, 5, 90, 190, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (14, 5, 40, 90, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (15, 4, 120, 220, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (16, 4, 60, 120, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (17, 6, 150, 320, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (18, 6, 180, 300, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (19, 6, 60, 120, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (20, 6, 200, 350, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (21, 7, 380, 520, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (22, 7, 220, 350, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (23, 8, 90, 160, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (24, 8, 60, 140, 1);
INSERT INTO producto_puesto (producto_id, puesto_id, precio_min, precio_max, disponible) VALUES (25, 8, 900, 1400, 1);

-- Sinónimos de producto
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (1, 'lap', 'producto', 1, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (2, 'laptop', 'producto', 1, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (3, 'portátil', 'producto', 1, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (4, 'notebook', 'producto', 1, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (5, 'thinkpad', 'producto', 1, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (6, 'laptop hp', 'producto', 2, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (7, 'elitebook', 'producto', 2, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (8, 'compu hp', 'producto', 2, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (9, 'cel', 'producto', 3, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (10, 'celu', 'producto', 3, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (11, 'celular', 'producto', 3, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (12, 'xiaomi', 'producto', 3, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (13, 'redmi', 'producto', 3, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (14, 'audífonos', 'producto', 4, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (15, 'auriculares', 'producto', 4, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (16, 'bluetooth', 'producto', 4, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (17, 'jbl', 'producto', 4, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (18, 'tele', 'producto', 5, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (19, 'televisor', 'producto', 5, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (20, 'tv', 'producto', 5, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (21, 'pantalla', 'producto', 5, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (22, 'cargador', 'producto', 6, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (23, 'cargador rápido', 'producto', 6, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (24, 'usb-c', 'producto', 6, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (25, 'tipo c', 'producto', 6, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (26, 'tablet', 'producto', 7, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (27, 'tableta', 'producto', 7, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (28, 'tab', 'producto', 7, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (29, 'llanta', 'producto', 8, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (30, 'neumático', 'producto', 8, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (31, 'rueda', 'producto', 8, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (32, 'aro 15', 'producto', 8, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (33, 'faro', 'producto', 9, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (34, 'foco delantero', 'producto', 9, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (35, 'luz', 'producto', 9, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (36, 'batería', 'producto', 10, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (37, 'batería de auto', 'producto', 10, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (38, 'acumulador', 'producto', 10, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (39, 'amortiguador', 'producto', 11, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (40, 'suspensión', 'producto', 11, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (41, 'espejo', 'producto', 12, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (42, 'retrovisor', 'producto', 12, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (43, 'chamarra', 'producto', 13, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (44, 'casaca de cuero', 'producto', 13, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (45, 'campera', 'producto', 13, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (46, 'cuerina', 'producto', 13, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (47, 'jean', 'producto', 14, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (48, 'jeans', 'producto', 14, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (49, 'pantalón de mezclilla', 'producto', 14, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (50, 'levis', 'producto', 14, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (51, 'casaca', 'producto', 15, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (52, 'impermeable', 'producto', 15, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (53, 'chaqueta', 'producto', 15, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (54, 'rompevientos', 'producto', 15, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (55, 'polera', 'producto', 16, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (56, 'chompa', 'producto', 16, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (57, 'suéter', 'producto', 16, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (58, 'alpaca', 'producto', 16, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (59, 'zapatillas', 'producto', 17, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (60, 'tenis', 'producto', 17, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (61, 'nike', 'producto', 17, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (62, 'zapatos deportivos', 'producto', 17, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (63, 'zapatos', 'producto', 18, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (64, 'zapatos formales', 'producto', 18, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (65, 'calzado de cuero', 'producto', 18, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (66, 'sandalias', 'producto', 19, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (67, 'ojotas', 'producto', 19, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (68, 'chanclas', 'producto', 19, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (69, 'botines', 'producto', 20, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (70, 'botas de seguridad', 'producto', 20, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (71, 'zapatos de trabajo', 'producto', 20, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (72, 'taladro', 'producto', 21, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (73, 'taladro percutor', 'producto', 21, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (74, 'bosch', 'producto', 21, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (75, 'amoladora', 'producto', 22, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (76, 'esmeril', 'producto', 22, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (77, 'angular', 'producto', 22, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (78, 'llaves', 'producto', 23, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (79, 'juego de llaves', 'producto', 23, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (80, 'herramientas', 'producto', 23, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (81, 'multímetro', 'producto', 24, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (82, 'tester', 'producto', 24, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (83, 'medidor', 'producto', 24, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (84, 'compresor', 'producto', 25, NULL);
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (85, 'compresor de aire', 'producto', 25, NULL);

-- Modismos e intenciones
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (86, 'a cómo está', 'intencion', NULL, 'consultar_precio');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (87, 'a cuánto', 'intencion', NULL, 'consultar_precio');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (88, 'cuánto cuesta', 'intencion', NULL, 'consultar_precio');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (89, 'cuánto está', 'intencion', NULL, 'consultar_precio');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (90, 'precio', 'intencion', NULL, 'consultar_precio');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (91, 'dónde queda', 'intencion', NULL, 'consultar_ubicacion');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (92, 'dónde está', 'intencion', NULL, 'consultar_ubicacion');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (93, 'ubicación', 'intencion', NULL, 'consultar_ubicacion');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (94, 'en la 16', 'modismo', NULL, 'Feria 16 de Julio');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (95, 'casero', 'modismo', NULL, 'comerciante');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (96, 'casera', 'modismo', NULL, 'comerciante');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (97, 'yapeo', 'modismo', NULL, 'regateo');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (98, 'regateo', 'modismo', NULL, 'regateo');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (99, 'hola', 'intencion', NULL, 'saludo');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (100, 'buenas', 'intencion', NULL, 'saludo');
INSERT INTO sinonimos (id, termino, tipo, producto_id, destino) VALUES (101, 'ayuda', 'intencion', NULL, 'ayuda');

COMMIT;

-- Resumen: 5 rubros, 25 productos, 8 puestos, 29 ofertas, 101 sinónimos.