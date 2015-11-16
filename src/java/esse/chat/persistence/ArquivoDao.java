package esse.chat.persistence;

import esse.chat.model.Arquivo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Equipe ESSE Chat
 */
public interface ArquivoDao{
    boolean subirArquivo(Arquivo arquivo) throws SQLException;
    Arquivo baixarArquivo(Arquivo arquivo) throws SQLException;
    boolean excluirArquivo(Arquivo arquivo) throws SQLException;
    List<Arquivo> listarArquivos(String roomId) throws SQLException;
}
