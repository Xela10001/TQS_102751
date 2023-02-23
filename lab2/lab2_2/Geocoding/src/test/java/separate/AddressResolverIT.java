package separate;

import abc.*;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class AddressResolverIT {
	
	TqsBasicHttpClient httpClient = new TqsBasicHttpClient();
	
	AddressResolver resolver = new AddressResolver(httpClient);
	
	
	@Test
	public void findAddressForLocationTest() throws URISyntaxException, ParseException, IOException {
		Optional<Address> result = resolver.findAddressForLocation(40.633116,-8.658784);
		Assertions.assertEquals(result, Optional.of(new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null)));
	}
	
	@Test
	public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {
		Optional<Address> result = resolver.findAddressForLocation(-300, -810);
		
		Assertions.assertEquals(Optional.empty(), result);
	}
}
