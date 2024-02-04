package jena.lang.syntax;

public class JenaException extends RuntimeException
{
    String text;
    public JenaException(String text, Throwable cause)
    {
        super(text, cause);
        this.text = text;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception : ");
        sb.append(text);
        var cause = getCause();
        while(cause != null)
        {
            sb.append("\ncaused by\n");
            sb.append(cause.getMessage());
            cause = cause.getCause();
        }
        return sb.toString();
    }
}