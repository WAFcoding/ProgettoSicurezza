package request_man;

import java.util.List;

import bean.UserCertificateBean;

public class ResultListUser extends Result {

	private List<UserCertificateBean> list;
	public ResultListUser(List<UserCertificateBean> users) {
		this.list = users;
	}

	/**
	 * Restituisce la lista di utenti trovati in formato JSON:
	 * <code>
	 * <br>{users:[<br>
	 * {name:"Giovanni",surname:"Rossi",trustLevel:4,pkey:"MII[...]"},<br>
	 * [...]<br>
	 * ]}<br>
	 * </code>
	 * @return La lista di utenti trovati.
	 */
	@Override
	public String toSendFormat() {

		StringBuilder b = new StringBuilder();
		b.append("{\"users\":[");
		
		boolean first = true;
		for(UserCertificateBean u : list) {
			if(!first)
				b.append(",");
			b.append("{");
			b.append("\"name\":\""+u.getName() + "\",");
			b.append("\"surname\":\""+u.getSurname() + "\",");
			b.append("\"trustLevel\":"+u.getTrustLevel() + ",");
			b.append("\"pkey\":\""+u.getPublicKey() + "\"");
			b.append("}");
			first = false;
		}
		b.append("]}");
		return b.toString();
	}

}
