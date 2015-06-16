package bank.internettoegang;


public class LoginAccount implements ILoginAccount {

	private String naam;
	private String wachtwoord;
	private String reknr;

	public LoginAccount(String naam, String wachtwoord, String rekening) {
		this.naam = naam;
		this.wachtwoord = wachtwoord;
		this.reknr = rekening;
	}

        @Override
	public boolean checkWachtwoord(String wachtwoord) {
		return this.wachtwoord.equals(wachtwoord);
	}

        @Override
	public String getNaam() {
		return naam;
	}

        @Override
	public String getReknr() {
		return reknr;
	}

}
