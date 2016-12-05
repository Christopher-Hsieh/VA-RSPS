/**
 * 
 * @author Zion
 * 
 * This class stores the account data
 * 
 */
public class AccountData {
	
	/**
	 * Stores the account rank
	 */
	public int rank;
	
	/**
	 * Stores the amount of uses of the account
	 */
	public int uses;
	
	/**
	 * Stores account username
	 */
	public String username;
	
	/**
	 * Stores account password
	 */
	public String password;
	
	/**
	 * Creates the default account data
	 * 
	 * @param rank
	 * @param username
	 * @param password
	 */
	public AccountData(int rank, String username, String password) {
		this.rank = rank;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Stores the new account data
	 * 
	 * @param rank
	 * @param uses
	 * @param username
	 * @param password
	 */
	public AccountData(int rank, int uses, String username, String password) {
		this.rank = rank;
		this.uses = uses;
		this.username = username;
		this.password = password;
	}
}