/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.elasticdata.modules.gru.service.demand;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paris.lutece.plugins.crmclient.service.ICRMClientService;
import fr.paris.lutece.plugins.crmclient.util.CRMException;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * The demand type service
 */
public class DemandTypeService
{
    ObjectMapper _mapper = new ObjectMapper( );

    @Inject
    ICRMClientService _crmClientService;

    /**
     * Get a the remote demand types as Map of String,String
     * 
     * @return a map of key:demandTypeId, value:demandTypeLabel
     * @throws CRMException
     * @throws IOException
     */
    public Map<String, String> getDemandTypes( ) throws CRMException, IOException
    {
        String strResponse = _crmClientService.getCRMDemandTypes( );
        DemandType [ ] tabDemandTypes = _mapper.readValue( strResponse, DemandType [ ].class );
        List<DemandType> listDemandTypes = Arrays.asList( tabDemandTypes );
        return listDemandTypes.stream( ).collect(
                Collectors.toMap( demandType -> Integer.toString( demandType.getIdDemandType( ) ), demandType -> demandType.getLabel( ) ) );
    }
}
