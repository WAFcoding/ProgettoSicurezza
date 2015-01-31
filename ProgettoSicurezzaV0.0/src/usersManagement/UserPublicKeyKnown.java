/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 15/nov/2014
 */
package usersManagement;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.*;


@Entity
@Table(name = "UserPbcKeyKnown")
public class UserPublicKeyKnown implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String user_id;//l'id dell'utente a cui corrisponde la public key
	private Integer ID_pbcKnown;
	private String pbc_key;
	private User user;//l'utente che ha scambiato messaggi con questo altro utente
	
	public UserPublicKeyKnown() {}
	
	public UserPublicKeyKnown(String id_user, String pbc_key, User user){
		setID_user(id_user);
		setPbc_key(pbc_key);
		setUser(user);
	}

	/**
	 * @return the iD_user
	 */
	@Column(name = "user_id", nullable = false, length= 255)
	public String getID_user() {
		return user_id;
	}

	/**
	 * @param iD_user the iD_user to set
	 */
	public void setID_user(String iD_user) {
		user_id = iD_user;
	}

	/**
	 * @return the iD
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_pbcKnown", nullable = false)
	public Integer getID() {
		return ID_pbcKnown;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(Integer iD) {
		ID_pbcKnown = iD;
	}

	/**
	 * @return the pbc_key
	 */
	@Column(name = "pbc_key", nullable = false, length= 255)
	public String getPbc_key() {
		return pbc_key;
	}

	/**
	 * @param pbc_key the pbc_key to set
	 */
	public void setPbc_key(String pbc_key) {
		this.pbc_key = pbc_key;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_user", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
