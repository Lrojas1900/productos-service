package com.productos.service.controller;

import com.productos.service.entity.Producto;
import com.productos.service.repository.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Value("${api.key}")
    private String apiKey;

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestHeader("X-API-KEY") String headerKey, @RequestBody Producto producto) {
        if (!apiKey.equals(headerKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por su ID")
    public ResponseEntity<Producto> obtenerProducto(@RequestHeader("X-API-KEY") String headerKey, @PathVariable Long id) {
        if (!apiKey.equals(headerKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public List<Producto> listarProductos(@RequestHeader("X-API-KEY") String headerKey) {
        if (!apiKey.equals(headerKey)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "API Key inválida");
        return productoRepository.findAll();
    }
}
