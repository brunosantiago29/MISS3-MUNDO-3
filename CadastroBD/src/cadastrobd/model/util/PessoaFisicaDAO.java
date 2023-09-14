// Classe PessoaFisicaDAO
package cadastrobd.model.util;

import cadastrobd.model.PessoaFisica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    public void incluir(PessoaFisica pessoaFisica) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa(nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO PessoaFisica(idPessoa, cpf) VALUES (?, ?)";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement psPessoa = connection.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement psPessoaFisica = connection.prepareStatement(sqlPessoaFisica)) {

            psPessoa.setString(1, pessoaFisica.getNome());
            psPessoa.setString(2, pessoaFisica.getLogradouro());
            psPessoa.setString(3, pessoaFisica.getCidade());
            psPessoa.setString(4, pessoaFisica.getEstado());
            psPessoa.setString(5, pessoaFisica.getTelefone());
            psPessoa.setString(6, pessoaFisica.getEmail());

            int rowsAffected = psPessoa.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Falha ao inserir pessoa.");
            }

            ResultSet generatedKeys = psPessoa.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idPessoa = generatedKeys.getInt(1);
                pessoaFisica.setId(idPessoa);

                psPessoaFisica.setInt(1, idPessoa);
                psPessoaFisica.setString(2, pessoaFisica.getCpf());
                psPessoaFisica.executeUpdate();
            } else {
                throw new SQLException("Falha ao obter ID gerado.");
            }
        }
    }

    public PessoaFisica getPessoa(int id) throws SQLException {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pf.cpf FROM Pessoa p " +
                     "INNER JOIN PessoaFisica pf ON p.idPessoa = pf.idPessoa " +
                     "WHERE p.idPessoa = ?";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPessoaFisicaFromResultSet(rs);
                }
            }
        }

        return null;
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pf.cpf FROM Pessoa p " +
                     "INNER JOIN PessoaFisica pf ON p.idPessoa = pf.idPessoa";

        List<PessoaFisica> pessoas = new ArrayList<>();

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pessoas.add(extractPessoaFisicaFromResultSet(rs));
            }
        }

        return pessoas;
    }

    public void alterar(PessoaFisica pessoaFisica) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlPessoaFisica = "UPDATE PessoaFisica SET cpf = ? WHERE idPessoa = ?";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement psPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement psPessoaFisica = connection.prepareStatement(sqlPessoaFisica)) {

            psPessoa.setString(1, pessoaFisica.getNome());
            psPessoa.setString(2, pessoaFisica.getLogradouro());
            psPessoa.setString(3, pessoaFisica.getCidade());
            psPessoa.setString(4, pessoaFisica.getEstado());
            psPessoa.setString(5, pessoaFisica.getTelefone());
            psPessoa.setString(6, pessoaFisica.getEmail());
            psPessoa.setInt(7, pessoaFisica.getId());

            psPessoaFisica.setString(1, pessoaFisica.getCpf());
            psPessoaFisica.setInt(2, pessoaFisica.getId());

            psPessoa.executeUpdate();
            psPessoaFisica.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE idPessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement psPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
             PreparedStatement psPessoa = connection.prepareStatement(sqlPessoa)) {

            psPessoaFisica.setInt(1, id);
            psPessoaFisica.executeUpdate();

            psPessoa.setInt(1, id);
            psPessoa.executeUpdate();
        }
    }

    private PessoaFisica extractPessoaFisicaFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idPessoa");
        String nome = rs.getString("nome");
        String logradouro = rs.getString("logradouro");
        String cidade = rs.getString("cidade");
        String estado = rs.getString("estado");
        String telefone = rs.getString("telefone");
        String email = rs.getString("email");
        String cpf = rs.getString("cpf");

        return new PessoaFisica(id, nome, logradouro, cidade, estado, telefone, email, cpf);
    }


}
