package usersManagement;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
@Entity
@Table(name = "Users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer ID;
	private String name; 
	private String surname;
	private String mail;
	private String code;
	private String city; 
	private String country;
	private String country_code;
	private String organization;
	private String dir_def;
	private String dir_in;
	private String dir_out;
	private String publicKey;
	private String privateKey;
	private String password;
	
	private Set<UserPublicKeyKnown> publicKeyKnown= new HashSet<UserPublicKeyKnown>(0);
	
	public User(){
	}
	
	public User(String name, String surname, String mail, String code, String city, String country,
					String country_code, String organization, String dir_def, String dir_in, String dir_out){

		setDir_def(dir_def);
		setDir_in(dir_in);
		setDir_out(dir_out);
		setName(name);
		setSurname(surname);
		setCountry(country);
		setCountry_code(country_code);
		setCity(city);
		setOrganization(organization);
		setMail(mail);
		setCode(code);
		
	}
	/**
	 * @return the iD
	 */
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "ID_user", unique= true, nullable= false)
	public Integer getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(Integer iD) {
		ID = iD;
	}
	/**
	 * @return the name
	 */
	@Column(name = "name", nullable= false, length= 255 )
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surname
	 */
	@Column(name = "surname", nullable= false, length= 255 )
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the mail
	 */
	@Column(name = "mail", nullable= false, length= 255 )
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the code
	 */
	@Column(name = "code", nullable= false, length= 1024 )
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the city
	 */
	@Column(name = "city", nullable= false, length= 255 )
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the country
	 */
	@Column(name = "country", nullable= false, length= 255 )
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the country_code
	 */
	@Column(name = "country_code", nullable= false, length= 2 )
	public String getCountry_code() {
		return country_code;
	}
	/**
	 * @param country_code the country_code to set
	 */
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	/**
	 * @return the organization
	 */
	@Column(name = "organization", nullable= false, length= 255 )
	public String getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	/**
	 * @return the dir_def
	 */
	@Column(name = "dir_default", nullable= false, length= 255 )
	public String getDir_def() {
		return dir_def;
	}
	/**
	 * @param dir_def the dir_def to set
	 */
	public void setDir_def(String dir_def) {
		this.dir_def = dir_def;
	}
	/**
	 * @return the dir_in
	 */
	@Column(name = "dir_input", nullable= false, length= 255 )
	public String getDir_in() {
		return dir_in;
	}
	/**
	 * @param dir_in the dir_in to set
	 */
	public void setDir_in(String dir_in) {
		this.dir_in = dir_in;
	}
	/**
	 * @return the dir_out
	 */
	@Column(name = "dir_output", nullable= false, length= 255 )
	public String getDir_out() {
		return dir_out;
	}
	/**
	 * @param dir_out the dir_out to set
	 */
	public void setDir_out(String dir_out) {
		this.dir_out = dir_out;
	}

	@Column(name = "pbc_key", nullable= false, length= 1024 )
	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Column(name = "pvt_key", nullable= false, length= 1024 )
	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	/**
	 * Inserisce l'utente e la sua chiave pubblica, se l'utente e' presente
	 * viene sovrascritto il precedente valore
	 * @param user
	 * @param paramPblKey
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy= "user")
	public Set<UserPublicKeyKnown> getPublicKeyKnown() {
		return publicKeyKnown;
	}

	public void setPublicKeyKnown(Set<UserPublicKeyKnown> publicKeyKnown) {
		this.publicKeyKnown = publicKeyKnown;
	}
	@Column(name = "password", nullable= false, length= 1024)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
