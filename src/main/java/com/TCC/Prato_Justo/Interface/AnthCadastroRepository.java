package com.TCC.Prato_Justo.Interface;

import com.TCC.Prato_Justo.Model.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnthCadastroRepository extends JpaRepository<Cadastro, Long> {
    Optional<Cadastro> findByNomeAndSenhaHashAndEmail(String nome, String senhaHash, String email);
    Optional<Cadastro> findByEmail(String email);
}
