package request_man;

import java.util.List;

import bean.LevelKey;

public class ResultListKey extends Result {

	private List<LevelKey> keys = null;
	public ResultListKey(List<LevelKey> keys) {
		this.keys = keys;
	}

	@Override
	public String toSendFormat() {

		StringBuilder b = new StringBuilder();
		b.append("{\"keys\":[");
		
		boolean first = true;
		for(LevelKey lkey : keys) {
			if(!first)
				b.append(",");
			b.append("{");
			b.append("\"level\":"+ lkey.getLevel() + ",");
			b.append("\"key\":\""+ lkey.getClearKey().replace("\n", "") + "\"");
			b.append("}");
			first = false;
		}
		b.append("]}");
		return b.toString();
	}

}
