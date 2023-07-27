package br.senai.petshopteste;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/cadastro-pets")
public class CadastroPet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Pet> petsCadastrados = Banco.busca();
		
		PrintWriter writer = resp.getWriter();
		writer.print("<ul>");
		
		for (Pet pet : petsCadastrados) {
			writer.printf("<li>Id: %d, Nome: %s, Peso: %.2f, Raca: %s, Especie: %s </li>",
					pet.getId(),
					pet.getNome(),
					pet.getPeso(),
					pet.getRaca(),
					pet.getEspecie()
					);
		}
	
		writer.print("</ul>");
	} 
	
	
@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String nome = req.getParameter("nome");
		String peso = req.getParameter("peso");
		String raca = req.getParameter("raca");
		String especie = req.getParameter("especie");
		
		Pet novoPet = new Pet();
		novoPet.setNome(nome);
		novoPet.setPeso(Double.parseDouble(peso));
		novoPet.setRaca(raca);
		novoPet.setEspecie(especie);
		
		Banco.salva(novoPet);
		
		PrintWriter writer = resp.getWriter();
		writer.printf("Pet cadastrado com sucesso. +"
				+ " Nome: %s, Peso: %.2f, Raça: %s, Espécie: %s",
				novoPet.getNome(),
				novoPet.getPeso(),
				novoPet.getRaca(),
				novoPet.getEspecie());
		
		resp.setStatus(201);
		
	}

@Override
protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
	int id = Integer.parseInt(req.getParameter("id"));
	List<Pet> pets = Banco.busca();
	Pet oldPet = null;
	
	for (Pet pet : pets) {
		if (pet.getId() == id) {
			oldPet = pet;
			break;
		}
	}
	
	if (oldPet != null) {
		// TODO: atualizar os dados do Pet
		String nome = req.getParameter("nome");
		String peso = req.getParameter("peso");
		String raca = req.getParameter("raca");
		String especie = req.getParameter("especie");
		
		if (nome != null) {
			oldPet.setNome(nome);
		}
		if (peso != null) {
			oldPet.setPeso(Double.parseDouble(peso));
		}
		if (raca != null) {
			oldPet.setRaca(raca);
		}
		if (especie != null) {
			oldPet.setEspecie(especie);
		}
		
	} else {
		resp.setStatus(404);
	}
	
}


@Override
protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
	 int id = Integer.parseInt(req.getParameter("id"));
	 List<Pet> pets = Banco.busca();
	 Pet removerPet = null;
	 
	 for (Pet pet : pets) {
		 if (pet.getId() == id) {
			 removerPet = pet;
			 break;
		 }
	 }
	 
	 if (removerPet != null) {
		 pets.remove(removerPet);
	 }
	 
	 resp.setStatus(204);
}
	
}
