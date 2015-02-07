package db.dao;

import java.util.List;

import bean.LevelKey;

public interface LevelKeyDAO {
	public void saveKey(LevelKey lkey);
	public void updateKey(LevelKey lkey);
	public void deleteKey(LevelKey lkey);
	public LevelKey findKeyByLevel(int level);
	public List<LevelKey> getAllKeysToLevel(int level);
}
