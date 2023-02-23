package abc;

import java.io.IOException;

public interface ISimpleHttpClient {
	String doHttpGet(String http) throws IOException;
}
