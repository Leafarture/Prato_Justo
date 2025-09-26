package com.TCC.Prato_Justo.Service;


import com.TCC.Prato_Justo.Controller.AutchCadastroController.CadastroRequest;
import com.TCC.Prato_Justo.Interface.AnthCadastroRepository;
import com.TCC.Prato_Justo.Model.Cadastro;
import com.TCC.Prato_Justo.Model.TipoUsuario;
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
       cadastro.setNome(username);
       cadastro.setEmail(email);
       cadastro.setPassword(password);
       cadastro.setEnderecoCompleto("Endereço não informado");
       cadastro.setTipoUsuario(TipoUsuario.INDIVIDUAL);
       return autchCadastroRepository.save(cadastro);
    }

    public Cadastro cadastrarNovoUsusario(Cadastro cadastro){
        return autchCadastroRepository.save(cadastro);
    }

    public Cadastro cadastrarNovoUsuario(CadastroRequest request){
        Cadastro cadastro = new Cadastro();
        cadastro.setNome(request.getUsername());
        cadastro.setEmail(request.getEmail());
        cadastro.setPassword(request.getPassword());
        cadastro.setTelefone(request.getTelefone());
        cadastro.setEnderecoCompleto(request.getEnderecoCompleto() != null ? request.getEnderecoCompleto() : "Endereço não informado");
        cadastro.setLatitude(request.getLatitude());
        cadastro.setLongitude(request.getLongitude());
        
        // Mapear perfil do frontend para enum
        if (request.getPerfil() != null) {
            switch (request.getPerfil().toUpperCase()) {
                case "PESSOA_FISICA":
                    cadastro.setTipoUsuario(TipoUsuario.INDIVIDUAL);
                    break;
                case "ESTABELECIMENTO_ONG":
                    // Por padrão, vamos usar ESTABELECIMENTO, mas pode ser ajustado
                    cadastro.setTipoUsuario(TipoUsuario.ESTABELECIMENTO);
                    break;
                default:
                    cadastro.setTipoUsuario(TipoUsuario.INDIVIDUAL);
            }
        } else {
            cadastro.setTipoUsuario(TipoUsuario.INDIVIDUAL);
        }
        
        return autchCadastroRepository.save(cadastro);
    }

    public boolean validarLogin(String email, String rawPassword){
        return autchCadastroRepository.findByEmail(email)
                .map(cad -> passwordEncoder.matches(rawPassword, cad.getSenhaHash()))
                .orElse(false);
    }
}
