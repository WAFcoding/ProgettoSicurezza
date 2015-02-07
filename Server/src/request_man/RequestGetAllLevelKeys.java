package request_man;

import java.util.List;

import bean.LevelKey;
import db.dao.LevelKeyDAO;
import db.dao.LevelKeyDaoImpl;

public class RequestGetAllLevelKeys extends Request {

	private int maxTrustLevel = 0;
	public RequestGetAllLevelKeys(int i) {
		this.maxTrustLevel = i;
	}

	@Override
	public Result doAndGetResult() {
		LevelKeyDAO ldao = new LevelKeyDaoImpl();
		List<LevelKey> keys = ldao.getAllKeysToLevel(this.maxTrustLevel);
		
		if(keys == null || keys.isEmpty()) {
			return new ResultInvalidJson("No level key retrieved");
		}
		
		return new ResultListKey(keys);
	}

}
