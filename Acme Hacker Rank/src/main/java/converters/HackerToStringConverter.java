
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Hacker;

@Component
@Transactional
public class HackerToStringConverter implements Converter<Hacker, String> {

	@Override
	public String convert(final Hacker hacker) {
		String result;

		if (hacker == null)
			result = null;
		else
			result = String.valueOf(hacker.getId());

		return result;
	}

}
