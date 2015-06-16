package bank.internettoegang;


public interface ILoginAccount {
	  String getNaam();
	  String getReknr();
	  boolean checkWachtwoord(String wachtwoord);
	}

