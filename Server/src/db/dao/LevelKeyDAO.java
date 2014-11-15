package db.dao;

import bean.LevelKey;

public interface LevelKeyDAO {
	public void saveKey(LevelKey lkey);
	public void updateKey(LevelKey lkey);
	public void deleteKey(LevelKey lkey);
	public LevelKey findKeyByLevel(int level);
}
