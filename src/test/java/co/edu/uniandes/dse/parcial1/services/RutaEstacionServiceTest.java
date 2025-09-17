package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;

@SpringBootTest
@Transactional
public class RutaEstacionServiceTest {

    @Autowired
    private RutaEstacionService rutaEstacionService;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    private RutaEntity ruta;
    private EstacionEntity estacion;

    @BeforeEach
    void setup() {
        rutaRepository.deleteAll();
        estacionRepository.deleteAll();

        ruta = new RutaEntity();
        ruta.setName("Ruta A");
        ruta.setColor("Rojo");
        ruta.setTipo("diurna");
        ruta = rutaRepository.save(ruta);

        estacion = new EstacionEntity();
        estacion.setName("Estacion 1");
        estacion.setDireccion("Calle 1");
        estacion.setCapacidad(200);
        estacion = estacionRepository.save(estacion);
    }

    @Test
    void addEstacionRuta_exitoso() throws Exception {
        RutaEntity result = rutaEstacionService.addEstacionRuta(estacion.getId(), ruta.getId());
        assertTrue(result.getEstaciones().contains(estacion));
    }

    @Test
    void addEstacionRuta_estacionNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            rutaEstacionService.addEstacionRuta(999L, ruta.getId());
        });
    }

    @Test
    void addEstacionRuta_rutaNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            rutaEstacionService.addEstacionRuta(estacion.getId(), 999L);
        });
    }

    @Test
    void addEstacionRuta_estacionYaTeniaRuta() throws Exception {
        rutaEstacionService.addEstacionRuta(estacion.getId(), ruta.getId());
        assertThrows(IllegalOperationException.class, () -> {
            rutaEstacionService.addEstacionRuta(estacion.getId(), ruta.getId());
        });
    }


    @Test
    void removeEstacionRuta_exitoso() throws Exception {
        rutaEstacionService.addEstacionRuta(estacion.getId(), ruta.getId());
        RutaEntity result = rutaEstacionService.removeEstacionRuta(estacion.getId(), ruta.getId());
        assertFalse(result.getEstaciones().contains(estacion));
    }

    @Test
    void removeEstacionRuta_estacionNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            rutaEstacionService.removeEstacionRuta(999L, ruta.getId());
        });
    }

    @Test
    void removeEstacionRuta_rutaNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            rutaEstacionService.removeEstacionRuta(estacion.getId(), 999L);
        });
    }

    @Test
    void removeEstacionRuta_estacionNoEnRuta() {
        assertThrows(IllegalOperationException.class, () -> {
            rutaEstacionService.removeEstacionRuta(estacion.getId(), ruta.getId());
        });
    }
}

