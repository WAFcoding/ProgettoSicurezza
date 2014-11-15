package db.dao;

import java.util.List;

import bean.User;

public interface UserDAO {
	public void saveUser(User u);
	public void updateUser(User u);
	public void deleteUser(User u);
	public User findUserByUsername(String username);
	public List<User> findUserByTrustLevel(int l);
	public List<User> findUserByMinTrustLevel(int l);
}
