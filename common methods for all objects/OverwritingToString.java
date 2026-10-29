public class OverwritingToString {
    /*
     * Retorne a representação da string para essa phone number
     * A string é composta de doze caracteres, cujo formato é
     * "XXX-YYY-ZZZZ", onde X, Y e Z são dígitos. Por exemplo, o número de telefone
     * 1234567890 seria representado como "123-456-7890".
     */
    @Override
    public String toString() {
        return String.format("%03d-%03d-%04d", areaCode, centralOfficeCode, lineNumber);
    }
}
