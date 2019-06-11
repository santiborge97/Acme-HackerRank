
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.HackerRepository;
import domain.Hacker;

@Component
@Transactional
public class StringToHackerConverter implements Converter<String, Hacker> {

	@Autowired
	private HackerRepository	hackerRepository;


	@Override
	public Hacker convert(final String text) {
		Hacker result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.hackerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
