package co.edu.uniandes.dse.parcial1.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.RutaEstacionService;

public class RutaEstacionDTO {

    @Autowired
	private RutaEstacionService rutaEstacionService;

	@Autowired
	private ModelMapper modelMapper;

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public RutaEstacionDTO create(@RequestBody RutaEstacionDTO conciertoDTO) throws IllegalOperationException {
    RutaEntity entity = rutaEstacionService.addEstacionRuta(modelMapper.map(rutaEstacionDTO, RutaEntity.class));
    return modelMapper.map(entity, RutaEstacionDTO.class);
}
}
