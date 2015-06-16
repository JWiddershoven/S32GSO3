package bank.bankieren;

import java.io.Serializable;

public interface IRekening extends Serializable {
  String getNr();
  Money getSaldo();
  IKlant getEigenaar();
  int getKredietLimietInCenten();
}

