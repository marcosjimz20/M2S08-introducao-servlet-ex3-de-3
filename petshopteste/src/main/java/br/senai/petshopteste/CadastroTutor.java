package br.senai.petshopteste;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(value = "/cadastro-tutores")
public class CadastroTutor extends HttpServlet {
	
	//private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Tutor> tutoresCadastrados = Banco.buscar();
		
		PrintWriter writer = resp.getWriter();
		writer.print("<ul>");
		
		for (Tutor tutor : tutoresCadastrados) {
			writer.printf("<li>Id: %d, Nome: %s, Rg: %s, Telefone: %s, Email: %s </li>",
					tutor.getId(),
					tutor.getNome(),
					tutor.getRg(),
					tutor.getTelefone(),
					tutor.getEmail()
					);
		}
	
		writer.print("</ul>");
	} 

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String nome = req.getParameter("nome");
		String rg = req.getParameter("rg");
		String telefone = req.getParameter("telefone");
		String email = req.getParameter("email");
		
		Tutor novoTutor = new Tutor();
		novoTutor.setNome(nome);
		novoTutor.setRg(rg);
		novoTutor.setTelefone(telefone);
		novoTutor.setEmail(email);
		
		Banco.salva(novoTutor);
		
		PrintWriter writer = resp.getWriter();
		writer.printf("Tutor cadastrado com sucesso. +"
				+ " Nome: %s, Rg: %s, Telefone: %s, Email: %s",
				novoTutor.getNome(),
				novoTutor.getRg(),
				novoTutor.getTelefone(),
				novoTutor.getEmail());
		
		resp.setStatus(201);
		
	}
	
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		List<Tutor> tutores = Banco.buscar();
		Tutor oldTutor = null;
		
		for (Tutor tutor : tutores) {
			if (tutor.getId() == id) {
				oldTutor = tutor;
				break;
			}
		}
		
		if (oldTutor != null) {
			// TODO: atualizar os dados do Tutor
			String nome = req.getParameter("nome");
			String rg = req.getParameter("rg");
			String telefone = req.getParameter("telefone");
			String email = req.getParameter("email");
			
			if (nome != null) {
				oldTutor.setNome(nome);
			}
			if (rg != null) {
				oldTutor.setRg(rg);
			}
			if (telefone != null) {
				oldTutor.setTelefone(telefone);
			}
			if (email != null) {
				oldTutor.setEmail(email);
			}
			
		} else {
			resp.setStatus(404);
		}
		
	}
 



	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		 int id = Integer.parseInt(req.getParameter("id"));
		 List<Tutor> tutores = Banco.buscar();
		 Tutor removerTutor = null;
		 
		 for (Tutor tutor : tutores) {
			 if (tutor.getId() == id) {
				 removerTutor = tutor;
				 break;
			 }
		 }
		 
		 if (removerTutor != null) {
			 tutores.remove(removerTutor);
		 }
		 
		 resp.setStatus(204);
	}

}
