package com.TCC.Prato_Justo.Controller;

import com.TCC.Prato_Justo.Model.Cadastro;
import com.TCC.Prato_Justo.Service.CadastroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AutchCadastroController {

   private final CadastroService cadastroService;

    public AutchCadastroController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @PostMapping("registro")
    public Cadastro registro(@RequestBody Cadastro cadastro){
        return cadastroService.cadastrarNovoUsusario(cadastro);
    }

    @PostMapping("/cadastros")
    public String cadastros(@RequestBody Cadastro cadastro){
        Cadastro cad = cadastroService.fazerCadastro(cadastro.getUsername(), cadastro.getEmail(), cadastro.getPassword());
        if(cad != null){
            return "Cadastro Realizado" + cad.getUsername() + "!!!!!";

        }
        return "Usuario ou senha ou email errada !!!!";
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        boolean valido = cadastroService.validarLogin(request.getEmail(), request.getPassword());
        if (valido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
