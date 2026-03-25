package com.example.CsrPrestamo.controller;

import com.example.CsrPrestamo.model.Prestamo;
import com.example.CsrPrestamo.service.PrestamoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @RestController indica que esta clase responderá peticiones web y devolverá datos (como JSON), no pantallas HTML.
 * @RequestMapping("/api/libros") define la URL base para todos los métodos de esta clase.
 */
@RestController
@RequestMapping("/api/v1/prestamo")
public class PrestamoController {

    private final PrestamoService prestamoService;

    // Inyección de dependencias: el controlador necesita del servicio para funcionar
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    /**
     * CREATE - POST: Se usa para enviar y crear nuevos datos.
     * @RequestBody indica que los datos del libro vendrán en el cuerpo de la petición (en formato JSON).
     */
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody Prestamo prestamo) {
        Object resultado = prestamoService.crearPrestamo(prestamo);

        if(resultado instanceof String ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }
        else{
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        }
    }

    /**
     * READ ALL - GET: Se usa para solicitar información.
     */
    @GetMapping
    public List<Prestamo> listarTodos() {
        return prestamoService.obtenerTodos();
    }

    /**
     * READ BY ID - GET: Solicita información de un elemento específico.
     * @PathVariable captura el número que venga en la URL (ejemplo: /api/libros/1 captura el 1).
     * ResponseEntity permite controlar el código de estado HTTP (200 OK, 404 Not Found, etc.).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamoPorId(@PathVariable Long id) {
        Optional<Prestamo> prestamo = prestamoService.obtenerPorId(id);
        if (prestamo.isPresent()) {
            return ResponseEntity.ok(prestamo.get()); // Retorna HTTP 200 con el libro
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404 si no existe
        }
    }

}

