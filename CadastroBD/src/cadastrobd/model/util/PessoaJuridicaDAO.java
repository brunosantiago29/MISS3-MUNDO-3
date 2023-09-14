// Classe PessoaJuridicaDAO
package cadastrobd.model.util;

import cadastrobd.model.PessoaJuridica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    public void incluir(PessoaJuridica pessoaJuridica) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa(nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoaJuridica(idPessoa, cnpj) VALUES (?, ?)";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement psPessoa = connection.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement psPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica)) {

            psPessoa.setString(1, pessoaJuridica.getNome());
            psPessoa.setString(2, pessoaJuridica.getLogradouro());
            psPessoa.setString(3, pessoaJuridica.getCidade());
            psPessoa.setString(4, pessoaJuridica.getEstado());
            psPessoa.setString(5, pessoaJuridica.getTelefone());
            psPessoa.setString(6, pessoaJuridica.getEmail());

            int rowsAffected = psPessoa.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Falha ao inserir pessoa.");
            }

            ResultSet generatedKeys = psPessoa.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idPessoa = generatedKeys.getInt(1);
                pessoaJuridica.setId(idPessoa);

                psPessoaJuridica.setInt(1, idPessoa);
                psPessoaJuridica.setString(2, pessoaJuridica.getCnpj());
                psPessoaJuridica.executeUpdate();
            } else {
                throw new SQLException("Falha ao obter ID gerado.");
            }
        }
    }

    public PessoaJuridica getPessoa(int id) throws SQLException {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pj.cnpj FROM Pessoa p " +
                     "INNER JOIN PessoaJuridica pj ON p.idPessoa = pj.idPessoa " +
                     "WHERE p.idPessoa = ?";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPessoaJuridicaFromResultSet(rs);
                }
            }
        }

        return null;
    }

    public List<PessoaJuridica> getPessoas() throws SQLException {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pj.cnpj FROM Pessoa p " +
                     "INNER JOIN PessoaJuridica pj ON p.idPessoa = pj.idPessoa";

        List<PessoaJuridica> pessoas = new ArrayList<>();

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pessoas.add(extractPessoaJuridicaFromResultSet(rs));
            }
        }

        return pessoas;
    }

    public void alterar(PessoaJuridica pessoaJuridica) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlPessoaJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE idPessoa = ?";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement psPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement psPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica)) {

            psPessoa.setString(1, pessoaJuridica.getNome());
            psPessoa.setString(2, pessoaJuridica.getLogradouro());
            psPessoa.setString(3, pessoaJuridica.getCidade());
            psPessoa.setString(4, pessoaJuridica.getEstado());
            psPessoa.setString(5, pessoaJuridica.getTelefone());
            psPessoa.setString(6, pessoaJuridica.getEmail());
            psPessoa.setInt(7, pessoaJuridica.getId());

            psPessoaJuridica.setString(1, pessoaJuridica.getCnpj());
            psPessoaJuridica.setInt(2, pessoaJuridica.getId());

            psPessoa.executeUpdate();
            psPessoaJuridica.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE idPessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement psPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
             PreparedStatement psPessoa = connection.prepareStatement(sqlPessoa)) {

            psPessoaJuridica.setInt(1, id);
            psPessoaJuridica.executeUpdate();

            psPessoa.setInt(1, id);
            psPessoa.executeUpdate();
        }
    }

    private PessoaJuridica extractPessoaJuridicaFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idPessoa");
        String nome = rs.getString("nome");
        String logradouro = rs.getString("logradouro");
        String cidade = rs.getString("cidade");
        String estado = rs.getString("estado");
        String telefone = rs.getString("telefone");
        String email = rs.getString("email");
        String cnpj = rs.getString("cnpj");

        return new PessoaJuridica(id, nome, logradouro, cidade, estado, telefone, email, cnpj);
    }
}
