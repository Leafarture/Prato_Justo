package com.TCC.Prato_Justo.Service;


import com.TCC.Prato_Justo.Interface.AnthCadastroRepository;
import com.TCC.Prato_Justo.Model.Cadastro;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class CadastroService {

    private final AnthCadastroRepository autchCadastroRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CadastroService(AnthCadastroRepository autchCadastroRepository) {
        this.autchCadastroRepository = autchCadastroRepository;
    }


    public Cadastro fazerCadastro(String username, String password, String email){
       Cadastro cadastro = new Cadastro();
       cadastro.setUsername(username);
       cadastro.setEmail(email);
       cadastro.setPassword(password);
       return autchCadastroRepository.save(cadastro);
    }

    public Cadastro cadastrarNovoUsusario(Cadastro cadastro){
        return autchCadastroRepository.save(cadastro);
    }

    public boolean validarLogin(String email, String rawPassword){
        return autchCadastroRepository.findByEmail(email)
                .map(cad -> passwordEncoder.matches(rawPassword, cad.getPassword()))
                .orElse(false);
    }
}
