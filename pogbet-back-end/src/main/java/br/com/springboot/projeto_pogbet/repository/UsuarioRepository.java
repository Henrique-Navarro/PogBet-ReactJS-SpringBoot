package br.com.springboot.projeto_pogbet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.projeto_pogbet.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query(value = "select u from Usuario u where upper(trim(u.nome)) like %?1%")
	List<Usuario> buscarPorNome(String name);
	
	@Query(value = "select u from Usuario u where upper(trim(u.email)) like ?1")
	List<Usuario> buscarPorEmail(String email);
	
}
