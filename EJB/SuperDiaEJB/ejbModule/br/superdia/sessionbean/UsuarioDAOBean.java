package br.superdia.sessionbean;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.superdia.jpa.JPAUtil;
import br.superdia.modelo.Usuario;

@Stateless
@Remote(IUsuarioDAO.class)
public class UsuarioDAOBean implements IUsuarioDAO {
	
	/*
	 * Verifica se um usuário é válido, caso seja, retorna o perfil do mesmo para
	 * que possa ser setado no objeto e liberar apenas suas permissões de acesso.
	 */
	public String isValid(Usuario usuario){
		EntityManager em = JPAUtil.getEntityManager();
		String q = "SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.senha = :senha";
		TypedQuery<Usuario> query = em.createQuery(q, Usuario.class);
		query.setParameter("usuario", usuario.getUsuario());
		query.setParameter("senha", usuario.getSenha());
		Usuario u = query.getSingleResult();
		em.close();
		return u != null ? u.getPerfil() : null;
	}
}