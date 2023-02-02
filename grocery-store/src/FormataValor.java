import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormataValor {

    public DecimalFormat valorDecimal() {
        String modelo = "###,###.##";
        DecimalFormatSymbols dFormatSymbols = new DecimalFormatSymbols(new Locale("pt","Brazil"));
        dFormatSymbols.setDecimalSeparator(',');
        dFormatSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat(modelo,dFormatSymbols);
        return dFormat;
    }

}
