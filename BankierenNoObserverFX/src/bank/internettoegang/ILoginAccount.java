package bank.internettoegang;

public interface ILoginAccount
{

    /**
     * @return de gebruikersnaam van het account
     */
    String getNaam();

    /**
     * @return het rekeningnummer van het account
     */
    String getReknr();

    /**
     * Controleert of het ingevoerde wachtwoord klopt
     * @param wachtwoord het ingevoerde wachtwoord
     * @return true indien correct, anders false
     */
    boolean checkWachtwoord(String wachtwoord);
}
