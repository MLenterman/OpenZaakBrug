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
package nl.haarlem.translations.zdstozgw.converter.impl.translate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import nl.haarlem.translations.zdstozgw.config.model.Translation;
import nl.haarlem.translations.zdstozgw.converter.Converter;
import nl.haarlem.translations.zdstozgw.requesthandler.RequestResponseCycle;
import nl.haarlem.translations.zdstozgw.translation.zds.model.ZdsBv03;
import nl.haarlem.translations.zdstozgw.translation.zds.model.ZdsZaak;
import nl.haarlem.translations.zdstozgw.translation.zds.model.ZdsZakLk01;
import nl.haarlem.translations.zdstozgw.translation.zds.services.ZaakService;
import nl.haarlem.translations.zdstozgw.utils.XmlUtils;

public class CreeerZaakTranslator extends Converter {

	public CreeerZaakTranslator(RequestResponseCycle context, Translation translation, ZaakService zaakService) {
		super(context, translation, zaakService);
	}

	@Override
	public void load() throws ResponseStatusException {
		this.zdsDocument = (ZdsZakLk01) XmlUtils.getStUFObject(this.getSession().getClientRequestBody(), ZdsZakLk01.class);
	}

	@Override
	public ResponseEntity<?> execute() throws ResponseStatusException {
		String rsin = this.getZaakService().getRSIN(this.zdsDocument.stuurgegevens.zender.organisatie);
		ZdsZakLk01 zdsZakLk01 = (ZdsZakLk01) this.zdsDocument;
		ZdsZaak zdsZaak = zdsZakLk01.objects.get(0);

		this.getSession().setFunctie("CreeerZaak");
		this.getSession().setKenmerk("zaakidentificatie:" + zdsZaak.identificatie);

		var zgwZaak = this.getZaakService().creeerZaak(rsin, zdsZaak);
		var bv03 = new ZdsBv03(zdsZakLk01.stuurgegevens, this.getSession().getReferentienummer());
		var response = XmlUtils.getSOAPMessageFromObject(bv03);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}