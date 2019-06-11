
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.MiscellaneousData;

@Component
@Transactional
public class MiscellaneousDataToStringConverter implements Converter<MiscellaneousData, String> {

	@Override
	public String convert(final MiscellaneousData miscellaneousData) {
		String result;

		if (miscellaneousData == null)
			result = null;
		else
			result = String.valueOf(miscellaneousData.getId());

		return result;
	}

}
