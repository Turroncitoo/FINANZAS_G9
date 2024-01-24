package ob.debitos.simp.utilitario;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExportFilterOutputStream extends FilterOutputStream
{
	public ExportFilterOutputStream(OutputStream out)
	{
		super(out);
	}

	public void close() throws IOException
	{
		flush();
	}
}
