package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;

@Service
public class RutaEstacionService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public RutaEntity addEstacionRuta(Long estacionId, Long rutaId)
            throws EntityNotFoundException, IllegalOperationException {

        EstacionEntity estacion = estacionRepository.findById(estacionId)
                .orElseThrow(() -> new EntityNotFoundException("La estación no existe"));

        RutaEntity ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new EntityNotFoundException("La ruta no existe"));

        if (ruta.getEstaciones().stream().anyMatch(e -> e.getId().equals(estacionId))) {
            throw new IllegalOperationException("La estación ya pertenece a la ruta");
        }

        ruta.getEstaciones().add(estacion);
        estacion.getRutas().add(ruta);

        return rutaRepository.save(ruta);
    }

    @Transactional
    public RutaEntity removeEstacionRuta(Long estacionId, Long rutaId)
            throws EntityNotFoundException, IllegalOperationException {

        EstacionEntity estacion = estacionRepository.findById(estacionId)
                .orElseThrow(() -> new EntityNotFoundException("La estación no existe"));

        RutaEntity ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new EntityNotFoundException("La ruta no existe"));

        boolean HaceParte = ruta.getEstaciones().removeIf(e -> e.getId().equals(estacionId));
        if (!HaceParte) {
            throw new IllegalOperationException("La estación no pertenece a la ruta");
        }

        estacion.getRutas().removeIf(r -> r.getId().equals(rutaId));

        rutaRepository.save(ruta);
        estacionRepository.save(estacion);

        return ruta;
    }
}

