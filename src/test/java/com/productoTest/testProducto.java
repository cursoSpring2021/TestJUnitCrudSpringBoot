package com.productoTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.productoTest.producto.Producto;
import com.productoTest.producto.ProductoRepository;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertFalse;


import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@TestMethodOrder(OrderAnnotation.class)
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)

public class testProducto {
	@Autowired
	private ProductoRepository repository;

	@Test
	@Rollback(false)
	@Order(1)
	public void testCrearProducto() {
		Producto producto = new Producto("Televisor Samsung HD", 1800);
		Producto productoGuardar = repository.save(producto);
		assertNotNull(productoGuardar);

	}

	@Test
	@Order(2)
	public void testsBuscarProductoPorNombre() {
		String nombre = "Televisor Samsung HD";
		Producto producto = repository.findByNombre(nombre);
		assertThat(producto.getNombre()).isEqualTo(nombre);

	}

	@Test
	@Order(3)
	public void testsBuscarProductoPorNombreNoExistente() {
		String nombre = "Iphone 11";
		Producto producto = repository.findByNombre(nombre);
		// assertThat(producto.getNombre()).isEqualTo(nombre);
		assertNull(producto);
	}

	@Test
	@Rollback(false)
	@Order(4)
	public void testActualizarProducto() {

		String nombreProducto = "Televisor HD";
		Producto producto = new Producto(nombreProducto, 2800);
		producto.setId(1);
		repository.save(producto);
		Producto productoActualizado = repository.findByNombre(nombreProducto);
		assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);

	}

	@Test
	@Order(5)
	public void testListarProductos() {
		List<Producto> productos = repository.findAll();
		assertThat(productos).size().isGreaterThan(0);
		for (Producto producto : productos) {
			System.out.println(producto);
		}

	}

	@Rollback(false)
	@Test
	@Order(6)
	public void testEliminarProducto() {
		Integer eliminar = 10;
		boolean esExistenteAntesDeEliminar = repository.findById(eliminar).isPresent();

		repository.deleteById(eliminar);

		boolean esnoExistenteAntesDeEliminar = repository.findById(eliminar).isPresent();
		assertTrue(esExistenteAntesDeEliminar);
		assertFalse(esnoExistenteAntesDeEliminar);

	}
}