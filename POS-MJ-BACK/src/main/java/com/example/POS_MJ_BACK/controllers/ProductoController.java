package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Producto;
import com.example.POS_MJ_BACK.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping     //Tested
    public ResponseEntity<Page<Producto>> obtenerProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(productoService.obtenerTodosProductos(page, limit));
    }

    @GetMapping("/{id}")        //Tested
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping        //Tested
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.crearProducto(producto));
    }

    @PutMapping("/{id}/desactivar")     //Tested
    public ResponseEntity<Void> desactivarProducto(@PathVariable Long id) {
        productoService.desactivarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")    //Tested
    public ResponseEntity<Void> activarProducto(@PathVariable Long id) {
        System.out.println("Llamada a activarProducto con ID: " + id); // Log simple
        productoService.activarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")        //Tested
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @RequestBody Producto productoActualizado) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, productoActualizado));
    }

    @DeleteMapping("/{id}") //Tested
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}