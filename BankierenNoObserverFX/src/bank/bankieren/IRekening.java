package bank.bankieren;

import java.io.Serializable;

public interface IRekening extends Serializable
{

    /**
     * @return het rekeningnummer van de rekening
     */
    String getNr();

    /**
     * @return het saldo van de rekening
     */
    Money getSaldo();

    /**
     * @return de eigenaar van de rekening
     */
    IKlant getEigenaar();

    /**
     * @return het kredietlimiet van de rekening in centen
     */
    int getKredietLimietInCenten();
}
