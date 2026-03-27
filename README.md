# Sistema de Carrito de Compras y Facturación en Consola - Kotlin

Esta es una solución para un sistema de gestión de ventas desarrollado en Kotlin. El proyecto permite simular un flujo completo de compra desde consola, incluyendo manejo de inventario, carrito de compras, generación de facturas en PDF y envío por correo electrónico.

---

## Tabla de contenidos

* Resumen
* El desafío
* Funcionalidades
* Estructura del proyecto
* Tecnologías utilizadas
* Lo que aprendimos
* Autores

---

## Resumen

El sistema permite gestionar productos, realizar compras mediante un carrito, generar facturas automáticamente y enviarlas por correo. Está diseñado para simular un flujo real de ventas utilizando una arquitectura modular.

---

## El desafío

El objetivo del proyecto fue desarrollar una aplicación que permitiera:

* Gestionar un inventario de productos
* Simular compras mediante un carrito
* Generar facturas automáticamente
* Exportar facturas en formato PDF
* Enviar facturas por correo electrónico
* Mantener una estructura organizada por capas (model, service, util, ui)

---

## Funcionalidades

Los usuarios pueden:

* Ver productos disponibles en el inventario
* Agregar productos al carrito de compras
* Validar stock disponible antes de comprar
* Finalizar la compra generando una factura
* Generar automáticamente un PDF de la factura
* Enviar la factura por correo electrónico
* Visualizar información clara desde consola

---

## Estructura del proyecto

```bash
src/
├── model/        # Clases de negocio (Cliente, Producto, Factura)
├── service/      # Lógica del sistema (Carrito, Inventario, Facturación)
├── util/         # Utilidades (PDF, Email, DataSeeder)
├── ui/           # Interfaz de consola
├── templates/    # Plantillas (correo HTML)
└── Main.kt       # Punto de entrada
```

---

## Tecnologías utilizadas

* Kotlin
* OpenPDF (generación de PDF)
* Java Mail (envío de correos)
* Programación orientada a objetos (POO)
* Arquitectura por capas

---

## Lo que aprendimos

Durante el desarrollo aprendimos a:

* Estructurar un proyecto por capas en Kotlin
* Manejar colecciones y lógica de negocio en consola
* Generar archivos PDF dinámicamente
* Enviar correos electrónicos con adjuntos
* Validar stock en tiempo real
* Separar responsabilidades (UI, lógica, utilidades)
* Simular un flujo completo de compra tipo sistema real

---

## Autores

Este proyecto fue desarrollado:

- [Andrea Paola Montenegro Paz](https://github.com/aaaamontenegro)
- [Mayron Steve Lopez Giron](https://github.com/mayronlopezg)
- [Alejandro Benjamín Rivera Ochoa](https://github.com/alejandrorivera)
- [Marlon Eduardo Montenegro Paz](https://github.com/MarlonMontenegro)
- [Miguel Eduardo Vallejos Linares](https://github.com/miguevallejossv)

---
