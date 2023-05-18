package br.com.springboot.projeto_pogbet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.projeto_pogbet.model.Usuario;
import br.com.springboot.projeto_pogbet.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */

    
    /*@RequestMapping(value = "/login/{nome_usuario}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String login(@PathVariable String nome_usuario) {
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome_usuario);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Login de: "+usuario.getNome();
    }*/
    
    @GetMapping(value = "listatodos")
    @ResponseBody
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/*retorna lista em JSON*/
    }
    
    
    @PostMapping(value = "salvar")
    @ResponseBody
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){
    	Usuario user = usuarioRepository.save(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @DeleteMapping(value = "delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long idUser){
    	usuarioRepository.deleteById(idUser);
    	return new ResponseEntity<String>("Usuraio deletado!!", HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarUserId")
    @ResponseBody
    public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "idUser") Long idUser){
    	Usuario usuario = usuarioRepository.findById(idUser).get();
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){
    	if(usuario.getId()==null) {
    		return new ResponseEntity<String>("Id não indormado para atualizar",HttpStatus.OK);
    	}    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name){
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorEmail")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorEmail(@RequestParam(name = "email") String email){
    	List<Usuario> usuario = usuarioRepository.buscarPorEmail(email.trim().toUpperCase());
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
    
    @Controller
    public class MyController {

        @GetMapping("index.html")
        public String minhaPagina() {
            return "index"; // retorna o nome do arquivo HTML
        }
    }

    
}
