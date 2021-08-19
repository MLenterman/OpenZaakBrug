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
package nl.haarlem.translations.zdstozgw.translation.zgw.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ZgwStatusType {

	@SerializedName("url")
	@Expose
	public String url;

	@SerializedName("omschrijving")
	@Expose
	public String omschrijving;

	@SerializedName("omschrijvingGeneriek")
	@Expose
	public String omschrijvingGeneriek;

	@SerializedName("statustekst")
	@Expose
	public String statustekst;

	@SerializedName("zaaktype")
	@Expose
	public String zaaktype;

	@SerializedName("volgnummer")
	@Expose
	public int volgnummer;

	@SerializedName("isEindstatus")
	@Expose
	public String isEindstatus;

	@SerializedName("informeren")
	@Expose
	public String informeren;
}
