package com.gustavoalves.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.gustavoalves.cursomc.domain.Cliente;
import com.gustavoalves.cursomc.domain.enums.TipoCliente;
import com.gustavoalves.cursomc.dto.ClienteNewDTO;
import com.gustavoalves.cursomc.repositories.ClienteRepository;
import com.gustavoalves.cursomc.resources.exceptions.FieldMessage;
import com.gustavoalves.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getTipo() == TipoCliente.PESSOAFISICA.getCod()
				&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		} else if (objDto.getTipo() == TipoCliente.PESSOAJURIDICA.getCod()
				&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente cliente = repo.findByEmail(objDto.getEmail());
		
		if(cliente != null) {
			list.add(new FieldMessage("email" ,"Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}