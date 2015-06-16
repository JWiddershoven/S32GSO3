package bank.bankieren;

public class Rekening implements IRekeningTbvBank {

    private static final long serialVersionUID = 7221569686169173632L;
    private static final int KREDIETLIMIET = -10000;
    private String nr;
    private IKlant eigenaar;
    private Money saldo;

    /**
     * creatie van een bankrekening met saldo van 0.0<br>
     * de constructor heeft package-access omdat de PersistentAccount-objecten door een
     * het PersistentBank-object worden beheerd
     * @see banking.persistence.PersistentBank
     * @param number het bankrekeningnummer
     * @param klant de eigenaar van deze rekening
     * @param currency de munteenheid waarin het saldo is uitgedrukt
     */
    public Rekening(String number, IKlant klant, String currency) {
        this(number, klant, new Money(1000, currency));
    }

    /**
     * creatie van een bankrekening met saldo saldo<br>
     * de constructor heeft package-access omdat de PersistentAccount-objecten door een
     * het PersistentBank-object worden beheerd
     * @param klant
     * @param saldo
     * @see banking.persistence.PersistentBank
     * @param number het bankrekeningnummer
     */
    public Rekening(String number, IKlant klant, Money saldo) {
        this.nr = number;
        this.eigenaar = klant;
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        IRekening rek = (IRekening) obj;
        if (nr.equals(rek.getNr()))
        {
            result = true;
        }
        return result;
    }

    @Override
    public String getNr() {
        return nr;
    }

    @Override
    public String toString() {
        return nr + ": " + eigenaar.toString();
    }

    boolean isTransferPossible(Money bedrag) {
        return (bedrag.getCents() + saldo.getCents() >= KREDIETLIMIET);
    }

    @Override
    public IKlant getEigenaar() {
        return eigenaar;
    }

    @Override
    public Money getSaldo() {
        return saldo;
    }

    @Override
    public boolean muteer(Money bedrag) {
        if (bedrag.getCents() == 0) {
            throw new RuntimeException(" bedrag = 0 bij aanroep 'muteer'");
        }

        if (isTransferPossible(bedrag)) {
            saldo = Money.sum(saldo, bedrag);
            return true;
        }
        return false;
    }

    @Override
    public int getKredietLimietInCenten() {
        return KREDIETLIMIET;
    }
}
