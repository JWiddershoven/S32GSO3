package bank.bankieren;

import java.io.Serializable;

public interface IKlant extends Serializable, Comparable<IKlant>
{

    /**
     * @return de naam van de klant
     */
    String getNaam();

    /**
     * @return de woonplaats van de klant
     */
    String getPlaats();
}
