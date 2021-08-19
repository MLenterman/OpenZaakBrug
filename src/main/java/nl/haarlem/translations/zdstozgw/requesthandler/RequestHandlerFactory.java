/*
 * Copyright 2020-2021 The Open Zaakbrug Contributors
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the 
 * European Commission - subsequent versions of the EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package nl.haarlem.translations.zdstozgw.requesthandler;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import nl.haarlem.translations.zdstozgw.config.ConfigService;
import nl.haarlem.translations.zdstozgw.converter.Converter;

@Component
public class RequestHandlerFactory {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final ConfigService configService;

	@Autowired
	public RequestHandlerFactory(ConfigService configService) {
		this.configService = configService;
	}

	public RequestHandler getRequestHandler(Converter converter) throws ResponseStatusException {
		var classname = this.configService.getConfiguration().getRequestHandlerImplementation();
		try {
			Class<?> c = Class.forName(classname);
			java.lang.reflect.Constructor<?> ctor = c.getConstructor(Converter.class, ConfigService.class);
			Object object = ctor.newInstance(new Object[] { converter, this.configService });
			return (RequestHandler) object;
		} catch (Exception e) {
			log.error("error loading class:" + classname, e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading class:" + classname, e);
		}
	}
}
