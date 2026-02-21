package com.veterinaria.huellitasVet.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veterinaria.huellitasVet.models.DetalleVenta;
import com.veterinaria.huellitasVet.models.Producto;
import com.veterinaria.huellitasVet.models.Venta;
import com.veterinaria.huellitasVet.repositories.ProductoRepository;
import com.veterinaria.huellitasVet.repositories.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public Venta guardar(Venta venta) {
        venta.setFecha(LocalDateTime.now());
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto no encontrado: " + detalle.getProducto().getIdProducto()));
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getDenominacion());
            }
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
            detalle.setVenta(venta);
        }
        return ventaRepository.save(venta);
    }

    @Transactional
    public void eliminar(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            int nuevoStock = producto.getStock() + detalle.getCantidad();
            producto.setStock(nuevoStock);
            productoRepository.save(producto);
        }
        ventaRepository.delete(venta);
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }
}
